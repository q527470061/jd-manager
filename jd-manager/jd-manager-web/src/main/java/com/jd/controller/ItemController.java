package com.jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.Item;
import com.jd.pojo.ItemDesc;
import com.jd.pojo.JDResult;
import com.jd.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/{itemId}")
	public Item getItemById(@PathVariable Long itemId) {
		Item item = itemService.getItemById(itemId);
		return item;
	}

	@RequestMapping("/list")
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping("/save")
	public JDResult addItem(Item item, String desc, String itemParams) {
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		JDResult result = itemService.addItem(item, itemDesc, itemParams);
		return result;
	}

	@RequestMapping("/update")
	public JDResult updateItem(Item item, String desc, String itemParams) {
		JDResult result = itemService.updateItem(item, desc, itemParams);
		return result;
	}

	@RequestMapping("/delete")
	public JDResult deleteItem(Long ids) {
		return itemService.deleteItem(ids);
	}
	
	//下架
	@RequestMapping("/instock")
	public JDResult instockItem(Long ids) {
		return itemService.instockItem(ids);
	}
	
	//上架
	@RequestMapping("/reshelf")
	public JDResult reshelfItem(Long ids) {
		return itemService.reshelfItem(ids);
	}
	
}
