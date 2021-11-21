package com.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dao.AssetDAO;
import com.dao.VehicleDAO;
import com.google.gson.Gson;

@WebServlet("/AssetController")
public class AssetController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   
   static Logger logger=Logger.getLogger(AssetController.class);
   
   public AssetController() {
	   super();
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	String action = request.getParameter("action");
	
	switch(action) {
	   case "add-assets":
		   addAssetsDetails(request,response);
		   break;
	}
	
   }

private void addAssetsDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	Boolean isDone = false;
	
	Map<String, String[]> params = request.getParameterMap();
	
	System.out.println("Params --> " + new Gson().toJson(params));

	try{
		System.out.println("I am inside add assets details method Asset Controller");
		//Asset Info
		int asset_type_id = Integer.parseInt(request.getParameter("asset_type"));
		String country = request.getParameter("country");
		String city = request.getParameter("city");
		String site = request.getParameter("site");
		String barcode = request.getParameter("barcode");
		String tag_name = request.getParameter("tag_name");
		
		//Tech Info
		String make = request.getParameter("make");
		String model = request.getParameter("model");
		String mac_address = request.getParameter("mac_address");
		String processor = request.getParameter("processor");
		String os = request.getParameter("os");
		String hard_disk = request.getParameter("hard_disk");
		String memory = request.getParameter("memory");
		String procurred_date = request.getParameter("procurred_date");
		String warranty_exp_date = request.getParameter("warranty_exp_date");
		String warranty_amc = request.getParameter("warranty_amc");
		String remark = request.getParameter("remarks");
		
		//Emp_info
		int emp_id = Integer.parseInt(request.getParameter("emp"));
		
		System.out.println("---<asset_type>---"+asset_type_id);
		System.out.println("---<country>---"+country);
		System.out.println("---<city>---"+city);
		System.out.println("---<site>---"+site);
		System.out.println("---<barcode>---"+barcode);
		System.out.println("---<tag_name>---"+tag_name);
		System.out.println("---<make>---"+make);
		System.out.println("---<model>---"+model);
		System.out.println("---<mac_address>---"+mac_address);
		System.out.println("---<os>---"+os);
		System.out.println("---<hard_disk>---"+hard_disk);
		System.out.println("---<memory>---"+memory);
		System.out.println("---<procurred_date>---"+procurred_date);
		System.out.println("---<warranty_exp_date>---"+warranty_exp_date);
		System.out.println("---<warranty_amc>---"+warranty_amc);
		System.out.println("---<remark>---"+remark);
		System.out.println("---<emp_id>---"+emp_id);
		
		isDone = AssetDAO.addAssetInfo(asset_type_id, country, city, site, barcode, tag_name, make, model, mac_address, processor, os, hard_disk, memory, procurred_date, warranty_exp_date, warranty_amc, remark, emp_id);
		
	}catch(SQLException e) {
		System.out.println("exception is : " + e.getMessage());
		e.printStackTrace();
		logger.error("Error on Asset Controller doPost[addAssetsDetails]: ", e);
	}
	response.setContentType("text/html");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(isDone.toString());
}

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	if(request.getParameter("action") != null) {
		String action = request.getParameter("action");
		
		switch(action) {
			case "get-models-by-make":
				fetchModelsByMake(request,response);
				break;
			case "getEmployeeDetails":
				fetchEmpolyeeDetails(request, response);
				break;
			case "fetch-servers-list":
				fetchServersList(request, response);
				break;
			case "fetch-mobiles-list":
				fetchMobilesList(request, response);
				break;
			case "fetch-laptops-list":
				fetchLaptopsList(request, response);
				break;
			case "fetch-desktop-list":
			    fetchDesktopList(request, response);
			    break;
			case "fetch-monitor-list":
				fetchMonitorsList(request, response);
				break;
			case "fetch-printers-list":
				fetchPrintersList(request, response);
				break;
			case "fetch-ups-list":
				fetchUPSList(request, response);
				break;
			case "fetch-tab-list":
				fetchTabsList(request, response);
				break;
			case "fetch-powerbank-list":
				fetchPowerBanksList(request, response);
				break;
			case "getAssetDetails":
				getAssetDetails(request, response);
				break;
		}
	}
  }

