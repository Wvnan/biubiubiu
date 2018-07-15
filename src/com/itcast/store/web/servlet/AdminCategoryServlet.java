package com.itcast.store.web.servlet;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itcast.store.domain.Category;
import com.itcast.store.service1.CategoryService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;
import com.itcast.store.utils.UUIDUtils;


/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findAll(HttpServletRequest req, HttpServletResponse resp){
		CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
		try {
			List<Category> list = service.findAll();
			req.setAttribute("list", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/category/list.jsp";
	}
	
	public String saveUI(HttpServletRequest req, HttpServletResponse resp){
		return "/admin/category/add.jsp";
	}
	
	public String save(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			String cname = req.getParameter("cname");
			Category category = new Category();
			category.setCid(UUIDUtils.getUUID());
			category.setCname(cname);
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
			service.save(category);
			
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String edit(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			String cid = req.getParameter("cid");
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
			Category category = new Category();
			category = service.findById(cid);
			req.setAttribute("category", category);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/admin/category/edit.jsp";
	}
	
	public String update(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			Map<String, String[]> map = req.getParameterMap();
			Category category = new Category();
			BeanUtils.populate(category, map);
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
			service.update(category);
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String delete(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			String cid = req.getParameter("cid");
			CategoryService service = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
			service.delete(cid);
			resp.sendRedirect(req.getContextPath()+"/AdminCategoryServlet?method=findAll");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
