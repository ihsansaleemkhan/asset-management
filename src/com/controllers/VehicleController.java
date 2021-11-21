package com.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.beans.Drivers;
import com.beans.Model;
import com.beans.Vehicleholoteq;
import com.dao.VehicleDAO;
import com.google.gson.Gson;

@WebServlet("/VehicleController")
@MultipartConfig
public class VehicleController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static Logger logger = Logger.getLogger(VehicleController.class);

	public VehicleController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		System.out.println("action:"+action);
		
		switch (action) {
		case "add-vehicle":
			addVehicle(request, response);
			break;
		case "add-driver":
			addDriver(request, response);
			break;
		case "assign-driver":
			assignDriver(request, response);
			break;
		case "return-vehicle":
			returnVehicle(request, response);
			break;
		case "update-vehicle":
			updateVehicle(request, response);
			break;
		case "update-driver":
			updateDriver(request, response);
			break;
		case "delete-vehicle":
			deleteVehicle(request, response);
			break;
		case "delete-driver":
			deleteDriver(request, response);
			break;
		case "add-vehicle-services":
			addVehicleServices(request, response);
			break;
		case "add-accident":
		    addAccidentDetails(request, response);
		    break;
		case "add-penalty":
			addPenaltyDetails(request, response);
			break;
		}
	}
	
	private void addPenaltyDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
	try {
			int vehicle_id = Integer.parseInt(request.getParameter("pvehicle"));
			int driver_id = Integer.parseInt(request.getParameter("pdriver"));
			String penalty_cat = request.getParameter("penalty_cat");
			String penalty_date = request.getParameter("penalty_date");
			String remark = request.getParameter("remark");

		   isDone = VehicleDAO.addPenaltyDetails(vehicle_id,driver_id,penalty_cat,penalty_date,remark);
		
		} catch (SQLException e) {
			System.out.println("exception is : " + e.getMessage());
			e.printStackTrace();
			logger.error("Error on Vehicle Controller doPost[addPenaltyDetails]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		System.out.println("Data is :  "+isDone.toString());
		response.getWriter().write(isDone.toString());
	}

	private void addAccidentDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
	try {
			int vehicle_id = Integer.parseInt(request.getParameter("vehicle"));
			int driver_id = Integer.parseInt(request.getParameter("driver"));
			String date = request.getParameter("date");
			String remark = request.getParameter("remark");
			
			Part accident_img = request.getPart("accident_img");
			byte[] photoByte =null;
			if(accident_img != null){
				photoByte = IOUtils.toByteArray(accident_img.getInputStream());					
			}
			
			Part accident_img2 = request.getPart("accident_img2");
			byte[] photoByte2 =null;
			if(accident_img2 != null){
				photoByte2 = IOUtils.toByteArray(accident_img2.getInputStream());					
			}
			
			Part accident_img3 = request.getPart("accident_img3");
			byte[] photoByte3 =null;
			if(accident_img3 != null){
				photoByte3 = IOUtils.toByteArray(accident_img3.getInputStream());					
			}

			  isDone = VehicleDAO.addAccidentDetails(vehicle_id, driver_id, date, remark,photoByte,photoByte2,photoByte3);
		
		} catch (SQLException e) {
			System.out.println("exception is : " + e.getMessage());
			e.printStackTrace();
			logger.error("Error on Vehicle Controller doPost[addAccidentDetails]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());
		
	}

	private void addVehicleServices(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;

		try {
			
			int vehicle = Integer.parseInt(request.getParameter("vehicle"));
			int driver = Integer.parseInt(request.getParameter("driver"));
			int service_type = Integer.parseInt(request.getParameter("service_type"));
			String date = request.getParameter("date");
			double cost = Double.parseDouble(request.getParameter("cost"));
			String place = request.getParameter("place");
	
			  isDone = VehicleDAO.addVehicleServices(vehicle, driver, service_type, date, cost, place);
			  
			if(isDone){
				String success_message = "Successfully Added Vehicle Service Details!!";
			    request.setAttribute("success_message", success_message);
		        request.getServletContext().getRequestDispatcher("/WEB-INF/views/add_vehicle_services.jsp").forward(request, response);	
			}else {
				String fail_message = "Something Went wrong!";
			    request.setAttribute("fail_message", fail_message);
		        request.getServletContext().getRequestDispatcher("/WEB-INF/views/add_vehicle_services.jsp").forward(request, response);	
			}
			
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[addVehicleServices]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());
		
	}

	private void addVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
	
		try {
			String plate_no = request.getParameter("plate_no");
			String make = request.getParameter("make");
			String model = request.getParameter("model");
			String permit_date = request.getParameter("permit_date");
			
			Part permit_img = request.getPart("permit_img");
			byte[] photoByte =null;
			if(permit_img != null){
				photoByte = IOUtils.toByteArray(permit_img.getInputStream());					
			}
			
			Part permit_img2 = request.getPart("permit_img2");
			byte[] photoByte2 =null;
			if(permit_img2 != null){
				photoByte2 = IOUtils.toByteArray(permit_img2.getInputStream());					
			}
			
			Part vehicleImgePart = request.getPart("vehicle_img");
			byte[] vehicleByte =null;
			if(vehicleImgePart != null){
				vehicleByte = IOUtils.toByteArray(vehicleImgePart.getInputStream());					
			}
			
			Part vehicleImgePart2 = request.getPart("vehicle_img2");
			byte[] vehicleByte2 =null;
			if(vehicleImgePart2 != null){
				vehicleByte2 = IOUtils.toByteArray(vehicleImgePart2.getInputStream());					
			}
			
			Part vehicleImgePart3 = request.getPart("vehicle_img3");
			byte[] vehicleByte3 =null;
			if(vehicleImgePart3 != null){
				vehicleByte3 = IOUtils.toByteArray(vehicleImgePart3.getInputStream());					
			}
			
			Part vehicleImgePart4 = request.getPart("vehicle_img4");
			byte[] vehicleByte4 =null;
			if(vehicleImgePart4 != null){
				vehicleByte4 = IOUtils.toByteArray(vehicleImgePart4.getInputStream());					
			}
			
			boolean validate_plateno = VehicleDAO.validatePlateNo(plate_no);
			 System.out.println("validate plate no"+validate_plateno);
			if(!validate_plateno) {
			  isDone = VehicleDAO.addVehicle(plate_no, make, model, permit_date, 
					                        photoByte,photoByte2,vehicleByte,vehicleByte2,vehicleByte3,vehicleByte4);
			}else {
				isDone = false;	
				System.out.println("false Added");
			}
		} catch (SQLException e) {
			System.out.println("exception is : " + e.getMessage());
			e.printStackTrace();
			logger.error("Error on Vehicle Controller doPost[addVehicle]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		System.out.println("Data is :  "+isDone.toString());
		response.getWriter().write(isDone.toString());

	}

	private void addDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;

		try {
			
			String driver_name = request.getParameter("driver_name");
			String license_no = request.getParameter("license_no");
			String given_date = request.getParameter("given_date");
			String vehicle = request.getParameter("assign_vehicle_id");
			
			Part license_img = request.getPart("license_img");
			byte[] photoByte =null;
			if(license_img != null){
				photoByte = IOUtils.toByteArray(license_img.getInputStream());					
			}
			
			Part profImgePart = request.getPart("profile_img");
			byte[] profByte =null;
			if(profImgePart != null){
				profByte = IOUtils.toByteArray(profImgePart.getInputStream());					
			}
			
			boolean validate_license_no = VehicleDAO.validateLicenseNo(license_no);
			 System.out.println("validate plate no"+validate_license_no);
			if(validate_license_no) {
			  isDone = VehicleDAO.addDriver(driver_name,license_no,photoByte,given_date,vehicle,profByte);
			}else {
				isDone = false;	
				System.out.println("false Added");
			}
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[addDrivers]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void assignDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;

		try {
			
			int vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
			int driver_id = Integer.parseInt(request.getParameter("driver_id"));
			
			isDone = VehicleDAO.assignDriver(vehicle_id, driver_id);
			if(isDone) {
				VehicleDAO.assignDriverToVehicle(vehicle_id, driver_id);
			}

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[assignDriver]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void returnVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;

		try {
			
			int vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
			int driver_id = Integer.parseInt(request.getParameter("driver_id"));
			String remark=request.getParameter("remark");
			isDone=VehicleDAO.returnVehicle(vehicle_id, driver_id, remark);
			if(isDone) {
				isDone = VehicleDAO.returnVehicle(vehicle_id, driver_id);
			}

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[assignDriver]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void updateVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
		try {
			
			int vehicle_id = Integer.parseInt(request.getParameter("vehicle_id"));
			String plate_no = request.getParameter("plate_no");
			String vehiclePlateNo = request.getParameter("vehicle_plateno");
			String permit_date = request.getParameter("permit_date");
			String make = request.getParameter("make");
			String model = request.getParameter("model");
			
			Part permit_img = request.getPart("update_permit_img");
			byte[] photoByte =null;
			if(permit_img != null){
				photoByte = IOUtils.toByteArray(permit_img.getInputStream());					
			}
			
			Part permit_img2 = request.getPart("update_permit_img2");
			byte[] photoByte2 =null;
			if(permit_img2 != null){
				photoByte2 = IOUtils.toByteArray(permit_img2.getInputStream());					
			}
			
			Part vehicleImgePart = request.getPart("update_vehicle_img");
			byte[] vehicleByte =null;
			if(vehicleImgePart != null){
				vehicleByte = IOUtils.toByteArray(vehicleImgePart.getInputStream());					
			}
			
			Part vehicleImgePart2 = request.getPart("update_vehicle_img2");
			byte[] vehicleByte2 =null;
			if(vehicleImgePart2 != null){
				vehicleByte2 = IOUtils.toByteArray(vehicleImgePart2.getInputStream());					
			}
			
			Part vehicleImgePart3 = request.getPart("update_vehicle_img3");
			byte[] vehicleByte3 =null;
			if(vehicleImgePart3 != null){
				vehicleByte3 = IOUtils.toByteArray(vehicleImgePart3.getInputStream());					
			}
			
			Part vehicleImgePart4 = request.getPart("update_vehicle_img4");
			byte[] vehicleByte4 =null;
			if(vehicleImgePart4 != null){
				vehicleByte4 = IOUtils.toByteArray(vehicleImgePart4.getInputStream());					
			}

			boolean validate_plateno =false;
			if(vehiclePlateNo != null && plate_no != null) {
				if(!vehiclePlateNo.equals(plate_no)) {
					validate_plateno=VehicleDAO.validatePlateNo(plate_no);
				}
			}

			if(validate_plateno) {
				isDone = false;	
			}else {
				isDone = VehicleDAO.updateVehicle(vehicle_id, plate_no, permit_date, make, model,photoByte,photoByte2,
						vehicleByte,vehicleByte2,vehicleByte3,vehicleByte4);
			}

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[updateVehicle]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void updateDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
		boolean validate_license_no =true;
		try {
			
					
			int driver_id = Integer.parseInt(request.getParameter("driver_id"));
			String driver_name = request.getParameter("driver_name");
			String given_date = request.getParameter("given_date");
			String license_no = request.getParameter("license_no");
			String licenseNo=request.getParameter("licenseNo");
			
			Part license_img = request.getPart("update_license_img");
			byte[] photoByte =null;
			if(license_img != null){
				photoByte = IOUtils.toByteArray(license_img.getInputStream());					
			}
			
			Part profImgePart = request.getPart("update_profile_img");
			byte[] profByte =null;
			if(profImgePart != null){
				profByte = IOUtils.toByteArray(profImgePart.getInputStream());					
			}			
			
			 System.out.println("License NO 1"+license_no);
			 System.out.println("License NO 2"+licenseNo);
			 
			if(licenseNo != null && license_no != null) {
				if(!licenseNo.equals(license_no)) {
				 validate_license_no = VehicleDAO.validateLicenseNo(license_no);
				}			    
			}
			
			if(!validate_license_no) {
				isDone = false;
			}else {				
				 System.out.println("driver id"+driver_id);
				 isDone = VehicleDAO.updateDriver(driver_id, driver_name, given_date, license_no,photoByte,profByte);				
			}

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[updateDriver]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void deleteVehicle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
		try {
			
			int vehicle_id = Integer.parseInt(request.getParameter("id"));
			String reason = request.getParameter("reason");			
			
			//System.out.println("vehicle id"+vehicle_id);
			//System.out.println("reason--->"+reason);
			
			isDone = VehicleDAO.deleteVehicle(vehicle_id, reason);

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[deleteVehicle]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	private void deleteDriver(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Boolean isDone = false;
		try {
			
			int driver_id = Integer.parseInt(request.getParameter("id"));
			String reason = request.getParameter("reason");
						
			//System.out.println("driver id"+driver_id);
			//System.out.println("reason--->"+reason);
			
			isDone = VehicleDAO.deleteDriver(driver_id, reason);

		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[deleteDriver]: ", e);
		}

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(isDone.toString());

	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action") != null) {
			
			String action = request.getParameter("action");
			
			System.out.println("Action here ="+action);
			
			
			switch(action) {
				case "fetch-vehicle-list":
					fetchVehicleList(request,response);
					break;		
				case "fetch-driver-list":
					fetchDriverList(request,response);
					break;
				case "get-vehicle-pro-image":
					getVehicleImage(request,response);
					break;
				case "get-vehicle-image":
					getVehiclePermitImage(request,response);
					break;
				case "get-driver-image":
					getDriverImage(request,response);
					break;
				case "get-license-image":
					getDriverLicenseImage(request,response);
					break;
				case "fetch-vehicle":
					fetchVehicleById(request,response);
					break;
				case "fetch-driver":
					fetchDriverById(request,response);
					break;
				case "fetch-vehicle-report":
					fetchDriverById(request,response);
					break;
				case "fetch-models":
					fetchVehicleModels(request, response);
					break;
				case "fetch-vehicle-services":
					fetchVehicleServicesList(request, response);
					break;
				case "fetch-vehicle-accidents":
					fetchVehicleAccidentsList(request, response);
					break;
				case "fetch-penalty-list":
					fetchVehiclePenaltyList(request, response);
					break;
			}
		}
	}
	
	private void fetchVehiclePenaltyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info(" ---  Enter to fetchVehiclePenaltyList() Methos  --- ");
		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));

		Map<String, String> filters = new HashMap<String, String>();
		
		if(request.getParameter("search_filter") != null) {
			filters.put("search_filter", request.getParameter("search_filter"));
		}
		if(request.getParameter("date") != null) {
			filters.put("date", request.getParameter("date"));
		}
		if(request.getParameter("penalty_vehicle") != null) {
			filters.put("penalty_vehicle", request.getParameter("penalty_vehicle"));
		}
		if(request.getParameter("penalty_driver") != null) {
			filters.put("penalty_driver", request.getParameter("penalty_driver"));
		}
		String vehiclePenaltyList = null;
		try {
			vehiclePenaltyList = VehicleDAO.fetchVehiclePenaltyList(filters, draw, length, start);
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[fetchVehiclePenaltyList]: ", e);
		}
		response.setContentType("vehicleAccidents/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vehiclePenaltyList);
		logger.info(" ---  End to fetchVehiclePenaltyList() Methos  --- ");
	}

	private void fetchVehicleAccidentsList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		logger.info(" ---  Enter to fetchVehicleAccidentsList() Methos  --- ");
		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));

		Map<String, String> filters = new HashMap<String, String>();
		
		if(request.getParameter("search_filter") != null) {
			filters.put("search_filter", request.getParameter("search_filter"));
		}
		if(request.getParameter("date") != null) {
			filters.put("date", request.getParameter("date"));
		}
		if(request.getParameter("accident_vehicle") != null) {
			filters.put("accident_vehicle", request.getParameter("accident_vehicle"));
		}
		if(request.getParameter("accident_driver") != null) {
			filters.put("accident_driver", request.getParameter("accident_driver"));
		}
		String vehicleAccidents = null;
		try {
			vehicleAccidents = VehicleDAO.fetchVehicleAccidentsList(filters, draw, length, start);
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[fetchVehicleAccidentsList]: ", e);
		}
		response.setContentType("vehicleAccidents/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vehicleAccidents);
		logger.info(" ---  End to fetchVehicleAccidentsList() Methos  --- ");
		
	}

	private void fetchVehicleServicesList(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info(" ---  Enter to fetchVehicleServicesList() Methos  --- ");
		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));

		Map<String, String> filters = new HashMap<String, String>();
		
		if(request.getParameter("search_filter") != null) {
			filters.put("search_filter", request.getParameter("search_filter"));
		}
		if(request.getParameter("date") != null) {
			filters.put("date", request.getParameter("date"));
		}
		if(request.getParameter("vehicle") != null) {
			filters.put("vehicle", request.getParameter("vehicle"));
		}
		if(request.getParameter("service_type") != null) {
			filters.put("service_type", request.getParameter("service_type"));
		}
		String vehicleServices = null;
		try {
			vehicleServices = VehicleDAO.fetchVehicleServicesList(filters, draw, length, start);
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller doPost[fetchVehicleServicesList]: ", e);
		}
		response.setContentType("vehicleServices/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vehicleServices);
		logger.info(" ---  End to fetchVehicleServicesList() Methos  --- ");
		
	}

	protected void fetchVehicleList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
		try {
			
			String vehicles = VehicleDAO.fetchVehicleList();
			
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(vehicles);
    	    
		} catch (SQLException e) {
			System.out.println("Error Fetching All fetchVehicleList [Vehicle Controller]" + e);
		}
	}
	
	protected void fetchVehicleById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		try {
			
			String vehicle = VehicleDAO.fetchVehicleById(id);
			
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(vehicle);
    	    
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller fetchVehicle[Fetch Vehicel]:", e);
		}
	}
	
	protected void fetchDriverList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
		try {
			
			String drivers = VehicleDAO.fetchDriverList();
			
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(drivers);
    	    
		} catch (SQLException e) {
			System.out.println("Error Fetching All fetchDriverList [Vehicle Controller]" + e);
		}
	}
	
	
	protected void fetchDriverById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer id = Integer.parseInt(request.getParameter("id"));
		try {
			
			String driver = VehicleDAO.fetchDriverById(id);
			
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(driver);
    	    
		} catch (SQLException e) {
			logger.error("Error on Vehicle Controller fetchDriverById[Fetch Driver]:", e);
		}
	}
	
	
	protected void fetchVehicleReportList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vehiclesJSON = "";  
		List<Vehicleholoteq> vehicleList =null;
		try {
			String dateString = request.getParameter("reason");
			vehicleList= VehicleDAO.fetchVehicleReportList(dateString);
			if(vehicleList != null) {
			vehiclesJSON = new Gson().toJson(vehicleList);
			}
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(vehiclesJSON);
    	    
		} catch (SQLException e) {
			System.out.println("Error Fetching All fetchVehicleList [Vehicle Controller]" + e);
		}
	}

	protected void getDriverImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if(idStr != null && !idStr.equals("")){
			int id = Integer.parseInt(idStr);
			byte[] driverImgData;
			try {
				driverImgData = new VehicleDAO().getDriverImages(id);
				response.setContentType("image/gif");
			    OutputStream o = response.getOutputStream();
				if(driverImgData !=null){				
				      o.write(driverImgData);				     
				}else {
					o.write(getNoUserImage());
				}
				o.flush();
			    o.close();
			} catch (Exception e) {
				logger.error("Error on Vehicle Controller doGet[Fetch Driver Lecense Image]:", e);
			}
		}	
	}
	
	protected void getDriverLicenseImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		if(idStr != null && !idStr.equals("")){
			int id = Integer.parseInt(idStr);
			byte[] LicenseImgData;
			try {
				LicenseImgData = new VehicleDAO().getDriverLicenseImages(id);
				OutputStream o = response.getOutputStream();
				if(LicenseImgData !=null){				
				      o.write(LicenseImgData);				     
				}else {
					o.write(getNoUserImage());
				}
				o.flush();
			    o.close();
			} catch (Exception e) {
				logger.error("Error on Vehicle Controller doGet[Fetch Driver Lecense Image]:", e);
			}
		}	
	}
	
	
	protected void getVehicleImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String no =request.getParameter("no");		
		
		if(idStr != null && !idStr.equals("")){
			int id = Integer.parseInt(idStr);
			byte[] vehicleImgData;
			try {
				File file=new File("images/not.png");
				vehicleImgData = new VehicleDAO().getVehicleProImages(id,no);
				response.setContentType("image/gif");
			    OutputStream o = response.getOutputStream();
				if(vehicleImgData !=null){					  
				   o.write(vehicleImgData);
				}else {
				   o.write(getNoImage());				     
				}
				 o.flush();
			     o.close();
			} catch (Exception e) {
				logger.error("Error on Vehicle Controller doGet[Fetch Permit Image]:", e);
			}
		}	
	}
	
	protected void getVehiclePermitImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String no =request.getParameter("no");	
		if(idStr != null && !idStr.equals("")){
			int id = Integer.parseInt(idStr);
			byte[] vehicleImgData;
			try {
				vehicleImgData = new VehicleDAO().getVehicleImages(id,no);
				response.setContentType("image/gif");
			    OutputStream o = response.getOutputStream();
				if(vehicleImgData !=null){					  
				   o.write(vehicleImgData);
				}else {
				   o.write(getNoImage());				     
				}
				 o.flush();
			     o.close();
			} catch (Exception e) {
				logger.error("Error on Vehicle Controller doGet[Fetch Permit Image]:", e);
			}
		}	
	}
	
	private byte[] getNoImage() {
		  BufferedImage bufferimage;
		
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      try {
	    	  InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("not.png");
		    	bufferimage = ImageIO.read(inputStream);
			ImageIO.write(bufferimage, "jpg", output );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      byte [] data = output.toByteArray();
	      
	      return data;
	}
	
	private byte[] getNoUserImage() {
		  BufferedImage bufferimage;
		
	      ByteArrayOutputStream output = new ByteArrayOutputStream();
	      try {
	    	InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("no_image_user.png");
	    	bufferimage = ImageIO.read(inputStream);
			ImageIO.write(bufferimage, "jpg", output );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      byte [] data = output.toByteArray();
	      
	      return data;
	}
	
	
	/**
	 * Fetch All Vehicles Model
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void fetchVehicleModels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String modelsJSON = "";  
		List<Model> models =null;
		try {
			String makeIdStr = request.getParameter("makeId");
			models=new VehicleDAO().getAllModelsByMake(makeIdStr);
			if(models != null) {
				modelsJSON = new Gson().toJson(models);
			}
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    
    	    System.out.println("Model Json="+modelsJSON);
    	    response.getWriter().write(modelsJSON);
    	    
		} catch (SQLException e) {
			System.out.println("Error Fetching All fetchVehicleList [Vehicle Controller]" + e);
		}
	}

}
