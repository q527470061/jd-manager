package com.jd.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.mapper.ItemDescMapper;
import com.jd.pojo.JDResult;
import com.jd.service.ItemDescService;

@Service
public class ItemDescServiceImp implements ItemDescService {

	@Autowired
	private ItemDescMapper itemDescMapper;

	@Override
	public JDResult queryByItemId(Long itemId) {
		return JDResult.ok(itemDescMapper.selectByPrimaryKey(itemId));
	}

}
