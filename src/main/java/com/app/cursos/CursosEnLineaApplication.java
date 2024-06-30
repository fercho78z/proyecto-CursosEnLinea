package com.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.app.cursos")
public class CursosEnLineaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursosEnLineaApplication.class, args);
	}

}
