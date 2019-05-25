package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.ItemParam;
import com.jd.pojo.JDResult;
import com.jd.service.ItemParamService;

/**
 * 商品规格参数模板管理Controller
 */
@RestController
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public JDResult getItemParamByCid(@PathVariable Long itemCatId) {
		JDResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	@RequestMapping("/save/{cid}")
	public JDResult insertItemParam(@PathVariable Long cid, String paramData) {
		// 创建pojo对象
		ItemParam itemParam = new ItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		JDResult result = itemParamService.insertItemParam(itemParam);
		return result;
	}

	@RequestMapping("/list")
	public EUDataGridResult getAllItemParam(int page, int rows) {
		return itemParamService.getAllItemParam(page, rows);
	}

	@RequestMapping("/delete")
	public JDResult deleteItemParam(long ids) {
		return itemParamService.deleteItemParamById(ids);
	}
}
