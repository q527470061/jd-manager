package com.jd.service;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.JDResult;

public interface ItemParamItemService {
	String getItemParamByItemId(Long itemId);
	// 列出所有参数
	EUDataGridResult selectAllItemParam(int page, int rows);
	// 保存商品参数
	JDResult insertItemParamItem(Long itemId, String itemParam);
	//按照商品id获取商品规格参数
	JDResult QueryItemParamItem(Long itemId);
}
