package org.openinfinity.tagcloud.domain.entity;

import java.util.HashSet;
import java.util.Set;

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
@EqualsAndHashCode(exclude = { "id", "likes" })
public class Tag implements TextEntity, Likeable {
	@NonNull
	private String text;

	@Id
	private String id;

	private Set<Like> likes = new HashSet<Like>();
	
	@Override
	public String toString() {
		return "Tag, id="+id+", text="+text;
	}

	@Override
	public void like(Profile profile) {
		likes.add(new Like(profile));
	}
}