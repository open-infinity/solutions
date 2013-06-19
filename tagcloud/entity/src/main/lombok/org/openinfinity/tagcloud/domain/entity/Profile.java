package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = { "id" })
public class Profile implements Serializable {
	
	private Collection<Tag> tags;
	
	private Collection<Comment> comments;
	
	@NonNull
	private Collection<String> groups;
	
	@NonNull
	private String id;

}