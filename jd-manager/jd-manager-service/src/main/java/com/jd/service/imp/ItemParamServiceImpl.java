package com.jd.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.mapper.ItemParamMapper;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.ItemParam;
import com.jd.pojo.ItemParamExample;
import com.jd.pojo.ItemParamExample.Criteria;
import com.jd.pojo.JDResult;
import com.jd.service.ItemParamService;
import com.jd.util.ExceptionUtil;

/**
 * 商品规格参数模板管理
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private ItemParamMapper itemParamMapper;

	@Override
	public JDResult getItemParamByCid(long cid) {
		ItemParamExample example = new ItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		List<ItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		// 判断是否查询到结果
		if (list != null && list.size() > 0) {
			return JDResult.ok(list.get(0));
		}

		return JDResult.ok();
	}

	@Override
	public JDResult insertItemParam(ItemParam itemParam) {
		// 补全pojo
		itemParam.setCreated(new Date());
		itemParam.setUpdated(new Date());
		// 插入到规格参数模板表
		itemParamMapper.insert(itemParam);
		return JDResult.ok();
	}

	@Override
	public EUDataGridResult getAllItemParam(int page, int rows) {
		EUDataGridResult result = new EUDataGridResult();

		ItemParamExample example = new ItemParamExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<ItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);

		result.setRows(list);
		// 取记录总条数
		PageInfo<ItemParam> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public JDResult deleteItemParamById(long id) {
		try {
			itemParamMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

}
