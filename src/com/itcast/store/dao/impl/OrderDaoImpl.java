package com.itcast.store.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itcast.store.dao1.OrderDao;
import com.itcast.store.domain.Order;
import com.itcast.store.domain.OrderItem;
import com.itcast.store.domain.Product;
import com.itcast.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws Exception {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values (?,?,?,?,?,?,?,?)";
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		runner.update(conn, sql, params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem orderItem) throws Exception{
		QueryRunner queryRunner = new QueryRunner();
		String sql = "insert into orderitem values (?,?,?,?,?)";
		Object[] params = { orderItem.getItemid(), orderItem.getCount(), orderItem.getSubtotal(),
				orderItem.getProduct().getPid(), orderItem.getOrder().getOid() };
		queryRunner.update(conn, sql, params);
		
	}

	@Override
	public Integer findCountByUid(String uid) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		Long count = (Long) runner.query(sql, new ScalarHandler(), uid);
		return count.intValue();
	}

	@Override
	public List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception {
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = queryRunner.query(sql, new BeanListHandler<Order>(Order.class), uid,begin,pageSize);
		for (Order order : list) {
			sql = "SELECT * FROM orderitem o,product p WHERE o.pid = p.pid AND o.oid = ?";
			List<Map<String,Object>> oList = queryRunner.query(sql, new MapListHandler(), order.getOid());
			// 遍历LIst的Map集合:获取每条记录，封装到不同的对象中.
			for (Map<String, Object> map : oList) {
				Product product = new Product();
				//product.setPname(map.get("pname"));
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				orderItem.setProduct(product);
				
				order.getOrderItems().add(orderItem);
			}
		}
		return list;
	}


}
