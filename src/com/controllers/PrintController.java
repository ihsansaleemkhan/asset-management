package com.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.PrintDao;

@WebServlet("/printController")
public class PrintController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2218649545145749262L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		System.out.println("action:"+action);
		PrintDao printDao = null;
		String ApplicantTrainerData =null;
		Integer draw = 0;
		Integer length = 0;
		Integer start = 0;
		if("getData".equals(action)) {
			printDao = new PrintDao();
			String startDate = req.getParameter("startdate");
			String school = req.getParameter("school");
			String endDate = req.getParameter("endDate");
			if(req.getParameter("print")==null) {
			draw = Integer.parseInt(req.getParameter("draw"));
			length = Integer.parseInt(req.getParameter("length"));
			start = Integer.parseInt(req.getParameter("start"));
			System.out.println("drqw: "+draw+",  length: "+length +", start :"+ start);
			}
			try {
				ApplicantTrainerData = printDao.getDataToPrint(startDate, endDate, school,draw, length, start);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			resp.getWriter().write(ApplicantTrainerData);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//super.doPost(req, resp);
	}
	
	

}
