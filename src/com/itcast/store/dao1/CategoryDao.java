package com.itcast.store.dao1;

import java.util.List;

import com.itcast.store.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category category) throws Exception;

	Category findById(String cid) throws Exception;

	void update(Category category) throws Exception;

	void delete(String cid) throws Exception;

}
