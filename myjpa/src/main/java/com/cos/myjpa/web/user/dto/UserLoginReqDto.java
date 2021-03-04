package com.cos.myjpa.web.user.dto;

import com.cos.myjpa.domain.user.User;

import lombok.Data;

@Data
public class UserLoginReqDto {
	private String username;
	private String password;
	
	public User toEntity() { // 지금 필요 없음.
		return User.builder()
				.username(username)
				.password(password)
				.build();
	}
}
