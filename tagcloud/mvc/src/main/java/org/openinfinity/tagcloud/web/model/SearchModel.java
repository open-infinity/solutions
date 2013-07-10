package org.openinfinity.tagcloud.web.model;


public class SearchModel {
	private TagModel[] required;
	private TagModel[] preferred;
	private TagModel[] nearby;
	private double[] location;
	private double[] bounds; //{CE lng, CE lat, NE lng, NE lat}
	
	public TagModel[] getRequired() {
		return required;
	}
	public void setRequired(TagModel[] required) {
		this.required = required;
	}
	public TagModel[] getPreferred() {
		return preferred;
	}
	public void setPreferred(TagModel[] preferred) {
		this.preferred = preferred;
	}
	public TagModel[] getNearby() {
		return nearby;
	}
	public void setNearby(TagModel[] nearby) {
		this.nearby = nearby;
	}
	public double[] getLocation() {
		return location;
	}
	public void setLocation(double[] location) {
		this.location = location;
	}

    public double[] getBounds() {
        return bounds;
    }

    public void setBounds(double[] bounds) {
        this.bounds = bounds;
    }
}
