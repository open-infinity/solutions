package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.openinfinity.tagcloud.utils.Utils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Profile implements Entity {

	@Id
	private String id;
	
	@NonNull
	private String facebookId;
	
	private Map<String, List<Tag>> myTags = new HashMap<String, List<Tag>>(); //String = target.id
	
	private Set<String> myScoredTargets = new HashSet<String>();

	private Settings settings;
	
	public void addTag(Tag tag, Target target) {
		if(myTags.containsKey(target.getId())) {
			if(!myTags.get(target.getId()).contains(tag))
				myTags.get(target.getId()).add(tag);
		}
		else {
			myTags.put(target.getId(), Utils.createList(tag));
		}
	}
	
	public void addScoredTarget(Target target) {
		myScoredTargets.add(target.getId());
	}
	
	@Override
	public String toString() {
		return "Profile, id="+id;
	}
	
}