<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Cartonbox"%>
<%@ page import="com.beans.Cardbox"%>
<%@ page import="com.beans.Schools"%>
<%@ page import="com.beans.Shipment"%>

<jsp:useBean id="cardboxDAO" class="com.dao.CardboxDAO"></jsp:useBean>
<jsp:useBean id="summaryDAO" class="com.dao.SummaryDAO"></jsp:useBean>

<%
	Integer carton_id = 0;
	String sessionShipment = null;
	ArrayList<Cartonbox> cartonbox = null;

	ArrayList<Shipment> shipments = summaryDAO.getShipment();

	String shipmentIdNo = null;

	if (request.getParameter("shipmentId") == null) {
		shipmentIdNo = shipments.get(0).getShipment();
	} else {
		shipmentIdNo = request.getParameter("shipmentId");
	}

	cartonbox = cardboxDAO.getCartonboxByStatus(shipmentIdNo);

	if (request.getParameter("group") != null) {
		carton_id = Integer.parseInt(request.getParameter("group"));
	} else {
		carton_id = cardboxDAO.getCartonID();
	}

	ArrayList<Cartonbox> cartonboxDetails = cardboxDAO.getCartonboxDeatils(carton_id);

	ArrayList<Cardbox> cardboxdetails = cardboxDAO.getCardboxDeatils(carton_id);

	ArrayList<Schools> schools = cardboxDAO.getSchools();
	ArrayList<Schools> schools_list = cardboxDAO.getSchools();

	Map<String, Integer> requests = new HashMap<String, Integer>();
	requests.put("totalCardboxes", summaryDAO.getCardboxesCountsByShipment(shipmentIdNo));
	requests.put("newCardboxes", summaryDAO.getNewboxesCountsByShipment(shipmentIdNo));
	requests.put("pendingCardboxes", summaryDAO.getPendingboxesCountsByShipment(shipmentIdNo));
	requests.put("deliveredCardboxes", summaryDAO.getDeliveredboxesCountsByShipment(shipmentIdNo));

	Map<String, Integer> req_group = new HashMap<String, Integer>();
	req_group.put("newCardboxes", summaryDAO.getNewboxesCountsByGroup(carton_id));
	req_group.put("pendingCardboxes", summaryDAO.getReadyBoxesCountsByGroup(carton_id));
	req_group.put("deliveredCardboxes", summaryDAO.getDeliveredBoxesCountsByGroup(carton_id));
%>

