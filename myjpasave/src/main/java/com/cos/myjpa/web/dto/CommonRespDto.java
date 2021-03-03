package com.cos.myjpa.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonRespDto<T> {
	private int statusCode; // 1 정상, -1 실패
	private String msg; // 오류내용
	private T data;
}
