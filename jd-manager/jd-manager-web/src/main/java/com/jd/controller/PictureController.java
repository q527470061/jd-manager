package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jd.pojo.PictureResult;
import com.jd.service.PictureService;

@RestController
@RequestMapping("/pic")
public class PictureController {

	@Autowired
	private PictureService pictureService;

	@RequestMapping("/upload")
	public PictureResult upload(@RequestParam MultipartFile uploadFile) {
		PictureResult result = pictureService.uploadPicture(uploadFile);
		return result;
	}

}
