package com.cos.myjpa.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.dto.CommonRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {
	
	private final PostRepository postRepository;
	
	@PostMapping("/test/post")
	public CommonRespDto<?> save(@RequestBody PostSaveReqDto postSaveDto) { // title, content
		// 원래는 세션값을 넣어야 함.
		User user = new User(1L, "ssar", "1234", "ssar@nate.com", LocalDateTime.now());
		
		Post postEntity = postRepository.save(postSaveDto.toEntity()); // => 실패 Exception을 탄다.
		postEntity.setUser(user);
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@GetMapping("/test/post")
	public CommonRespDto<?> findAll(){
		List<Post> postEntity = postRepository.findAll();
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@GetMapping("/test/post/{id}")
	public CommonRespDto<?> findById(@PathVariable Long id){
		// null이 될 수 있다.
		// 화살표 함수 get() (orElseThrow 안에 인터페이스가 가지고있는 get()함수 함수가 하나 뿐이라 화살표 함수를 사용함.)
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		return new CommonRespDto<>(1, "성공", postEntity);
	}
	
	@PutMapping("/test/post/{id}")
	public CommonRespDto<?> update(@PathVariable Long id, @RequestBody PostUpdateReqDto postUpdateReqDto){
		// 1. id에 해당하는 것 찾기
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		
		Post postUpdateEntity = postRepository.save(postEntity); // 더티체킹을 사용해야하는데 그러려면 @Service만들어야 가능함.
		
		return new CommonRespDto<>(1, "성공", postUpdateEntity);
	}
	
	@DeleteMapping("/test/post/{id}")
	public CommonRespDto<?> deleteById(@PathVariable Long id){
		postRepository.deleteById(id); 
		return new CommonRespDto<>(1, "성공", null);
	}
	

}