<c:set var="cartonbox" value="<%=cartonbox%>" />
<c:set var="cardboxdetails" value="<%=cardboxdetails%>" />
<c:set var="cartonboxDetails" value="<%=cartonboxDetails%>" />
<c:set var="schools" value="<%=schools%>" />
<c:set var="schools_list" value="<%=schools_list%>" />
<c:set var="shipments" value="<%=shipments%>" />
<c:set var="requests" value="<%=requests%>" />
<c:set var="shipmentIdNo" value="<%=shipmentIdNo%>"></c:set>
<c:set var="req_group" value="<%=req_group%>"></c:set>
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row" style="margin-right: 0; margin-top: 60px;">
			<div class="col-sm-3">
				<div class="box-container">
					<div class="box-inner-list">
						<h5 class="card-header info-color white-text text-center py-4"
							style="padding-bottom: 10px !important;">
							<strong>Carton Box List</strong> <select id="shipment"
								name="shipment"
								class="form-control form-control-sm input-shipment"
								onchange="changeShipment();">
								<c:forEach var="shipments" items="${shipments}">
									<option value="${shipments.getCarton_id()}"
										<c:if test="${shipments.getShipment() eq shipmentIdNo}" >selected</c:if>>${shipments.getShipment()}</option>
								</c:forEach>
							</select> <input type="hidden" id="selectedShipmentHidden" /> <input
								type="hidden" id="selectedCartonIdHidden" />

						</h5>

						<h6
							style="display: flex; justify-content: space-between; margin: 8px 77px;">
							<span class="badge badge-success">New :
								${requests.get("newCardboxes")}</span> <span
								class="badge badge-warning">Ready to Deliver :
								${requests.get("pendingCardboxes")}</span> <span
								class="badge badge-danger">Delivered :
								${requests.get("deliveredCardboxes")}</span>
						</h6>
						<div class="list-type5 box-scroll">
							<ol>
								<c:forEach var="cartonbox" items="${cartonbox}">
									<a
										href="${context}/carton-box?shipmentId=${shipmentIdNo}&group=${cartonbox.getCarton_id()}"
										data-id="${cartonbox.getCarton_id()}">
										<li style="display: flex; justify-content: space-between;">
											Carton Group : ${cartonbox.getCartonGroup()}
											<div>
												<span class="badge badge-success" style="width: 30px;">${cartonbox.getNew_box_count()}</span>
												<span class="badge badge-warning" style="width: 30px;">${cartonbox.getPending_box_count()}</span>
												<span class="badge badge-danger" style="width: 30px;">${cartonbox.getDeliveredCount()}</span>
											</div>
									</li>
									</a>
								</c:forEach>
							</ol>
							<input type="hidden" id="list_id" value="<%=carton_id%>" readonly>
						</div>
					</div>
				</div>

			</div>
			<div class="col-sm-9">
				<div class="box-container">
					<div class="box-inner-view">
						<div id="carton_box_view">
							<div style="height: 98px;"
								class="card-header info-color white-text text-center py-4">
								<strong style="font-size: 18px;">Carton Box View</strong></br>
								<div style="text-align: end; font-size: 18px;">
									<span class="badge badge-success">New :
										${req_group.get("newCardboxes")}</span> <span
										class="badge badge-warning">Ready to Deliver :
										${req_group.get("pendingCardboxes")}</span> <span
										class="badge badge-danger">Delivered :
										${req_group.get("deliveredCardboxes")}</span>
								</div>
							</div>
							<div id="cartonboxPanel">

								<div class="flex-container nowrap" style="overflow: auto;">

									<div id="cards" class="cards">
										<c:forEach var="cardboxdetails" items="${cardboxdetails}">
											<c:if test="${cardboxdetails.getStatus()==1}">
												<a onclick="cardbox_green(${cardboxdetails.getBox_id()})"><div
														class="card app-card card-bax"
														style="background: #00b050; font-size: 20px; font-weight: bold">
														<p>${cardboxdetails.getBox_group()}</p>
														<span class="badge badge-secondary">New</span>
													</div></a>
											</c:if>
											<c:if test="${cardboxdetails.getStatus()==2}">
												<a onclick="cardbox_yellow(${cardboxdetails.getBox_id()})"><div
														class="card app-card card-bax"
														style="background: #ffc000;; font-size: 20px; font-weight: bold">
														<p>${cardboxdetails.getBox_group()}</p>
														<span class="badge badge-secondary">${cardboxdetails.getSchool()}</span>
													</div></a>
											</c:if>
											<c:if test="${cardboxdetails.getStatus()==3}">
												<a onclick="cardbox_red(${cardboxdetails.getBox_id()})"><div
														class="card app-card card-bax"
														style="background: #dc3545; font-size: 20px; font-weight: bold">
														<p>${cardboxdetails.getBox_group()}</p>
														<span class="badge badge-secondary">${cardboxdetails.getSchool()}</span>
													</div></a>
											</c:if>
										</c:forEach>
									</div>

								</div>

								<span id="message"
									style="color: #28a745; margin: 52px 550px; font-size: 20px;"></span>
								<span id="fail_msg"
									style="color: #dc3545; margin: 0px -832px; font-size: 20px;"></span>
								<c:if test="${not empty success_carton_message}">
									<h2 id="success_carton_message"
										style="color: green; margin: 52px 420px;">${success_carton_message}</h2>
								</c:if>
								<c:if test="${not empty carton_message}">
									<h2 id="carton_message" style="color: red; margin: 52px 420px;">${carton_message}</h2>
								</c:if>
								<div id="green" class="dtscard-container" style="display: none">
									<div class="dtscard-inner">
										<form action="CardboxController" method="POST"
											name="update-cardbox" id="update-cardbox" role="form">

											<input type="hidden" class="form-control input-card"
												id="box_id" name=box_id value="" readonly>


											<div class="dts-card green">
												<div class="dts-card-header">
													<h5 class="dts-card-title">Update DTS Card Box Info</h5>
												</div>
												<div class="dts-card-body">
													<div class="row">
														<div class="col-6">
															<p>
																<span class="sp-card">Box Group :</span> <input
																	type="text" class="form-control input-card"
																	id="box_group" name=box_group value="" readonly>
															</p>
															<p>
																<span class="sp-card">Card Serial Number :</span> <input
																	type="text" class="form-control input-card"
																	id="card_sn" name="card_sn" value="" readonly>
															</p>
															<p>
																<span class="sp-card">Note :</span> <input type="text"
																	class="form-control input-card" id="note" name="note"
																	value="" required>
															</p>
														</div>
														<div class="col-6">
															<p>
																<span class="sp-card">School :</span> <select
																	name="school_id" id="school_id"
																	class="form-control form-control-sm input-card">
																	<option value="Select School">Select School</option>
																	<c:forEach var="schools" items="${schools}">
																		<option value="${schools.getId()}">${schools.getSchool_name()}</option>
																	</c:forEach>
																</select>
															</p>
															<p>
																<span class="sp-card">Ready Cards count:</span> <input
																	style="width: 82px; margin: 0px 42px;" type="number"
																	min="0" class="form-control input-card"
																	id="ready_count" name="ready_count" value="" required>
																<button type="button" onclick="addDamageCards()"
																	class="btn damage-card-btn">Add Damage Cards</button>
															</p>
															<p>
																<span class="sp-card">Completed Date :</span> <input
																	type="date" class="form-control input-card" id="date"
																	name="date" value="" required> <span
																	class="error">${errors.card_type}</span>
															</p>

															<span class="" id="error-msg" style="color: red;"></span>
														</div>
													</div>
													<div class="btn-center">
														<input type="hidden" name="action" value="update-cardbox" />
														<button type="submit" class="btn update-btn">Ready</button>
													</div>
													<div class="dts-card-footer"></div>
												</div>
											</div>
										</form>
									</div>
								</div>


								<div id="yellow" class="dtscard-container" style="display: none">
									<div class="dtscard-inner">
										<form action="CardboxController" method="POST"
											name="add-delivered-cardbox" id="add-delivered-cardbox"
											role="form">

											<input type="hidden" class="form-control input-card"
												id="box_id" name=box_id value="" readonly>

											<div class="dts-card yellow">
												<div class="dts-card-header">
													<h5 class="dts-card-title">DTS Card Box Info</h5>
												</div>
												<div class="dts-card-body">
													<div class="row">
														<div class="col-6">
															<p>
																<span class="sp-card">Box Group :</span> <input
																	type="text" class="form-control input-card"
																	id="box_group" name=box_group value="" readonly>
															</p>
															<p>
																<span class="sp-card">Card Serial Number :</span> <input
																	type="text" class="form-control input-card"
																	id="card_sn" name="card_sn" value="" readonly>
															</p>
															<p>
																<span class="sp-card">Note :</span> <input type="text"
																	class="form-control input-card" id="note" name="note"
																	value="" readonly>
															</p>
															<p>
																<span class="sp-card">Delivery Date :</span> <input
																	type="date" class="form-control input-card"
																	id="delivery_date" name="delivery_date" value=""
																	required>
															</p>
														</div>
														<div class="col-6">
															<p>
																<span class="sp-card">School :</span> <input type="text"
																	class="form-control input-card" id="school" name=shool
																	value="" readonly>
															</p>
															<p>
																<span class="sp-card">Ready Cards count:</span> <input
																	style="width: 82px; margin: 0px 42px;" type="text"
																	class="form-control input-card" id="ready_count"
																	name="ready_count" value="" readonly>
																<!-- <button type="button" onclick="addDamageCards()" class="btn damage-card-btn">View Damaged Cards</button> -->
															</p>
															<p>
																<span class="sp-card">Completed Date :</span> <input
																	type="text" class="form-control input-card"
																	id="completed_date" name="completed_date" value=""
																	readonly> <span class="error">${errors.card_type}</span>
															</p>
															<p>
																<span class="sp-card">Delivery Person :</span> <input
																	type="text" class="form-control input-card"
																	id="delivery_person" name="delivery_person" value="">
																<span class="error">${errors.card_type}</span>
															</p>

															<span class="" id="error-msg" style="color: red;"></span>
														</div>
													</div>
													<div class="btn-center">
														<input type="hidden" name="action"
															value="add-delivered-cardbox" />
														<button onclick="cardbox_edit()" type="button"
															class="btn update-btn">Edit</button>
														<button type="submit" class="btn update-btn">Deliver</button>
													</div>
													<div class="dts-card-footer"></div>
												</div>
											</div>
										</form>
									</div>
								</div>


								<div id="yellow_update" class="dtscard-container"
									style="display: none">
									<div class="dtscard-inner">
										<form action="CardboxController" method="POST"
											name="edit-cardbox" id="edit-cardbox" role="form">

											<input type="hidden" class="form-control input-card"
												id="box_id" name=box_id value="" readonly>


											<div class="dts-card yellow">
												<div class="dts-card-header">
													<h5 class="dts-card-title">DTS Card Box Info</h5>
												</div>
												<div class="dts-card-body">
													<div class="row">
														<div class="col-6">
															<p>
																<span class="sp-card">Box Group :</span> <input
																	type="text" class="form-control input-card"
																	id="box_group" name=box_group value="" readonly>
															</p>
															<p>
																<span class="sp-card">Card Serial Number :</span> <input
																	type="text" class="form-control input-card"
																	id="e_card_sn" name="e_card_sn" value="" readonly>
															</p>
															<p>
																<span class="sp-card">Note :</span> <input type="text"
																	class="form-control input-card" id="e_note"
																	name="e_note" value="">
															</p>
														</div>
														<div class="col-6">
															<p>
																<span class="sp-card">School :</span> <select
																	name="e_school_id" id="e_school_id"
																	class="form-control form-control-sm input-card">
																	<c:forEach var="schools_list" items="${schools_list}">
																		<option value="${schools_list.getId()}">${schools_list.getSchool_name()}</option>
																	</c:forEach>
																</select>
															</p>
															<p>
																<span class="sp-card">Ready Cards count:</span> <input
																	style="width: 82px; margin: 0px 42px;" type="number"
																	min="0" class="form-control input-card"
																	id="e_ready_count" name="e_ready_count" value="">
																<button type="button" onclick="editDamageCards()"
																	class="btn damage-card-btn">
																	<i class="fas fa-edit"></i> Damaged Cards
																</button>
															</p>
															<p>
																<span class="sp-card">Completed Date :</span> <input
																	type="date" class="form-control input-card"
																	id="e_box_type" name="e_box_type" value="" required>
																<span class="error">${errors.card_type}</span>
															</p>

															<span class="" id="error-msg" style="color: red;"></span>
														</div>
													</div>
													<div class="btn-center">
														<input type="hidden" name="action" value="edit-cardbox" />
														<button type="submit" class="btn update-btn">Update</button>
													</div>
													<div class="dts-card-footer"></div>
												</div>
											</div>
										</form>
									</div>
								</div>


								<div id="red" class="dtscard-container" style="display: none">
									<div class="dtscard-inner">
										<form>

											<input type="hidden" class="form-control input-card"
												id="box_id" name=box_id value="" readonly>


											<div class="dts-card red">
												<div class="dts-card-header">
													<h5 class="dts-card-title">Delivered DTS Card Box Info</h5>
												</div>
												<div class="dts-card-body">
													<div class="row">
														<div class="col-6">
															<p>
																<span class="sp-card">Box Group :</span> <input
																	type="text" class="form-control input-card"
																	id="box_group" name=box_group value="" readonly>
															</p>
															<p>
																<span class="sp-card">Card Serial Number :</span> <input
																	type="text" class="form-control input-card"
																	id="card_sn" name="card_sn" value="" readonly>
															</p>
															<p>
																<span class="sp-card">Note :</span> <input type="text"
																	class="form-control input-card" id="note" name="note"
																	value="" readonly>
															</p>
															<p>
																<span class="sp-card">Delivered Date :</span> <input
																	type="text" class="form-control input-card"
																	id="delivered_date" name="delivered_date" value=""
																	readonly>
															</p>
														</div>
														<div class="col-6">
															<p>
																<span class="sp-card">School :</span> <input type="text"
																	class="form-control input-card" id="school" name=shool
																	value="" readonly>
															</p>
															<p>
																<span class="sp-card">Delivered Cards:</span> <input
																	type="text" class="form-control input-card"
																	id="ready_count" name="ready_count" value="" readonly>
															</p>
															<p>
																<span class="sp-card">Completed Date :</span> <input
																	type="text" class="form-control input-card"
																	id="completed_date" name="completed_date" value=""
																	readonly> <span class="error">${errors.card_type}</span>
															</p>
															<p>
																<span class="sp-card">Delivered By :</span> <input
																	type="text" class="form-control input-card"
																	id="delivered_by" name="delivered_by" value="" readonly>
																<span class="error">${errors.card_type}</span>
															</p>
															<span class="" id="error-msg" style="color: red;"></span>
														</div>
													</div>

													<div class="dts-card-footer"></div>
												</div>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@include file="../includes/modals.jsp"%>
