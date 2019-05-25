package com.jd.mapper;

import java.util.List;

import com.jd.pojo.OrderCustom;

public interface OrderCustomMapper {

	public List<OrderCustom> getOrderCupstomByUserId(long userId);
}
