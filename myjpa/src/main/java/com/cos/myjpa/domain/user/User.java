package com.cos.myjpa.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User { // User 1 <-> Post N
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Table, auto_increment, Sequence
	private Long id;
	private String username;
	private String password;
	private String email;
	@CreationTimestamp // 자동으로 현재시간이 들어감.
	private LocalDateTime createDate;
	
	// 테이블에 영향을 미치면 안됨. 유저에서 포스트데이터를 가져오기 위할 뿐.
	// mappedBy는 연관관계가 있는 Post의 user변수(FK)를 적는다. -> 나는 FK의 주인이 아니다. FK는 user변수이다.
	// 역방향 매핑
	@JsonIgnoreProperties({"user"})
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 데이터를 가져올 때 N개이면 LAZY
	private List<Post> post;

//	@Transient // DB에 컬럼이 안만들어짐
//	private int value;
	
}
