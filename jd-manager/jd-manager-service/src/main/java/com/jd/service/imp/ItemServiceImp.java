package com.jd.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.mapper.ItemDescMapper;
import com.jd.mapper.ItemMapper;
import com.jd.mapper.ItemParamItemMapper;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.Item;
import com.jd.pojo.ItemDesc;
import com.jd.pojo.ItemExample;
import com.jd.pojo.ItemParamItem;
import com.jd.pojo.ItemParamItemExample;
import com.jd.pojo.ItemParamItemExample.Criteria;
import com.jd.pojo.JDResult;
import com.jd.service.ItemParamItemService;
import com.jd.service.ItemService;
import com.jd.util.ExceptionUtil;
import com.jd.util.IDUtils;

@Service
public class ItemServiceImp implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
	@Autowired
	private ItemParamItemService itemParamItemService;
	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	@Override
	public Item getItemById(Long id) {

		Item item = itemMapper.selectByPrimaryKey(id);
		return item;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		ItemExample example = new ItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<Item> list = itemMapper.selectByExample(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<Item> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	public JDResult addItem(Item item, ItemDesc itemDesc, String itemParams) {
		try {
			// 生成商品id
			Long itemId = IDUtils.genItemId();
			// 补全不完整的字段
			item.setId(itemId);
			item.setStatus((byte) 1);
			Date date = new Date();
			item.setCreated(date);
			item.setUpdated(date);
			// 把数据插入到商品表
			itemMapper.insert(item);
			// 添加商品描述
			itemDesc.setItemId(itemId);
			itemDesc.setCreated(date);
			itemDesc.setUpdated(date);
			// 把数据插入到商品描述表
			itemDescMapper.insert(itemDesc);
			// 插入参数参数
			itemParamItemService.insertItemParamItem(itemId, itemParams);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return JDResult.ok();
	}

	@Override
	@Transactional
	public JDResult updateItem(Item item, String desc, String itemParams) {
		try {
			// 更新商品表
			Item _item = itemMapper.selectByPrimaryKey(item.getId());
			item.setUpdated(new Date());
			item.setStatus(_item.getStatus());
			item.setCreated(_item.getCreated());
			itemMapper.updateByPrimaryKey(item);
			// 更新商品内容表
			ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(item.getId());
			itemDesc.setItemDesc(desc);
			itemDesc.setUpdated(new Date());
			itemDescMapper.updateByPrimaryKey(itemDesc);
			// 更新规格参数表
			ItemParamItemExample example = new ItemParamItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andItemIdEqualTo(item.getId());
			List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
			if (!list.isEmpty()) {
				ItemParamItem itemParamItem = list.get(0);
				itemParamItem.setParamData(itemParams);
				itemParamItem.setUpdated(new Date());
				int t = itemParamItemMapper.updateByPrimaryKeySelective(itemParamItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

	@Override
	public JDResult deleteItem(Long ids) {
		try {
			// 删除商品，就是要删除3张表
			// 删除规格参数表
			ItemParamItemExample example = new ItemParamItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andItemIdEqualTo(ids);
			itemParamItemMapper.deleteByExample(example);
			// 删除商品内容表
			itemDescMapper.deleteByPrimaryKey(ids);
			// 删除商品表
			itemMapper.deleteByPrimaryKey(ids);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

	@Override
	public JDResult instockItem(Long id) {
		try {
			// 下架 status=2
			Item item = new Item();
			item.setId(id);
			item.setStatus((byte) 2);
			itemMapper.updateByPrimaryKeySelective(item);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

	@Override
	public JDResult reshelfItem(Long id) {
		try {
			// 下架 status=2
			Item item = new Item();
			item.setId(id);
			item.setStatus((byte) 1);
			itemMapper.updateByPrimaryKeySelective(item);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

}
