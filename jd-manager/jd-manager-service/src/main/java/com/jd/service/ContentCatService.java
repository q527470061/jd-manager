package com.jd.service;

import java.util.List;

import com.jd.pojo.EUTreeNode;
import com.jd.pojo.JDResult;


public abstract class ContentCatService {
	
	//内容分类id
	public abstract List<EUTreeNode> getCategoryList(long parentId) ;
	//插入内容分类
	public abstract JDResult insertContentCategory(long parentId, String name) ;
	//删除分类
	public abstract JDResult deleteContentCategory(long id);
	//更新分类
	public abstract JDResult updateContentCategory(long id,String name);
	
}
