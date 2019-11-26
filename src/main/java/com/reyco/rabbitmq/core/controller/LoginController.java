package com.reyco.rabbitmq.core.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reyco.rabbitmq.core.domain.LogEntity;
import com.reyco.rabbitmq.core.domain.LoginLog;
import com.reyco.rabbitmq.core.domain.User;
import com.reyco.rabbitmq.core.domain.UserEntity;
import com.reyco.rabbitmq.core.service.UserService;
import com.reyco.rabbitmq.core.service.impl.Sender;
import com.reyco.rabbitmq.core.utils.CacheUtils;
import com.reyco.rabbitmq.core.utils.CookieUtil;
import com.reyco.rabbitmq.core.utils.R;


@RestController
@RequestMapping("/api")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Sender sender;
	
	@Autowired
	private ApplicationContext applicationContext;
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @param captcha
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/login")
	public R Login(String username,String password,String captcha,HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)|| StringUtils.isBlank(captcha) ) {
			logger.info("参数错误");
			return R.fail("参数错误...");
		}
		UserEntity userEntity = userService.getByName(username);
		if(!username.equals(userEntity.getName()) || !password.equals(userEntity.getPassword())) {
			logger.info("用户名或密码错误。。。");
			return R.fail("用户名或密码错误...");
		}
		String captchaCookie = CookieUtil.getCookieByName(request, "rabbitmq_captcha");
		if(StringUtils.isBlank(captchaCookie)) {
			logger.info("没有验证码");
			return R.fail("没有验证码...");
		}
		Object selCaptcha = CacheUtils.get(captchaCookie);
		CacheUtils.remove(captchaCookie);
		if(null == selCaptcha ) {
			logger.info("验证码失效");
			return R.fail("验证码失效...");
		}
		if(!captcha.equalsIgnoreCase(selCaptcha.toString())) {
			logger.info("验证码错误");
			return R.fail("验证码错误...");
		}
		User user = new User();
		user.setId(userEntity.getId());
		user.setUsername(userEntity.getName());
		// 创建 token
		String key = UUID.randomUUID().toString().replace("-", "");
		CacheUtils.put(key, user);
		// 设置cookie
		CookieUtil.setCookie(request, response, "rabbitmq_token", key, -1);
		// 设置cookie
		CookieUtil.setCookie(request, response, "rabbitmq_token", key, -1);
		LogEntity logEntity = new LogEntity();
		logEntity.setUsername(username);
		logEntity.setUserId(userEntity.getId());
		sender.send(logEntity);
		//返回数据
		return R.success(user);
	}
	@GetMapping("/checkUser")
	public R checkLogin(HttpServletRequest request) {
		String cookie = CookieUtil.getCookieByName(request, "rabbitmq_token");
		Object object = CacheUtils.get(cookie);
		return R.success(object);
	}
	
}
