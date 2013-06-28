package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
public class Target implements Entity<BigInteger> {

	@Id
	private BigInteger id;

	@NonNull
	private String text;

	//@DBRef
	private List<Tag> tags = new ArrayList<Tag>();

	private List<Score> scores = new ArrayList<Score>();

	private List<Comment> comments = new ArrayList<Comment>();

	@GeoSpatialIndexed(bits=30, collection="target")
	private double[] location = new double[2];
	

	
	public void setLocation(double longitude, double latitude) {
		location[0] = longitude;
		location[1] = latitude;
	}
	
	@Override
	public String toString() {
		return "Target, id="+id+", text="+text;
	}
}