package org.openinfinity.tagcloud.web.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import lombok.NonNull;

import org.openinfinity.tagcloud.domain.entity.Comment;
import org.openinfinity.tagcloud.domain.entity.Profile;

/**
 * 
 * @author Kavan Soleimanbeigi
 *
 */

public class CommentModel {

	private Map<String, Collection<String>> errorStatuses = new HashMap<String, Collection<String>>();
	
	@NonNull
	@Size(min=1,max=200)
	private String text;

	@NonNull
	private Profile profile;
	
	public Comment getTarget() {
		Comment comment = new Comment();
		comment.setText(text);
		comment.setProfile(profile);
		
		return comment;
	}
	
	public void addErrorStatuses(String level, Collection<String> ids) {
		errorStatuses.put(level, ids);
	}
	
	public Map<String, Collection<String>> getErrorStatuses() {
		return errorStatuses;
	}


	public void setErrorStatuses(Map<String, Collection<String>> errorStatuses) {
		this.errorStatuses = errorStatuses;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}


