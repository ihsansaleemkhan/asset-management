package com.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.beans.User;
import com.beans.Users;
import com.dao.CardboxDAO;
import com.dao.UserDao;
import com.google.gson.Gson;

@WebServlet("/UserController")
@MultipartConfig(maxFileSize = 16177215)
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(UserController.class);

	public UserController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "add-user":
			addNewUser(request, response);
			break;
		case "update-user":
			updateUser(request, response);
			break;
		case "deleteUser":
			deleteUser(request, response);
			break;
		case "get-employee-info":
			getEmployeeInfo(request, response);
			break;
		case "add-employee":
			addEmployee(request, response);
			break;
		case "update-employee":
			updateEmployee(request, response);
			break;
		case "deleteEmployee":
			deleteEmployee(request ,response);
			break;
		case "search-employee":
			serachEmployee(request ,response);
			break;
		}
		
	}






	
	private void addNewUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		User user = new User();
		
		if(request.getParameter("dash_board_tab") != null) {
			user.setHaveDashBoardAccess(true);
		}else {
			user.setHaveDashBoardAccess(false);
		}
		
		
		if(request.getParameter("material_tab") != null) {
			user.setHaveMaterialAccess(true);
		}else {
			user.setHaveMaterialAccess(false);
		}
		
		
		if(request.getParameter("dts_card_tab") != null) {
			user.setHaveDtsCardAccess(true);
		}else {
			user.setHaveDtsCardAccess(false);
		}
		
		
		if(request.getParameter("vehicle_tab") != null) {
			user.setHaveVehicleAccess(true);
		}else {
			user.setHaveVehicleAccess(false);
		}
		
		if(request.getParameter("hardware_tab") != null) {
			user.setHaveHardwareAccess(true);
		}else {
			user.setHaveHardwareAccess(false);
		}
		
		if(request.getParameter("manage_user_tab") != null) {
			user.setHaveManageUsersAccess(true);
		}else {
			user.setHaveManageUsersAccess(false);
		}
		
		if(request.getParameter("add_asset_tab") != null) {
			user.setHaveAddAssetAccess(true);
		}else {
			user.setHaveAddAssetAccess(false);
		}
		
		
		if(request.getParameter("add_employee_tab") != null) {
			user.setHaveAddEmployeeAccess(true);
		}else {
			user.setHaveAddEmployeeAccess(false);
		}
		
		UserDao userdao = new UserDao();
		

		user.setUser_name(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setRold_id(Integer.valueOf(request.getParameter("role")));
		user.setEmployee_id(Integer.valueOf(request.getParameter("employee-id")));
		boolean isDone;
		boolean checkUsername;
		String fail_message = null;
		try {

			checkUsername = userdao.checkUsername(user.getUser_name());

			if (checkUsername) {
				fail_message = "Username has been already taken, please try another";
				request.setAttribute("fail_message", fail_message);
				request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
						response);
				System.out.println("sadfasf asdfas " + fail_message);
				return;
			}

			if (fail_message == null) {
				isDone = userdao.addUser(user);

				if (isDone) {
					String success_message = "Successfully Added User !!";
					request.setAttribute("success_message", success_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
					System.out.println("sadfasf asdfas " + success_message);
				} else {
					fail_message = "something Went wrong!";
					request.setAttribute("fail_message", fail_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
					System.out.println("sadfasf asdfas " + fail_message);
				}
			}

		} catch (SQLException e) {
			logger.error("Error on User Controller doPost[Add User]: ", e);
		}

	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		UserDao userdao = new UserDao();
		User user = new User();

		user.setId(Integer.valueOf(request.getParameter("user_id")));
		user.setUser_name(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setRold_id(Integer.valueOf(request.getParameter("user_role")));
		user.setEmployee_id(Integer.valueOf(request.getParameter("employee-id")));
		if(request.getParameter("dash_board_tab") != null) {
			user.setHaveDashBoardAccess(true);
		}else {
			user.setHaveDashBoardAccess(false);
		}
		
		
		if(request.getParameter("material_tab") != null) {
			user.setHaveMaterialAccess(true);
		}else {
			user.setHaveMaterialAccess(false);
		}
		
		
		if(request.getParameter("dts_card_tab") != null) {
			user.setHaveDtsCardAccess(true);
		}else {
			user.setHaveDtsCardAccess(false);
		}
		
		
		if(request.getParameter("vehicle_tab") != null) {
			user.setHaveVehicleAccess(true);
		}else {
			user.setHaveVehicleAccess(false);
		}
		
		if(request.getParameter("hardware_tab") != null) {
			user.setHaveHardwareAccess(true);
		}else {
			user.setHaveHardwareAccess(false);
		}
		
		if(request.getParameter("manage_user_tab") != null) {
			user.setHaveManageUsersAccess(true);
		}else {
			user.setHaveManageUsersAccess(false);
		}
		
		if(request.getParameter("add_asset_tab") != null) {
			user.setHaveAddAssetAccess(true);
		}else {
			user.setHaveAddAssetAccess(false);
		}
		
		
		if(request.getParameter("add_employee_tab") != null) {
			user.setHaveAddEmployeeAccess(true);
		}else {
			user.setHaveAddEmployeeAccess(false);
		}
		
		boolean isDone;
		String fail_message = null;
		try {

			if (fail_message == null) {
				isDone = userdao.updateUser(user);

				if (isDone) {
					String success_message = "Successfully Updated User !!";
					request.setAttribute("success_message", success_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
				} else {
					fail_message = "something Went wrong!";
					request.setAttribute("fail_message", fail_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
				}
			}

		} catch (SQLException e) {
			logger.error("Error on User Controller doPost[Add User]: ", e);
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		switch (action) {
		case "getUser":
			fetchUserDetails(request, response);
			break;
		}

	}

	protected void fetchUserDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		System.out.println("user_id = " + user_id);
		try {

			String user_detail = new UserDao().getUserDeatil(user_id);
			System.out.println("user_detail :" + user_detail);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(user_detail);

		} catch (SQLException e) {
			System.out.println("Error fetchUserDetails [User Controller]" + e);
		}
	}

/*	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("req.getParameter(\"userid\")::::" + request.getParameter("user_id"));

		String action = request.getParameter("action");

		switch (action) {
		case "deleteUser":
			deleteUser(request, response);
			break;
		}
	}*/

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDao userdao = new UserDao();

		Integer user_id = Integer.parseInt(request.getParameter("user_id"));
		System.out.println("user_id = " + user_id);

		boolean isDone;
		String fail_message = null;

		try {

			if (fail_message == null) {
				isDone = userdao.deleteUser(user_id);

				if (isDone) {
					String success_message = "Successfully Deleted User !!";
					request.setAttribute("success_message", success_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
				} else {
					fail_message = "something Went wrong!";
					request.setAttribute("fail_message", fail_message);
					/*
					 * System.out.println(request.getServletContext().getRequestDispatcher(
					 * "/WEB-INF/views/carton_box.jsp").toString());
					 */
					request.getServletContext().getRequestDispatcher("/WEB-INF/views/manage_users.jsp").forward(request,
							response);
				}
			}

		} catch (SQLException e) {
			logger.error("Error on User Controller doPost[Add User]: ", e);
		}

	}
	
	private void getEmployeeInfo(HttpServletRequest request, HttpServletResponse response) {
		String result = new Gson().toJson(new UserDao().getEmployeeInfoList());
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
	


	private void addEmployee(HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String mobileNumber = request.getParameter("mobileNumber");
		String department = request.getParameter("department");
		Map<String,String> params = new HashMap<String,String>();
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("email", email);
		params.put("mobileNumber", mobileNumber);
		params.put("department", department);
		
		boolean result = new UserDao().addEmployee(params);
		System.out.println("result is :  " + result);
		response.setContentType("text/html");
		  try {
				response.getWriter().write(String.valueOf(result));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) {
		String employeeId = request.getParameter("employeeId");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String mobileNumber = request.getParameter("mobileNumber");
		String department = request.getParameter("department");
		Map<String,String> params = new HashMap<String,String>();
		params.put("employeeId", employeeId);
		params.put("firstName", firstName);
		params.put("lastName", lastName);
		params.put("email", email);
		params.put("mobileNumber", mobileNumber);
		params.put("department", department);
		
		boolean result = new UserDao().updateEmployee(params);
		System.out.println("result is :  " + result);
		response.setContentType("text/html");
		  try {
				response.getWriter().write(String.valueOf(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) {
		int emp_id = Integer.parseInt(request.getParameter("emp_id"));

		boolean result = new UserDao().deleteEmployee(emp_id);
		System.out.println("result is :  " + result);
		response.setContentType("text/html");
		  try {
				response.getWriter().write(String.valueOf(result));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void serachEmployee(HttpServletRequest request, HttpServletResponse response) {
		String name_email_id = request.getParameter("searchField") ;
		
		String result = new Gson().toJson(new UserDao().searchEmployee(name_email_id));
		System.out.println("result is :  " + result);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    try {
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}
