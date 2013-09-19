package org.openinfinity.tagcloud.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public class Score implements Entity {

	@Id
	private String id;
	
	private int stars;

	@NonNull
	private Profile profile;

	@Override
	public String toString() {
		return "Score, id="+id+", stars="+stars;
	}
}