package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.core.exception.AbstractCoreException;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.exception.SystemException;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.service.ProfileService;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.connection.entity.ResponseObject;
import org.openinfinity.tagcloud.web.model.TagModel;
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

@Controller
@RequestMapping(value = "/tag")
public class TagController {

	private static final Logger LOGGER = Logger.getLogger(TagController.class);

	@Autowired
	private TagService tagService;

	@Autowired
	private TargetService targetService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private ConnectionManager connectionManager;

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	private Validator validator;

	@Log
	@ExceptionHandler({ SystemException.class, ApplicationException.class,
			BusinessViolationException.class })
	public void exceptionOccurred(AbstractCoreException abstractCoreException,
			HttpServletResponse response, Locale locale) {
		TagModel tagModel = new TagModel();
		if (abstractCoreException.isErrorLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getErrorLevelExceptionIds(), locale);
			tagModel.addErrorStatuses("errorLevelExceptions",
					localizedErrorMessages);
		}
		if (abstractCoreException.isWarningLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getWarningLevelExceptionIds(), locale);
			tagModel.addErrorStatuses("warningLevelExceptions",
					localizedErrorMessages);
		}
		if (abstractCoreException.isInformativeLevelExceptionMessagesIncluded()) {
			Collection<String> localizedErrorMessages = getLocalizedExceptionMessages(
					abstractCoreException.getInformativeLevelExceptionIds(),
					locale);
			tagModel.addErrorStatuses("informativeLevelExceptions",
					localizedErrorMessages);
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		SerializerUtil.jsonSerialize(ServletUtil.getWriter(response),
				tagModel.getErrorStatuses());
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

	@RequestMapping(method = RequestMethod.GET, value = "autocomplete")
	public @ResponseBody
	Collection<TagModel> getAutocompleteSuggestions(
			@RequestParam(value = "q") String q) {
		Set<TagModel> tagModels = new HashSet<TagModel>();
		LOGGER.error("hmph " + tagService.loadAll().size());
		for (Tag tag : tagService.searchLike(q)) {
			tagModels.add(new TagModel(tag.getId().toString(), tag.getText()));
		}
		return tagModels;
	}

	@RequestMapping(method = RequestMethod.GET, value = "reset")
	public String resetTagDB() {
		tagRepository.dropCollection();
		// for(Tag tag : Utils.createList(new Tag("outdoor"), new Tag("bar"),
		// new Tag("gym"), new Tag("shop"),
		// new Tag("bus station"), new Tag("statue"))) {
		// tagService.create(tag);
		// }
		return "redirect:/";
	}

	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value = "/{target_id}")
	public String taghome(Model model,
			@PathVariable("target_id") String target_id) {
		Target target = targetService.loadById(target_id);
		model.addAttribute("tags", target.getTags());
		model.addAttribute("tagModel", new TagModel());
		model.addAttribute("target_id", target.getId());
		return "tagAdd";
	}
	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.GET, value = "json/{target_id}")
	public @ResponseBody
	ResponseObject<Tag> getTargetTags(@PathVariable("target_id") String target_id,
			HttpServletResponse response, HttpServletRequest request) {
		ResponseObject<Tag> result = new ResponseObject<Tag>();
		Target target = targetService.loadById(target_id);
		result.setSuccess(convertTagSetToList(target.getTags()));
		return result;
	}
	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.POST, value = "json/{target_id}")
	public @ResponseBody
	ResponseObject<String> savNewtag(@RequestParam("text") String text,
			@PathVariable("target_id") String target_id,
			HttpServletResponse response, HttpServletRequest request) {
		ResponseObject<String> result = new ResponseObject<String>();
		Set<ConstraintViolation<TagModel>> failures = checkNewTag(text);
		String session_id = request.getSession().getId();
		if (failures.isEmpty() && connectionManager.isUserLoggedIn(session_id)) {
			Target target = targetService.loadById(target_id);
			tagService.addTagToTarget(text, target,
					getSessionFacebookId(session_id));
			targetService.update(target);
			result.setSuccess();
		} else {
			result.setIs_error(true);
			if (!connectionManager.isUserLoggedIn(session_id)) {
				result.setMessage("not logged in");
			} else {
				result.setMessage("not logged in");
			}
			this.setErrorReasons(result, failures);
		}
		return result;
	}

	private String getSessionFacebookId(String session_id) {
		Facebook facebook = connectionManager.getSessionFacebook(session_id);
		return facebook.userOperations().getUserProfile().getId();
	}

	private Set<ConstraintViolation<TagModel>> checkNewTag(String text) {
		TagModel tagModel = new TagModel();
		tagModel.setText(text);
		Set<ConstraintViolation<TagModel>> failures = validator
				.validate(tagModel);
		return failures;
	}

	private Map<String, String> getValidationMessages(
			Set<ConstraintViolation<TagModel>> failures) {
		Map<String, String> failureMessages = new HashMap<String, String>();
		for (ConstraintViolation<TagModel> failure : failures) {
			failureMessages.put(failure.getPropertyPath().toString(),
					failure.getMessage());
		}
		return failureMessages;
	}

	private void setErrorReasons(ResponseObject<String> res,
			Set<ConstraintViolation<TagModel>> failures) {
		for (ConstraintViolation c : failures) {
			res.addErrorReason(c.getPropertyPath().toString() + " ["
					+ c.getMessage() + "]");
		}
	}
	private List<Tag> convertTagSetToList( Set<Tag> tags){
		List<Tag> list = new LinkedList<Tag>();
		Iterator<Tag> iter = tags.iterator();
		while(iter.hasNext()){
			list.add(iter.next());
		}
		return list;
		
	}
}
