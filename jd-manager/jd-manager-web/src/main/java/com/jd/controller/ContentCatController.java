package com.jd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.EUTreeNode;
import com.jd.pojo.JDResult;
import com.jd.service.ContentCatService;

@RestController
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCatService contentCateService;

	@RequestMapping("/list")
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		return contentCateService.getCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	public JDResult createContentCategory(Long parentId,String name) {
		return contentCateService.insertContentCategory(parentId, name);
	}
	
	@RequestMapping("/delete")
	public JDResult deleteContentCategory(Long id) {
		return contentCateService.deleteContentCategory( id);
	}
	
	@RequestMapping("/update")
	public JDResult updateContentCategory(Long id,String name) {
		return contentCateService.updateContentCategory(id, name);
	}

}
