package com.itcast.store.service1;

import com.itcast.store.domain.Order;
import com.itcast.store.domain.PageBean;

public interface OrderService {

	void save(Order order) throws Exception;

	PageBean<Order> findByUid(String uid, Integer currPage) throws Exception;

}
