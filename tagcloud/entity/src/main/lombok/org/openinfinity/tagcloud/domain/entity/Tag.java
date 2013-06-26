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
public class Tag implements Entity<BigInteger> {

	@Id
	private BigInteger id;

	@NonNull
	private String text;

	@Override
	public String toString() {
		return "Tag, id="+id+", text="+text;
	}
	
	
}