package com.mock.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notify")
public class NotifyReciver {
	
	@PostMapping("/stopInfo")
	public void reciveNotify(@RequestParam(value = "stopInfo") String stopInfo){
		System.out.println(stopInfo);
	}
}	
