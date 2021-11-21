<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Vehicleholoteq"%>
<%@ page import="com.beans.Drivers"%>

<jsp:useBean id="vehicleDAO" class="com.dao.VehicleDAO"></jsp:useBean>

<%
	ArrayList<Vehicleholoteq> vehicles_plateno = vehicleDAO.getVehiclePlateNo();
    ArrayList<Drivers> drivers = vehicleDAO.getDrivername();
%>

<c:set var="vehicles_plateno" value="<%=vehicles_plateno%>" />
<c:set var="drivers" value="<%=drivers%>" />
<c:set var="penalty_vehicles" value="<%=vehicles_plateno%>" />
<c:set var="penalty_drivers" value="<%=drivers%>" />


<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
	  <div class="vehicle-tab-container">
	    <div class="container-record">
	      <section id="fancyTabWidget" class="tabs t-tabs">
	         <ul class="nav nav-tabs fancyTabs" role="tablist">
	          <li class="tab fancyTab active" style="width: 50%">
				 <a style="padding: 35px 60px;;" id="accidentTab" href="#accidentTabBody" data-toggle="tab"> 
				   <span class="fa"> <i class="fas fa-car-crash"></i></span> 
				   <span class="hidden-xs">Accident Details</span>
				 </a>
			   </li>
			   	<li class="tab fancyTab" style="width: 50%">
				 <a style="padding: 35px 60px;;" id="penaltyTab" href="#penaltyTabBody" data-toggle="tab"> 
				   <span class="fa"> <i class="fas fa-hand-holding-usd"></i></span> 
				   <span class="hidden-xs">Penalty fees Details</span>
				 </a>
			   </li>
	         </ul>
	         
	         <div id="myTabContent" class="tab-content fancyTabContent" aria-live="polite">
				<div class="tab-pane  fade active show" id="accidentTabBody" role="tabpanel" aria-labelledby="accidentTab" aria-hidden="false" tabindex="0">
		          <div class="row">
					<form class="filter-form form-card-tracking" id="vehicleAccidentsFilterForm">
		                <div class="row">
		                    <div class="col-lg-2">
				                <select name="accident_vehicle" id="accident_vehicle" class="form-control form-control-sm">
								    <option value=""  driverId="0" >-- Select Vehicle--</option>
									<c:forEach var="vehicles_plateno" items="${vehicles_plateno}">
											<option value="${vehicles_plateno.getPlate_no()}" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
									</c:forEach>
								</select>
		                    </div>
		                    <div class="col-lg-2">
		                    		<select name="accident_driver" id="accident_driver" class="form-control form-control-sm">
									    <option value="" >-- Select Driver--</option>
										<c:forEach var="drivers" items="${drivers}">
											<option value="${drivers.getDriver_name()}" >${drivers.getDriver_name()}</option>
										</c:forEach>
									</select>
		                    </div>
		                    <div class="col-lg-2">
		                    	<input type="date" class="form-control" id="date" name="date"required>
		                    </div>
		                	<div class="col-lg-4">
								<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Seach by plate Number Here">
							</div>
							
							<input class="btn-filter" type="button" value="Apply Filter" id="filterButton" />
							<input class="btn-filter" type="reset" value="Clear Filter" />
		                </div>
						<input type="hidden" name="action" value="fetch-vehicle-accidents" />
					</form>
					<div class="col-md-12" style="padding: 10px 48px;">
						<table id="vehicleAccidentTable" class="table table-striped table-bordered" style="width: 100%; text-align: center">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th><i class='fas fa-link'></i>Plate No</th>
									<th>Driver</th>
									<th>Date</th>
								    <th>Remarks</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
							 <tr>
							   <td colspan="100%" class="align-right"></td>
							 </tr>
						   </tbody>
						</table>
					</div>
				  </div>
				</div>
				
				<div class="tab-pane  fade" id="penaltyTabBody" role="tabpanel" aria-labelledby="penaltyTab" aria-hidden="false" tabindex="0">
                  <div class="row">
					<form class="filter-form form-card-tracking" id="vehiclePenaltyFilterForm">
		                <div class="row">
		                    <div class="col-lg-2">
				                <select name="penalty_vehicle" id="penalty_vehicle" class="form-control form-control-sm">
								    <option value=""  vehicleId="0" >-- Select Vehicle--</option>
									<c:forEach var="penalty_vehicles" items="${penalty_vehicles}">
											<option value="${penalty_vehicles.getPlate_no()}" >${penalty_vehicles.getMake() } ${penalty_vehicles.getModel() } -${penalty_vehicles.getPlate_no() }</option>
									</c:forEach>
								</select>
		                    </div>
		                    <div class="col-lg-2">
		                    		<select name="penalty_driver" id="penalty_driver" class="form-control form-control-sm">
									    <option value="" >-- Select Driver--</option>
										<c:forEach var="penalty_drivers" items="${penalty_drivers}">
											<option value="${penalty_drivers.getDriver_name()}" >${penalty_drivers.getDriver_name()}</option>
										</c:forEach>
									</select>
		                    </div>
		                    <div class="col-lg-2">
		                    	<input type="date" class="form-control" id="date" name="date"required>
		                    </div>
		                	<div class="col-lg-4">
								<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Seach by plate Number Here">
							</div>
							
							<input class="btn-filter" type="button" value="Apply Filter" id="penaltyFilterButton" />
							<input class="btn-filter" type="reset" value="Clear Filter" />
		                </div>
						<input type="hidden" name="action" value="fetch-penalty-list" />
					</form>
					<div class="col-md-12" style="padding: 10px 48px;">
						<table id="vehiclePenaltyTable" class="table table-striped table-bordered" style="width: 100%; text-align: center">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th><i class='fas fa-link'></i>Plate No</th>
									<th>Driver</th>
									<th>Category/Penalty Type</th>
									<th>Date</th>
									<th>Remarks</th>
								</tr>
							</thead>
							<tbody>
							 <tr>
							   <td colspan="100%" class="align-right"></td>
							 </tr>
						   </tbody>
						</table>
					</div>
				  </div>
				</div>
		     </div>
	      </section>
	    </div>
	  </div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
