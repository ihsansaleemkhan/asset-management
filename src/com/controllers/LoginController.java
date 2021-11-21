package com.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.User;
import com.dao.UserDao;

@WebServlet(name="/LoginController", urlPatterns = {"/loginCont"})
public class LoginController extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6598093557047794232L;

	public LoginController() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		Integer role = Integer.valueOf(req.getParameter("userRole"));
		System.out.println("username:"+username +", password:"+password);
		boolean loginStatus = false;
		User user = null;
		try {
			user = new UserDao().getUser(username, password, role);
//			loginStatus = new UserDao().verifyLoginCredential(username, password, role);
			if(user != null) {
				loginStatus = true;
			}
			
		}catch(Exception e) {
			req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
			e.printStackTrace();
		}
		if(loginStatus) {
			HttpSession session = req.getSession();
//			session.setAttribute("username", username);
//			session.setAttribute("userrole", role);
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(60*60*12); //15 mins

			
			resp.sendRedirect(req.getContextPath()+"/dashboard");
			
		}else {
			req.setAttribute("msg", "Invalid Credentials !!");
			req.getRequestDispatcher("WEB-INF/views/login.jsp").forward(req, resp);
		}
		
	}
	
}
