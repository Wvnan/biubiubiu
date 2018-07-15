package com.itcast.store.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itcast.store.dao1.UserDao;
import com.itcast.store.domain.User;
import com.itcast.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public User findByName(String username) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ?";
		return runner.query(sql, new BeanHandler<User>(User.class), username);
	}

	@Override
	public void save(User user) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into user values (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		runner.update(sql, params);
		
		
	}

	@Override
	public User findByCode(String code) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where code = ?";
		return runner.query(sql, new BeanHandler<User>(User.class), code);
	}

	@Override
	public void update(User existUser) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update user set username = ?,password = ?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid = ?";
		Object[] params = {existUser.getUsername(), existUser.getPassword(), existUser.getName(), existUser.getEmail(),
				existUser.getTelephone(), existUser.getBirthday(), existUser.getSex(), existUser.getState(), existUser.getCode(),existUser.getUid() };
		runner.update(sql, params);
		
	}

	@Override
	public User login(User user) throws Exception {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? and state = ?";
		return runner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword(), 2);
	}

}
