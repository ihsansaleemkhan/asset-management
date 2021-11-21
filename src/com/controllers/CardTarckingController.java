package com.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.beans.DeliveredCardInfo;
import com.beans.DeliveryPerson;
import com.dao.CardTrackingDAO;
import com.dao.PrinterinkDAO;
import com.google.gson.Gson;

/**
 * Servlet implementation class CardTarckingController
 */
@WebServlet("/CardTarckingController")
public class CardTarckingController extends HttpServlet {

	static Logger LOGGER = Logger.getLogger(CardTarckingController.class);

	CardTrackingDAO cardTrackingDAO;

	private static final long serialVersionUID = 1L;

	public CardTarckingController() {
		super();
	}

	@Override
	public void init() throws ServletException {
		cardTrackingDAO = new CardTrackingDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
        System.out.println("Action ----> "+action);
		switch (action) {
		case "update-trainers-card-status":
			updateTrainersCardStatus(request, response);
			break;
		case "add-card-delivery-details":
			addCardDeliveryDetails(request, response);
			break;
		case "update-vehicle-card-status":
			updateVehicleCardStatus(request, response);
			break;
		case "add-delivery-person":
			addDeliveryPerson(request, response);
			break;
		case "update-delivery-person-detail":
			updateDeliveryPersonDetail(request, response);
			break;
		case "delete-delivery-person-detail":
			deleteDeliveryPersonDetail(request, response);
			break;
		}
		
	}

	private void addCardDeliveryDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
 		CardTrackingDAO cardTrackingDao = new CardTrackingDAO();
		DeliveredCardInfo delivery_card_info;
		String result = null;
		boolean success = false;
		
		Integer delivery_person_id = Integer.parseInt(request.getParameter("delivery_person"));
		String card_type = request.getParameter("card_type");
		String date = request.getParameter("date");
		List<String> tagIdList = Arrays.asList(request.getParameterValues("selectedTagIds[]"));
		List<String> cardIdList = Arrays.asList(request.getParameterValues("selectedCardNo[]"));
	
		
		String[] selectedTagIds = request.getParameterValues("selectedTagIds[]");
		
		System.out.println("Tag Ids--> " + new Gson().toJson(tagIdList));
		System.out.println("Card Ids--> " + new Gson().toJson(cardIdList));
		System.out.println("Card Ids--> " + new Gson().toJson(selectedTagIds));
		
		try {
			
			delivery_card_info = new DeliveredCardInfo();
			
			delivery_card_info.setDelivery_person_id(delivery_person_id);
			delivery_card_info.setCard_type(card_type);
			delivery_card_info.setCard_no(cardIdList);
			delivery_card_info.setDate(date);
			
			success	 = cardTrackingDao.addDeliveredCardInfo(delivery_card_info);
			
			if(success == true) {
			   result = "1";
		       String status_result = cardTrackingDAO.UpdateTagsStatus(selectedTagIds);
		       System.out.println("Status --> "+status_result);
			} else {
			   result = "3";
		    }
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(result);
			    
		} catch(SQLException e) {
			LOGGER.error("Error on CardTrackingController doPost[addCardDeliveryDetails]: ", e);
		}
		
		
	}

	private void deleteDeliveryPersonDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CardTrackingDAO cardTrackingDao = new CardTrackingDAO();

		Integer id = Integer.parseInt(request.getParameter("id"));
		
		boolean isDone;
		String fail_message = null;

		try {

			if (fail_message == null) {
				isDone = cardTrackingDao.deleteDeliveryPersonDetail(id);

				if (isDone) {
					isDone = true;
				} else {
					isDone = false;
				}
			}

		} catch (SQLException e) {
			LOGGER.error("Error on Card Tracking doPost[deleteDeliveryPersonDetail]: ", e);
		}
		
	}

	private void updateDeliveryPersonDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        CardTrackingDAO cardTrackingDao = new CardTrackingDAO();
        DeliveryPerson delivery_person = null;
		String result = null;
//		Part part = null;
//		byte[] photo = null;
		
		try {
			
			String qID = request.getParameter("qid");
			String currentQID = request.getParameter("currentQID");
			
			if(!qID.equals(currentQID)) {
				if(cardTrackingDao.checkIfQIDExist(request.getParameter("qid")) == true) {
					
					Map<String, String> resultMap = new HashMap<String, String>();
					resultMap.put("title", "Something Went Wrong!");
					resultMap.put("type", "error");
					resultMap.put("msg", "Delivery Person QID is already Exist!");
					
					response.setContentType("application/json");
		    	    response.setCharacterEncoding("UTF-8");
		    	    response.getWriter().write(new Gson().toJson(resultMap));
				    
				    return;
				}
			}
			
			delivery_person = new DeliveryPerson();
			

			delivery_person.setQid(qID);
			delivery_person.setName(request.getParameter("name"));
			delivery_person.setMobile(request.getParameter("mobile"));
			
//			part = request.getPart("uplDeliveryPersonPhotoUpdate");
			
//			if(part != null){
//				photo = IOUtils.toByteArray(part.getInputStream());
//				if(photo != null) {
//					delivery_person.setPhoto(photo);
//				}
//			}
		
			result	 = cardTrackingDao.updateDeliveryPersonDetails(delivery_person, currentQID);
	
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(result);
		
			
		}catch(SQLException e){
			LOGGER.error("Error on CardTracking Controller doPost[Update Delivery Person Details]: ", e);
		}
	}

	private void updateVehicleCardStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String[] selectedIds = request.getParameterValues("selectedIds[]");

		String result = cardTrackingDAO.UpdateTagsStatus(selectedIds);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}

	private void updateTrainersCardStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String[] selectedIds = request.getParameterValues("selectedIds[]");

		String result = cardTrackingDAO.UpdateTagsStatus(selectedIds);

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(result);
	}
	
	private void addDeliveryPerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         
		CardTrackingDAO cardTrackingDao = new CardTrackingDAO();
		DeliveryPerson delivery_person;
		String result = null;
		boolean success = false;
