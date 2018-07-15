package com.itcast.store.dao1;

import java.sql.Connection;
import java.util.List;

import com.itcast.store.domain.Order;
import com.itcast.store.domain.OrderItem;

public interface OrderDao {



	void saveOrder(Connection conn, Order order) throws Exception;

	void saveOrderItem(Connection conn, OrderItem orderItem) throws Exception;

	Integer findCountByUid(String uid) throws Exception;

	List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception;


}
