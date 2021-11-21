<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
  
  <!-- jQuery UI Datepicker - Default functionality -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
<!--   <script src="https://code.jquery.com/jquery-1.12.4.js"></script> -->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<div>
<input type="hidden" id="userAssetId" value="">
</div>
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="record-container">
		  <div class="container-record" style="border-radius: 18px;">

				<div class="row">
					<div class="col-md-12 grid-margin stretch-card">
						<div class="title-card position-relative">
							<h5>Device Tracking</h5>
						</div>
					</div>
				</div>

				<div class="row">
				   <form class="filter-form" id="filter_form">
					  <div class="row">
							<div class="col-md-3">
							<span class="tx-label">Employee ID/Name :</span>
								<input type="text" id="employee_from" name="employee_from" class="form-control" autocomplete="off" placeholder="Employee ID or Name" style="display: inline;width: 50%;">							
							</div>								
							<div class="col-md-3">
								<span class="tx-label">Check In Date :</span> 
								<input type="text" class="form-control" id="check-in-date-form" name="check-in-date-form" autocomplete="off" placeholder="Select Date" style="display: inline;width: 50%;">
							</div>
								<div class="col-md-3">
								<span class="tx-label">Check Out Date :</span> 
								<input type="text" class="form-control" id="check-out-date-form" name="date-form" autocomplete="off" placeholder="Select Date" style="display: inline;width: 50%;">
							</div>
							<div class="col-md-1">
								<span>In Use</span>
								<input type="checkbox" id="in-user-form">
							</div>
							<div class="col-md-2">
								<input class="btn-filter" type="submit" value="Apply Filter" id="filterButton" />
								<input class="btn-filter" type="reset" value="Clear Filter" />
							</div>
		              </div>
				   </form>
				   
				   <div class="col-md-12" style="padding: 10px 48px;">
						<table  class="table table-striped table-bordered " style="width: 100%; text-align: center" id="result-table">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th>Employee ID</th>
									<th>Employee Name</th>
									<th>Device Serial Number</th>
									<th>Check In Date</th>
									<th>Check Out Date</th>
								</tr>
							</thead>
							<tbody>
<!-- 							 <tr> -->
<!-- 							   <td colspan="100%" class="align-right"></td> -->
<!-- 							 </tr> -->
						   </tbody>
						</table>
					</div>
				   
				</div>

			</div>
		</div>
	</div>
</div>

<%@include file="../includes/footer.jsp"%>
	
<!-- 	<div id="check-out-modal" class="modal fade bd-example-modal-lg" -->
<!-- 		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" -->
<!-- 		aria-hidden="true"> -->
<!-- 		<div class="modal-dialog modal-lg"> -->
<!-- 			<div class="modal-content"> -->
<!-- 				<div class="modal-header"> -->
<!-- 					<h5 class="modal-title">Check Out</h5> -->
<!-- 					<button type="button" class="close" data-dismiss="modal" -->
<!-- 						aria-label="Close"> -->
<!-- 						<span aria-hidden="true">&times;</span> -->
<!-- 					</button> -->
<!-- 				</div> -->
<!-- 				<div class="modal-body"> -->
<!-- 					<span>this device assigned to <span id="assigned_name">ahmad</span></span><br/> -->
<!-- 					<hr> -->
<!-- 					 <input type="text" class="form-control" id="ordered_date" name="ordered_date" autocomplete="off" placeholder="Select Date" required> -->
					
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

	<div id="employee-information" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Employee Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<table class="table">
						<tbody>
							<tr>
								<td><span>Employee ID</span></td>
								<td><span></span></td>
							</tr>
							<tr>
								<td><span>Employee Name</span></td>
								<td><span></span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div id="asset-information" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" style="font-weight: bold;left: 44%;position: relative;">Asset Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body row">
					<div class="col-md-6">
						<div class="card border-danger mb-3" style="max-width: 33rem;margin:auto;height: 98%;">
							  <div class="card-header  text-center" style="font-weight: bold;">Asset Information</div>
							  <div class="card-body text-danger">
							 	<table class="table">
							 		<tbody>
							 			<tr>
							 				<th>Asset ID</th>
							 				<td>:</td>
							 				<td><span id="asset_id_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Country</th>
							 				<td>:</td>
							 				<td><span id="country_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>City</th>
							 				<td>:</td>
							 				<td><span id="city_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Site</th>
							 				<td>:</td>
							 				<td><span id="site_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Asset Type</th>
							 				<td>:</td>
							 				<td><span id="asset_type_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Asset Number</th>
							 				<td>:</td>
							 				<td><span id="asset_number_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Asset Tag Name</th>
							 				<td>:</td>
							 				<td><span id="asset_tag_name_tag"></span></td>
							 			</tr>
							 			<tr>
							 				<th>FA Code</th>
							 				<td>:</td>
							 				<td><span id="fa_code_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Owner</th>
							 				<td>:</td>
							 				<td><span id="owner_card"></span></td>
							 			</tr>
							 		</tbody>
							 	</table>

							  </div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="card border-danger mb-3" style="max-width: 33rem;margin:auto;height: 98%;">
							  <div class="card-header  text-center" style="font-weight: bold;">Technical Information</div>
							  <div class="card-body text-danger">
								<table class="table">
							 		<tbody>
							 			<tr>
							 				<th>Make</th>
							 				<td>:</td>
							 				<td><span id="make_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Model</th>
							 				<td>:</td>
							 				<td><span id="model_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Serial Number</th>
							 				<td>:</td>
							 				<td><span id="serial_nubmer_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>MAC Address</th>
							 				<td>:</td>
							 				<td><span id="mac_address_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Processor</th>
							 				<td>:</td>
							 				<td><span id="processor_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Operation System</th>
							 				<td>:</td>
							 				<td><span id="os_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Hard Disk</th>
							 				<td>:</td>
							 				<td><span id="hard_drive_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Memory</th>
							 				<td>:</td>
							 				<td><span id="memory_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Procured Date</th>
							 				<td>:</td>
							 				<td><span id="procured_date_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Warranty Expire Date</th>
							 				<td>:</td>
							 				<td><span id="exipre_date_card"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Warranty/AMC/No AMC</th>
							 				<td>:</td>
							 				<td><span id="amc_card"></span></td>
							 			</tr>
							 		</tbody>
							 	</table>
							  </div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<script>

