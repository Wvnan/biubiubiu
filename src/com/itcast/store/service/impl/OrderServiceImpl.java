package com.itcast.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itcast.store.dao1.OrderDao;
import com.itcast.store.domain.Order;
import com.itcast.store.domain.OrderItem;
import com.itcast.store.domain.PageBean;
import com.itcast.store.utils.BeanFactory;
import com.itcast.store.utils.JDBCUtils;

public class OrderServiceImpl implements com.itcast.store.service1.OrderService {

	@Override
	public void save(Order order) throws Exception {
		
		Connection conn = null;
		try {
			OrderDao dao = (OrderDao) BeanFactory.getBean("OrderDaoImpl");
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			dao.saveOrder(conn, order);
			for(OrderItem orderItem: order.getOrderItems()){
				dao.saveOrderItem(conn, orderItem);
			}
			DbUtils.commitAndCloseQuietly(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			DbUtils.rollbackAndCloseQuietly(conn);
		}
		
	}

	@Override
	public PageBean<Order> findByUid(String uid, Integer currPage) throws Exception {
		PageBean<Order> pageBean = new PageBean<Order>();
		// 设置当前的页数：
		pageBean.setCurrPage(currPage);
		// 设置每页显示的记录数:
		Integer pageSize = 5;
		pageBean.setPageSize(pageSize);
		// 设置总记录数:
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDaoImpl");
		Integer totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		// 设置总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		// 设置每页显示的数据的集合:
		int begin = (currPage - 1)* pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

}
