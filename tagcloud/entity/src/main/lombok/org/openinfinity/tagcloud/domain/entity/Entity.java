package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;

import org.openinfinity.tagcloud.domain.specification.AbstractSpecification;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public interface Entity extends Serializable {
	public String getId();
}
