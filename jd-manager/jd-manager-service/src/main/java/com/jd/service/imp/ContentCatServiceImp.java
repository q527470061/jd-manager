package com.jd.service.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jd.mapper.ContentCategoryMapper;
import com.jd.pojo.ContentCategory;
import com.jd.pojo.ContentCategoryExample;
import com.jd.pojo.ContentCategoryExample.Criteria;
import com.jd.pojo.EUTreeNode;
import com.jd.pojo.JDResult;
import com.jd.service.ContentCatService;
import com.jd.util.ExceptionUtil;

@Service
public class ContentCatServiceImp extends ContentCatService {

	@Autowired
	private ContentCategoryMapper contentCateMapper;

	public List<EUTreeNode> getCategoryList(long parentId) {

		// 按parent_id查询节点列表
		ContentCategoryExample example = new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		// 添加条件
		criteria.andParentIdEqualTo(parentId);

		// 查询
		List<ContentCategory> tbContentCategories = contentCateMapper.selectByExample(example);
		// 填充EUTreeNode
		List<EUTreeNode> resultList = new ArrayList<>();
		for (ContentCategory tbContentCategory : tbContentCategories) {
			EUTreeNode node = new EUTreeNode(tbContentCategory.getId(), tbContentCategory.getName(),
					tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

	public JDResult insertContentCategory(long parentId, String name) {
		ContentCategory contentCategory = new ContentCategory();
		try {
			contentCategory.setParentId(parentId);
			contentCategory.setName(name);
			contentCategory.setIsParent(false);
			// '状态。可选值:1(正常),2(删除)',
			contentCategory.setStatus(1);
			contentCategory.setParentId(parentId);
			contentCategory.setSortOrder(1);
			contentCategory.setCreated(new Date());
			contentCategory.setUpdated(new Date());

			// 插入记录
			contentCateMapper.insert(contentCategory);

			// 查看父节点的isParent列是否为true，如果不是true改成true
			ContentCategory parent = contentCateMapper.selectByPrimaryKey(parentId);
			if (!parent.getIsParent()) {
				parent.setIsParent(true);
				contentCateMapper.updateByPrimaryKey(parent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return JDResult.ok(contentCategory);
	}

	public JDResult deleteContentCategory(long id) {
		try {
			/*
			 * 业务逻辑： 接收parentid、id两个参数。删除id对应的记录。需要判断parentid对应的记录下是否有子节点。如果没有子节点。
			 * 需要把parentid对应的记录的isparent改成false。 注意：删除直接是物理删除。
			 */
			ContentCategory contentCategory = contentCateMapper.selectByPrimaryKey(id);

			// parent
			ContentCategory parent = contentCateMapper.selectByPrimaryKey(contentCategory.getParentId());

			// 删除
			delete(contentCategory);

			// 判断其父节点还有子节点吗
			ContentCategoryExample example = new ContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parent.getId());
			List<ContentCategory> contentCategories = contentCateMapper.selectByExample(example);
			if (contentCategories.size() == 0)
				parent.setIsParent(false);
			contentCateMapper.updateByPrimaryKey(parent);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return JDResult.ok();
	}

	// 删除本节点及其子节点
	public void delete(ContentCategory contentCategory) {
		if (contentCategory.getIsParent()) {
			// 递归删除
			ContentCategoryExample example = new ContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(contentCategory.getId());
			List<ContentCategory> contentCategories = contentCateMapper.selectByExample(example);
			for (ContentCategory contentCategory2 : contentCategories) {
				delete(contentCategory2);
			}
		}
		{
			contentCateMapper.deleteByPrimaryKey(contentCategory.getId());
		}
	}

	public JDResult updateContentCategory(long id, String name) {
		ContentCategoryExample example = new ContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);

		ContentCategory contentCategory = contentCateMapper.selectByPrimaryKey(id);
		contentCategory.setName(name);
		contentCateMapper.updateByExample(contentCategory, example);
		return JDResult.ok();
	}

}
