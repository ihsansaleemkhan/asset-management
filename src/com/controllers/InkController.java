package com.controllers;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import com.dao.PrinterinkDAO;
import com.google.gson.Gson;


@WebServlet("/InkController")
public class InkController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static Logger logger=Logger.getLogger(InkController.class);
 
    public InkController() {
        super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	      String action = request.getParameter("action");
			
			switch(action) {
			    case "request-ink": 
				    requestInk(request, response);
				    break;
				case "add-printer-ink": 
					addPrinterInkDetails(request, response);
					break;
				case "deliver-printer-ink":
					deliverPrinterInk(request,response);
					break;
			}
		
	}
	
	private void deliverPrinterInk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isDone = false; 
		
		try {
			String barcode = request.getParameter("barcode");
			String location =  request.getParameter("location");
			String delivered_date = request.getParameter("delivered_date");
			String delivered_by = request.getParameter("delivered_by");
			String sent_by = request.getParameter("sent_by");
			Integer ink_id = Integer.parseInt(request.getParameter("ink_id"));
			Integer barcode_id = Integer.parseInt(request.getParameter("barcode_id"));
			String device_no = request.getParameter("device_no");
						
		    isDone = PrinterinkDAO.addDeliveredInk(barcode_id, ink_id, barcode, location, device_no, delivered_date, delivered_by,sent_by);
			
		} catch(SQLException e) {
			logger.error("Error on Ink Controller doPost[deliverPrinterInk]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
		
	}

	private void requestInk(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isDone = false;
		String via = null;
		try {
			Integer order_no = Integer.parseInt(request.getParameter("order_no"));
			String model =  request.getParameter("model");
			Integer qty = Integer.parseInt(request.getParameter("qty"));
			String cat = request.getParameter("category");
			String req_date = request.getParameter("requested_date");
			String order_recieved = request.getParameter("order_recieved");
			String call = request.getParameter("call");
			String email = request.getParameter("email");
			String message = request.getParameter("message");
			
			if(call == null && email == null) {
				 via = message;
			}else if(call == null && message == null) {
				 via = email;
			}else if(message == null && email == null) {
				 via = call;
			}else if(call == null) {
				 via = email+ " , "+message;
			}else if(email == null) {
				 via = call+ " , "+message;
			}else if(message == null) {
				 via = call+ " , "+email;
			}else {
				 via = call+ " , "+email+ " , "+message;
			}
		
			String supplier = request.getParameter("supplier");
			String req_by = request.getParameter("requested_by");
			
			if(call != null || email != null || message != null) {
			 isDone = PrinterinkDAO.addRequestedInk(order_no, model, qty, cat, req_date, order_recieved, via, supplier, req_by);
			}else {
				isDone = false;
			}
			
		} catch (SQLException e) {
			logger.error("Error on Ink Controller doPost[Add requestInk]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
		
	}

	protected void addPrinterInkDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isDone = false;	
		try {
			String[] barcodes = request.getParameterValues("barcodes[]");
			int req_id = Integer.parseInt(request.getParameter("order_no_id"));
			String model = request.getParameter("model");
			String cat =  request.getParameter("category");
			String date = request.getParameter("stock_in_date");
		    Integer qty = Integer.parseInt(request.getParameter("qty"));
			String supplier = request.getParameter("supplier");
			String shipment = request.getParameter("shipment");
			String recieved_by = request.getParameter("received_by");
			String status = "in-stock";
			
		    isDone = PrinterinkDAO.addPrinterInk(barcodes,req_id,model,cat,date,qty,supplier,shipment,recieved_by,status);
			System.out.println("isDone-->"+isDone);
		} catch (SQLException e) {
			logger.error("Error on InkController doPost[addPrinterInkDetails]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String action = request.getParameter("action");
	
	switch(action) {
		case "getPrinterInkDetail":
			getPrinterInkDetail(request,response);
			break;
		case "getRibbonBarcode":
			getRibbonBarcode(request,response);
			break;
		case "searchBarcode":
			searchBarcode(request,response);
			break;
	}
	
   }

private void getRibbonBarcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	Integer id = Integer.parseInt(request.getParameter("id"));
		try {

	        String barcodes = PrinterinkDAO.getBarcodes(id);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(barcodes);

		} catch (SQLException e) {
			System.out.println("Error getRibbonBarcode method" + e);
		}
		
	}

private void searchBarcode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 
	String barcode = request.getParameter("barcode");
	System.out.println("barcode sdfkjashf"+barcode);
		try {

	        String barcodes = PrinterinkDAO.searchBarcode(barcode);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(barcodes);

		} catch (SQLException e) {
			System.out.println("Error searchBarcode method" + e);
		}
		
	}

protected void getPrinterInkDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   
		  Integer id = Integer.parseInt(request.getParameter("id"));
			try {

		        String printerink = PrinterinkDAO.getReqOrderInk(id);
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(printerink);
	 
			} catch (SQLException e) {
				System.out.println("Error getPrinterInkDetail method" + e);
			}
	   
   }
	
}
