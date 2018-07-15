package com.itcast.store.service.impl;

import com.itcast.store.dao1.UserDao;
import com.itcast.store.domain.User;
import com.itcast.store.service1.UserService;
import com.itcast.store.utils.BeanFactory;
import com.itcast.store.utils.MailUtils;
import com.itcast.store.utils.UUIDUtils;

public class UserServiceImpl implements UserService {

	@Override
	public User findByName(String username) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDaoImpl");
		return dao.findByName(username);
	}

	@Override
	public void save(User user) throws Exception {
		user.setState(1);
		user.setUid(UUIDUtils.getUUID());
		String code = UUIDUtils.getUUID()+""+UUIDUtils.getUUID();
		user.setCode(code);
		UserDao dao = (UserDao) BeanFactory.getBean("UserDaoImpl");
		dao.save(user);
		
		MailUtils.sendMail("aaa@zzuli.com", code);
		
	}

	@Override
	public User findByCode(String code) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDaoImpl");
		return dao.findByCode(code);
	}

	@Override
	public void update(User existUser) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDaoImpl");
		dao.update(existUser);
		
	}

	@Override
	public User login(User user) throws Exception {
		UserDao dao = (UserDao) BeanFactory.getBean("UserDaoImpl");
		return dao.login(user);
	}


}
