package com.jd.service;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.ItemParam;
import com.jd.pojo.JDResult;

public interface ItemParamService {
	//按照商品分类获取商品规格个参数模板
	JDResult getItemParamByCid(long cid);
	//插入商品规格模板
	JDResult insertItemParam(ItemParam itemParam);
	//获取所有商品规格模板
	EUDataGridResult getAllItemParam(int page, int rows);
	//按找id删除规格模板
	JDResult deleteItemParamById(long id);
}
