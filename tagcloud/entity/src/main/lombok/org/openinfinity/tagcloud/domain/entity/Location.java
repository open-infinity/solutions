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
	private BigInteger id;

	private Target target;

	@GeoSpatialIndexed
	private double[] location = new double[2];

}