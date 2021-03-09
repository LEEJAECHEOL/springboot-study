package com.cos.myjpa.web.post;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.domain.user.UserRepository;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class PostControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager entityManager;

	@BeforeEach
	public void init() {
		entityManager.createNativeQuery("ALTER TABLE user ALTER COLUMN id RESTART WITH 1").executeUpdate();
		entityManager.createNativeQuery("ALTER TABLE post ALTER COLUMN id RESTART WITH 1").executeUpdate();
	}
	
	@Test
	public void save_테스트() throws Exception {
		
		User user = new User();
		user.setUsername("ssar");
		user.setPassword("1234");
		user.setEmail("ssar@nate.com");
		userRepository.save(user);
		
		PostSaveReqDto dto = new PostSaveReqDto();
		dto.setTitle("title1");
		dto.setContent("content1");
		
		User principal = new User();
		principal.setId(1L);
		principal.setUsername("ssar");
		principal.setPassword(null);
		principal.setEmail("ssar@nate.com");
		
		String content = new ObjectMapper().writeValueAsString(dto);
		
		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(post("/post")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.sessionAttr("principal", user)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction
				.andExpect(jsonPath("$.data.title").value("title1"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findById_테스트() throws Exception {
		User principal = new User();
		principal.setId(1L);
		principal.setUsername("ssar");
		principal.setPassword(null);
		principal.setEmail("ssar@nate.com");
		userRepository.save(principal);
		
		Post post = new Post();
		post.setTitle("title1");
		post.setContent("content1");
		post.setUser(principal);
		
		postRepository.save(post);
		
		Long id = 1L;

		// when
		ResultActions resultActions = mockMvc.perform(get("/post/{id}",id)
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// 문서에서 mockMvc를 찾아보자
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.title").value("title1"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void findByAll_테스트() throws Exception {
		User principal = new User();
		principal.setId(1L);
		principal.setUsername("ssar");
		principal.setPassword(null);
		principal.setEmail("ssar@nate.com");
		userRepository.save(principal);
		
		Post post = new Post();
		post.setTitle("title1");
		post.setContent("content1");
		post.setUser(principal);
		Post post2 = new Post();
		post2.setTitle("title1");
		post2.setContent("content1");
		post2.setUser(principal);
		
		postRepository.save(post);
		postRepository.save(post2);
		
		// when
		ResultActions resultActions = mockMvc.perform(get("/post")
				.accept(MediaType.APPLICATION_JSON_UTF8));
		
		// 문서에서 mockMvc를 찾아보자
		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.[0].title").value("title1"))
			.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void update_테스트() throws Exception {
		User principal = new User();
		principal.setId(1L);
		principal.setUsername("ssar");
		principal.setPassword(null);
		principal.setEmail("ssar@nate.com");
		userRepository.save(principal);
		
		Post post = new Post();
		post.setTitle("title1");
		post.setContent("content1");
		post.setUser(principal);
		postRepository.save(post);
		
		PostUpdateReqDto postUpdateReqDto = new PostUpdateReqDto();
		postUpdateReqDto.setTitle("updatedTitle");
		postUpdateReqDto.setContent("updatedContent");
		
		Long id = 1L;
		String content = new ObjectMapper().writeValueAsString(postUpdateReqDto);
		
		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(put("/post/{id}", id)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction
				.andExpect(jsonPath("$.data.title").value("updatedTitle"))
				.andDo(MockMvcResultHandlers.print());
		
	}
	
	@Test
	public void delete_테스트() throws Exception {
		User principal = new User();
		principal.setId(1L);
		principal.setUsername("ssar");
		principal.setPassword(null);
		principal.setEmail("ssar@nate.com");
		userRepository.save(principal);
		
		Post post = new Post();
		post.setTitle("title1");
		post.setContent("content1");
		post.setUser(principal);
		postRepository.save(post);
		
		Long id = 1L;
		
		// when(테스트 실행)
		ResultActions resultAction = mockMvc.perform(delete("/post/{id}", id)
				.accept(MediaType.APPLICATION_JSON_UTF8));

		// then (검증)
		resultAction
				.andDo(MockMvcResultHandlers.print());
		
	}

}
