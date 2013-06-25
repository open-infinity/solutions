package org.openinfinity.tagcloud.domain.entity;

import java.io.Serializable;

import org.openinfinity.tagcloud.domain.service.AbstractSpecification;

public interface Entity<ID_TYPE> extends Serializable {
	public ID_TYPE getId();
}
