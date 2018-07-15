package com.itcast.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itcast.store.dao1.CategoryDao;
import com.itcast.store.domain.Category;
import com.itcast.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category";
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void save(Category category) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values (?, ?)";
		runner.update(sql, category.getCid(), category.getCname());
	}

	@Override
	public Category findById(String cid) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		return runner.query(sql, new BeanHandler<Category>(Category.class), cid);
	}

	@Override
	public void update(Category category) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		runner.update(sql, category.getCname(), category.getCid());
		
	}

	@Override
	public void delete(String cid) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update product set cid = null where cid = ?";
		runner.update(sql, cid);
		String sql1 = "delete from category where cid = ?";
		runner.update(sql1, cid);
		
	}

}
