package com.chinalife.datacheck.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.chinalife.datacheck.interceptor.WebReturnValueResolver;

public class ResponseBodyWrapFactoryBean implements InitializingBean {
	@Autowired
	private RequestMappingHandlerAdapter adapter;

	@Override
	public void afterPropertiesSet() throws Exception {
		List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);
		decorateHandlers(handlers);
		adapter.setReturnValueHandlers(handlers);
	}

	private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
		for (HandlerMethodReturnValueHandler handler : handlers) {
			if (handler instanceof RequestResponseBodyMethodProcessor) {
				WebReturnValueResolver decorator = new WebReturnValueResolver(handler);
				int index = handlers.indexOf(handler);
				handlers.set(index, decorator);
				break;
			}
		}
	}
}