var urlParam = "";
$(document).ready(function(){
	showDataTable(urlParam);
	$(document).on("click",".employee-link",function(e){
		e.preventDefault();
		let employeeId = $(this).data("id");
		
		$("#employee-information").modal();
	});
	$(document).on("click",".asset-serialNumber",function(e){
		e.preventDefault();
		let serialNumber = $(this).text();
		getAssetInfo(serialNumber);
		$("#asset-information").modal();
	});
	$(document).on("click",".check-out",function(e){
		e.preventDefault();
		let userAssetId = $(this).data("userassetid");
		console.log(userAssetId)
		$("#userAssetId").val(userAssetId);
		checkOut(userAssetId);
	});
	$( "#check-in-date-form" ).datepicker({ 
		maxDate: new Date,
		dateFormat: 'yy-mm-dd'
	
	});
	$( "#check-out-date-form" ).datepicker({ 
		maxDate: new Date,
		dateFormat: 'yy-mm-dd'
	
	});
	$("#filter_form").submit(function(e){
		e.preventDefault();
		let check_in_date = $("#check-in-date-form").val();
		let check_out_date = $("#check-out-date-form").val();
		let employee_name_id = $("#employee_from").val();
		let inUse ;
		if($('#in-user-form').is(":checked")){
			inUse  = true;
		}else{
			inUse  = false;
		}
		urlParam = "&check_in_date="+check_in_date+"&check_out_date="+check_out_date+"&employee_name_id="+employee_name_id+"&inUse="+inUse;
		showDataTable(urlParam);
	});

});
function showDataTable(urlParam){
// 	console.log("inside function now");
	var table = $("#result-table").DataTable();
		table.destroy();
	$.ajax({
		url : "DeviceController?action=getUserAsset"+urlParam,
		method: "GET"
	}).done(function(result){
		$("#result-table tbody").empty();
		$.each(result,function(key,std){
			var row = "<tr>";
			row +="<td>"+std.employeeId+"</td>";
			row +="<td><a href='#' class='employee-link' data-id='"+std.employeeId+"'>"+std.employeeName+"</a></td>";
			row +="<td><a href='#' class='asset-serialNumber'>"+std.deviceSerialNumber+"</a></td>";
			row +="<td>"+std.checkInDate+"</td>";
			if(std.checkOutDate == "Currently In Use"){
			 row +="<td><a href='#' class='check-out' data-userAssetId="+std.userAssetId+">Check Out</a></td>";
			}else{
				row +="<td>"+std.checkOutDate+"</td>";
			}
			row +="</tr>";
			$("#result-table tbody").append(row);
		});
		table = $("#result-table").DataTable();
	}).fail(function(error){
		 Swal.fire(
				    'fail!',
				    'somthing went wrong !!',
				    'error'
				  )
	});
	
}

