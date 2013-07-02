package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.aspect.ArgumentStrategy;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.openinfinity.tagcloud.web.model.SearchModel;
import org.openinfinity.tagcloud.web.model.TagModel;
import org.openinfinity.tagcloud.web.model.TargetModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/tag")
public class TagController {

		private static final Logger LOGGER = LoggerFactory.getLogger(TagController.class);

		@Autowired
		private TagService tagService;
		
		@RequestMapping(method = RequestMethod.GET, value="autocomplete")
		public @ResponseBody Collection<TagModel> getAutocompleteSuggestions() {
			List<TagModel> tagModels = new ArrayList<TagModel>();
			for(Tag tag : tagService.loadAll()) {
				tagModels.add(new TagModel(tag.getId().toString(), tag.getText()));
			}
			return tagModels;
		}
		
}

