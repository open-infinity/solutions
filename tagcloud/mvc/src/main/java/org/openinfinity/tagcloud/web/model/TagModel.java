package org.openinfinity.tagcloud.web.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Size;

import org.openinfinity.core.annotation.NotScript;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
public class TagModel {

	private Map<String, Collection<String>> errorStatuses = new HashMap<String, Collection<String>>();

	
	public TagModel() {
	}

	public TagModel(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	private String id;
	
	@NonNull
	@NotScript
	@Size(min=1, max=30)
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagModel other = (TagModel) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}
	
}
