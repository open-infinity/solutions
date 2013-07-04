package org.openinfinity.tagcloud.domain.service;

import java.math.BigInteger;
import java.util.Collection;

import org.openinfinity.tagcloud.domain.entity.Entity;
import org.openinfinity.tagcloud.domain.entity.Tag;
import org.openinfinity.tagcloud.domain.entity.TextEntity;

public abstract interface AbstractTextEntityCrudServiceInterface<T extends TextEntity> extends AbstractCrudServiceInterface<T>{
	public Collection<T> loadByText(String text);
}
