package com.reyco.rabbitmq.core.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reyco.rabbitmq.core.domain.Captcha;
import com.reyco.rabbitmq.core.utils.CacheUtils;
import com.reyco.rabbitmq.core.utils.CaptchaUtils;
import com.reyco.rabbitmq.core.utils.CookieUtil;
import com.reyco.rabbitmq.core.utils.R;

/**
 * 验证码接口
 * 
 * @author Admin
 *
 */
@Controller
@RequestMapping("/api")
public class CaptchaController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@GetMapping("/captcha")
	public R getCaptcha(HttpServletRequest request, HttpServletResponse response) {
		// 1.生成验证码
		logger.info("生成验证码");
		String code = CaptchaUtils.createCode();
		String key = UUID.randomUUID().toString().replace("-", "");
		// 2.验证码放入缓存
		CacheUtils.put(key, code, 60L);
		// 3.验证码设置cookie
		CookieUtil.setCookie(request, response, "rabbitmq_captcha", key, -1);
		// 4.验证码返回给前端
		Captcha captcha = new Captcha(key, code);
		return R.success(captcha, "获取验证码成功");
	}
}
