package com.cos.myjpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.cos.myjpa.web.user.dto.UserRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	
	@Transactional(readOnly = true) // 변경감지 안함 고립성
	public List<UserRespDto> 전체찾기() {
		List<User> usersEntity = userRepository.findAll();
		
//		List<UserRespDto> userRespDtos = new ArrayList<>();
//		for (User user : usersEntity) {
//			userRespDtos.add(new UserRespDto(user));
//		}
//		List<User> usersEntity = userRepository.findAll();
//		// 1. foreach, stream().forEach
//		List<UserRespDto> userRespDtos = new ArrayList<>();
//		for (User user : usersEntity) {
//			userRespDtos.add(new UserRespDto(user));
//		}
//		usersEntity.stream().forEach((u)->{
//			userRespDtos.add(new UserRespDto(u));
//		});
		// 2 stream().map
		 List<UserRespDto> userRespDtos = usersEntity.stream().map(v->{
	         return new UserRespDto(v);
	      }).collect(Collectors.toList());
		return userRespDtos;
	}
	
	@Transactional(readOnly = true) // 변경감지 안함 고립성
	public UserRespDto 한건찾기(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		
		UserRespDto userRespDto = new UserRespDto(userEntity);
		return userRespDto;
	}
	
	@Transactional(readOnly = true) // 변경감지 안함 고립성
	public User 프로파일(Long id) {
		User userEntity = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		return userEntity;
	}
	
	
	@Transactional(readOnly = true) // 변경감지 안함 고립성
	public User 로그인(UserLoginReqDto userLoginReqDto) {
		User userEntity = userRepository.findByUsernameAndPassword(userLoginReqDto.getUsername(), userLoginReqDto.getPassword());
		return userEntity;
	}
	
	// 읽는게 아니라 쓰기이기때문에 트랜잭션을 발동시켜야함. 동시접근 못하게 할려고. 서비스안에서 두가지의 일을하는데 하나라도 실패하면 롤백된다.
	@Transactional
	public User 회원가입(UserJoinReqDto userJoinReqDto) {
		User userEntity = userRepository.save(userJoinReqDto.toEntity());
		return userEntity;
	}

}
