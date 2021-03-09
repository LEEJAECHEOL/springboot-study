package com.cos.myjpa.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.handler.ex.MyAuthenticationException;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		
		// 인터페이스에서 default가 붙혀져 있으면 강제성이 부여되지 않는다.
		registry.addInterceptor(new HandlerInterceptor() {
			// 컨트롤 실행 직전에 동작
			// 반환값이 true일 경우 정상적 진행
			// 반환값이 false일 경우 컨트롤러 진입 안함.
			@Override
			public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
					throws Exception {
				HttpSession session = request.getSession();
				
				User principal = (User)session.getAttribute("principal");
				
				if(principal == null) {
					throw new MyAuthenticationException();
//					return false;
				}else {
					return true;
				}
			}
		}).addPathPatterns("/user");
	}
}
