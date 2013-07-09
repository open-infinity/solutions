
package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.core.exception.AbstractCoreException;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.exception.SystemException;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.web.connection.ConnectionManager;
import org.openinfinity.tagcloud.web.model.TargetModel;
import org.openinfinity.tagcloud.web.support.SerializerUtil;
import org.openinfinity.tagcloud.web.support.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/target")
public class TargetController {

		@Autowired
		private TargetRepository targetRepository;
	
		@Autowired
		private TargetService targetService;
		
		@Autowired
		private Validator validator;
		
		@Autowired 
		ApplicationContext applicationContext;
		
		@Autowired 
		ConnectionManager conn_man;
		
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
		public String createNewTarget(Model model) {
			model.addAttribute("targetModel", new TargetModel());
			return "createTarget";
		}
		
		
		@RequestMapping(method = RequestMethod.GET, value="reset")
		public String resetTargetDB(HttpServletRequest request) {
			String session_id = request.getSession().getId();
			
			if(conn_man.isUserLoggedIn(session_id)){
			targetRepository.dropCollection();
			
			return "redirect:/target/loadAll";
			}
			return "redirect:/login";
		}
		
		@Log
		@AuditTrail(argumentStrategy=ArgumentStrategy.ALL)
		@RequestMapping(method = RequestMethod.GET, value="loadAll")
		public String loadAllTargets(Model model) {
			Collection<Target> targets = targetService.loadAll();
			model.addAttribute("targets", targets);
			return "target/listTargets";
		}
		
		
		
		@Log
		@AuditTrail(argumentStrategy=ArgumentStrategy.ALL)
		@RequestMapping(method = RequestMethod.GET, value="{target_id}")
		public @ResponseBody Target showTarget(@PathVariable String target_id) {
			Target target = targetService.loadById(target_id);

			return target;
		}
		
		@Log
		@AuditTrail(argumentStrategy=ArgumentStrategy.ALL) 
		@RequestMapping(method = RequestMethod.POST)
		public @ResponseBody Map<String, ? extends Object> create(@Valid @RequestBody TargetModel targetModel, HttpServletResponse response) {
			Set<ConstraintViolation<TargetModel>> failures = validator.validate(targetModel);
			if (failures.isEmpty()) {
				Target target = targetService.create(targetModel.getTarget());
				return new ModelMap("id", target.getId());
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return getValidationMessages(failures);
			}
		}
	

		private Map<String, String> getValidationMessages(Set<ConstraintViolation<TargetModel>> failures) {
			Map<String, String> failureMessages = new HashMap<String, String>();
			for (ConstraintViolation<TargetModel> failure : failures) {
				failureMessages.put(failure.getPropertyPath().toString(), failure.getMessage());
			}
			return failureMessages;
		}

		
		
}

