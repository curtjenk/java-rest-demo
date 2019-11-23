package com.curtjenk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		// org.springframework.security.crypto.password.PasswordEncoder 
		// encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
		// String passwd = encoder.encode("xxxxxx");

		// // passwd - password from database
		// System.out.println("----------------------------");
		// System.out.println("----------------------------");
		// System.out.println(passwd); // print hash
		// System.out.println("----------------------------");
		// System.out.println("----------------------------");

	}

}
