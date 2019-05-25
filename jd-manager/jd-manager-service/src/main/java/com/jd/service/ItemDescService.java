package com.jd.service;

import com.jd.pojo.JDResult;

public interface ItemDescService {

	//按照id查询商品描述
	public JDResult queryByItemId(Long itemId);
}
