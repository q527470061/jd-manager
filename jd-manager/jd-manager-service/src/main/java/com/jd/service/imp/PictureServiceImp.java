package com.jd.service.imp;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jd.pojo.PictureResult;
import com.jd.service.PictureService;
import com.jd.util.ExceptionUtil;

@Service
public class PictureServiceImp extends PictureService {

	@Value("${IMAGE_ADDRESS}")
	private String IMAGE_ADDRESS;
	@Value("${IMAGE_PORT}")
	private String IMAGE_PORT;
	
	private String IMAGE_PREFIX="pic";

	private String PIC_PATH="G:\\\\XUNI\\\\upload\\\\temp";

	@Override
	public PictureResult uploadPicture(MultipartFile uploadFile) {
		// 上传图片
		// 判断上传图片是否为空
		if (null == uploadFile || uploadFile.isEmpty()) {
			return PictureResult.error("上传图片为空");
		}

		// 原始文件的名称
		String originalFilename = uploadFile.getOriginalFilename();
		String newfilename=null;

		if ( originalFilename != null && originalFilename.length() > 0) {

			// 新的图片名称
			newfilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
			// 新图片
			File newFile = new File(PIC_PATH +"\\\\" +newfilename);

			// 将内存中数据写入磁盘
			try {
				uploadFile.transferTo(newFile);
			} catch (IllegalStateException e) {
				return PictureResult.error(ExceptionUtil.getStackTrace(e));
			} catch (IOException e) {
				return PictureResult.error(ExceptionUtil.getStackTrace(e));
			}

	
		}
		
		String url="http://"+IMAGE_ADDRESS +":"+ IMAGE_PORT + "/"+IMAGE_PREFIX+"/"+ newfilename;
		return PictureResult.ok(url);
	}

}
