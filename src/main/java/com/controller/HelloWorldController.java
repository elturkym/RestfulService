package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.User;

@RestController
public class HelloWorldController {

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public  User showMessage() {
		return new User(1, "name1", "address1", "a@a.com");
	}
}