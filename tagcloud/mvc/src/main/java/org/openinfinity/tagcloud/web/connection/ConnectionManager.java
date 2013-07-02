package org.openinfinity.tagcloud.web.connection;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;

public class ConnectionManager {

	private HashMap<String, ActiveConnection> connection_map;

	public ConnectionManager() {
		this.connection_map = new HashMap<String, ActiveConnection>();
	}

	public Facebook getFacebook(String session_id, String client_id,
			String client_secret) {
		if (session_id == null || (client_id == null || client_id.isEmpty())
				|| (client_secret.isEmpty() || client_secret.isEmpty())) {
			return null;
		}
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn == null || conn.getAccessGrant() == null) {
			return null;
		}
		AccessGrant accessGrant = conn.getAccessGrant(); 
		Facebook facebook;		
		Connection<Facebook> connection = new FacebookConnectionFactory(
				client_id, client_secret).createConnection(accessGrant);
		facebook = connection.getApi();
		return facebook;
	}

	public AccessGrant getFacebookAccessGrant(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null && conn.getAccessGrant() != null) {
			return conn.getAccessGrant();
		}
		return null;
	}

	public void cacheThisRequest(HttpServletRequest request) {
		String session_id = request.getSession().getId();
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn == null) {
			conn = new ActiveConnection();
		}
		conn.setCachedrequest(request);
		this.connection_map.put(session_id, conn);
	}

	public boolean isRedirectNeeded(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		return this.redirectElementsAvalaible(conn);
	}

	public CachedRequest retrieveCachedRequest(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null) {
			CachedRequest cache = conn.getCachedRequest();
			conn.setCachedrequest(null);
			return cache;
		}
		return null;
	}

	/**
	 * demo
	 * 
	 */

	public void redirectToOriginal(HttpServletRequest req,
			HttpServletResponse response, List<String> logList) {

		ActiveConnection conn = this.connection_map.get(req.getSession()
				.getId());

		if (this.redirectElementsAvalaible(conn)) {
			CachedRequest cache = conn.getCachedRequest();

			try {
				response.sendRedirect(cache.getRequestURL() + "?"
						+ cache.getQuerryString());
			} catch (Exception e) {
				logList.add("redirect to original page failed, cause: "
						+ e.toString());
			}
		}
	}

	public boolean isUserLoggedIn(String session_id) {
		if (session_id == null) {
			return false;
		}
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null && conn.getAccessGrant() != null) {
			return true;
		}
		return false;
	}

	public void login(String session_id, AccessGrant accessGrant) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn == null) {
			conn = new ActiveConnection();
		}
		conn.setAccessGrant(accessGrant);
		this.connection_map.put(session_id, conn);
	}

	public void logout(String session_id) {
		if (session_id == null) {
			return;
		}
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null) {
			conn.setAccessGrant(null);
		}

	}

	private boolean redirectElementsAvalaible(ActiveConnection conn) {
		if (conn != null && conn.getCachedRequest() != null) {
			return true;
		}
		return false;
	}

	

}
