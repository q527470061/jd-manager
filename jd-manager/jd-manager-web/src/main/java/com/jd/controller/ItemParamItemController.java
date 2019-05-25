package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.JDResult;
import com.jd.service.ItemParamItemService;

/**
 * 展示商品规格参数
 */
@RestController
@RequestMapping("/item/param")
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping("/showitem/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String string = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", string);
		return "item";
	}

	/*@RequestMapping("/list")
	public EUDataGridResult selectAllItemParam(int page, int rows) {
		return itemParamItemService.selectAllItemParam(page, rows);
	}*/

	@RequestMapping("/item/query/{itemId}")
	public JDResult QueryItemParamItem(@PathVariable Long itemId) {
		return itemParamItemService.QueryItemParamItem(itemId);
	}
}
