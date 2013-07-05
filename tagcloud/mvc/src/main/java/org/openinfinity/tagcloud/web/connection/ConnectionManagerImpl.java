package org.openinfinity.tagcloud.web.connection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfinity.tagcloud.web.connection.config.Config;
import org.openinfinity.tagcloud.web.connection.exception.InvalidConnectionCredentialException;
import org.openinfinity.tagcloud.web.connection.exception.NullAccessGrantException;
import org.openinfinity.tagcloud.web.connection.exception.NullActiveConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

@Service
public class ConnectionManagerImpl implements ConnectionManager {

	private HashMap<String, ActiveConnection> connection_map;
	private List<String> connectionLog;
	private ConnectionCredential credential;
	private LoggingPolicy loggingPolicy;
	@Autowired
	private Config config;

	public ConnectionManagerImpl() {

	}

	@PostConstruct
	public void init() {
		this.connection_map = new HashMap<String, ActiveConnection>();
		credential = config.buildDefaultConnectionCredential();
		connectionLog = new LinkedList<String>();
		this.loggingPolicy = config.getDefaultLoggingPolicy();
	}

	@Override
	public LoggingPolicy getLoggingPolicy() {
		return this.loggingPolicy;
	}

	@Override
	public void setLoggingPolicy(LoggingPolicy policy) {
		this.loggingPolicy = policy;
	}

	@Override
	public CachedRequest requireLogin(HttpServletRequest request,
			HttpServletResponse response) {
		this.cacheThisRequest(request);
		if (!this.isUserLoggedIn(request.getSession().getId())) {			
			try {
				response.sendRedirect(request.getContextPath() + "/connect");
			} catch (Exception e) {
				connectionLog.add("ConnectionManager/requireLogin> error on redirect to /connect path: " + e.toString());
			}
		} 
		return this.retrieveCachedRequest(request.getSession().getId());
		
	}

	@Override
	public void connect(HttpServletRequest request, HttpServletResponse response)
			throws InvalidConnectionCredentialException,
			NullActiveConnectionException, NullAccessGrantException {
		String auth_code = this.getFacebookAuthorizationCode(request);
		if (this.isUserLoggedIn(request.getSession().getId())) {
			this.handlePostConnectionRedirections(request, response);
		}else if (auth_code == null || auth_code.isEmpty()) {
			this.facebook_connect(request, response);
		}else if (auth_code != null && !auth_code.isEmpty()) {
			this.saveNewConnection(request, auth_code);
		}else{
			connectionLog.add("ConnectionManager/connect> confused!");
			return;
		}
	}

	@Override
	public String disconnect(HttpServletRequest request,
			HttpServletResponse response) {
		return this.disconnect(request, response, false);

	}

