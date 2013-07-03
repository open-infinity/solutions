package org.openinfinity.tagcloud.domain.entity;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
public class Location implements Entity {

	@Id
	private String id;

	private Target target;

	@GeoSpatialIndexed(bits=30)
	private double[] location = new double[2];

	@Override
	public String toString() {
		return "Location, id="+id+", ["+location[0]+", "+location[1]+"]";
	}
}