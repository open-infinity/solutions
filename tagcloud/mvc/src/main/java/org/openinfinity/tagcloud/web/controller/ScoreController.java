package org.openinfinity.tagcloud.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.ScoreService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.connection.entity.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/score")
public class ScoreController {
	
	@Autowired
	private TargetRepository targetRepository;

	@Autowired
	private TargetService targetService;
	
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private Validator validator;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ConnectionManager connectionManager;
	
	/**
	 * Refactoring needed
	 * 
	 * @param target_id
	 * @param text
	 * @return
	 */
	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.POST, value = "{target_id}")
	public @ResponseBody
	ResponseObject<String> scoreTarget(
			@PathVariable("target_id") String target_id,
			@RequestParam("score") String score, HttpServletRequest request) {
		ResponseObject<String> responseObject = new ResponseObject<String>();
		int sc = Integer.parseInt(score);
		Target target = targetService.loadById(target_id);
		String facebookId = connectionManager.getSessionFacebook(request.getSession().getId()).userOperations().getUserProfile().getId();
		scoreService.scoreTarget(sc, target, facebookId);
		targetService.update(target);
		responseObject.setSuccess("score successful");
		return responseObject;
	}

}
