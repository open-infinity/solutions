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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.exception.ApplicationException;
import org.openinfinity.core.exception.BusinessViolationException;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.Target;
import org.openinfinity.tagcloud.domain.entity.query.NearbyTarget;
import org.openinfinity.tagcloud.domain.entity.query.Result;
import org.openinfinity.tagcloud.domain.repository.TargetRepository;
import org.openinfinity.tagcloud.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Product service implementation with specification.
 * 
 * @author Ilkka Leinonen
 */
@Service
public class TargetServiceImpl implements TargetService {

	@Autowired
	private TargetSpecification targetSpecification;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private TargetRepository targetRepository;
	
	@Log
	@AuditTrail
	@Override
	@Transactional
	public Target create(Target entity) {
		Collection<Target> entities = targetRepository.loadByText(entity.getText());
		if (targetSpecification.isNotEligibleForCreation(entity, entities)) {
			ExceptionUtil.throwApplicationException(
				"Entity already exists: " + entity.getText(), 
				ExceptionLevel.INFORMATIVE, 
				TargetService.UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS);
		}
		targetRepository.create(entity);
		return entity;
	}
	
	@Log
	@AuditTrail
	@Override
	@Transactional
	public void update(Target entity) {
		if (targetRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwBusinessViolationException(
				"Entity does not exist: " + entity.getText(), 
				ExceptionLevel.ERROR, 
				TargetService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		targetRepository.update(entity);
	}
	
	@Override
	public Collection<Target> loadAll() {
		return targetRepository.loadAll();
	}
	
	@Log
	@AuditTrail
	@Override
	public Target loadById(BigInteger id) {
		Target entity = targetRepository.loadById(id);
		if (entity == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + id, 
				ExceptionLevel.WARNING, 
				TargetService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		return entity; 
	}
	
	@Log
	@AuditTrail
	@Override
	@Transactional
	public void delete (Target entity) {
		if (targetRepository.loadById(entity.getId()) == null) {
			ExceptionUtil.throwApplicationException(
				"Entity does not exist: " + entity.getId(), 
				ExceptionLevel.INFORMATIVE, 
				TargetService.UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST);
		}
		targetRepository.delete(entity);
	}

	@Log
	@AuditTrail
	@Override
	@Transactional
	public void addTagToTarget(Tag tag, Target target) {
		for (Tag oldTag : target.getTags()) {
			if(oldTag.getText().equals(tag.getText())) 
				ExceptionUtil.throwBusinessViolationException(
						"Tag with the same name already exists in the target", 
						ExceptionLevel.INFORMATIVE,
						TargetService.UNIQUE_EXCEPTION_TAG_ALREADY_INCLUDED);
		}
		
		if(!tagService.contains(tag)) 
			tag = tagService.create(tag);
		
		target.getTags().add(tag);
		update(target);
	}
	
	@Log
	@AuditTrail
	@Override
	@Transactional
	public void removeTagFromTarget(Tag tag, Target target) {
		if(!target.getTags().contains(tag))
			ExceptionUtil.throwApplicationException(
					"Target does not contain the tag that is to be removed", 
					ExceptionLevel.WARNING,
					TargetService.UNIQUE_EXCEPTION_TAG_NOT_INCLUDED);
		
		target.getTags().remove(tag);
		update(target);
		
		//fixme
//		tag.getTargets().remove(target);
//		if(tag.getTargets().size() > 0) tagService.update(tag);
//		else tagService.delete(tag);
	}

	@Override
	public boolean contains(Target target) {
		if(target==null || target.getId()==null) return false;
		try {
			loadById(target.getId());
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Target> loadByTag(Tag tag) {
		return targetRepository.loadByTag(tag);
	}

	@Override
	public List<Result> loadByQuery(List<Tag> requiredTags, List<Tag> preferredTags, List<Tag> nearbyTags, 
			double longitude, double latitude, double radius) {
		
		List<Target> targets = targetRepository.loadByCoordinates(longitude, latitude, radius);
		System.out.println(targets.size());
		List<Result> results = requireTags(targets, requiredTags, nearbyTags);
		
		if(nearbyTags.size()>0) {
			results = nearbySearch(targetRepository.loadByCoordinates(longitude, latitude, 2*radius), results, nearbyTags);
		}

		return results;
	}


	private List<Result> requireTags(List<Target> targets, List<Tag> requiredTags, List<Tag> nearbyTags) {
		List<Result> results = new ArrayList<Result>();
		for(Target target : targets) {
			if(target.getTags().containsAll(requiredTags)) {
				Result result = new Result(nearbyTags);
				result.setTarget(target);
				result.getRequiredTags().addAll(requiredTags);
				results.add(result);
			}
		}
		return results;
	}
	
	

	private List<Result> nearbySearch(List<Target> allTargets,
			List<Result> results, List<Tag> nearbyTags) {
		
		for (Target target : allTargets) {
			List<Tag> foundNearbyTags = new ArrayList<Tag>();
			for(Tag tag : nearbyTags) {
				if(target.getTags().contains(tag)) foundNearbyTags.add(tag);
			}
			if(foundNearbyTags.size()==0) continue;
			
			Iterator<Result> resultIterator = results.iterator();
			while(resultIterator.hasNext()) {
				Result result = resultIterator.next();
				for(Tag tag : foundNearbyTags) {
					if(!target.getTags().contains(tag)) continue;
					NearbyTarget nearbyTarget = result.getNearbyTargetsMap().get(tag.getText());
					double distance = calcRelativeDistance(result.getTarget(), target); 
					if(distance < nearbyTarget.getDistance()) {
						nearbyTarget.setTarget(target);
						nearbyTarget.setDistance(distance);
					}
				}
				//remove result if some of the nearby tags can not be found close enough
				for(String tagName : result.getNearbyTargetsMap().keySet()) {
					if(result.getNearbyTargetsMap().get(tagName).getTarget() == null ||
							calcDistance(result.getTarget(), result.getNearbyTargetsMap().get(tagName).getTarget()) > NearbyTarget.MAX_DISTANCE) {
						resultIterator.remove();
						break;
					}
				}
			}
		}
		
		//update correct absolute distances
		for(Result result : results) {
			for(NearbyTarget nearbyTarget : result.getNearbyTargetsList()) {
				nearbyTarget.setDistance(calcDistance(result.getTarget(), nearbyTarget.getTarget()));
			}
		}
		
		return results;
	}

	private double calcRelativeDistance(Target t1, Target t2) {
		double lonDif = Math.abs(t1.getLocation()[0]-t2.getLocation()[0]);
		if(lonDif > 180) lonDif -= 180;
		double latDif = Math.abs(t1.getLocation()[0]-t2.getLocation()[0]);
		if(latDif > 180) latDif -= 180;
		
		return Math.sqrt(lonDif*lonDif + latDif*latDif);
	}
	
	
	private double calcDistance(Target t1, Target t2) {
		return Utils.calcDistanceGCS(t1.getLocation()[0], t1.getLocation()[1], t2.getLocation()[0], t2.getLocation()[1]);
	}
	
	
	
}
