package com.app.cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.cursos.entity.Users;



public interface UserRepository extends JpaRepository<Users, Integer>{
	@Query("select u from Users u where u.username= :username")
	Users getUserByUsername(@Param("username") String username);
}
