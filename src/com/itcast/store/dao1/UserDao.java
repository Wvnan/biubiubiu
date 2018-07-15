package com.itcast.store.dao1;

import com.itcast.store.domain.User;

public interface UserDao {

	User findByName(String username) throws Exception;

	void save(User user) throws Exception;

	User findByCode(String code) throws Exception;

	void update(User existUser) throws Exception;

	User login(User user) throws Exception;

}
