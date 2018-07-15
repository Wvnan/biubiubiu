package com.itcast.store.service1;

import java.util.List;

import com.itcast.store.domain.Category;

public interface CategoryService {

	List<Category> findAll() throws Exception;

	void save(Category category) throws Exception;

	Category findById(String cid) throws Exception;

	void update(Category category) throws Exception;

	void delete(String cid) throws Exception;

}
