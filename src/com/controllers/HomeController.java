package com.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.utils.Urls;

@WebServlet(name = "HomeController", urlPatterns = { 
		  Urls.LOGIN,
		  Urls.LOGOUT,
		  Urls.ON_LOAD, 
		  Urls.INDEX_URL,
		  Urls.ADD_CARDINFO,
		  Urls.ADD_RETURNEDCARD,
		  Urls.STOCK_OUT,
		  Urls.CARTON_BOX,
		  Urls.SUMMARY,
		  Urls.PRINT_STOCK_DETAILS,
		  Urls.SEARCH,
		  Urls.ADD_PRINTER_INK,
		  Urls.PRINTER_INK_STOCK,
		  Urls.REQUEST_ORDER,
		  Urls.MANAGE_USERS,
		  Urls.DELIVER_INK,
		  Urls.CARD_TRACKING,
		  Urls.DASHBOARD,
		  Urls.ADD_VEHICLE,
		  Urls.VEHICLE_LIST,
		  Urls.REQUEST_RIBBON,
		  Urls.RIBBON_STOCK,
		  Urls.RIBBON_DISPATCH,
		  Urls.ADD_ASSETS,
		  Urls.ADD_VEHICLE_SERVICE,
		  Urls.VEHICLE_SERVICE,
		  Urls.ADD_VEHICLE_ISSUES,
		  Urls.VEHICLE_ISSUES,
		  Urls.SERVERS,
		  Urls.MOBILE_PHONES,
		  Urls.LAPTOP,
		  Urls.DESKTOP,
		  Urls.MONITOR,
		  Urls.PRINTER,
		  Urls.UPS,
		  Urls.TAB,
		  Urls.POWERBANK,
		  Urls.TRACKING,
		  Urls.MANAGE_EMPLOYEE,
		  Urls.MANAGE_DELIVERY_PERSON,
		  Urls.CARD_DELIVERY_REPORT
		})

public class HomeController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageToLoad=null;
		String urlStr=request.getRequestURL().toString();
		
		if(urlStr != null){
				String urlpattern = urlStr.substring(urlStr.lastIndexOf("/"));
				
				request.setAttribute("loading_url", urlStr.substring(urlStr.lastIndexOf("/")+1));
				
				String view=request.getParameter("view_page");
				if(view != null && !view.equals("")){
					request.setAttribute("view_param", "view_page="+view);	
				}
				switch (urlpattern) {
						 case Urls.LOGIN:
							 pageToLoad="login.jsp";
							 break;
						 case Urls.INDEX_URL:
							 pageToLoad="carton_box.jsp";
							 break;
						 case Urls.ADD_CARDINFO:
							 pageToLoad="add_card.jsp";
						     break;	 
						 case Urls.ADD_RETURNEDCARD:
							 pageToLoad="returned_card.jsp";
						     break;	
						 case Urls.STOCK_OUT:
							 pageToLoad="stock_out.jsp";
						     break;
						 case Urls.CARTON_BOX:
							 pageToLoad="carton_box.jsp";
							 break;
						 case Urls.SUMMARY:
							 pageToLoad="records.jsp";
							 break;
						 case Urls.PRINT_STOCK_DETAILS:
							 pageToLoad="printStockDetails.jsp";
							 break;
						 case Urls.SEARCH:
							 pageToLoad="search.jsp";
							 break;
						 case Urls.ADD_PRINTER_INK:
							 pageToLoad="add_printer_ink.jsp";
							 break;
						 case Urls.PRINTER_INK_STOCK:
							 pageToLoad="printerInkStock.jsp";
							 break;
						 case Urls.REQUEST_ORDER:
							 pageToLoad="ink_request_order.jsp";
							 break;
						 case Urls.MANAGE_USERS:
							 pageToLoad="manage_users.jsp";
						     break;	
						 case Urls.DELIVER_INK:
							 pageToLoad="deliver_ink.jsp";
						     break;	
						 case Urls.CARD_TRACKING:
							 pageToLoad="cardTracking.jsp";
						     break;	
						 case Urls.DASHBOARD:
							 pageToLoad="dashboard.jsp";
						     break;	
						 case Urls.ADD_VEHICLE:
							 pageToLoad="add_vehicle.jsp";
						     break;
						 case Urls.VEHICLE_LIST:
							 pageToLoad="vehicle_list.jsp";
						     break;	
						 case Urls.REQUEST_RIBBON:
							 pageToLoad="ribbon_req.jsp";
						     break;	
						 case Urls.RIBBON_STOCK:
							 pageToLoad="ribbon_stock.jsp";
						     break;	
						 case Urls.RIBBON_DISPATCH:
							 pageToLoad="ribbon_dispatch.jsp";
						     break;	
						 case Urls.ADD_ASSETS:
							 pageToLoad="add_assets.jsp";
						     break;
						 case Urls.ADD_VEHICLE_SERVICE:
							 pageToLoad="add_vehicle_services.jsp";
						     break;	
						 case Urls.VEHICLE_SERVICE:
							 pageToLoad="view_vehicle_services.jsp";
						     break;
						 case Urls.ADD_VEHICLE_ISSUES:
							 pageToLoad="add_vehicle_issues.jsp";
						     break;	
						 case Urls.VEHICLE_ISSUES:
							 pageToLoad="vehicle_issues_view.jsp";
						     break;
						 case Urls.SERVERS:
						     pageToLoad="view_server.jsp";
						     break;
						 case Urls.MOBILE_PHONES:
							 pageToLoad="view_mobiles.jsp";
							 break;
						 case Urls.LAPTOP:
							 pageToLoad="view_laptop.jsp";
						     break;
						 case Urls.DESKTOP:
							 pageToLoad="view_desktop.jsp";
							 break;
						 case Urls.MONITOR:
							 pageToLoad="view_monitor.jsp";
						     break;
						 case Urls.PRINTER:
							 pageToLoad="view_printer.jsp";
							 break;
						 case Urls.UPS:
							 pageToLoad="view_ups.jsp";
						     break;
						 case Urls.TAB:
							 pageToLoad="view_tab.jsp";
							 break;
						 case Urls.POWERBANK:
							 pageToLoad="view_powerbank.jsp";
						     break;
						 case Urls.TRACKING:
							 pageToLoad="asset-tracking.jsp";
							 break;
						 case Urls.MANAGE_EMPLOYEE:
						 	pageToLoad="manage_employee.jsp";
						 	break;
						 case Urls.MANAGE_DELIVERY_PERSON:
							 pageToLoad="manage_delivery_person.jsp";
							 break;
						 case Urls.CARD_DELIVERY_REPORT:
							 pageToLoad="card_delivered_report.jsp";
							 break;
						 case Urls.LOGOUT:
							 HttpSession session = request.getSession();
							 session.removeAttribute("username");
							 session.removeAttribute("userrole");
							 session.invalidate();
							 pageToLoad="login.jsp";
							 break;
						default:
							response.sendRedirect("login"); 
							break;
						}		
			}else{
				pageToLoad="login.jsp";
			}
		
		if(pageToLoad !=null){
			pageToLoad =Urls.BASE_VIEW_PATH+pageToLoad;
			RequestDispatcher dispatcher = request.getRequestDispatcher(pageToLoad);
		    dispatcher.forward(request, response);
		}
		
	}

}
