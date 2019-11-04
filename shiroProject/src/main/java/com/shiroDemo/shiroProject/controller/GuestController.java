package com.shiroDemo.shiroProject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/guest")
public class GuestController {

	@Autowired
	@Qualifier("shiroRedisTemplate")
	private RedisTemplate<String, String> shiroRedisTemplate;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		shiroRedisTemplate.opsForValue().set("hechuanfei", "I lOVE U");
		return "login";
	}

	@RequestMapping(value = "/getMessage", method = RequestMethod.GET)
	public @ResponseBody String submitLogin() {
		return "您拥有获得该接口的信息的权限！";
	}

}
