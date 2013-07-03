package org.openinfinity.tagcloud.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

@Service
public class GenericAutowireService {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public <T> T resolve(Class<T> beanType, Class<?> callingClass){
		Class<?> entityClass = GenericTypeResolver.resolveTypeArgument(callingClass, callingClass.getSuperclass());
		List<T> beans = new ArrayList<T>((Collection<? extends T>) applicationContext.getBeansOfType(beanType).values());
	
		for(T beanAlternative : beans) {
			if(GenericTypeResolver.resolveTypeArgument(beanAlternative.getClass(), beanType).equals(entityClass)){
				return beanAlternative;
			}
		}

		throw new NoSuchBeanDefinitionException(beanType);
	}
	
}
