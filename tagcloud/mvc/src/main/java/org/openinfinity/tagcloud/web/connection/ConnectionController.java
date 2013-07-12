package org.openinfinity.tagcloud.web.connection;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.service.ProfileService;
import org.openinfinity.tagcloud.web.connection.entity.CachedRequest;
import org.openinfinity.tagcloud.web.connection.entity.LoginObject;
import org.openinfinity.tagcloud.web.connection.entity.ResponseObject;
import org.openinfinity.tagcloud.web.connection.exception.InvalidConnectionCredentialException;
import org.openinfinity.tagcloud.web.connection.exception.NullAccessGrantException;
import org.openinfinity.tagcloud.web.connection.exception.NullActiveConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * 
 * @author: Kavan Soleimanbeigi
 */
@Controller
public class ConnectionController {

	@Autowired
	private ConnectionManager connectionManager;

	@Autowired
	private ProfileService profileService;

	// ***************
	private final String check_connection_path = "/check/connection";
	private final String connect_path = "/connect";
	private final String login_path = "/login";
	private final String logout_path = "/logout";

	@RequestMapping(value = login_path)
	public String authTest(HttpServletRequest request,
			HttpServletResponse response) {
		String next = request.getParameter("next");
		if (next != null && !next.isEmpty()) {
			connectionManager.setRedirectUrl(request, next);
		}
		return "redirect:" + connect_path;
	}

	@RequestMapping(value = logout_path)
	public String doLogout(@Param("facebook") String facebook,
			HttpServletRequest req, HttpServletResponse response) {

		String manager_redirect_url = null;
		if (connectionManager.isUserLoggedIn(req.getSession().getId())) {
			boolean facebook_logout = this.isFacebookLogoutNeeded(facebook);
			manager_redirect_url = connectionManager.disconnect(req, response,
					facebook_logout);

		}
		return "redirect:" + this.getLogoutRedirectURL(manager_redirect_url);

	}

	@RequestMapping(value = connect_path)
	public @ResponseBody
	List<String> FacebookConnect(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> logList = new LinkedList<String>();
		logList.add("Connecting...");
		this.continueToConnection(request, response, logList);
		if (connectionManager.isUserLoggedIn(request.getSession().getId())) {
			Facebook facebook = connectionManager.getSessionFacebook(request
					.getSession().getId());
			String facebookId = facebook.userOperations().getUserProfile()
					.getId();
			profileService.createByFacebookId(facebookId);
		}
		logList.addAll(connectionManager.getConnectionLog());
		return logList;
	}

	@RequestMapping(value = check_connection_path)
	public @ResponseBody
	ResponseObject<LoginObject<Profile>> loginTest(HttpServletRequest req) {
		String session_id = req.getSession().getId();
		List<String> logList = new LinkedList<String>();
		ResponseObject<LoginObject<Profile>> res_obj = new ResponseObject<LoginObject<Profile>>();
		LoginObject<Profile> login = new LoginObject<Profile>();
		if (connectionManager.isUserLoggedIn(session_id)) {
			res_obj.setSuccess("User is logged in");
			Facebook facebook = connectionManager
					.getSessionFacebook(session_id);
			String facebookId = facebook.userOperations().getUserProfile()
					.getId();
			Profile profile = profileService.loadByFacebookId(facebookId);
			login.setLogged_in(true);
			login.setUser_object(profile);

		} else {
			res_obj.setSuccess("User is not logged in");
			login.setLogged_in(false);

		}
		login.setSession_id(session_id);
		res_obj.addResultObject(login);

		return res_obj;
	}

	@RequestMapping(value = "/redx", method = RequestMethod.GET)
	public @ResponseBody
	List<String> redTest(HttpServletRequest req, HttpServletResponse response) {
		List<String> logList = new LinkedList<String>();
		CachedRequest login = connectionManager.requireLogin(req, response);
		logList.addAll(connectionManager.getConnectionLog());

		logList.add("now, let's do our thing!");

		if (login != null) {

			logList.add("your cached query string: " + login.getQuerryString());
			logList.add("your cached uri was: " + login.getRequestURI());
			logList.add("your cached url was: " + login.getRequestURL());

		} else {
			logList.add("ConnectionController/redx> CachedRequest object is null");
		}
		return logList;
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
		CachedRequest login = connectionManager.requireLogin(request, response);
		if (login != null) {
			try {

				message = request.getParameter("message");
				Facebook facebook = connectionManager
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

	private void continueToConnection(HttpServletRequest request,
			HttpServletResponse response, List<String> logList) {
		try {
			connectionManager.connect(request, response);
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
	}

	private boolean isFacebookLogoutNeeded(String facebook_param) {

		if (facebook_param == null || facebook_param.isEmpty()) {
			return false;
		}
		if (facebook_param.equalsIgnoreCase("true")) {
			return true;
		}
		return false;
	}

	private String getLogoutRedirectURL(String manager_redirect_url) {
		String redirect_default = check_connection_path;
		if (manager_redirect_url == null || manager_redirect_url.isEmpty()
				|| manager_redirect_url.equalsIgnoreCase("/")) {
			return redirect_default;
		} else {
			return manager_redirect_url;
		}
	}

	private void getFacebookFriends(HttpServletRequest req, List<String> logList) {
		Facebook facebook = connectionManager.getSessionFacebook(req
				.getSession().getId());
		List<FacebookProfile> profs = facebook.friendOperations()
				.getFriendProfiles();
		for (FacebookProfile p : profs) {
			logList.add(" " + p.getFirstName() + " " + p.getLastName()
					+ " about:" + p.getAbout());
		}
	}

}
