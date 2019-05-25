package com.jd.service;

import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.Item;
import com.jd.pojo.ItemDesc;
import com.jd.pojo.JDResult;

public interface ItemService {	
	//按商品id查询
	public Item getItemById(Long id);
	//按页查询商品
	public EUDataGridResult getItemList(int page, int rows);
	//添加商品
	public JDResult addItem(Item item, ItemDesc itemDesc,String itemParams) ;
	//更新修改商品
	JDResult updateItem(Item item, String desc,String itemParams);
	//删除商品
	JDResult deleteItem(Long ids);
	//商品下架
	JDResult instockItem(Long id);	
	//上架
	JDResult reshelfItem(Long id);
}
