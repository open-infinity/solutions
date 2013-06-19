package org.openinfinity.tagcloud.domain.service;

public interface AbstractLocalizedExceptionCrudServiceInterface<T> extends AbstractCrudServiceInterface<T> {

	public static final String UNIQUE_EXCEPTION_ENTITY_ALREADY_EXISTS = "localized.exception.product.already.exists";

	public static final String UNIQUE_EXCEPTION_ENTITY_DOES_NOT_EXIST = "localized.exception.product.does.not.exist";

}
