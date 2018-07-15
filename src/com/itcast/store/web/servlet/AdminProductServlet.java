package com.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.store.domain.Category;
import com.itcast.store.domain.PageBean;
import com.itcast.store.domain.Product;
import com.itcast.store.service1.CategoryService;
import com.itcast.store.service1.ProductService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 商品分页查询的Servlet:
	 * @return
	 */
	public String findByPage(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			// 调用业务层:
			ProductService productService = (ProductService) BeanFactory.getBean("ProductServiceImpl");
			PageBean<Product> pageBean = productService.findByPage(currPage);
			
			req.setAttribute("pageBean", pageBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/list.jsp";
	}
	
	/**
	 * 跳转到添加页面的执行的方法:
	 */
	public String saveUI(HttpServletRequest req,HttpServletResponse resp){
		// 查询所有分类:
		try{
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryServiceImpl");
		List<Category> list = categoryService.findAll();
		req.setAttribute("list", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/admin/product/add.jsp";
	}

}
