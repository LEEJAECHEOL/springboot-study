package com.cos.myjpa.web.post.dto;

import java.time.LocalDateTime;

import com.cos.myjpa.domain.post.Post;

import lombok.Data;

@Data
public class PostRespDto {

	private Long id;
	private String title;
	private String content;
	private LocalDateTime createDate;
	
	public PostRespDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createDate = post.getCreateDate();
	}

}
