package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.JDResult;
import com.jd.service.ItemDescService;

@RestController
@RequestMapping("/item/desc")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;

	//按照item id查询描述
	@RequestMapping("/query/{itemId}")
	public JDResult queryById(@PathVariable Long itemId) {
		return itemDescService.queryByItemId(itemId);
	}
}
