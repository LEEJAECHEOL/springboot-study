package com.cos.myjpa.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.myjpa.domain.post.Post;
import com.cos.myjpa.domain.post.PostRepository;
import com.cos.myjpa.domain.user.User;
import com.cos.myjpa.web.post.dto.PostRespDto;
import com.cos.myjpa.web.post.dto.PostSaveReqDto;
import com.cos.myjpa.web.post.dto.PostUpdateReqDto;

import lombok.RequiredArgsConstructor;

// JPA 영속성 컨텍스트는 변경감지를 하는데 언제하느냐 ? 서비스 종료시에 함!!

@RequiredArgsConstructor
@Service
public class PostService {
	private final PostRepository postRepository;
	private final EntityManager em; // 영속화 할 수 있음
	
	@Transactional
	public Post 저장하기(PostSaveReqDto postSaveReqDto, User principal) {
		Post post = postSaveReqDto.toEntity();
		post.setUser(principal);
		return postRepository.save(post);// => 실패 Exception을 탄다.
	}
	
	@Transactional(readOnly = true) // 변경감지 안함
	public PostRespDto 한건가져오기(Long id) {
		// 없으면 throw를 날림 낙아채서 쓸수 있음 -> exhandler참고
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
//		// 옵셔널 get()  : 있든 없든 가져와.
//		postRepository.findById(1L).get();
//		// 있으면 가져오고 없으면 빈객체를 던저라
//		postRepository.findById(1L).orElseGet(()->{
//			return new Post();
//		});
		
//		postEntity.getUser().getUsername();
		
		PostRespDto postRespDto = new PostRespDto(postEntity);
		return postRespDto;
	}
	
	@Transactional(readOnly = true) // 변경감지 안함 고립성
	public List<Post> 전체찾기() {
		List<Post> postEntity = postRepository.findAll();
		return postEntity;
	}
	
	@Transactional
	public Post 수정하기(Long id, PostUpdateReqDto postUpdateReqDto) {
//		Post p = new Post();
//		em.persist(p);
//		em.createNativeQuery("select * from post");
		
		// 영속화
		// 1. id에 해당하는 것 찾기
		Post postEntity = postRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("id를 찾을 수 없습니다.");
		});
		postEntity.setTitle(postUpdateReqDto.getTitle());
		postEntity.setContent(postUpdateReqDto.getContent());
		
		return postEntity;
	} // 트랜잭션(서비스) 종료시 영속 컨텍스트에 영속화되어있는 모든 객체의 변경을 감지하여 변경된 아이들을 flush한다.commit = 더티채킹
	
	@Transactional
	public void 삭제하기(Long id) {
		postRepository.deleteById(id); 
	}

}
