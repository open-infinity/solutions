package org.openinfinity.tagcloud.domain.service;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.openinfinity.core.annotation.AuditTrail;
import org.openinfinity.core.annotation.Log;
import org.openinfinity.core.exception.ExceptionLevel;
import org.openinfinity.core.util.ExceptionUtil;
import org.openinfinity.tagcloud.domain.entity.Entity;
import org.openinfinity.tagcloud.domain.entity.TextEntity;
import org.openinfinity.tagcloud.domain.repository.AbstractCrudRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


public abstract class AbstractTextEntityCrudServiceImpl<T extends TextEntity> extends AbstractCrudServiceImpl<T> implements AbstractTextEntityCrudServiceInterface<T>{

	@Override
	public Collection<T> loadByText(String text) {
		return repository.loadByText(text);
	}
	

	@Log
	@AuditTrail
	@Override
	public boolean contains(T entity) {
		if(entity==null || entity.getText().trim().length() == 0) return false;
		try {
            if(loadByText(entity.getText()).size() == 0) return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	
}
