package com.keepcount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan


// {
//
// "com.keepcount.dao.signup.Sign.SignUpDAO",
//
// "com.keepcount.model.signup.SignUp",
//
// "com.keepcount.model.signup.EmailsSignedUp",
//
// "com.keepcount.service.signup.SignUpService",
//
// "com.keepcount.controller.signup.SignUpController" }

public class KeepCountApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeepCountApplication.class, args);
	}
}
