package com.itcast.store.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itcast.store.domain.User;
import com.itcast.store.service1.UserService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;
import com.itcast.store.utils.MyDateConverter;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String registUI(HttpServletRequest req, HttpServletResponse resp) {
		return "/jsp/regist.jsp";
	}

	/**
	 * 检查用户名是否存在
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String checkUsername(HttpServletRequest req, HttpServletResponse resp) {
		String username = req.getParameter("username");
		UserService service = (UserService) BeanFactory.getBean("UserServiceImpl");
		try {
			User existUser = service.findByName(username);
			if (existUser == null) {
				resp.getWriter().println("1");
			} else {
				resp.getWriter().println("2");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * 注册用户
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp) {

		try {
			Map<String, String[]> map = req.getParameterMap();
			User user = new User();
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			UserService service = (UserService) BeanFactory.getBean("UserServiceImpl");
			service.save(user);
			req.setAttribute("msg", "注册成功,请去邮箱激活后使用!!!");
			return "/jsp/msg.jsp";

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

	}
	/**
	 * 用户激活
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public String active(HttpServletRequest req, HttpServletResponse resp){
		String code = req.getParameter("code");
		UserService service = (UserService) BeanFactory.getBean("UserServiceImpl");
		try {
			User existUser = service.findByCode(code);
			if(existUser != null){
				existUser.setCode(null);
				existUser.setState(2);
				req.setAttribute("msg", "激活成功,请去登录!!!");
				service.update(existUser);
			}else{
				req.setAttribute("msg", "激活码错误,请重新激活!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/jsp/msg.jsp";
	}
	
	public String loginUI(HttpServletRequest req, HttpServletResponse resp){
		return "/jsp/login.jsp";
	}
	/**
	 * 用户登录
	 * @param req
	 * @param resp
	 * @return
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			/*
			 * 验证码校验
			 */
			String code1 = req.getParameter("code");
			String code2 = (String) req.getSession().getAttribute("code");
			req.getSession().removeAttribute("code");
			if(!code1.equalsIgnoreCase(code2)){
				req.setAttribute("msg", "验证码有误!!!");
				return "/jsp/login.jsp";
			}
			
			
			
			Map<String, String[]> map = req.getParameterMap();
			User user = new User();
			BeanUtils.populate(user, map);
			UserService service = (UserService) BeanFactory.getBean("UserServiceImpl");
			User existUser = service.login(user);
			if(existUser == null){
				req.setAttribute("msg", "用户名密码错误或用户未激活!!!");
				return "/jsp/login.jsp";
			}else{
				/*
				 * 自动登录
				 */
				String autoLogin = req.getParameter("autoLogin");
				if("true".equals(autoLogin)){
					Cookie cookie = new Cookie("autoLogin", existUser.getUsername()+"#"+existUser.getPassword());
					cookie.setPath("/MyStore");
					cookie.setMaxAge(60*60*24*7);
					resp.addCookie(cookie);
				}
				/*
				 * 记住用户名
				 */
				String remember = req.getParameter("remember");
				if("true".equals(remember)){
					Cookie cookie = new Cookie("remember", existUser.getUsername());
					cookie.setPath("/MyStore");
					cookie.setMaxAge(60*60);
					resp.addCookie(cookie);
				
				}
				
				
				
				req.getSession().setAttribute("existUser", existUser);
				resp.sendRedirect(req.getContextPath()+"/index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	/**
	 * 用户退出
	 * @param req
	 * @param resp
	 * @return
	 */
	public String logOut(HttpServletRequest req, HttpServletResponse resp){
		
		try {
			req.getSession().invalidate();
			Cookie cookie = new Cookie("autoLogin", "");
			cookie.setPath("/MyStore");
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			resp.sendRedirect(req.getContextPath()+"/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