<%@include file="../includes/footer.jsp"%>


<script>
"use strict";

$(document).ready(function() {
	let list_id = $("#list_id").val();
 	 $(".list-type5").animate({
        scrollTop: $(".list-type5 a[data-id='"+list_id+"']").position().top - 180
        }, 100);  
    
	 let shipmentIdVal = "<%=shipmentIdNo%>"; 
	 $("#shipment option[value='"+shipmentIdVal+"']").prop('selected' , true); 
	 
	 $(".list-type5 a[data-id='"+list_id+"']").find("li").addClass("active");
	 
});



function changeShipment() {
    var selectedShipment = $('#shipment :selected').text();
    $("#selectedShipmentHidden").val(selectedShipment);
    
    var seletectedCarton_id = $('#shipment :selected').val();
    $("#selectedCartonIdHidden").val(seletectedCarton_id);
    
    <%session.setAttribute("shipment", shipmentIdNo);%>
    window.location = "carton-box?shipmentId="+selectedShipment+"&group="+seletectedCarton_id;
}


function cardbox_green(box_id) {
	
	$("#message").text("");
	$("#error-msg").text("");

    $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
    	data.forEach(function(cardbox, key) {
    	    $("#yellow").hide();
    	    $("#red").hide();
    		$("#yellow_update").hide();
    		$("#green").show();
    		$("input#box_id").val(cardbox.box_id);
    		$("input#box_group").val(cardbox.box_group);
    		$("input#card_sn").val(cardbox.card_serial_no);
	   		$("input#note").val(cardbox.note);
	   		$("input#ready_count").val("");
	   		$("input#delivery_person").val("");
	   		$("input#completed_date").val(cardbox.completed_date);
	   	    $("#message").text(""); 
	  		$('#school_id').val("Select School");
	   	    $("#success").text("");
      	})
    	   
    	
	  }).fail(function( jqXHR, textStatus, errorThrown ) {
		  console.log('error');
	        console.log(jqXHR);
	        console.log(textStatus);
	        console.log(errorThrown );
	    });
 
    };
    
