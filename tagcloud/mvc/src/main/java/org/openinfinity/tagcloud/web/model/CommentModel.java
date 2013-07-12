package org.openinfinity.tagcloud.web.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import lombok.NonNull;

import org.openinfinity.core.annotation.NotScript;
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
	@NotScript
	@Size(min=1, max=800)
	private String text;

	
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

	
}


