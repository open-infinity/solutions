package org.openinfinity.tagcloud.web.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfinity.tagcloud.web.connection.CachedRequest;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Kavan Soleimanbeigi
 */
@Controller
public class LoginController {

	@Value("${facebook.client_id}")
	private String client_id;
	@Value("${facebook.client_secret}")
	private String client_secret;
	@Value("${facebook.namespace}")
	private String app_namespace;
	@Value("${facebook.auth_scope}")
	String auth_scope;
	@Value("${webapp.session_cookie_name}")
	private String session_cookieKey;
	@Value("${facebook.auth_redirect_link}")
	private String auth_redirect_link;
	String oauthurl = "";

	private ConnectionManager cmanager = new ConnectionManager();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = "/fb-login", method = RequestMethod.GET)
	public @ResponseBody
	String authTest(HttpServletResponse response) {
		String authurl = createAuthUrl(), error = "";
		oauthurl = authurl;
		try {
			response.sendRedirect(authurl);
		} catch (Exception e) {
			LOGGER.error(e.toString());
			error = e.toString();
		}
		return "sorry! " + error;
	}

	@RequestMapping(value = "/check-login", method = RequestMethod.GET)
	public @ResponseBody
	List<String> loginTest(HttpServletRequest req) {
		List<String> logList = new LinkedList<String>();
		if (cmanager.isUserLoggedIn(req.getSession().getId())) {
			logList.add("you're logged in");
		} else {
			logList.add("you're not logged in");
		}

		return logList;
	}

	@RequestMapping(value = "/redx", method = RequestMethod.GET)
	public @ResponseBody
	List<String> redTest(HttpServletRequest req, HttpServletResponse response) {
		List<String> logList = new LinkedList<String>();

		if (cmanager.isUserLoggedIn(req.getSession().getId())) {
			CachedRequest cache = cmanager.retrieveCachedRequest(req
					.getSession().getId());
			logList.add("now, let's do our thing!");

			if (cache != null) {

				logList.add("your cached query string: "
						+ cache.getQuerryString());
				logList.add("your cached uri was: " + cache.getRequestURI());
				logList.add("your cached url was: " + cache.getRequestURL());

			} else {

				logList.add("your query string was: " + req.getQueryString());
				logList.add("your uri was: " + req.getRequestURI());
				logList.add("your url was: " + req.getRequestURL());
			}

		} else {
			try {
				cmanager.cacheThisRequest(req);
				response.sendRedirect("/tagcloud/fb-login");
			} catch (Exception ex) {
				logList.add("redirect to login page failed!");
			}
		}

		return logList;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest req) {

		cmanager.logout(req.getSession().getId());
		return "redirect:/check-login";
	}

	@RequestMapping(value = "/auth-response", method = RequestMethod.GET)
	public @ResponseBody
	List<String> auth_response(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> logList = new LinkedList<String>();
		String auth_code = getFacebookAuthorizationCode(request, logList);
		if (auth_code != null
				&& createFacebookSession(request, auth_code, logList)) {
			logList.add(oauthurl);
			logList.add("Facebook login session created successfully!");

		} else {
			logList.add("creating facebook session failed!");
		}
		try {
			if (cmanager.isRedirectNeeded(request.getSession().getId())) {
				cmanager.redirectToOriginal(request, response, logList);
			}

		} catch (Exception e) {
			logList.add("redirect to redx failed");

		}
		return logList;
	}

	private String getFacebookAuthorizationCode(HttpServletRequest req,
			List<String> logList) {

		if (req.getParameter("error") == null
				&& req.getParameter("code") != null) {

			logList.add("login ok!");

			return req.getParameter("code");

		} else {
			logList.add("error: " + req.getParameter("error"));
			logList.add("error_code: " + req.getParameter("error_code"));
			logList.add("error_description: "
					+ req.getParameter("error_description"));
			logList.add("error_reason: " + req.getParameter("error_reason"));
			return null;
		}
	}

	@RequestMapping(value = "/update-status", method = RequestMethod.GET)
	public @ResponseBody
	List<String> postToWall(HttpServletRequest request,
			HttpServletResponse response) {
		String message = request.getParameter("message");
		List<String> logList = new LinkedList<String>();
		if (cmanager.isUserLoggedIn(request.getSession().getId())) {
			try {
				Facebook facebook = cmanager.getFacebook(request.getSession()
						.getId(), client_id, client_secret);
				if (message != null && !message.equalsIgnoreCase("")) {
					facebook.feedOperations().updateStatus(message);
					logList.add(" Your status update, " + message
							+ " posted to your facbook wall successfully!");
				} else {
					logList.add("status update message should not be null or empty");
				}

			} catch (Exception e) {
				logList.add(e.toString());
			}
		} else {
			cmanager.cacheThisRequest(request);
			try {
				response.sendRedirect("/tagcloud/fb-login");
			} catch (Exception e) {
				logList.add("sorry, rquired login failed!");
			}
			// logList.add("sorry, you should login first");
		}
		return logList;
	}

	private String createAuthUrl() {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
				client_id, client_secret);

		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.add("scope", auth_scope);
		params.setRedirectUri(auth_redirect_link);
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, params);

		return authorizeUrl;
	}

	private boolean createFacebookSession(HttpServletRequest request,
			String auth_code, List<String> logList) {
		String session_id = request.getSession().getId();
		try {

			FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
					client_id, client_secret);
			OAuth2Operations oauth2 = connectionFactory.getOAuthOperations();
			AccessGrant accessGrant = oauth2.exchangeForAccess(auth_code,
					auth_redirect_link, null);
			cmanager.login(session_id, accessGrant);
			return true;

		} catch (Exception e) {
			logList.add(e.toString() + " ---> " + e.getMessage());
			return false;
		}
	}

}
