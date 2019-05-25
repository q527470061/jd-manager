package com.jd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

	//打开首页
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}
	
	/**
	 * 展示其他页面
	 * <p>Title: showpage</p>
	 */
	@RequestMapping("/{page}")
	public String showpage(@PathVariable String page) {
		return page;
	}

}
