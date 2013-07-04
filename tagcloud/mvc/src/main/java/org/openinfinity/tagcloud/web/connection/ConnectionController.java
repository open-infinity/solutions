package org.openinfinity.tagcloud.web.connection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfinity.tagcloud.web.connection.exception.InvalidConnectionCredentialException;
import org.openinfinity.tagcloud.web.connection.exception.NullAccessGrantException;
import org.openinfinity.tagcloud.web.connection.exception.NullActiveConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Kavan Soleimanbeigi
 */
@Controller
public class ConnectionController {

	@Value("${facebook.client_id}")
	private String client_id;
	@Value("${facebook.client_secret}")
	private String client_secret;
	@Value("${facebook.namespace}")
	private String app_namespace;
	@Value("${facebook.auth_scope}")
	String auth_scope;
	@Value("${facebook.auth_redirect_link}")
	private String auth_redirect_link;
	@Value("${facebook.logout_redirect_link}")
	private String logout_redirect_link;
	@Value("${webapp.default_redirect_path}")
	private String default_redirect_path;
	@Autowired
	private ConnectionManager connection_manager;
	// *** start c p area
	private final String check_connection_path = "/check/connection";
	private final String connect_path = "/connect";
	private final String login_path = "/login";

	@RequestMapping(value = login_path, method = RequestMethod.GET)
	public String authTest(HttpServletRequest request,
			HttpServletResponse response) {

		if (connection_manager.isUserLoggedIn(request.getSession().getId())) {
			return "redirect:" + check_connection_path;
		}
		return "redirect:" + connect_path;
	}

	@RequestMapping(value = connect_path, method = RequestMethod.GET)
	public @ResponseBody
	List<String> FacebookConnect(HttpServletRequest request,
			HttpServletResponse response) {

		List<String> logList = new LinkedList<String>();
		logList.add("Connecting...");
		ConnectionCredential credential = this
				.buildDefaultConnectionCredential();
		try {
			connection_manager.setConnectionCredential(credential);
			connection_manager.connect(request, response);
			logList.add("Facebook login session created successfully!");
		} catch (InvalidConnectionCredentialException e) {
			logList.add("connection failed > check your credentials > "
					+ e.toString());
		} catch (NullAccessGrantException e) {
			logList.add("connection failed > check facebook AccessGrant > "
					+ e.toString());
		} catch (NullActiveConnectionException e) {
			logList.add("connection failed > check web app ActiveConnection Object >  "
					+ e.toString());
		} catch (Exception e) {
			logList.add("connection failed >  " + e.toString());
		}
		logList.addAll(connection_manager.getConnectionLog());
		return logList;
	}

	@RequestMapping(value = check_connection_path, method = RequestMethod.GET)
	public @ResponseBody
	List<String> loginTest(HttpServletRequest req) {
		List<String> logList = new LinkedList<String>();
		if (connection_manager.isUserLoggedIn(req.getSession().getId())) {
			logList.add("you're logged in, ");
			Facebook facebook = connection_manager.getSessionFacebook(req
					.getSession().getId());
			List<FacebookProfile> profs = facebook.friendOperations()
					.getFriendProfiles();
			for (FacebookProfile p : profs) {
				logList.add(" " + p.getFirstName() + " " + p.getLastName()
						+ " about:" + p.getAbout());
			}

		} else {
			logList.add("you're not logged in");
		}
		logList.add("Session_id: " + req.getSession().getId());
		// logList.addAll(cmanager.getConnectionLog());

		return logList;
	}

	@RequestMapping(value = "/redx", method = RequestMethod.GET)
	public @ResponseBody
	List<String> redTest(HttpServletRequest req, HttpServletResponse response) {
		List<String> logList = new LinkedList<String>();

		CachedRequest login = connection_manager.requireLogin(req, response);
		logList.addAll(connection_manager.getConnectionLog());

		logList.add("now, let's do our thing!");

		if (login != null) {

			logList.add("your cached query string: " + login.getQuerryString());
			logList.add("your cached uri was: " + login.getRequestURI());
			logList.add("your cached url was: " + login.getRequestURL());

		}
		return logList;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(HttpServletRequest req, HttpServletResponse response) {

		String redirect_default = "redirect:" + check_connection_path, redirect = "";

		if (connection_manager.isUserLoggedIn(req.getSession().getId())) {
			redirect = connection_manager.disconnect(req, response, true);
		}
		if (redirect == null || redirect.isEmpty()
				|| redirect.equalsIgnoreCase("/")) {
			return redirect_default + "?k=" + redirect;
		}

		return redirect;

	}

	@RequestMapping(value = "/update-status", method = RequestMethod.GET)
	public @ResponseBody
	List<String> postToWall(HttpServletRequest request,
			HttpServletResponse response) {
		String message;
		List<String> logList = new LinkedList<String>();
		@SuppressWarnings("unchecked")
		Map<String, String[]> mp = request.getParameterMap();
		Set<String> keys = mp.keySet();
		for (String m : keys) {

			logList.add(m + " : " + mp.get(m)[0]);
		}
		CachedRequest login = connection_manager
				.requireLogin(request, response);
		if (login != null) {
			try {

				message = request.getParameter("message");
				Facebook facebook = connection_manager
						.getSessionFacebook(request.getSession().getId());
				if (facebook != null && message != null && !message.isEmpty()) {
					facebook.feedOperations().updateStatus(message);

					logList.add(" Your status update," + message
							+ " posted to your facbook wall successfully!");
				} else {
					logList.add("status update message should not be null or empty, message:"
							+ message
							+ " isFacebook null? "
							+ (facebook == null));
				}

			} catch (Exception e) {
				logList.add(e.toString());
			}
		}
		return logList;
	}

	public ConnectionCredential buildDefaultConnectionCredential() {
		return new ConnectionCredentialBuilder().setClient_id(client_id)
				.setClient_secret(client_secret)
				.setApp_namespace(app_namespace)
				.setAuth_redirect_link(auth_redirect_link)
				.setAuth_scope(auth_scope)
				.setLogout_redirect_link(logout_redirect_link)
				.setDefault_redirect_path(default_redirect_path).build();
	}

	// ** end c p area

}
