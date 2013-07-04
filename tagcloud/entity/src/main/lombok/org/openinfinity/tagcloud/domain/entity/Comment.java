package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;

import org.springframework.data.annotation.Id;
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
@EqualsAndHashCode
public class Comment implements TextEntity {

	@Id
	private String id;

	@NonNull
	private String text;

	@NonNull
	private Profile profile;

	@Override
	public String toString() {
		String string = "Comment, id="+id+", profile-id="+profile.getId();
		return string;
	}
	
}