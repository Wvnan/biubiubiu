package com.itcast.store.service.impl;

import java.util.List;

import com.itcast.store.dao1.CategoryDao;
import com.itcast.store.domain.Category;
import com.itcast.store.service1.CategoryService;
import com.itcast.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> findAll() throws Exception {
//		CategoryDao dao = new CategoryDaoImpl();
//		return dao.findAll();
		/**
		 * 使用缓存优化程序,先从缓存中获取数据
		 *   * 获取到:直接返回
		 *   * 获取不到:查询数据库,将记录存入到缓存中.
		 */
		// 读取配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		// 判断缓存中是否有list集合:
		Element element = cache.get("list");
		List<Category> list = null;
		if(element == null){
			// 缓存中没有数据
			CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDaoImpl");
			list = categoryDao.findAll();
			element = new Element("list",list); 
			cache.put(element);
		}else{
			// 缓存中已经存在数据
			list = (List<Category>)element.getObjectValue();
			
		}
		return list;
	}

	@Override
	public void save(Category category) throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDaoImpl");
		dao.save(category);
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		cache.remove("list");
	}

	@Override
	public Category findById(String cid) throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDaoImpl");
		return dao.findById(cid);
	}

	@Override
	public void update(Category category) throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDaoImpl");
		dao.update(category);
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		cache.remove("list");
	}

	@Override
	public void delete(String cid) throws Exception {
		CategoryDao dao = (CategoryDao) BeanFactory.getBean("CategoryDaoImpl");
		dao.delete(cid);
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		// 从配置文件中获取名称为categoryCache缓存区
		Cache cache = cacheManager.getCache("categoryCache");
		cache.remove("list");
	}

}
