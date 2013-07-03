package org.openinfinity.tagcloud.domain.entity;

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
@EqualsAndHashCode(exclude = { "id" })
public class Tag implements Entity {


	@NonNull
	private String text;

	@Id
	private String id;
	
	
	@Override
	public String toString() {
		return "Tag, id="+id+", text="+text;
	}


	
	
}