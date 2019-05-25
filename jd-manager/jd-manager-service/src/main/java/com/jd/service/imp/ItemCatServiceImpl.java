package com.jd.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.mapper.ItemCatMapper;
import com.jd.pojo.EUTreeNode;
import com.jd.pojo.ItemCat;
import com.jd.pojo.ItemCatExample;
import com.jd.pojo.ItemCatExample.Criteria;
import com.jd.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public List<EUTreeNode> getItemCatList(long parentId) {
		// 根据parentId查询分类列表
		ItemCatExample example = new ItemCatExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<ItemCat> list = itemCatMapper.selectByExample(example);
		// 分类列表转换成TreeNode的列表
		List<EUTreeNode> resultList = new ArrayList<>();
		for (ItemCat tbItemCat : list) {
			// 创建一个TreeNode对象
			EUTreeNode node = new EUTreeNode(tbItemCat.getId(), tbItemCat.getName(),
					tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

}
