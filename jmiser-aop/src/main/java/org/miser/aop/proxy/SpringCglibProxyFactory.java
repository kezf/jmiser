package org.miser.aop.proxy;

import org.miser.aop.aspects.Aspect;
import org.miser.aop.interceptor.SpringCglibInterceptor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 基于Spring-cglib的切面代理工厂
 *
 * @author Oliver
 *
 */
public class SpringCglibProxyFactory extends ProxyFactory{
	private static final long serialVersionUID = 1L;

	@Override
	@SuppressWarnings("unchecked")
	public <T> T proxy(T target, Aspect aspect) {
		final Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(new SpringCglibInterceptor(target, aspect));
		return (T) enhancer.create();
	}

}
