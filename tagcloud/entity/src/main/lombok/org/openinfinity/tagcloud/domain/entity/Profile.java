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

import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Profile implements Entity {

	@Id
	private String id;
	
	@NonNull
	private String facebookId;
	
	private Map<String, Set<Tag>> myTags = new HashMap<String, Set<Tag>>(); //string = target.id
	
	private List<String> myScoredTargets = new ArrayList<String>();
	
	public void addTag(Tag tag, Target target) {
		System.out.println(tag);
		if(myTags.containsKey(target.getId())) {
			myTags.get(target.getId()).add(tag);
			System.out.println("contains");
		}
		else {
			Set<Tag> tags = new HashSet<Tag>();
			tags.add(tag);
			myTags.put(target.getId(), tags);
			System.out.println("new");
		}
	}

	@Override
	public String toString() {
		return "Profile, id="+id;
	}
	
}