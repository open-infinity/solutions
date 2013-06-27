package org.openinfinity.tagcloud.web.controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/*
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
	String oauthurl = "";

	public final static HashMap<String, AccessGrant> session_map = new HashMap<String, AccessGrant>();

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
		String session_id = null;
		if (isUserLoggedIn(req.getCookies(), session_map)) {
			logList.add("you're logged in");
		} else {
			logList.add("you're not logged in");
		}

		return logList;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest req) {

		logout(req, session_map);
		return "redirect:/check-login";
	}

	@RequestMapping(value = "/auth-response", method = RequestMethod.GET)
	public @ResponseBody
	List<String> auth_response(WebRequest req, HttpServletResponse response,
			HttpServletRequest req2) {
		List<String> logList = new LinkedList<String>();
		String result = "", auth_code = getFacebookAuthorizationCode(req,
				logList);
		if (auth_code != null
				&& createFacebookSession(response, auth_code, logList)) {
			logList.add(oauthurl);
			logList.add("Facebook login session created successfully!");
		} else {
			logList.add("creating facebook session failed!");
		}
		return logList;
	}

	private String getFacebookAuthorizationCode(WebRequest req,
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
	List<String> postToWall(HttpServletRequest req,
			HttpServletResponse response) {
		String message = req.getParameter("message");
		List<String> list = new LinkedList<String>();
		if (isUserLoggedIn(req.getCookies(), session_map)) {
			try {
				AccessGrant accessGrant = null;
				for (Cookie cookie : req.getCookies()) {
					if (cookie.getName().equalsIgnoreCase(session_cookieKey)) {
						accessGrant = session_map.get(cookie.getValue());
						break;
					}
				}
				Connection<Facebook> connection = new FacebookConnectionFactory(
						client_id, client_secret).createConnection(accessGrant);
				Facebook facebook = connection.getApi();
				if (message != null && !message.equalsIgnoreCase("")){
					facebook.feedOperations().updateStatus(message);
					list.add(" Your status update, " + message +
							" posted to your facbook wall successfully!");
				}else{
					list.add("status update message should not be null or empty");
				}
	
			} catch (Exception e) {
				list.add(e.toString());
			}
		} else {
			list.add("sorry, you should login first");
		}
		return list;
	}

	private String createAuthUrl() {
		FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
				client_id, client_secret);

		OAuth2Operations oauthOperations = connectionFactory
				.getOAuthOperations();
		OAuth2Parameters params = new OAuth2Parameters();
		params.add("scope", auth_scope);
		params.setRedirectUri("http://www.solxiom.info/tagcloud/auth-response");
		String authorizeUrl = oauthOperations.buildAuthorizeUrl(
				GrantType.AUTHORIZATION_CODE, params);

		return authorizeUrl;
	}

	private boolean createFacebookSession(HttpServletResponse response,
			String auth_code, List<String> logList) {
		String session_id = UUID.randomUUID().toString();
		try {

			FacebookConnectionFactory connectionFactory = new FacebookConnectionFactory(
					client_id, client_secret);
			OAuth2Operations oauth2 = connectionFactory.getOAuthOperations();
			AccessGrant accessGrant = oauth2.exchangeForAccess(auth_code,
					"http://www.solxiom.info/tagcloud/auth-response", null);
			session_map.put(session_id, accessGrant);
			response.addCookie(new Cookie("tagcloud_session_id", session_id));
			return true;

		} catch (Exception e) {
			logList.add(e.toString() + " ---> " + e.getMessage());
			return false;
		}
	}

	private boolean isUserLoggedIn(Cookie[] cookies,
			HashMap<String, AccessGrant> map) {
		String session_id = null;
		if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(session_cookieKey)) {
					session_id = cookie.getValue();
				}
			}

			if (session_id != null && map.containsKey(session_id)
					&& map.get(session_id) != null) {
				return true;

			}
		}
		return false;
	}

	private void logout(HttpServletRequest req, HashMap<String, AccessGrant> map) {
		Cookie[] cookies = req.getCookies();
		String session_id = null;
		if (cookies != null) {

			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase(session_cookieKey)) {
					session_id = cookie.getValue();
					cookie.setValue(null);
				}
			}
		}

		if (session_id != null && map.containsKey(session_id)) {
			map.remove(session_id);
		}
	}

}
