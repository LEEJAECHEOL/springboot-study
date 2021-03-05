package com.cos.myjpa.domain.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.myjpa.domain.user.User;
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
public class Post {
	@Id // PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Table, auto_increment, Sequence
	private Long id;
	@Column(length = 60, nullable = false)
	private String title;
	@Lob // 대용량 데이터
	private String content;
	
	// 누가 적었는지 ?
	// FetchType.EAGER 빨리, FetchType.LAZY 느리게
	// 즉시 가져오는 건 조인이고 느리게 가져오는건 셀렉트로 가져옴.
	// 한건 만가져올 떄는 괜찮음 근데 이반대 상황에는 LAZY전략이 좋음. -> user를 호출할 때 가져옴.
	// 순방향 매핑
	@ManyToOne(fetch = FetchType.EAGER) // 연관관계 맺는 법. FK의 주인인 곳에서 적어야 됨.
	@JoinColumn(name = "userId")
//	@JsonIgnoreProperties({"posts"}) 
	private User user; // 데이터베이스에는 FK가 들어감.
	
	@CreationTimestamp // 자동으로 현재시간이 들어감.
	private LocalDateTime createDate;
}
