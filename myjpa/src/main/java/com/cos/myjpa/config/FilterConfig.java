package com.cos.myjpa.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.cos.myjpa.filter.MyAuthenticationFilter;


// web.xml

@Configuration // 설정관련 어노테이션이다.
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<MyAuthenticationFilter> authenticationFilterRegister() {
		// 필터객체 생성
		FilterRegistrationBean<MyAuthenticationFilter> bean = new FilterRegistrationBean<>(new MyAuthenticationFilter());
		bean.addUrlPatterns("/test");
		bean.setOrder(1); // 낮은 번호가 필터중에서 가장 먼저 실행됨.
		// Bean은 리턴해야 등록됨.
		return bean;
	}
}
