package org.openinfinity.tagcloud.domain.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.openinfinity.tagcloud.domain.service.AbstractSpecification;
import org.openinfinity.tagcloud.domain.service.TagSpecification;
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

	private List<Target> targets = new ArrayList<Target>();
	
	@Override
	public String toString() {
		return "Tag, id="+id+", text="+text;
	}
	
	
}