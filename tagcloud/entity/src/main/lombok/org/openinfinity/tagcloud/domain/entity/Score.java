package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Score implements Entity {

	@Id
	private String id;
	
	@NonNull
	private Integer stars;

	@Override
	public String toString() {
		return "Score, id="+id+", stars="+stars;
	}
}