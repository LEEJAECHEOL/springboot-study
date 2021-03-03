package com.cos.myiocdi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Component(용도없음), Configuration(설정파일), Service(서비스), Repository(레파지토리), Bean(내부 메서드에 사용)
// Configuration(설정파일), Service(서비스), Repository(레파지토리), Bean들은 Component를 상속한다.

// RestController, Controller -> IoC(싱글톤)등록 (new PostController(주입);)
@RestController
public class PostController {
	
//	@Autowired // DI를 가져올 때 사용하는 어노테이션 지금은 잘 안쓸 것이다.
//	private Robot robot; // DI
	
	private final Robot robot; // DI
	
	public PostController(Robot robot) {
		super();
		this.robot = robot;
	}

	@GetMapping("/")
	public String home() {
		return "home " + robot.getName();
	}
}
