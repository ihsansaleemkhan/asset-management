<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Vehicleholoteq"%>
<%@ page import="com.beans.Drivers"%>
<%@ page import="com.beans.VehicleServiceType"%>

<jsp:useBean id="vehicleDAO" class="com.dao.VehicleDAO"></jsp:useBean>

<%
	ArrayList<Vehicleholoteq> vehicles_plateno = vehicleDAO.getVehiclePlateNo();
    ArrayList<Drivers> drivers = vehicleDAO.getDrivername();
    ArrayList<VehicleServiceType> service_type_list = vehicleDAO.getVehicleServicesList();
%>

<c:set var="vehicles_plateno" value="<%=vehicles_plateno%>" />
<c:set var="drivers" value="<%=drivers%>" />
<c:set var="service_type_list" value="<%=service_type_list%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

		<div class="record-container">
			<div class="container-record" style="border-radius: 18px;">

				<div class="row">
					<div class="col-md-12 grid-margin stretch-card">
						<div class="title-card position-relative">
							<h5>List of Vehicle Services</h5>
						</div>
					</div>
				</div>

				<div class="row">
					<form class="filter-form" id="vehicleServicesFilterForm">
		                <div class="row">
		                    <div class="col-lg-2">
				                <select name="vehicle" id="vehicle" class="form-control form-control-sm">
								    <option value=""  driverId="0" >-- Select Vehicle--</option>
									<c:forEach var="vehicles_plateno" items="${vehicles_plateno}">
											<option value="${vehicles_plateno.getPlate_no()}" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
									</c:forEach>
								</select>
		                    </div>
		                    <div class="col-lg-2">
		                    		<select name="service_type" id="service_type" class="form-control form-control-sm">
									    <option value="" >-- Select Service Type--</option>
										<c:forEach var="service_type_list" items="${service_type_list}">
											<option value="${service_type_list.getService_type()}" >${service_type_list.getService_type()}</option>
										</c:forEach>
									</select>
		                    </div>
		                    <div class="col-lg-2">
		                    	<input type="date" class="form-control" id="date" name="date"required>
		                    </div>
		                	<div class="col-lg-4">
								<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Search by plate Number Here">
							</div>
							
							<input class="btn-filter" type="button" value="Apply Filter" id="filterButton" />
							<input class="btn-filter" type="reset" value="Clear Filter" />
		                </div>
						<input type="hidden" name="action" value="fetch-vehicle-services" />
					</form>
					<div class="col-md-12" style="padding: 10px 48px;">
						<table id="vehicleServicesTable" class="table table-striped table-bordered" style="width: 100%; text-align: center">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th><i class='fas fa-link'></i>Plate No</th>
									<th>Driver</th>
									<th>Service Type</th>
									<th>Date</th>
									<th>Cost</th>
									<th>Place</th>
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

	</div>
</div>
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
"use strict"
 var webUrl = "VehicleController";
  $(document).ready(function() {
	  
	  $("#filterButton").click(function(){
		  populateVehicleServicesTable();
	  });
	  
	  populateVehicleServicesTable();
	  populateVehicleServicesFilterTags()
  });

function getVehicleServicesParam() {
	let formData = $("#vehicleServicesFilterForm").serializeArray();
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

function populateVehicleServicesTable() {
	let table = $("#vehicleServicesTable").dataTable({
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
			data : getVehicleServicesParam(),
			type : "GET",
			dataFilter : function(data) {
				
				let newData = JSON.parse(new Object(data));
				let json = {};
				
				json.draw = newData.draw;
				json.recordsTotal = newData.recordsTotal;
				json.recordsFiltered = newData.recordsFiltered;
				json.data = JSON.parse(newData.data);
				
				$(".table-header-title").html("<h4>Vehicle Services :<span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
				console.log("------data--------",json);
				return JSON.stringify(json);
			}
		},
		columns : [
			{ data : "vehicle" },
			{ data : "driver" },
			{ data : "service_type" },
			{ data : "date" },
			{ data : "cost" },
			{ data : "place" },
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

$("#vehicleServicesFilterForm").on("click",".filter-tag-remove",function() {
			let inputName = $(this).parents(".filter-tag").data("name");

			$(this).parents(".filter-tag").remove();

			$("#vehicleServicesFilterForm select[name='"+ inputName + "'] option:first-child").prop("selected", true);

			populateVehicleServicesTable();
		});
		
function populateVehicleServicesFilterTags() {

	let tags = "";

	$.each($("#vehicleServicesFilterForm select"),function(key, value) {

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