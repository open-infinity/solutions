package org.openinfinity.tagcloud.web.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.openinfinity.tagcloud.domain.entity.Target;

public class TargetModel {
	
	private Map<String, Collection<String>> errorStatuses = new HashMap<String, Collection<String>>();
/*
	private BigInteger id;
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
*/




	private String text;
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}





	private double latitude;
	private double longitude;
	
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
	

	

	public void addErrorStatuses(String level, Collection<String> ids) {
		errorStatuses.put(level, ids);
	}

	public Map<String, Collection<String>> getErrorStatuses() {
		return errorStatuses;
	}

	public void setErrorStatuses(Map<String, Collection<String>> errorStatuses) {
		this.errorStatuses = errorStatuses;
	}
/*
	@Override
	public String toString() {
		return "TargetModel [target=" + target + ", errorStatuses="
				+ errorStatuses + ", toString()=" + super.toString() + "]";
	}
*/
	public Target getTarget() {
		Target target = new Target();
		//target.setId(super.getId());
		target.setLocation(longitude, latitude);
		target.setText(text);
		return target;
	}

}
