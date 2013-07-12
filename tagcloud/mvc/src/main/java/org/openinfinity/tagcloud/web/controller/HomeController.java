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

import java.util.*;

import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.Result;
import org.openinfinity.tagcloud.domain.entity.TagQuery;
import org.openinfinity.tagcloud.domain.repository.ProfileRepository;
import org.openinfinity.tagcloud.domain.repository.ScoreRepository;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.openinfinity.tagcloud.domain.service.TargetService;
import org.openinfinity.tagcloud.domain.service.testdata.TestDataGenerator;
import org.openinfinity.tagcloud.utils.Utils;
import org.openinfinity.tagcloud.web.model.SearchModel;
import org.openinfinity.tagcloud.web.model.TagModel;
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
	private TargetService targetService;
	
	@Autowired
	private Validator validator;

	@Autowired
	private TestDataGenerator testDataGenerator;

	@Autowired
	private TagRepository tagRepository;
	@Autowired
	private TargetRepository targetRepository;
	@Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ScoreRepository scoreRepository;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("searchModel", new SearchModel());
		model.addAttribute("targetModel", new TargetModel());
		LOGGER.error("home get");
		return "home";
	}

	@Log
	@AuditTrail(argumentStrategy = ArgumentStrategy.ALL)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Map<String, ? extends Object> create(@RequestBody SearchModel searchModel) {
		List<Tag> required = new ArrayList<Tag>();
		List<Tag> preferred = new ArrayList<Tag>();
		List<Tag> nearby = new ArrayList<Tag>();
		
		for(TagModel tagModel : searchModel.getRequired()) {
			required.add(tagService.loadById(tagModel.getId()));
		}
		for(TagModel tagModel : searchModel.getPreferred()) {
			preferred.add(tagService.loadById(tagModel.getId()));
		}
		for(TagModel tagModel : searchModel.getNearby()) {
			nearby.add(tagService.loadById(tagModel.getId()));
		}

        double radius = Utils.calcDistanceGCS(searchModel.getBounds()[0], searchModel.getBounds()[1],
                searchModel.getBounds()[2], searchModel.getBounds()[3])/2;
		List<Result> results = targetService.loadByQuery(
                new TagQuery(required, preferred, nearby, searchModel.getLocation()[0], searchModel.getLocation()[1], radius));

        Collections.sort(results);
		return new ModelMap("results", results);
	}

	@RequestMapping(method = RequestMethod.GET, value="reset")
	public String resetDB() {
		tagRepository.dropCollection();
		targetRepository.dropCollection();
        profileRepository.dropCollection();
        scoreRepository.dropCollection();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        List<Target> targets = testDataGenerator.generate();
        LOGGER.info("Created "+targets.size()+" targets:");

        for(Target target : targets){
            String s = target.getText();
            if(target.getTags().size()>0){
                s += " (";
                for(Tag tag : target.getTags()){
                    s += tag.getText()+", ";
                }
                s = s.substring(0, s.length()-2);
                s += ")";
            }

            LOGGER.info(s);
        }
		return "redirect:/";
	}



	
//	//kavan test home
//	@RequestMapping(method = RequestMethod.GET)
//	public @ResponseBody String resetTagDB() {
//		
//
//		return "welcome to tagclod!";
//	}
	

}
