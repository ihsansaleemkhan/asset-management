package com.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.serviceImp.DevicesImp;

/**
 * Servlet implementation class DeviceController
 */
@WebServlet("/DeviceController")
public class DeviceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   DevicesImp devicesImp = new DevicesImp() ;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		System.out.println("device controller action :  " + action);
		if(action != null) {
			switch(action) {
			case "getUserAsset":
				getUserAsset(request,response);
				break;
			case "getAssetInfo":
				getAssetInformation(request,response);
				break;
			case "getAssignedUserName":
				getAssignedUserName(request, response);
				break;
			case "setCheckOut":
				setCheckOut(request, response);
				break;
			}
		} 
		
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void getUserAsset(HttpServletRequest request, HttpServletResponse response) {

//		request.getParameterMap();
		String check_in_date = request.getParameter("check_in_date") ;
		String check_out_date = request.getParameter("check_out_date") ;
		String employee_name_id = request.getParameter("employee_name_id");
		String isInUse = request.getParameter("inUse");
		System.out.println("check_in_date --> " + check_in_date +  "  check_out_date  ---> " + check_out_date + "  employee_name_id---> " + employee_name_id + "   isInUse ---> " + isInUse);
		Map<String,String> params = new HashMap<String,String>();
		params.put("check_in_date", check_in_date);
		params.put("check_out_date", check_out_date);
		params.put("employee_name_id", employee_name_id);
		params.put("isInUse", isInUse);
		
		String result = new Gson().toJson(devicesImp.getUserDevice(params));
		System.out.println("result is :  " + result);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void getAssetInformation(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String serialNumber = request.getParameter("serialNumber");
		String result = new Gson().toJson(devicesImp.getAssetInformation(serialNumber));
		System.out.println("result is :  " + result);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
			response.getWriter().write(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getAssignedUserName(HttpServletRequest request, HttpServletResponse response) {
		int userAssetId = Integer.parseInt(request.getParameter("userAssetId"));
		String result = devicesImp.getAssignedUserName(userAssetId);
		System.out.println("result is :  " + result);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		  try {
				response.getWriter().write(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	
	private void setCheckOut(HttpServletRequest request, HttpServletResponse response) {
		int userAssetId = Integer.parseInt(request.getParameter("userAssetId"));
		boolean result = devicesImp.setCheckOut(userAssetId);
		System.out.println("result is :  " + result);
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		  try {
				response.getWriter().write(String.valueOf(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}

}