function cardbox_yellow(box_id) {
    

        console.log('b4 web service call');
        $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
    		console.log('status ::::::::::' + status);
        	console.log(data);
        	
        	data.forEach(function(cardbox, key) {
        		  $("#green").hide();
        		  $("#red").hide();
        		  $("#yellow_update").hide();
        		  $("#yellow").show();
        	      $("input#box_id").val(cardbox.box_id);
        /* 	      $('#school_list').val(cardbox.school_id); */
                  $("input#school").val(cardbox.school);
        		  $("input#box_group").val(cardbox.box_group);
        		  $("input#card_sn").val(cardbox.card_serial_no);
        		  $("input#note").val(cardbox.note);
        		  $("input#ready_count").val(cardbox.ready_count);
        		  $("input#completed_date").val(cardbox.completed_date);
        		  $("input#delivery_date").val("");
            	  $("input#delivery_person").val("");
        		  $("#message").text("");
        		  $("#success").text("");
        		  
        		  let completed_date = cardbox.completed_date;
        		  document.getElementsByName("delivery_date")[0].setAttribute('min', completed_date);
        	
        	})
        	
    	  }).fail(function( jqXHR, textStatus, errorThrown ) {
    		  console.log('error');
    	        console.log(jqXHR);
    	        console.log(textStatus);
    	        console.log(errorThrown );
    	    });
     
        };
        
 function cardbox_red(box_id) {

            $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
        		console.log('status ::::::::::' + status);
            	console.log(data);
            	
            	data.forEach(function(cardbox, key) {
            		  $("#green").hide();
            		  $("#yellow").hide();
            		  $("#yellow_update").hide();
            		  $("#red").show();
            		  $("input#box_id").val(cardbox.box_id);
             		  $("input#box_group").val(cardbox.box_group);
            		  $("input#school").val(cardbox.school);
             		  $("input#card_sn").val(cardbox.card_serial_no);
             		  $("input#note").val(cardbox.note);
            		  $("input#ready_count").val(cardbox.ready_count);
            		  $("input#completed_date").val(cardbox.completed_date);
            		  $("input#delivered_date").val(cardbox.delivery_date);
            		  $("input#delivered_by").val(cardbox.delivery_person);
            		  $("input#delivery_person").val("");
            		  $("#message").text("");
            		  $("#success").text("");
            	}) 	            	
        	    }).fail(function( jqXHR, textStatus, errorThrown ) {
        		  console.log('error');
        	        console.log(jqXHR);
        	        console.log(textStatus);
        	        console.log(errorThrown );
        	    });
         
            };
            
            
 function cardbox_edit(){

		var box_id = parseInt(document.getElementById('box_id').value); 
	 
	    console.log('after click edit');
	    $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
			console.log('status ::::::::::' + status);
	    	console.log(data);
	    	
	    	data.forEach(function(cardbox, key) {
	    	    $("#red").hide();
	    	    $("#yellow").hide();
	    	    $("#red").hide();
	    		$("#yellow_update").show();
	    		$("input#box_id").val(cardbox.box_id);
	    		$('#e_school_id').val(cardbox.school_id);
	    		$("input#e_box_group").val(cardbox.box_group);
	    		$("input#e_card_sn").val(cardbox.card_serial_no);
		   		$("input#e_note").val(cardbox.note);
		   		$("input#e_ready_count").val(cardbox.ready_count);
		   		$("input#e_box_type").val(cardbox.completed_date);
		   		$("input#delivery_person").val("");
		   	    $("#message").text(""); 
		   	    $("#success").text("");
		   	    
	      		  let completed_date1 = new Date().toISOString().split('T')[0];
	      		  let completed_date = $("input#completed_date").val(cardbox.completed_date);
	      		  console.log("today date ",completed_date1);
	      		  console.log("completed_date ",completed_date);
	      		  document.getElementsByName("delivery_date")[0].setAttribute('min', completed_date);
	      	})
	    	   
	    	
		  }).fail(function( jqXHR, textStatus, errorThrown ) {
			  console.log('error');
		        console.log(jqXHR);
		        console.log(textStatus);
		        console.log(errorThrown );
		    });
	 
	    };

        
	    $('#school_id').change(function() {
	  	     $("#error-msg").text(""); 
	     });
	               	
	    	   
 $('#ready_count').change(function() {
            	
            	var count = parseInt(document.getElementById('ready_count').value);
            	var box_id = parseInt(document.getElementById('box_id').value);
            	
            	var damage_cards = 200-count;
          	
            	if(count < 200)
                {
            		$('#add-damage-card-modal').modal('show');
            	/* 	alert("damage cards = "+ damage_cards); */
            		  $("#error-msg").text(""); 
            		  $("#row_count").text("");
            		  $("#cardsn_rq").text("");
    				  $("#reason_rq").text(""); 

                }else
            	{
                	if(count > 200)
                		{
            		      $("#error-msg").text("Card Count Ready Should Be Less than 200");	
                		}
                	else{
                	  $("#error-msg").text("");
                	}
                	 
              	}
                 	
            });
            
            
  function addDamageCards() {
       		$('#add-damage-card-modal').modal('show');
           		  $("#error-msg").text(""); 
           		  $("#row_count").text("");
           		  $("#cardsn_rq").text("");
   				  $("#reason_rq").text(""); 
           	}
  
  function editDamageCards() {
	  
		let box_id = parseInt(document.getElementById('box_id').value);
		console.log("box_id "+box_id);
	    console.log('after click edit Damaged Card');
	    $('#update_damaged_card_table tbody tr').remove();
	    $.get("CardboxController?action=getDamagedCard&box_id="+box_id, function(data, status){
				console.log('status ::::::::::' + status);
		    	console.log(data);
		    	console.log("length --- ",data.length);
		    	let row='<tr>';
		    	data.forEach(function(damaged_card, key) {
		    		
		    	  row+='<th class="cardSerialNumberTh">'+damaged_card.card_serial_no+'</th><th class="reasonTh">'+damaged_card.reason+'</th><th  data-id = "'+damaged_card.id+'"><button id="edit_damaged_card" type="button" class="btn btn-secondary" data-boxId = "'+box_id+'"><i class="fa fa-edit"></i></button>  <button type="button" id="delete_damaged_card" class="btn btn-danger " data-boxId = "'+box_id+'"><i class="fa fa-trash"></i></button></th>';
	              row+='</tr>';
	              
		      	})
		      	
		      	if(data.length == 0){
			        row+='<td></td><td>Damaged cards not available</td><td></td>';
		            row+='</tr>';
		      	}
	            
		 		$('#update_damaged_card_table tbody').append(row);
	      	}).fail(function( jqXHR, textStatus, errorThrown ) {
			    console.log('error');
		        console.log(jqXHR);
		        console.log(textStatus);
		        console.log(errorThrown );
		    });
	  
 		$('#edit-damage-card-modal').modal('show');
     		  $("#error-msg").text(""); 
     		  $("#row_count").text("");
     		  $("#cardsn_rq").text("");
			  $("#reason_rq").text(""); 
     }
            
    $('#update-cardbox').submit(function () {
    	
    	var form = $('#update-cardbox');
    	var box_id = parseInt(document.getElementById('box_id').value);
    	var note = document.getElementById('note').value;
    	var school = document.getElementById('school_id').value
    	if(school != "Select School")
    	{
	    	$.ajax({
		    		type: form.attr('method'),
		    		url: form.attr('action'),
		    		data: form.serialize(),
		    		success: function (data) {
		    		let result = data;
		    		console.log("result ---->", result);
		    		if(result == 'true'){
		    		   $('#result').attr("value",result);
		    		   $('#cards').load(document.URL +  ' #cards');
		    		   cardbox_yellow_now(box_id);
		    		}else{
		    			$("#error-msg").text("Please Add Remaining Damaged Cards");
		    		}
	    		}
	    	});
    	}else{
    		$("#error-msg").text("Please Select School");
    	}
    	
    		return false;
     }); 
    
    $('#edit-cardbox').submit(function () {
    	let table_length = document.getElementById("update_damaged_card_table").rows.length;
    	let count = parseInt(document.getElementById('e_ready_count').value);
    	let damaged_card = 200 - count;
    	if(table_length == damaged_card)
        {
	    	let form = $('#edit-cardbox');
	    	let box_id = parseInt(document.getElementById('box_id').value);
	    	console.log("box id == " +box_id);
	    	$.ajax({
		    		type: form.attr('method'),
		    		url: form.attr('action'),
		    		data: form.serialize(),
		    		success: function (data) {
		    		var result=data;
		    		$('#result').attr("value",result);
		    		   $('#cards').load(document.URL +  ' #cards');
		    		   cardbox_yellow_now(box_id);
		    		}
	    		});
        }else{
        	$('#fail_msg').text("Total Number of Damaged Card Count and Ready Cards count must be equals to 200!!");
        }
    		return false;
     }); 
    
    
  function cardbox_yellow_now(box_id) { 
    	
            $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
        		console.log('status ::::::::::' + status);
            	console.log(data);
            	
            	data.forEach(function(cardbox, key) {
            		  $("#green").hide();
            		  $("#red").hide();
            		  $("#yellow_update").hide();
            		  $("#yellow").show();
            	      $("input#box_id").val(cardbox.box_id);
            	      $("input#school").val(cardbox.school);
            		  $("input#box_group").val(cardbox.box_group);
            		  $("input#card_sn").val(cardbox.card_serial_no);
            		  $("input#note").val(cardbox.note);
            		  $("input#ready_count").val(cardbox.ready_count);
            		  $("input#completed_date").val(cardbox.completed_date);
            		  $("input#delivery_person").val("");
            		  $("#message").text("Cardbox Details Updated !!");
            		  $("#success").text("");
            	
            	})
            	
        	  }).fail(function( jqXHR, textStatus, errorThrown ) {
        		  console.log('error');
        	        console.log(jqXHR);
        	        console.log(textStatus);
        	        console.log(errorThrown );
        	    });
         
            };
            
 $('#add-delivered-cardbox').submit(function () {
	   
        	var form = $('#add-delivered-cardbox');
        	var box_id = parseInt(document.getElementById('box_id').value);
        	
        	 
        	Swal.fire({
        	  title: 'Are you sure?',
        	  text: "You won't be able to revert this!",
        	  type: 'warning',
        	  showCancelButton: true,
        	  confirmButtonColor: '#3085d6',
        	  cancelButtonColor: '#d33',
        	  confirmButtonText: 'Yes'
        	}).then((result) => {
        	  if (result.value) {
        		  $.ajax({
      	    		type: form.attr('method'),
      	    		url: form.attr('action'),
      	    		data: form.serialize(),
      	    		success: function (data) {
      	    		var result=data;
      	    		$('#result').attr("value",result);
      	    		   $('#cards').load(document.URL +  ' #cards');
      	    		   cardbox_red_now(box_id);
      	    		}
          		});
        	  }
        	})
        	
        	return false;
         });
   
 function cardbox_red_now(box_id) {

       console.log('b4 web service call');
       $.get("CardboxController?action=getCardbox&box_id="+box_id, function(data, status){
   		console.log('status ::::::::::' + status);
       	console.log(data);
       	
       	data.forEach(function(cardbox, key) {
       		  $("#green").hide();
       		  $("#yellow").hide();
      		  $("#yellow_update").hide();
       		  $("#red").show();
       		  $("input#box_id").val(cardbox.box_id);
        	  $("input#box_group").val(cardbox.box_group);
       		  $("input#school").val(cardbox.school);
        	  $("input#card_sn").val(cardbox.card_serial_no);
              $("input#note").val(cardbox.note);
       		  $("input#ready_count").val(cardbox.ready_count);
       		  $("input#completed_date").val(cardbox.completed_date);
       		  $("input#delivered_date").val(cardbox.delivery_date);
    		  $("input#delivered_by").val(cardbox.delivery_person);
    		  $("input#delivery_person").val("");
       		  $("#message").text("Cardbox Delivered Successfully");
       		  $("#success").text("");
       	})
       	
       	
       	
   	  }).fail(function( jqXHR, textStatus, errorThrown ) {
   		  console.log('error');
   	        console.log(jqXHR);
   	        console.log(textStatus);
   	        console.log(errorThrown );
   	    });
    
   };
</script>