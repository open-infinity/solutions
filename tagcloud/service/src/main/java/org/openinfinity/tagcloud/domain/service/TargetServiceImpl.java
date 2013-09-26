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
package org.openinfinity.tagcloud.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.openinfinity.tagcloud.domain.entity.Profile;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.NearbyTarget;
import org.openinfinity.tagcloud.domain.entity.query.Recommendation;
import org.openinfinity.tagcloud.domain.entity.query.TagQuery;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class TargetServiceImpl extends
		AbstractTextEntityCrudServiceImpl<Target> implements TargetService {

	@Autowired
	private TargetRepository targetRepository;

	@Autowired
	private TagService tagService;

	@Autowired
	private ProfileService profileService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RecommendationService recommendationService;

	@Override
	public List<Target> searchLike(String input) {
		return targetRepository.searchLike(input);
	}
	
	@Override
	public List<Target> searchFromTags(String tag) {
		return targetRepository.searchFromTags(tag);
	}	
	
	@Override
	public List<Target> searchAll() {
		return targetRepository.searchAll();
	}
  
	@Override
	public Collection<Target> loadByTag(Tag tag) {
		return targetRepository.loadByTag(tag);
	}

    @Override
	public List<Recommendation> loadByQuery(TagQuery tagQuery) {
		List<Target> targets = targetRepository.loadByCoordinates(tagQuery.getLongitude(), tagQuery.getLatitude(), tagQuery.getRadius());
		List<Recommendation> results = requireTags(targets, tagQuery);
		if(tagQuery.getNearby().size()>0) {
			results = nearbySearch(targetRepository.loadByCoordinates(tagQuery.getLongitude(), tagQuery.getLatitude(), tagQuery.getRadius() +NearbyTarget.MAX_DISTANCE), results, tagQuery.getNearby());
		}
        if(tagQuery.getPreferred().size() > 0){
            checkPreferredTags(results, tagQuery);
        }
        for(Recommendation recommendation : results){
            recommendationService.updateRecommendationScore(recommendation, tagQuery.getUser(), tagQuery.getFriendFacebookIds());
        }
		return results.subList(0, Math.min(15, results.size()));
	}


    private void checkPreferredTags(List<Recommendation> results, TagQuery tagQuery) {
        for(Recommendation result:results){
            for(Tag tag : tagQuery.getPreferred()){
                if(result.getTarget().getTags().contains(tag))
                    result.getPreferredTags().add(tag);
            }
        }
    }

    private List<Recommendation> requireTags(List<Target> targets, TagQuery tagQuery) {
        List<Tag> requiredTags = tagQuery.getRequired();
        List<Tag> nearbyTags = tagQuery.getNearby();

		List<Recommendation> results = new ArrayList<Recommendation>();
		//if(requiredTags.size()==0) return results; //return empty list because required tags should not be empty. fix later with exception?
		for(Target target : targets) {
			if(target.getTags().containsAll(requiredTags)) {
				Recommendation result = new Recommendation(tagQuery, nearbyTags);
				result.setTarget(target);
				result.getRequiredTags().addAll(requiredTags);
				results.add(result);
			}
		}
		return results;
	}

	private List<Recommendation> nearbySearch(List<Target> allTargets,
			List<Recommendation> results, List<Tag> nearbyTags) {

		for (Target target : allTargets) {
			List<Tag> foundNearbyTags = new ArrayList<Tag>();
			for (Tag tag : nearbyTags) {
				if (target.getTags().contains(tag))
					foundNearbyTags.add(tag);
			}
			if (foundNearbyTags.size() == 0)
				continue;

			ListIterator<Recommendation> resultIterator = results.listIterator(0);
			while (resultIterator.hasNext()) {
				Recommendation result = resultIterator.next();
				for (Tag tag : foundNearbyTags) {
					NearbyTarget nearbyTarget = result.getNearbyTargetsMap()
							.get(tag.getText());
					double distance = calcRelativeDistance(result.getTarget(),
							target);
					if (distance < nearbyTarget.getDistance()) {
						nearbyTarget.setTarget(target);
						nearbyTarget.setDistance(distance);
					}
				}
				result.updateNearbyTargetList();
			}
		}

		// update correct absolute distances and remove ones too far away
		ListIterator<Recommendation> resultIterator = results.listIterator(0);
		while (resultIterator.hasNext()) {
			Recommendation result = resultIterator.next();

			for (NearbyTarget nearbyTarget : result.getNearbyTargetsList()) {
				nearbyTarget.setDistance(calcDistance(result.getTarget(),
						nearbyTarget.getTarget()));
				if (nearbyTarget.getDistance() > NearbyTarget.MAX_DISTANCE) {
					resultIterator.remove();
					break;
				}
			}

		}

		return results;
	}

	private double calcRelativeDistance(Target t1, Target t2) {
		double lonDif = Math.abs(t1.getLocation()[0] - t2.getLocation()[0]);
		if (lonDif > 180)
			lonDif -= 180;
		double latDif = Math.abs(t1.getLocation()[0] - t2.getLocation()[0]);
		if (latDif > 180)
			latDif -= 180;

		return Math.sqrt(lonDif * lonDif + latDif * latDif);
	}

	private double calcDistance(Target t1, Target t2) {
		return Utils.calcDistanceGCS(t1.getLocation()[0], t1.getLocation()[1],
				t2.getLocation()[0], t2.getLocation()[1]);
	}

}