	private void handlePostConnectionRedirections(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (this.isRedirectNeeded(request.getSession().getId())) {
				this.redirectToOriginal(request, response);
			} else {
				this.redirectToDefault(request, response);
			}

		} catch (Exception e) {
			connectionLog.add("ConnectionManager/handlePostConnectionRedirections >" +
					" Exception on post connection Redirection  "
					+ e.toString());

		}

	}

	/**
	 * disconnect user and create new session,
	 * 
	 * @param conn_map
	 * @param request
	 * @param keep_previous_cachedRequest
	 *            return generated URL for logout from facebook via
	 *            controller(Optional)
	 */

	@Override
	public String disconnect(HttpServletRequest request,
			HttpServletResponse response, boolean facebook_logout) {
		String facebookAccessToken = this.getSessionFacebookAccessGrant(
				request.getSession().getId()).getAccessToken();
		if (this.isUserLoggedIn(request.getSession().getId())) {

			createNewConnectionSession(this.connection_map, request, true);

			if (facebook_logout) {

				return generateFacebookLogouUrl(facebookAccessToken);
			}
		}
		return "/";
	}

	@Override
	public void setConnectionCredential(ConnectionCredential credential)
			throws InvalidConnectionCredentialException {
		this.credential = credential;
		if (!this.isConnectionCredentialValid()) {
			throw new InvalidConnectionCredentialException(
					"empty or invalid credential object! after set the parameter client_id"
							+ credential.getClient_id());
		}

	}

	@Override
	public ConnectionCredential getConnectionCredential() {

		return this.credential;
	}

	@Override
	public List<String> getConnectionLog() {
		if (this.connectionLog == null) {
			return new LinkedList<String>();
		}
		return this.connectionLog;
	}

	@Override
	public AccessGrant getSessionFacebookAccessGrant(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null && conn.getAccessGrant() != null) {
			return conn.getAccessGrant();
		}
		return null;
	}

	@Override
	public Facebook getSessionFacebook(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null && conn.getFacebook() != null
				&& conn.getFacebook().isAuthorized()) {
			return conn.getFacebook();
		} else if (conn != null && conn.getFacebook() != null
				&& !conn.getFacebook().isAuthorized()) {
			conn.setFacebook(null);
		}
		return null;
	}

	@Override
	public boolean isUserLoggedIn(String session_id) {
		if (session_id == null) {
			return false;
		}
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null && conn.getAccessGrant() != null
				&& conn.getFacebook() != null) {
			return true;
		}
		return false;
	}

	private void cacheThisRequest(HttpServletRequest request) {
		String session_id = request.getSession().getId();
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn == null) {
			conn = new ActiveConnection();
		}
		conn.setCachedrequest(new CachedRequestBuilder()
				.buildCachedRequestFromRequest(request));
		this.connection_map.put(session_id, conn);
	}

	private boolean isRedirectNeeded(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		return this.redirectElementsAvalaible(conn);
	}

	private CachedRequest retrieveCachedRequest(String session_id) {
		ActiveConnection conn = this.connection_map.get(session_id);
		if (conn != null) {
			CachedRequest cache = conn.getCachedRequest();
			conn.setCachedrequest(null);
			return cache;
		}
		return null;
	}

	private void redirectToOriginal(HttpServletRequest req,
			HttpServletResponse response) {

		ActiveConnection conn = this.connection_map.get(req.getSession()
				.getId());

		if (this.redirectElementsAvalaible(conn)) {
			CachedRequest cache = conn.getCachedRequest();

			try {
				response.sendRedirect(cache.getRequestURL() + "?"
						+ cache.getQuerryString());
			} catch (Exception e) {
				connectionLog.add("ConnectionManager/redirectToOrginal > redirect to original page failed, cause: "
						+ e.toString());
			}
		}
	}

	private void redirectToDefault(HttpServletRequest req,
			HttpServletResponse response) {

		try {
			response.sendRedirect(req.getContextPath()
					+ credential.getDefault_redirect_path());
		} catch (Exception e) {
			connectionLog.add("ConnectionManager/redirectToDefault > redirect to default page failed, cause: "
					+ e.toString());
		}

	}

	private void facebook_connect(HttpServletRequest request,
			HttpServletResponse response)
			throws InvalidConnectionCredentialException,
			NullActiveConnectionException, NullAccessGrantException {

		String authurl = this.createAuthUrl();
		try {
			response.sendRedirect(authurl);
		} catch (Exception e) {

			connectionLog.add("ConnectionManager/facebook_connect > "
					+ e.toString());
		}

	}

	private void saveNewConnection(HttpServletRequest request, String auth_code)
			throws InvalidConnectionCredentialException,
			NullAccessGrantException, NullActiveConnectionException {
		this.createNewConnectionSession(this.connection_map, request, true);
		ActiveConnection new_connection = this.connection_map.get(request
				.getSession().getId());
		AccessGrant accessGrant = this.createFacebookAccessGrant(auth_code);
		Facebook facebook = createFacebookApi(accessGrant);
		this.login(request.getSession().getId(), new_connection, facebook,
				accessGrant);
	}

	private String generateFacebookLogouUrl(String facebookAccessToken) {

		String url = "/";
		if (facebookAccessToken != null && !facebookAccessToken.isEmpty()) {
			try {

				url = "https://www.facebook.com/logout.php?next="
						+ this.credential.getLogout_redirect_link()
						+ "&access_token=" + facebookAccessToken;
			} catch (Exception e) {

				connectionLog.add("generating facebook logout url failed  "
						+ e.toString());
				url = "/";
			}
		}
		return url;
	}

	/**
	 * remove old connection and create new one can keep the old connection
	 * cache_memory for redirection
	 * 
	 * @param conn_map
	 * @param request
	 * @param keep_previous_cachedRequest
	 */

	private void createNewConnectionSession(
			Map<String, ActiveConnection> conn_map, HttpServletRequest request,
			boolean keep_previous_cachedRequest) {

		ActiveConnection old_connection = this.connection_map.remove(request
				.getSession().getId());
		request.getSession().invalidate();
		ActiveConnection new_connection = new ActiveConnection();
		if (keep_previous_cachedRequest && old_connection != null
				&& old_connection.getCachedRequest() != null) {
			new_connection.setCachedrequest(old_connection.getCachedRequest());
		}
		this.connection_map.put(request.getSession().getId(), new_connection);
	}

	private AccessGrant createFacebookAccessGrant(String auth_code) {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
				this.credential.getClient_id(),
				this.credential.getClient_secret());
		OAuth2Operations oauth2 = connectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauth2.exchangeForAccess(auth_code,
				this.credential.getAuth_redirect_link(), null);
		return accessGrant;
	}

	private void login(String session_id, ActiveConnection conn,
			Facebook facebook, AccessGrant accessGrant)
			throws NullActiveConnectionException, NullAccessGrantException,
			InvalidConnectionCredentialException {
		if (conn == null) {
			throw new NullActiveConnectionException(
					"login need not null ActiveConnection object");
		}
		conn.setAccessGrant(accessGrant);
		conn.setFacebook(facebook);
		this.connection_map.put(session_id, conn);
	}

	private boolean redirectElementsAvalaible(ActiveConnection conn) {
		if (conn != null && conn.getCachedRequest() != null) {
			return true;
		}
		return false;
	}

	private Facebook createFacebookApi(AccessGrant accessGrant)
			throws NullAccessGrantException,
			InvalidConnectionCredentialException {
		if (accessGrant == null) {
			throw new NullAccessGrantException(
					"New FacbookApi cannot be created with Null AccessGrant object");
		}
		String client_id = this.credential.getClient_id();
		String client_secret = this.credential.getClient_secret();

		if ((client_id == null || client_id.isEmpty())
				|| (client_secret.isEmpty() || client_secret.isEmpty())) {
			throw new InvalidConnectionCredentialException(
					"New FacbookApi cannot be created with invalid credentials");
		}
		Connection<Facebook> connection = new FacebookConnectionFactory(
				client_id, client_secret).createConnection(accessGrant);
		return connection.getApi();
	}

	private String getFacebookAuthorizationCode(HttpServletRequest req) {

		if (req.getParameter("error") == null
				&& req.getParameter("code") != null) {
			return req.getParameter("code");
		}
		else if(req.getParameter("error") != null){
		
			this.connectionLog.add("error: " + req.getParameter("error"));
			this.connectionLog.add("error_code: "
					+ req.getParameter("error_code"));
			this.connectionLog.add("error_description: "
					+ req.getParameter("error_description"));
			this.connectionLog.add("error_reason: "
					+ req.getParameter("error_reason"));
			return null;
		}
		return null;
	}

	private String createAuthUrl() {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
				this.credential.getClient_id(),
				this.credential.getClient_secret());

		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.add("scope", this.credential.getAuth_scope());
		params.setRedirectUri(this.credential.getAuth_redirect_link());
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, params);

		return authorizeUrl;
	}

	private boolean isConnectionCredentialValid() {
		if (this.credential == null) {
			return false;
		}
		if (this.credential.getClient_id() == null
				|| this.credential.getClient_id().isEmpty()) {
			return false;
		}
		if (this.credential.getClient_secret() == null
				|| this.credential.getClient_secret().isEmpty()) {
			return false;
		}
		if (this.credential.getApp_namespace() == null
				|| this.credential.getApp_namespace().isEmpty()) {
			return false;
		}
		if (this.credential.getAuth_redirect_link() == null
				|| this.credential.getAuth_redirect_link().isEmpty()) {
			return false;
		}
		if (this.credential.getAuth_scope() == null
				|| this.credential.getAuth_scope().isEmpty()) {
			return false;
		}
		if (this.credential.getLogout_redirect_link() == null
				|| this.credential.getLogout_redirect_link().isEmpty()) {
			return false;
		}

		return true;
	}

}
