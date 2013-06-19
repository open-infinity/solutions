package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;

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
public class Target implements Serializable {

	private Collection<Tag> tags;

	private BigInteger id;

	private Collection<Score> scores;

	@NonNull
	private Location location;

	@NonNull
	private String name;

	private Collection<Comment> comments;

}