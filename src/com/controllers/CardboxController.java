package com.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dao.CardboxDAO;
import com.dao.SearchDao;
import com.google.gson.Gson;

@WebServlet("/CardboxController")
public class CardboxController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static Logger logger=Logger.getLogger(CardboxController.class);
 
    public CardboxController() {
        super();
    }
    private CardboxDAO cardboxdao;
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	this.cardboxdao = new CardboxDAO();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
      String action = request.getParameter("action");
      System.out.println("acton 9034785908 "+ action);
		
		switch(action) {
			case "add-cartonbox": 
				addCartonBox(request, response);
				break;
			case "update-cardbox": 
				updateCardbox(request, response);
				break;
			case "edit-cardbox": 
				editCardbox(request, response);
				break;
			case "add-damaged-card": 
				addDamagedCard(request, response);
				break;
			case "update-damage-card":
				updateDamagedCard(request, response);
				break;
			case "edit-damage-card":
				editDamagedCard(request, response);
				break;
			case "delete-damage-card":
				deleteDamagedCard(request, response);
				break;
			case "add-delivered-cardbox": 
				addDeliveredCardbox(request, response);
				break;
			case "add-returned-card":
				addReturncardDetails(request, response);
				break;
		}
		
	}
	
	
	protected void addCartonBox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		try {
			String box_no = request.getParameter("carton_no");
			String shipment = request.getParameter("shipment");
			String stock_in_date = request.getParameter("stock_in_date");
			String ordered_date = request.getParameter("ordered_date");
			
			int carton_box = Integer.parseInt(box_no);
			int no_of_boxes = Integer.parseInt(request.getParameter("no_of_boxes"));
			
			boolean validateShipment = cardboxdao.validateShipment(shipment);
			
			if(validateShipment)
			{
				boolean isDone = cardboxdao.addCartonbox(carton_box,no_of_boxes,shipment,stock_in_date,ordered_date);
				if(isDone){
					
					String success_carton_message = "Successfully Added carton boxes";
				    request.setAttribute("success_carton_message", success_carton_message);
			        request.getServletContext().getRequestDispatcher("/WEB-INF/views/carton_box.jsp").forward(request, response);	
				}else {
					String carton_message = "something wrong!";
				    request.setAttribute("carton_message", carton_message);
			        request.getServletContext().getRequestDispatcher("/WEB-INF/views/carton_box.jsp").forward(request, response);	
				}
			}else {
				String false_msg = "Shipment is already Available!";
			    request.setAttribute("false_msg", false_msg);
		        request.getServletContext().getRequestDispatcher("/WEB-INF/views/add_card.jsp").forward(request, response);	
			}
			
		} catch (SQLException e) {
			logger.error("Error on Cardbox Controller doPost[Add carton]: ", e);
		}

	}
	
	
	protected void addDamagedCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONArray jsonArray;
		Boolean isDone = false;
		try {
			jsonArray = new JSONArray(request.getParameter("json"));
			isDone = cardboxdao.addDamagedCard(jsonArray);
			
		} catch (JSONException | SQLException e) {
			 System.out.println(e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	}
	
	protected void updateDamagedCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String box_id = request.getParameter("box_id");
		String reason = request.getParameter("reason");
		String serial_no = request.getParameter("serial_no");
		
		Boolean isDone = false;
		try {
			boolean validateDamagedCard = cardboxdao.validateDamagedCard(serial_no);
			boolean validateReturnCard = cardboxdao.validateReturnCard(serial_no);
			if(validateDamagedCard && validateReturnCard) {
			  isDone = cardboxdao.updateDamagedCard(box_id,reason,serial_no);
			}else {
				   isDone = false;	
				   System.out.println("false updadate");
				}
		} catch (JSONException | SQLException e) {
			 System.out.println(e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	}
	
	protected void editDamagedCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer box_id = Integer.parseInt(request.getParameter("box_id"));
		String reason = request.getParameter("reason");
		String serial_no = request.getParameter("serial_no");
		
		Boolean isDone = false;
		try {
		
			 isDone = cardboxdao.editDamagedCard(id,box_id,reason,serial_no);
			 
		} catch (JSONException | SQLException e) {
			 System.out.println(e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	}
	
	protected void deleteDamagedCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer id = Integer.parseInt(request.getParameter("id"));
		Boolean isDone = false;
		try {
			isDone = cardboxdao.deleteDamagedCard(id);
			
		} catch (JSONException | SQLException e) {
			 System.out.println(e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	}
	
	protected void updateCardbox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Boolean isDone = false;
	    Integer damage_cards_count = 0;
	    
		try {
			Integer box_id = Integer.parseInt(request.getParameter("box_id"));
			String completed_date = request.getParameter("date"); 
			String card_sn = request.getParameter("card_sn");
			Integer school_id = Integer.parseInt(request.getParameter("school_id"));
			String note = request.getParameter("note");
			Integer card_count_ready = Integer.parseInt(request.getParameter("ready_count"));
	
			damage_cards_count = cardboxdao.getDamageCardCountsByBoxId(box_id);
		
				if(card_count_ready == (200-damage_cards_count)) {
	              isDone = cardboxdao.updateCardbox(box_id,completed_date,card_sn,school_id,note,card_count_ready);
	              System.out.println("Success--->"+isDone);
				}else {
					isDone = false;
				    System.out.println("false to add --->"+isDone);
				}
			
		}catch (SQLException e) {
			logger.error("Error on Cardbox Controller doPost[update cardbox]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	
	}
	
	protected void editCardbox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isDone = false;
		try {
			Integer box_id = Integer.parseInt(request.getParameter("box_id"));
			String completed_date = request.getParameter("e_box_type"); 
			String card_sn = request.getParameter("e_card_sn");
			Integer school_id = Integer.parseInt(request.getParameter("e_school_id"));
			String note = request.getParameter("e_note");
			Integer card_count_ready = Integer.parseInt(request.getParameter("e_ready_count"));
	
              isDone = cardboxdao.updateCardbox(box_id,completed_date,card_sn,school_id,note,card_count_ready);
			
		}catch (SQLException e) {
			logger.error("Error on Cardbox Controller doPost[update cardbox]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	
	}
	
	protected void addDeliveredCardbox(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Boolean isDone = false;
		try {
			Integer box_id = Integer.parseInt(request.getParameter("box_id"));
			Integer delivered_cards = Integer.parseInt(request.getParameter("ready_count"));
			String delivery_date = request.getParameter("delivery_date");
			String delivery_person = request.getParameter("delivery_person");
			 
			isDone = cardboxdao.addDeliveredboxInfo(box_id,delivered_cards,delivery_date,delivery_person);
	
		}catch (SQLException e) {
			logger.error("Error on Cardbox Controller doPost[update cardbox]: ", e);
		}
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(isDone.toString());
	
	}
	
	protected void addReturncardDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String card_serial_no = request.getParameter("card_serial_no");
			Integer school_id =  Integer.parseInt(request.getParameter("school_id"));
			String reason = request.getParameter("reason_drop");
			String recieved_by = request.getParameter("received");
			if(reason.equals("Others")) {
				String other_reason = request.getParameter("reason");
				reason = other_reason;
			}
			
			Integer box_id = cardboxdao.getBoxIdByserialNo(Integer.parseInt(card_serial_no));
			Integer enteredCardSerialNo = Integer.parseInt(card_serial_no);
			Integer status = cardboxdao.validateReturnedCardStatus(enteredCardSerialNo);
			
		     
			if(status == 1 || status == 2) {
				String fail_message = "Entered Card is not Delivered Yet!";
			    request.setAttribute("fail_message", fail_message);
		        request.getServletContext().getRequestDispatcher("/WEB-INF/views/returned_card.jsp").forward(request, response);	
			}else {
				  boolean validateDamagedCard = cardboxdao.validateDamagedCard(card_serial_no);
				  if(validateDamagedCard)
				  {
				   boolean validateReturnCard = cardboxdao.validateReturnCard(card_serial_no);
				   if(validateReturnCard) {
				    Integer getSchoolID = cardboxdao.getSchoolBySerialNo(enteredCardSerialNo);
				    System.out.println("SCHOOl ids "+school_id+ "dsffd"+ getSchoolID);
					boolean isDone = cardboxdao.addReturnCard(card_serial_no,school_id,reason,recieved_by,box_id);
					if(isDone){
						String success_message = "Successfully Added Returned Card Details";
					    request.setAttribute("success_message", success_message);
				        request.getServletContext().getRequestDispatcher("/WEB-INF/views/returned_card.jsp").forward(request, response);	
					}else {
						String fail_message = "Something Went wrong!";
					    request.setAttribute("fail_message", fail_message);
				        request.getServletContext().getRequestDispatcher("/WEB-INF/views/returned_card.jsp").forward(request, response);	
					}
						
				   }else {
					    String fail_message = "This Card is Added as Returned Card!";
					    request.setAttribute("fail_message", fail_message);
				        request.getServletContext().getRequestDispatcher("/WEB-INF/views/returned_card.jsp").forward(request, response);
				   }
					
				}else {
				    String fail_message = "This Card is Added as Damaged Card!";
				    request.setAttribute("fail_message", fail_message);
			        request.getServletContext().getRequestDispatcher("/WEB-INF/views/returned_card.jsp").forward(request, response);	
			    }
			}
			
		} catch (SQLException e) {
			logger.error("Error on Cardbox Controller doPost[Add carton]: ", e);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
             String action = request.getParameter("action");
		
		switch(action) {
			case "getCardbox":
				fetchCardboxDetails(request,response);
				break;
			case "getDamagedCard":
				getDamagedCardByBoxid(request,response);
				break;
		}
		
	}
	
	protected void fetchCardboxDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer box_id = Integer.parseInt(request.getParameter("box_id"));
		try {
	        String cardbox = cardboxdao.getCardboxDeatil(box_id);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(cardbox);
 
		} catch (SQLException e) {
			System.out.println("Error Fetching Initial cardbox [Cardbox Controller]" + e);
		}
	}
	
	protected void getDamagedCardByBoxid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer box_id = Integer.parseInt(request.getParameter("box_id"));
		try {
	        String damaged_card = cardboxdao.getDamagedCardListByBoxId(box_id);
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.getWriter().write(damaged_card);
 
		} catch (SQLException e) {
			System.out.println("Error Fetching Initial cardbox [Cardbox Controller]" + e);
		}
	}

}