private void getAssetDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
	Integer asset_id = Integer.parseInt(request.getParameter("asset_id"));
    System.out.println("Asset id "+asset_id);
	String asset_details = null;
	
	try {
		asset_details = AssetDAO.getAssetDetails(asset_id);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchPowerBanksList]: ", e);
	}
	
	response.setContentType("asset_details/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(asset_details);
}

private void fetchPowerBanksList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchPowerBanksList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String power_banks = null;
	try {
		power_banks = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchPowerBanksList]: ", e);
	}
	response.setContentType("power_banks/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(power_banks);
	logger.info(" ---  End to fetchPowerBanksList() Method  --- ");
}

private void fetchTabsList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchTabsList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String tabs = null;
	try {
		tabs = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchTabsList]: ", e);
	}
	response.setContentType("ups/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(tabs);
	logger.info(" ---  End to fetchTabsList() Method  --- ");
}

private void fetchUPSList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchUPSList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String ups = null;
	try {
		ups = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchUPSList]: ", e);
	}
	response.setContentType("ups/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(ups);
	logger.info(" ---  End to fetchUPSList() Method  --- ");
}

private void fetchPrintersList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchPrintersList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String printers = null;
	try {
		printers = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchPrintersList]: ", e);
	}
	response.setContentType("printers/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(printers);
	logger.info(" ---  End to fetchPrintersList() Method  --- ");
}

private void fetchMonitorsList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchMonitorsList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String monitors = null;
	try {
		monitors = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchLaptopsList]: ", e);
	}
	response.setContentType("monitors/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(monitors);
	logger.info(" ---  End to fetchMonitorsList() Method  --- ");
}

private void fetchDesktopList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchDesktopList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String desktop = null;
	try {
		desktop = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchLaptopsList]: ", e);
	}
	response.setContentType("mobiles/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(desktop);
	logger.info(" ---  End to fetchDesktopList() Method  --- ");
}

private void fetchLaptopsList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchLaptopsList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String laptops = null;
	try {
		laptops = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchLaptopsList]: ", e);
	}
	response.setContentType("laptops/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(laptops);
	logger.info(" ---  End to fetchLaptopsList() Method  --- ");
}

private void fetchMobilesList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchMobilesList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
    System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String mobiles = null;
	try {
		mobiles = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchMobilesList]: ", e);
	}
	response.setContentType("mobiles/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(mobiles);
	logger.info(" ---  End to fetchMobilesList() Method  --- ");
}

private void fetchServersList(HttpServletRequest request, HttpServletResponse response) throws IOException {
	logger.info(" ---  Enter to fetchServersList() Method  --- ");
	Integer draw = Integer.parseInt(request.getParameter("draw"));
	Integer length = Integer.parseInt(request.getParameter("length"));
	Integer start = Integer.parseInt(request.getParameter("start"));
	Integer asset_type = Integer.parseInt(request.getParameter("asset_type"));
       System.out.println("Asset Type "+asset_type);
	Map<String, String> filters = new HashMap<String, String>();
	
	if(request.getParameter("search_filter") != null) {
		filters.put("search_filter", request.getParameter("search_filter"));
	}
	String servers = null;
	try {
		servers = AssetDAO.fetchAssetListByAssetType(filters, draw, length, start, asset_type);
	} catch (SQLException e) {
		logger.error("Error on Asset Controller doPost[fetchServersList]: ", e);
	}
	response.setContentType("servers/json");
	response.setCharacterEncoding("UTF-8");
	response.getWriter().write(servers);
	logger.info(" ---  End to fetchServersList() Method  --- ");
}

private void fetchEmpolyeeDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	Integer id = Integer.parseInt(request.getParameter("id"));
	try {
    	   String emp_info = AssetDAO.getEmpDetails(id);
    	   
    	   response.setContentType("application/json");
		   response.setCharacterEncoding("UTF-8");
		   response.getWriter().write(emp_info);
		   
     } catch(SQLException e) {
    	System.out.println("Error fetchEmpolyeeDetails method "+ e); 
     }
}

private void fetchModelsByMake(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	Integer makeId = Integer.parseInt(request.getParameter("make_id"));
	try {
		
		String models = new AssetDAO().getModelsByMake(makeId);
		
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(models);
	    
	} catch (SQLException e) {
		System.out.println("Error fetchModelsByMake[Asset Controller]" + e);
	}
  }
}
