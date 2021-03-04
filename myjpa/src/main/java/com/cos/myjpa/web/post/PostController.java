package com.cos.myjpa.web.post;

import java.time.LocalDateTime;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;

import lombok.RequiredArgsConstructor;

/**
 * ORM = Object Relation Mapping (자바 오브젝트 먼저 만들고 DB에 테이블을 자동 생성. 자바 오브젝트로 연관관계를 맺을 수 있음.)
 * JPA = Java 오브젝트를 영구적으로 저장하기 위한 인터페이스 (함수)
 *
 */
@RequiredArgsConstructor
@RestController
public class PostController {
	
	private final PostRepository postRepository;
	private final HttpSession session;
	
	@PostMapping("/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveDto) { // title, content
		
		// 원래는 세션값을 넣어야 함.
		// User user = new User(1L, "ssar", "1234", "ssar@nate.com", LocalDateTime.now());
		// 인증이 필요한 것은 공통부분이고 계속 적어주기귀찮기에 AOP를 사용한다.
		// user table에 해당 값이 있어야만 값이 저장이 된다.
		User principal = (User)session.getAttribute("principal");
		if(principal == null) {
			return new CommonRespDto<>(-1, "실패", null);
		}
		
		Post post = postSaveDto.toEntity();
		post.setUser(principal);
		Post postEntity = postRepository.save(post); // => 실패 Exception을 탄다.
		
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@GetMapping("/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){
		
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		
//		postEntity.getUser().getUsername();
		
//		return null;
		// 만약 함수가 POST가 리턴일 때 MessageConverter (Jackson)가 발생하기때문에 리턴할 때 user값을 가져온다.
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
}
