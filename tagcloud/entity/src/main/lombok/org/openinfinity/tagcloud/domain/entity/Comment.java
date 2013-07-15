package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Size;

import org.openinfinity.core.annotation.NotScript;
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
public class Comment implements TextEntity, Likeable {

	@Id
	private String id;

	@NonNull
	@NotScript
	@Size(min=1, max=800)
	private String text;

	@NonNull
	private Profile profile;

	private Set<Like> likes = new HashSet<Like>();	

	private final Date date = new Date();
	
	@Override
	public String toString() {
		return "Comment, id="+id +", profile-id="+profile.getId();
	}

	@Override
	public void like(Profile profile) {
		likes.add(new Like(profile));
	}
	
}