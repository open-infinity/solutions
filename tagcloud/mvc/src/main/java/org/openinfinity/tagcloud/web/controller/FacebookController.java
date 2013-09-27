package org.openinfinity.tagcloud.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.connection.entity.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.social.facebook.api.ImageType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/facebook")
public class FacebookController {
	
	//private static final Logger LOGGER = Logger.getLogger(FacebookController.class);

	@Autowired
	ConnectionManager connectionManager;

	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value = "/profile/{fb_id}")
	public @ResponseBody
	ResponseObject<FacebookProfile> facebookProfile(
			@PathVariable("fb_id") String fb_id, HttpServletRequest request) {
		//LOGGER.debug("*** facebookProfile");
		ResponseObject<FacebookProfile> obj = new ResponseObject<FacebookProfile>();
		Facebook facebook = connectionManager.getPublicFacebook();
		obj.setSuccess("succuss",
				facebook.userOperations().getUserProfile(fb_id));
		return obj;

	}

	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value = "/user/profile")
	public @ResponseBody
	ResponseObject<FacebookProfile> userFacebookProfile(
			HttpServletRequest request) {
		//LOGGER.debug("*** userFacebookProfile");
		ResponseObject<FacebookProfile> obj = new ResponseObject<FacebookProfile>();
		Facebook facebook = connectionManager.getSessionFacebook(request
				.getSession().getId());
		if (facebook != null) {
			obj.setSuccess("succuss", facebook.userOperations()
					.getUserProfile());
		} else {
			obj.setIs_error(true);
			obj.setError_code("login_error");
			obj.setMessage("no user logged in");
			obj.setStatus(HttpServletResponse.SC_BAD_REQUEST + " bad request");
		}
		return obj;

	}
	@RequestMapping("/user/photo")
	public ResponseEntity<byte[]> userProfilePhoto(HttpServletRequest request) throws IOException {
		Facebook facebook = connectionManager.getSessionFacebook(request.getSession().getId());
		byte[] profile_photo = facebook.userOperations().getUserProfileImage(ImageType.LARGE);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(profile_photo, headers,
				HttpStatus.CREATED);
	}
	@RequestMapping("/photo/{fb_id}")
	public ResponseEntity<byte[]> profilePhoto(
			@PathVariable("fb_id") String fb_id) throws IOException {
		Facebook facebook = connectionManager.getPublicFacebook();
		byte[] profile_photo = facebook.userOperations().getUserProfileImage(
				fb_id, ImageType.LARGE);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		return new ResponseEntity<byte[]>(profile_photo, headers,
				HttpStatus.CREATED);
	}

}
