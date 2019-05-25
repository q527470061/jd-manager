package com.jd.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.mapper.ItemParamItemMapper;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.ItemParamItem;
import com.jd.pojo.ItemParamItemExample;
import com.jd.pojo.ItemParamItemExample.Criteria;
import com.jd.pojo.JDResult;
import com.jd.service.ItemParamItemService;
import com.jd.util.ExceptionUtil;
import com.jd.util.JsonUtils;

/**
 * 展示商品规格参数
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private ItemParamItemMapper itemParamItemMapper;

	@Override
	public String getItemParamByItemId(Long itemId) {
		// 根据商品id查询规格参数
		ItemParamItemExample example = new ItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		// 执行查询
		List<ItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if (list == null || list.size() == 0) {
			return "";
		}
		// 取规格参数信息
		ItemParamItem itemParamItem = list.get(0);
		String paramData = itemParamItem.getParamData();
		// 生成html
		// 把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for (Map m1 : jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for (Map m2 : list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
				sb.append("            <td>" + m2.get("v") + "</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

	@Override
	public EUDataGridResult selectAllItemParam(int page, int rows) {
		ItemParamItemExample example = new ItemParamItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);

		List<ItemParamItem> list = itemParamItemMapper.selectByExample(example);
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<ItemParamItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public JDResult insertItemParamItem(Long itemId, String itemParam) {
		// 创建一个pojo
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		// 向表中插入数据
		itemParamItemMapper.insert(itemParamItem);

		return JDResult.ok();

	}

	@Override
	public JDResult QueryItemParamItem(Long itemId) {
		ItemParamItem itemParamItem=null;
		ItemParamItemExample example=new ItemParamItemExample();
		Criteria criteria=example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		try {
		itemParamItem=itemParamItemMapper.selectByExampleWithBLOBs(example).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok(itemParamItem);
	}

}
