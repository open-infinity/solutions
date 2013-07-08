/*
 * Copyright (c) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openinfinity.tagcloud.web.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.openinfinity.tagcloud.domain.service.testdata.TestDataGenerator;
import org.openinfinity.tagcloud.web.model.SearchModel;
import org.openinfinity.tagcloud.web.model.TargetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Handles requests for the application home page.
 * 
 * @author Ilkka Leinonen
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {
	
	//private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	@Autowired
	private TagService tagService;
	
	@Autowired
	private Validator validator;

	@Autowired
	private TestDataGenerator testDataGenerator;

	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TargetRepository targetRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("searchModel", new SearchModel());
		model.addAttribute("targetModel", new TargetModel());

		return "home";
	}

	/*@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> create(@RequestBody SearchModel searchModel) {
		LOGGER.error(searchModel.getRequired()[0].getId());
		return new ModelMap("id", 0);
	}*/

	@RequestMapping(method = RequestMethod.GET, value="reset")
	public String resetDB() {
		tagRepository.dropCollection();
		targetRepository.dropCollection();
		testDataGenerator.generate();
		return "redirect:/";
	}



	

}
