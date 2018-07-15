package com.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.store.domain.Category;
import com.itcast.store.service1.CategoryService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findAll(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
			List<Category> list = service.findAll();
			JSONArray jsonArray = JSONArray.fromObject(list);
			resp.getWriter().println(jsonArray.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
