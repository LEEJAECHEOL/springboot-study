package com.cos.myjpa.web.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.user.dto.UserJoinReqDto;
import com.cos.myjpa.web.user.dto.UserLoginReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN id RESTART WITH 1").executeUpdate();
//		entityManager.createNativeQuery("ALTER TABLE user AUTO_INCREMENT = 1").executeUpdate();
	}
	
	@Test
	public void join_테스트() throws Exception {
		UserJoinReqDto dto = new UserJoinReqDto();
		dto.setUsername("ssar");
		dto.setPassword("1234");
		dto.setEmail("ssar@nate.com");
		String content = new ObjectMapper().writeValueAsString(dto);
		
		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/join")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction
				.andExpect(jsonPath("$.data.username").value("ssar"))
				.andDo(MockMvcResultHandlers.print());

	}
	
	@Test
	public void login_테스트() throws Exception {
		User dto = new User();
		dto.setUsername("ssar");
		dto.setPassword("1234");
		dto.setEmail("ssar@nate.com");
		userRepository.save(dto);
		
		UserLoginReqDto loginReqDto = new UserLoginReqDto();
		loginReqDto.setUsername("ssar");
		loginReqDto.setPassword("1234");
		
		String content = new ObjectMapper().writeValueAsString(loginReqDto);
		
		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction
				.andExpect(jsonPath("$.data.username").value("ssar"))
				.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void findAll_테스트() throws Exception {
		User dto = new User();
		dto.setUsername("ssar");
		dto.setPassword("1234");
		dto.setEmail("ssar@nate.com");
		userRepository.save(dto);
		
		// when
		ResultActions resultActions = mockMvc.perform(get("/user")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// 문서에서 mockMvc를 찾아보자
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0].username").value("ssar"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findById() throws Exception {
		Long id = 1L;
		User dto = new User();
		dto.setUsername("ssar");
		dto.setPassword("1234");
		dto.setEmail("ssar@nate.com");
		userRepository.save(dto);
		
		
		// when
		ResultActions resultActions = mockMvc.perform(get("/user/{id}",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// 문서에서 mockMvc를 찾아보자
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.username").value("ssar"))
			.andDo(MockMvcResultHandlers.print());
		
	}
	
	
	@Test
	public void profile_테스트() throws Exception {
		Long id = 1L;
		User dto = new User();
		dto.setUsername("ssar");
		dto.setPassword("1234");
		dto.setEmail("ssar@nate.com");
		userRepository.save(dto);
		// when
		ResultActions resultActions = mockMvc.perform(get("/user/{id}/post",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// 문서에서 mockMvc를 찾아보자
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.username").value("ssar"))
			.andDo(MockMvcResultHandlers.print());
				
	}
	


}
