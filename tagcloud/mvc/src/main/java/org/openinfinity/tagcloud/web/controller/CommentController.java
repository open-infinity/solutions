package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.model.CommentModel;
import org.openinfinity.tagcloud.web.model.TargetModel;
import org.openinfinity.tagcloud.web.support.SerializerUtil;
import org.openinfinity.tagcloud.web.support.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	private Validator validator;

	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	ConnectionManager conn_manager;
	
	@Log
	@ExceptionHandler({SystemException.class, ApplicationException.class, BusinessViolationException.class})
	public void exceptionOccurred(AbstractCoreException abstractCoreException, HttpServletResponse response, Locale locale) {
		TargetModel targetModel = new TargetModel();
		if (abstractCoreException.isErrorLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(abstractCoreException.getErrorLevelExceptionIds(), locale);
			targetModel.addErrorStatuses("errorLevelExceptions", localizedErrorMessages);
		}
		if (abstractCoreException.isWarningLevelExceptionMessagesIncluded())  {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(abstractCoreException.getWarningLevelExceptionIds(), locale);
			targetModel.addErrorStatuses("warningLevelExceptions", localizedErrorMessages);
		}
		if (abstractCoreException.isInformativeLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(abstractCoreException.getInformativeLevelExceptionIds(), locale);
			targetModel.addErrorStatuses("informativeLevelExceptions", localizedErrorMessages);
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		SerializerUtil.jsonSerialize(ServletUtil.getWriter(response), targetModel.getErrorStatuses());
	}
	
	private Collection<String> getLocalizedExceptionMessages(Collection<String> localizedExceptionIds, Locale locale) {
		Collection<String> localizedErrorMessages = new ArrayList<String>();
		for (String uniqueId : localizedExceptionIds) {
			String message = applicationContext.getMessage(uniqueId, null, locale);
			localizedErrorMessages.add(message);	
		}
		return localizedErrorMessages;
	}
	
	
	@Log
	@AuditTrail(argumentStrategy=ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET)
	public String createNewComment(Model model) {
		model.addAttribute("commentModel", new CommentModel());
		return "createComment";
	}
	
	@Log
	@AuditTrail(argumentStrategy=ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value="/list/{target_id}")
	public @ResponseBody List<Comment> loadAllComments(@PathVariable("target_id") String target_id) {
		List<Comment> comments = this.getFakeCommentList();
	
		return comments;
	}

	private Map<String, String> getValidationMessages(
			Set<ConstraintViolation<CommentModel>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<CommentModel> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}
	
	private List<Comment> getFakeCommentList(){
		List<Comment> comments = new LinkedList<Comment>();
		
		for(int i = 0; i <20; i++){
			Comment comment = new Comment();
			Profile profile = new Profile();
			profile.setFacebookId("kavan.sole");
			comment.setProfile(profile);
			comment.setText("la bala bla ajhdl basdkl bakljdbsjkl blajbsdlkj " +
					"blsjbasdlkj lasjkhdlskjhd baldkjdklj blkajsdjkl agjhdgdjhqweytu!");
			comment.setId("k"+i);
			comments.add(comment);
		}
		return comments;
	}
}
