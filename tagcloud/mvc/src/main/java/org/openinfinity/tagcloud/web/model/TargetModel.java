package org.openinfinity.tagcloud.web.model;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openinfinity.tagcloud.domain.entity.Target;

public class TargetModel extends Target{
private Target target;
	
	private Map<String, Collection<String>> errorStatuses = new HashMap<String, Collection<String>>();
	
	public void addErrorStatuses(String level, Collection<String> ids) {
		errorStatuses.put(level, ids);
	}
	
	public Map<String, Collection<String>> getErrorStatuses() {
		return errorStatuses;
	}

	public void setErrorStatuses(Map<String, Collection<String>> errorStatuses) {
		this.errorStatuses = errorStatuses;
	}

	@Override
	public String toString() {
		return "TargetModel [target=" + target + ", errorStatuses="
				+ errorStatuses + ", toString()=" + super.toString() + "]";
	}
	
	public Target getTarget() {
		Target target = new Target();
		target.setId(super.getId());
		target.setLocation(super.getLocation());
		target.setName(super.getName());
		target.setTags(super.getTags());
		target.setScores(super.getScores());
		target.setComments(super.getComments());
		return target;
	}
	
}
