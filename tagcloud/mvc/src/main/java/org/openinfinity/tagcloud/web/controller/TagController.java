package org.openinfinity.tagcloud.web.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.repository.TagRepository;
import org.openinfinity.tagcloud.domain.service.TagService;
import org.openinfinity.tagcloud.utils.Utils;
import org.openinfinity.tagcloud.web.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		private TagRepository tagRepository;
		
		@RequestMapping(method = RequestMethod.GET, value="autocomplete")
		public @ResponseBody Collection<TagModel> getAutocompleteSuggestions(@RequestParam(value="q") String q) {
			List<TagModel> tagModels = new ArrayList<TagModel>();
			LOGGER.error("hmph "+tagService.loadAll().size());
			for(Tag tag : tagService.loadAll()) {//searchLike(q)) {
				tagModels.add(new TagModel(tag.getId().toString(), tag.getText()));
			}
			return tagModels;
		}
		
		@RequestMapping(method = RequestMethod.GET, value="reset")
		public String resetTagDB() {
			tagRepository.dropCollection();
			for(Tag tag : Utils.createList(new Tag("outdoor"), new Tag("bar"), new Tag("gym"), new Tag("shop"),
					new Tag("bus station"), new Tag("statue"))) {
				tagService.create(tag);
			}
			return "redirect:/";
		}
			
}

