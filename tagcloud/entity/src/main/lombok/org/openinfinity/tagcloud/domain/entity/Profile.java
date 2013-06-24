package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
@EqualsAndHashCode(exclude = { "id" })
public class Profile implements Entity {
	
	private List<Tag> tags = new ArrayList<Tag>();
	
	private List<Comment> comments = new ArrayList<Comment>();
	
	@NonNull
	private List<String> groups = new ArrayList<String>();
	
	@Id @NonNull
	private String id;

}