function getAssetInfo(serialNumber){
	$("#asset_id_card").text("");
	$("#country_card").text("");
	$("#city_card").text("");
	$("#site_card").text("");
	$("#asset_type_card").text("");
	$("#asset_number_card").text("");
	$("#asset_tag_name_tag").text("");
	$("#fa_code_card").text("");
	$("#owner_card").text("");
	$("#make_card").text("");
	$("#model_card").text("");
	$("#serial_nubmer_card").text("");
	$("#mac_address_card").text("");
	$("#processor_card").text("");
	$("#os_card").text("");
	$("#hard_drive_card").text("");
	$("#memory_card").text("");
	$("#procured_date_card").text("");
	$("#exipre_date_card").text("");
	$("#amc_card").text("");
	$.ajax({
		url: "DeviceController?action=getAssetInfo&serialNumber="+serialNumber,
		method : "GET",
	}).done(function(result){
// 		console.log(result);
		let asset_id = result.asset_id;
		let s_no = result.s_no;
		let country = result.country;
		let city = result.city;
		let site = result.site;
		let asset_type = result.asset_type;
		let tag_name = result.tag_name;
		let fa_code = result.fa_code;
		let owner = result.ownerInfoObj.first_name;
		let make = result.techInfoObj.make;
		let model = result.techInfoObj.model;
		let serial_no = result.techInfoObj.serial_no;
		let mac_address = result.techInfoObj.mac_address;
		let processor = result.techInfoObj.processor;
		let os = result.techInfoObj.os;
		let memory = result.techInfoObj.memory;
		let hard_disk = result.techInfoObj.hard_disk;
		let procurred_date = result.techInfoObj.procurred_date;
		let warranty_exp_date = result.techInfoObj.warranty_exp_date;
		let amc = result.techInfoObj.amc;
		
		
		$("#asset_id_card").text(asset_id);
		$("#country_card").text(country);
		$("#city_card").text(city);
		$("#site_card").text(site);
		$("#asset_type_card").text(asset_type);
		$("#asset_number_card").text();
		$("#asset_tag_name_tag").text(tag_name);
		$("#fa_code_card").text(fa_code);
		$("#owner_card").text(owner);
		$("#make_card").text(make);
		$("#model_card").text(model);
		$("#serial_nubmer_card").text(serial_no);
		$("#mac_address_card").text(mac_address);
		$("#processor_card").text(processor);
		$("#os_card").text(os);
		$("#hard_drive_card").text(hard_disk);
		$("#memory_card").text(memory);
		$("#procured_date_card").text(procurred_date);
		$("#exipre_date_card").text(warranty_exp_date);
		$("#amc_card").text(amc);
	
	}).fail(function(error){
		 Swal.fire(
				    'fail!',
				    'somthing went wrong !!',
				    'error'
				  )
	});
}

function checkOut(userAssetId){
// 	cosnole.log(userAssetId);
	$.ajax({
		url:"DeviceController?action=getAssignedUserName&userAssetId="+userAssetId,
		method:"GET"
	}).done(function(result){
		console.log(result);
		let text="This device was assigned to "+result+" ."  ;
			swal({
				  title: "Are you sure?",
				  text: text,
				  icon: "warning",
				  buttons: true,
				  dangerMode: true,
				}).then((willDelete) => {
				  if (willDelete) {
					  
		 			  $.ajax({
		 					 url: "DeviceController?action=setCheckOut&userAssetId="+userAssetId,
		 					 type : 'GET',
							
		 					 success : function(result){
		 						if(result){
		 							 Swal.fire(
				 							    'Done!',
				 							    'Checked Out',
				 							    'success'
				 							  );
		 							 console.log(urlParam);
				 					showDataTable(urlParam);
		 						}else{
		 							 Swal.fire(
				 							    'fail!',
				 							    'somthing went wrong !!',
				 							    'error'
				 							  )
		 						}
		 					 },
		 					 error : function(error){
		 						 Swal.fire(
		 							    'fail!',
		 							    'somthing went wrong !!' ,
		 							    'error'
		 							  )
		 					 }
		 				 });
				   
				  } 
				});
	}).fail(function(error){
		 Swal.fire(
				    'fail!',
				    'somthing went wrong !!',
				    'error'
				  )
	});
	
}
</script>