package com.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.cursos.entity.Users;

@SpringBootApplication(scanBasePackages = "com.app.cursos")
public class CursosEnLineaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursosEnLineaApplication.class, args);
		
		Users user1=new Users();
		user1.toString();
		
	}

}
