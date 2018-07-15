package com.itcast.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

	/**
	 * BaseServlet
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String methodName = req.getParameter("method");
		if(methodName == null || "".equals(methodName)){
			resp.getWriter().println("method参数值为null");
			return;
		}
		Class<? extends BaseServlet> clazz = this.getClass();
		try {
			Method method = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			String path = (String) method.invoke(this, req, resp);
			if(path == null){
				return;
			}else{
				req.getRequestDispatcher(path).forward(req, resp);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	

}
