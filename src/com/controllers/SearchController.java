package com.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.SearchDao;

@WebServlet("/searchController")
public class SearchController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		System.out.println("action:"+action);
		if(("search").equals(action)) {
			String card_serial_no = req.getParameter("card_serial_no");
			System.out.println(card_serial_no);
			SearchDao searchDao = new SearchDao();
			String cardDetail = searchDao.getCardDetail(card_serial_no);
			System.out.println("cardDetail::::::::::::::::::::::::::"+cardDetail.length());
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(cardDetail);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	

}
