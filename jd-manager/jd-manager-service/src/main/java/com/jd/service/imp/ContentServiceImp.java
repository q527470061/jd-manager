package com.jd.service.imp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jd.mapper.ContentMapper;
import com.jd.pojo.Content;
import com.jd.pojo.ContentExample;
import com.jd.pojo.EUDataGridResult;
import com.jd.pojo.JDResult;
import com.jd.service.ContentService;
import com.jd.util.ExceptionUtil;
import com.jd.util.HttpClientUtil;

@Service
public class ContentServiceImp extends ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Value("REST_BASE_URL")
	private String REST_BASE_URL;
	@Value("REST_CONTENT")
	private String REST_CONTENT;

	@Override
	public EUDataGridResult selectContents(int page, int rows) {
		ContentExample example = new ContentExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<Content> list = contentMapper.selectByExampleWithBLOBs(example);
		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		// 取记录总条数
		PageInfo<Content> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public JDResult saveContent(Content content) {
		// 补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);

		try {
			// 更新缓存
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JDResult.ok();

	}

	@Override
	public JDResult updateContent(Content content) {
		try {
			contentMapper.updateByPrimaryKeySelective(content);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		try {
			// 更新缓存
			/*
			 * 没有使用消息队列，因为前期机器太卡，
			 * HttpClient调用
			 */
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JDResult.ok();
	}

	@Override
	public JDResult deleteContent(long id) {
		try {
			contentMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JDResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		// 亮爷就不加更新缓存了，困
		return JDResult.ok();
	}

}
