package org.openinfinity.tagcloud.web.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
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
	  
	    
	    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	    
	    @RequestMapping(value = "/test-auth", method = RequestMethod.GET)
	    public @ResponseBody String authTest(HttpServletResponse response) {
	        String authurl = createAuthUrl(), error = "";

	        try {
	            response.sendRedirect(authurl);
	        } catch (Exception e) {
	            LOGGER.error(e.toString());
	            error = e.toString();
	        }
	        return "sorry! " + error ;
	    }    
	    @RequestMapping(value = "/auth-response", method = RequestMethod.GET)
	    public @ResponseBody
	    List<String> auth_response(WebRequest req){
	    	List<String> list = new LinkedList<String>();
	    	if(req.getParameter("error") == null){
	    		String result = "all ok!", token = req.getParameter("code");		    	
		    	list.add(result);
		    	list.add(token);
	    	}else{
	    		list.add("error: " + req.getParameter("error") );
	    		list.add("error_code: " + req.getParameter("error_code") );
	    		list.add("error_description: " + req.getParameter("error_description") );
	    		list.add("error_reason: " + req.getParameter("error_reason") );
	    		
	    		
	    	}

	        return list;
	    }
	
	    
	    private String createAuthUrl(){
	        FacebookConnectionFactory connectionFactory =
	                new FacebookConnectionFactory(client_id, client_secret);

	        OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
	        OAuth2Parameters params = new OAuth2Parameters();
	        params.setRedirectUri("http://www.solxiom.info/tagcloud/auth-response");
	        String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);                            

	        return authorizeUrl;
	    }

}

