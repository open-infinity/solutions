package org.openinfinity.tagcloud.web.connection;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfinity.tagcloud.web.connection.exception.InvalidConnectionCredentialException;
import org.openinfinity.tagcloud.web.connection.exception.NullAccessGrantException;
import org.openinfinity.tagcloud.web.connection.exception.NullActiveConnectionException;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;

public interface ConnectionManager {

	public CachedRequest requireLogin(HttpServletRequest request,
			HttpServletResponse response);

	public void connect(HttpServletRequest request, HttpServletResponse response)throws InvalidConnectionCredentialException,
	NullActiveConnectionException, NullAccessGrantException;
	
	public void setConnectionCredential(ConnectionCredential credential)
			throws InvalidConnectionCredentialException;

	public ConnectionCredential getConnectionCredential();

	public List<String> getConnectionLog();

	public AccessGrant getSessionFacebookAccessGrant(String session_id);

	public String disconnect(HttpServletRequest request,
			HttpServletResponse response);

	public String disconnect(HttpServletRequest request,
			HttpServletResponse response, boolean facebook_logout);

	public Facebook getSessionFacebook(String session_id);

	public boolean isUserLoggedIn(String session_id);

}