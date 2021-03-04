package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Table, auto_increment, Sequence
	private Long id;
	private String username;
	private String password;
	private String email;

	@CreationTimestamp // 자동으로 현재시간이 들어감.
	private LocalDateTime createDate;
	
	// FK를 만들고 싶은게 아님.
	// 내가 user를 select 할 때 모든 글을 보고싶을때
//	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 난 연관관계의 주인이 아니야, 그러니깐 FK만들지마.
	// 왜 LAZY를 사용하는가? 내가 안들고 오고 싶을 때도 있기때문.
//	private List<Post> posts;
	
}
