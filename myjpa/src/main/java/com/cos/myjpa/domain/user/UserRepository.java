package com.cos.myjpa.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	
	// 1. namingQuery 간단한거 만들 때 사용
	// select * from user where username = ?1 이렇게 작동함.
//	User findByUsername(String username);
	// select * from user where username = ?1 and password = ?2   물음표 뒤 숫자는 순서이다.
	User findByUsernameAndPassword(String username, String password);
	
	// 2. nativeQuery 복잡한거 만들 때 사용
	@Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//	@Query(value = "SELECT * FROM user WHERE username = :username AND password = :password", nativeQuery = true)
	User mLogin(String username, String password);
	
	// 3. 동적쿼리 라이브러리 QueryDSL - 안함

}
