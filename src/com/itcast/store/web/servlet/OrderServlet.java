package com.itcast.store.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itcast.store.domain.Cart;
import com.itcast.store.domain.CartItem;
import com.itcast.store.domain.Order;
import com.itcast.store.domain.OrderItem;
import com.itcast.store.domain.PageBean;
import com.itcast.store.domain.User;
import com.itcast.store.service1.OrderService;
import com.itcast.store.utils.BaseServlet;
import com.itcast.store.utils.BeanFactory;
import com.itcast.store.utils.UUIDUtils;

/**
 * 订单servlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String saveOrder(HttpServletRequest req, HttpServletResponse resp) {
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1);

		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if (cart == null) {
			req.setAttribute("msg", "购物车是空的,去购物!!");
			return "/jsp/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		User existUser = (User) req.getSession().getAttribute("existUser");
		order.setUser(existUser);

		for (CartItem cartItem : cart.getMap().values()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);

			order.getOrderItems().add(orderItem);
		}
		OrderService service = (OrderService) BeanFactory.getBean("OrderServiceImpl");
		try {
			service.save(order);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cart.clearCart();

		req.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}

	public String findByUid(HttpServletRequest req, HttpServletResponse resp) {
		// 接收参数:
		try {
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			// 获得用户的信息:
			User user = (User) req.getSession().getAttribute("existUser");
			// 调用业务层:
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderServiceImpl");
			PageBean<Order> pageBean = orderService.findByUid(user.getUid(), currPage);

			req.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 页面跳转:
		return "/jsp/order_list.jsp";

	}

}