//		Part part = null;
//		byte[] photo = null;
		
		try {
			
			if(cardTrackingDao.checkIfQIDExist(request.getParameter("qid")) == true) {
				response.setContentType("text/html;charset=UTF-8");
			    response.getWriter().write("2");
				
				return;
			}
			
			delivery_person = new DeliveryPerson();
			
			delivery_person.setQid(request.getParameter("qid"));
			delivery_person.setName(request.getParameter("name"));
			delivery_person.setMobile(request.getParameter("mobile"));
			
//			   part = request.getPart("uplDeliveryPersonPhoto");
//				
//				if(part != null){
//					photo = IOUtils.toByteArray(part.getInputStream());
//					if(photo != null) {
//						delivered_person.setPhoto(photo);
//					}
//				}
			System.out.println("Param---> "+delivery_person);
			success = cardTrackingDao.addDeliveryPersonDetails(delivery_person);
			
			if(success == true) {
				result = "1";
			}else {
				result = "3";
			}
			response.setContentType("text/html;charset=UTF-8");
		    response.getWriter().write(result);
			
		}catch(SQLException e) {
			LOGGER.error("Error on CardTrackingController doPost[addDeliveryPerson]: ", e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("action") != null) {
			String action = request.getParameter("action");

			switch (action) {
			case "fetch-trainers-card-status-list":
			    fetchTrainerCardStatusList(request, response);
				break;
			case "fetch-vehicles-card-status-list":
				fetchVehicleCardStatusList(request, response);
				break;
			case "get-all-delivery-person-list":
				getAllDeliveryPersonList(request, response);
				break;
			case "fetch-delivered-card-report":
				fetchDeliveredCardReport(request, response);
				break;
			case "get-delivered-cards":
				getDeliveredCardsByDeliveryId(request, response);
			}
		}
	}

	private void getDeliveredCardsByDeliveryId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		Integer delivery_id = Integer.parseInt(request.getParameter("delivery_id"));
			try {

		        String delivered_cards = CardTrackingDAO.getDeliveredCardListByDeliveryId(delivery_id);
				response.setContentType("application/json");
			    response.setCharacterEncoding("UTF-8");
			    response.getWriter().write(delivered_cards);

			} catch (SQLException e) {
				System.out.println("Error getDeliveredCardsByDeliveryId method" + e);
			}
			
		}

	private void fetchDeliveredCardReport(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));
		
		Map<String, String> filters = new HashMap<String, String>();
		
		if (request.getParameter("card_type") != null) {
			filters.put("card_type", request.getParameter("card_type"));
		}
		if (request.getParameter("delivery_person") != null) {
			filters.put("delivery_person", request.getParameter("delivery_person"));
		}
		if(request.getParameter("delivered_from_date") != null) {
			filters.put("delivered_from_date", request.getParameter("delivered_from_date"));
			filters.put("delivered_to_date", request.getParameter("delivered_to_date"));
		}
		if (request.getParameter("search_filter") != null) {
			filters.put("search_filter", request.getParameter("search_filter"));
		}
		
		try {

			String deliveredCardReports = new CardTrackingDAO().fetchDeliveredCardReport(filters, draw, length, start);

			response.setContentType("cardList/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(deliveredCardReports);

		} catch (SQLException e) {
			LOGGER.error("Error on CardTrackingController [ fetchDeliveredCardReport ] " + e);
		}
		
	}

	private void getAllDeliveryPersonList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String delivery_persons = new CardTrackingDAO().getAllDeliveryPersonsList();
			
			response.setContentType("application/json");
    	    response.setCharacterEncoding("UTF-8");
    	    response.getWriter().write(delivery_persons);
			
		} catch (SQLException e) {
			LOGGER.error("Error on CardTrackingController [ getAllDeliveryPersonList ] "+e);
		}
		
	}

	protected void fetchTrainerCardStatusList(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		LOGGER.info(" ---  Enter to fetchTrainerCardStatusList() Methos  --- ");
		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));

		Map<String, String> filters = new HashMap<String, String>();
		
		if(request.getParameter("search[value]") != null) {
			filters.put("search_filter", request.getParameter("search[value]"));
		}
		String trainersCardStatus = null;
		try {
			trainersCardStatus = cardTrackingDAO.fetchTrainersByCardStatus(filters, draw, length, start);
		} catch (SQLException e) {
			LOGGER.error("Error on Approved Request Controller doPost[updateTrainerCardStatus]: ", e);
		}
		response.setContentType("trainersCardStatus/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(trainersCardStatus);
		LOGGER.info(" ---  End to fetchTrainerCardStatusList() Methos  --- ");
	}

	private void fetchVehicleCardStatusList(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Integer draw = Integer.parseInt(request.getParameter("draw"));
		Integer length = Integer.parseInt(request.getParameter("length"));
		Integer start = Integer.parseInt(request.getParameter("start"));

		Map<String, String> filters = new HashMap<String, String>();
		
		if(request.getParameter("search[value]") != null) {
			filters.put("search_filter", request.getParameter("search[value]"));
		}
		
		String vehiclesCardStatus = null;
		try {
			vehiclesCardStatus = cardTrackingDAO.fetchVehiclesByCardStatus(filters, draw, length, start);
		} catch (SQLException e) {
			LOGGER.error("Error on Approved Request Controller doPost[updateVehiclesCardStatus]: ", e);
		}
		response.setContentType("vehiclesCardStatus/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(vehiclesCardStatus);

	}

}
