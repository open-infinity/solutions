package org.openinfinity.tagcloud.web.connection;

/**
 *
 * @author Kavan Soleimanbeigi
 */
import javax.servlet.http.HttpServletRequest;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.oauth2.AccessGrant;

public class ActiveConnection {

	private AccessGrant accessGrant;
	private CachedRequest cahcedRequest;
	private Facebook facebook;

	public ActiveConnection() {
		this.accessGrant = null;
		this.cahcedRequest = null;

	}

	public AccessGrant getAccessGrant() {
		return accessGrant;
	}

	public void setAccessGrant(AccessGrant accessGrant) {
		this.accessGrant = accessGrant;
	}

	public Facebook getFacebook() {
		return facebook;
	}

	public void setFacebook(Facebook facebook) {
		this.facebook = facebook;
	}

	public CachedRequest getCachedRequest() {
		return cahcedRequest;
	}

	public void setCachedrequest(CachedRequest cachedRequest) {

		this.cahcedRequest = cachedRequest;

	}
	// public void setCachedrequest(HttpServletRequest request) {
	// if (request != null) {
	// this.cahcedRequest = new CachedRequestBuilder()
	// .buildCachedRequestFromRequest(request);
	// } else {
	// this.cahcedRequest = null;
	// }
	// }

}