"use strict"

$("ul li").click(function() {
	$(this).siblings('li').removeClass('active');
	$(this).addClass('active');
});

var webUrl = "VehicleController";

$(document).ready(function() {
	  
	$("#filterButton").click(function(){
		populateVehicleAccidentTable();
	});
	
	$("#penaltyFilterButton").click(function(){
		populateVehiclePenaltyTable();
	});
	
	populateVehicleAccidentTable();
	populateVehicleAccidentsFilterTags();

	populateVehiclePenaltyTable();
	populateVehiclePenaltyFilterTags();

	$('#accidentTab').on('shown.bs.tab', function(e) {
		$($.fn.dataTable.tables(true)).DataTable().columns.adjust();
	});

	$('#penaltyTab').on('shown.bs.tab', function(e) {
		$($.fn.dataTable.tables(true)).DataTable().columns.adjust();
	});
	
});

function getVehicleAccidentParam() {
	let formData = $("#vehicleAccidentsFilterForm").serializeArray();
	let jsonFormData = {};
	
	$.map(formData, function(n, i) {
		if(n['value'] != ""){
			let val=n['value'];
			jsonFormData[n['name']] = val;
		}
	});
	console.log("------>>>",jsonFormData);
	return jsonFormData;
}

function populateVehicleAccidentTable() {
	let table = $("#vehicleAccidentTable").dataTable({
		destroy : true,
		scrollX : true,
		responsive : true,
		"searching" : false,
		lengthMenu : [ [ 12, 30, 100 ], [ 12, 30, 100 ] ],
		pageLength : 12,
		pagingType : "full_numbers",

		serverSide : true,
		// processing : true,
		
		ajax : {
			url : webUrl,
			data : getVehicleAccidentParam(),
			type : "GET",
			dataFilter : function(data) {
				
				let newData = JSON.parse(new Object(data));
				let json = {};
				
				json.draw = newData.draw;
				json.recordsTotal = newData.recordsTotal;
				json.recordsFiltered = newData.recordsFiltered;
				json.data = JSON.parse(newData.data);
				
				$(".table-header-title").html("<h4>Vehicle Accidents :<span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
				console.log("------data--------",json);
				return JSON.stringify(json);
			}
		},
		columns : [
			{ data : "vehicle" },
			{ data : "driver" },
			{ data : "date" },
			{ data : "description" },
			{ data : "" },
		],
		columnDefs: [
			{
				data: null,
				defaultContent: "<i>N/A</i>",
				targets: "_all"
			},
			{
				targets: "_all",
				orderable: false,
			}
		],
		order: [[1, "asc"]],
	    dom: 'lBftipr'
	});
}

$("#vehicleAccidentsFilterForm").on("click",".filter-tag-remove",function() {
	let inputName = $(this).parents(".filter-tag").data("name");

	$(this).parents(".filter-tag").remove();

	$("#vehicleAccidentsFilterForm select[name='"+ inputName + "'] option:first-child").prop("selected", true);

	populateVehicleAccidentTable();
});

function populateVehicleAccidentsFilterTags() {

let tags = "";

$.each($("#vehicleAccidentsFilterForm select"),function(key, value) {

if ($(value).val() != "" && Number($(value).val()) != 100) {

	let tagText = $(value).find("option:selected").text().trim();

	if ($(value).prop("disabled")) {
		tags += "<span class='filter-tag' data-name='"
				+ $(value).prop("name")
				+ "'><span>"
				+ tagText
				+ "</span></span>";
	} else {
		tags += "<span class='filter-tag' data-name='"
				+ $(value).prop("name")
				+ "'><span>"
				+ tagText
				+ "</span> <a class='filter-tag-remove'><i class='fas fa-times'></i></a></span>";
	}
}

});

$(".filter-tags-container").empty().html(tags);

}


//fetch penalty list
function getVehiclePenaltyParam() {
	let formData = $("#vehiclePenaltyFilterForm").serializeArray();
	let jsonFormData = {};
	
	$.map(formData, function(n, i) {
		if(n['value'] != ""){
			let val=n['value'];
			jsonFormData[n['name']] = val;
		}
	});
	console.log("------>>>",jsonFormData);
	return jsonFormData;
}

function populateVehiclePenaltyTable() {
	let table = $("#vehiclePenaltyTable").dataTable({
		destroy : true,
		scrollX : true,
		responsive : true,
		"searching" : false,
		lengthMenu : [ [ 12, 30, 100 ], [ 12, 30, 100 ] ],
		pageLength : 12,
		pagingType : "full_numbers",

		serverSide : true,
		// processing : true,
		
		ajax : {
			url : webUrl,
			data : getVehiclePenaltyParam(),
			type : "GET",
			dataFilter : function(data) {
				
				let newData = JSON.parse(new Object(data));
				let json = {};
				
				json.draw = newData.draw;
				json.recordsTotal = newData.recordsTotal;
				json.recordsFiltered = newData.recordsFiltered;
				json.data = JSON.parse(newData.data);
				
				$(".table-header-title").html("<h4>Vehicle Penalty :<span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
				console.log("------data--------",json);
				return JSON.stringify(json);
			}
		},
		columns : [
			{ data : "vehicle" },
			{ data : "driver" },
			{ data : "category" },
			{ data : "date" },
			{ data : "remark" },
		],
		columnDefs: [
			{
				data: null,
				defaultContent: "<i>N/A</i>",
				targets: "_all"
			},
			{
				targets: "_all",
				orderable: false,
			}
		],
		order: [[1, "asc"]],
	    dom: 'lBftipr'
	});
}

$("#vehiclePenaltyFilterForm").on("click",".filter-tag-remove",function() {
	let inputName = $(this).parents(".filter-tag").data("name");

	$(this).parents(".filter-tag").remove();

	$("#vehiclePenaltyFilterForm select[name='"+ inputName + "'] option:first-child").prop("selected", true);

	populateVehiclePenaltyTable();
});

function populateVehiclePenaltyFilterTags() {

	let tags = "";
	
	$.each($("#vehiclePenaltyFilterForm select"),function(key, value) {
	
	if ($(value).val() != "" && Number($(value).val()) != 100) {
	
		let tagText = $(value).find("option:selected").text().trim();
	
		if ($(value).prop("disabled")) {
			tags += "<span class='filter-tag' data-name='"
					+ $(value).prop("name")
					+ "'><span>"
					+ tagText
					+ "</span></span>";
		} else {
			tags += "<span class='filter-tag' data-name='"
					+ $(value).prop("name")
					+ "'><span>"
					+ tagText
					+ "</span> <a class='filter-tag-remove'><i class='fas fa-times'></i></a></span>";
		}
	}
	
	});
	
	$(".filter-tags-container").empty().html(tags);

}
</script>