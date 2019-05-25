package com.jd.service;

import java.util.List;

import com.jd.pojo.EUTreeNode;

public interface ItemCatService {

	public List<EUTreeNode> getItemCatList(long parentId);
}
