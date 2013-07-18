package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.core.exception.AbstractCoreException;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.exception.SystemException;
import org.openinfinity.tagcloud.domain.entity.Comment;
import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.CommentService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.connection.entity.ResponseObject;
import org.openinfinity.tagcloud.web.model.CommentModel;
import org.openinfinity.tagcloud.web.model.TargetModel;
import org.openinfinity.tagcloud.web.support.SerializerUtil;
import org.openinfinity.tagcloud.web.support.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @author Kavan Soleimanbeigi
 * 
 */

@Controller
@RequestMapping(value = "/comment")
public class CommentController {

	@Autowired
	private TargetRepository targetRepository;

	@Autowired
	private TargetService targetService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private Validator validator;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ConnectionManager connectionMnager;

	@Log
	@ExceptionHandler({ SystemException.class, ApplicationException.class,
			BusinessViolationException.class })
	public void exceptionOccurred(AbstractCoreException abstractCoreException,
			HttpServletResponse response, Locale locale) {
		TargetModel targetModel = new TargetModel();
		if (abstractCoreException.isErrorLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getErrorLevelExceptionIds(), locale);
			targetModel.addErrorStatuses("errorLevelExceptions",
					localizedErrorMessages);
		}
		if (abstractCoreException.isWarningLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getWarningLevelExceptionIds(), locale);
			targetModel.addErrorStatuses("warningLevelExceptions",
					localizedErrorMessages);
		}
		if (abstractCoreException.isInformativeLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getInformativeLevelExceptionIds(),
					locale);
			targetModel.addErrorStatuses("informativeLevelExceptions",
					localizedErrorMessages);
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		SerializerUtil.jsonSerialize(ServletUtil.getWriter(response),
				targetModel.getErrorStatuses());
	}

	private Collection<String> getLocalizedExceptionMessages(
			Collection<String> localizedExceptionIds, Locale locale) {
		Collection<String> localizedErrorMessages = new ArrayList<String>();
		for (String uniqueId : localizedExceptionIds) {
			String message = applicationContext.getMessage(uniqueId, null,
					locale);
			localizedErrorMessages.add(message);
		}
		return localizedErrorMessages;
	}

	
	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value = "/list/{target_id}")
	public @ResponseBody
	ResponseObject<Comment> loadAllComments(@PathVariable("target_id") String target_id) {
		ResponseObject<Comment> result = new ResponseObject<Comment>();
		List<Comment> comments = targetService.loadById(target_id)
				.getComments();
		result.setSuccess(comments);
		return result;
	}

	/**
	 * Refactoring needed
	 * 
	 * @param target_id
	 * @param text
	 * @return
	 */
	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.POST, value = "/{target_id}")
	public @ResponseBody
	ResponseObject<String> submitComment(
			@PathVariable("target_id") String target_id,
			@RequestParam("text") String text, HttpServletRequest request) {

		ResponseObject<String> responseObject = new ResponseObject<String>();
		Target target = targetService.loadById(target_id);
		CommentModel commentModel = new CommentModel();
		commentModel.setText(text);

		Set<ConstraintViolation<CommentModel>> failures = validator
				.validate(commentModel);

		try {
			if (!connectionMnager.isUserLoggedIn(request.getSession().getId())) {
				throw new Exception("user is not logged in!");
			}
			if (!failures.isEmpty()) {
				throw new Exception();
			}
			if (target == null) {
				throw new Exception("target can't be null!");
			}
			Facebook facebook = connectionMnager.getSessionFacebook(request.getSession().getId());

			commentService.addCommentToTarget(commentModel.getText(), target,
					facebook.userOperations().getUserProfile().getId());

			responseObject.setSuccess("Comment saved successfully!",
					commentModel.getText());

		} catch (Exception ex) {

			responseObject.setIs_error(true);
			responseObject.setStatus(HttpServletResponse.SC_BAD_REQUEST + "");
			responseObject.setMessage("Comment can't be accepted");
			responseObject.setError_code("comment_error");
			responseObject.addErrorReason("Error: " + ex.getMessage());
			for (ConstraintViolation<CommentModel> e : failures) {
				responseObject.addErrorReason("Error: " + e.getMessage());
			}
			if (target == null) {
				responseObject.addErrorReason("Error: no Target found by Id:["
						+ target_id + "]");
			}
		}

		return responseObject;
	}

	
}
