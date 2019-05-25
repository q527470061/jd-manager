package com.jd.service;

import com.jd.pojo.Content;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.JDResult;

public abstract class ContentService {

	// 查询列表
	public abstract EUDataGridResult selectContents(int page, int rows);
	// 保存
	public abstract JDResult saveContent(Content content);
	// 修改
	public abstract JDResult updateContent(Content content);
	// 删除
	public abstract JDResult deleteContent(long id);
}
