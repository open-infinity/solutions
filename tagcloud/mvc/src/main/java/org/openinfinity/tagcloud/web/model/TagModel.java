package org.openinfinity.tagcloud.web.model;

import java.math.BigInteger;

public class TagModel {

	
	public TagModel() {
	}

	public TagModel(String id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	private String id;
	private String text;
	
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
	
	
}
