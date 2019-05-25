package com.jd.service;

import org.springframework.web.multipart.MultipartFile;

import com.jd.pojo.PictureResult;

public abstract class PictureService {

	public abstract PictureResult uploadPicture(MultipartFile uploadFile);
}
