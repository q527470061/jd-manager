package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.Content;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.JDResult;
import com.jd.service.ContentService;

@RestController
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping("/query/list")
	public EUDataGridResult queryListContents(int page, int rows) {
		return contentService.selectContents(page, rows);
	}

	@RequestMapping("/save")
	public JDResult saveContent(Content content) {
		return contentService.saveContent(content);
	}

	@RequestMapping("/edit")
	public JDResult updateContent(Content content) {
		return contentService.updateContent(content);
	}

	@RequestMapping("/delete")
	public JDResult deleteContent(long ids) {
		return null;
	}

}
