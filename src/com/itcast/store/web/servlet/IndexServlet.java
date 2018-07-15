package com.itcast.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.store.domain.Product;
import com.itcast.store.service1.ProductService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;

/**
 * Servlet implementation class IndexSerlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String index(HttpServletRequest req, HttpServletResponse resp){
		try{
			// 查询最新商品:
			ProductService productService = (ProductService) BeanFactory.getBean("ProductServiceImpl");
			List<Product> newList = productService.findByNew();
			// 查询热门商品:
			List<Product> hotList = productService.findByHot();
			req.setAttribute("newList", newList);
			req.setAttribute("hotList", hotList);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/jsp/index.jsp";
	}

}
