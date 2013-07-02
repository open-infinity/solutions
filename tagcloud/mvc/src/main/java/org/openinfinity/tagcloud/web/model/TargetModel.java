package org.openinfinity.tagcloud.web.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openinfinity.core.annotation.NotScript;
import org.openinfinity.tagcloud.domain.entity.Target;


import javax.validation.constraints.Size;


public class TargetModel {
	
	private Map<String, Collection<String>> errorStatuses = new HashMap<String, Collection<String>>();

	@NotScript
	@Size(min=2,max=50)
	private String text;

	private double latitude;
	private double longitude;
	

	public Target getTarget() {
		Target target = new Target();
		//target.setId(super.getId());
		target.setLocation(longitude, latitude);
		target.setText(text);
		return target;
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


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	
}
