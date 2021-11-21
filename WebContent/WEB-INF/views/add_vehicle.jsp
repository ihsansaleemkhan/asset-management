<%@page import="com.beans.Model"%>
<%@page import="com.beans.Make"%>
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Vehicleholoteq"%>
<%@ page import="com.beans.Drivers"%>

<jsp:useBean id="vehicleDAO" class="com.dao.VehicleDAO"></jsp:useBean>

<%
	ArrayList<Vehicleholoteq> vehicles_plateno = vehicleDAO.getVehiclePlateNo();
    ArrayList<Drivers> drivers = vehicleDAO.getDrivername();
    List<Make> makes=vehicleDAO.getAllMake();
    List<Model> models=vehicleDAO.getAllModelsByMake("1");
%>

<c:set var="vehicles_plateno" value="<%=vehicles_plateno%>" />
<c:set var="assign_vehicle" value="<%=vehicles_plateno%>" />
<c:set var="drivers" value="<%=drivers%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

<div class="vehicle-tab-container">
	<div class="container-record">
		<section id="fancyTabWidget" class="tabs t-tabs">
			<ul class="nav nav-tabs fancyTabs" role="tablist">
			
				<li class="tab fancyTab active" style="width: 25%">
				 <a style="padding: 35px 60px;;" id="vehicleTab" href="#vehicleTabBody" data-toggle="tab"> 
				   <span class="fa"> <i class="fas fa-shuttle-van"></i></span> 
				   <span class="hidden-xs">Add Vehicle</span>
				 </a>
			   </li>
			   <li class="tab fancyTab" style="width: 25%">
				<a style="padding: 35px 60px;;" id="driverTab" href="#driverTabBody" data-toggle="tab"> 
				 <span class="fa"><i class="fas fa-user-tie"></i></span> 
				 <span class="hidden-xs">Add Staff</span>
				 </a>
				</li>
				<li class="tab fancyTab" style="width: 25%">
				 <a style="padding: 35px 60px;;" id="assignTab" href="#assignTabBody" data-toggle="tab"> 
				   <span class="fa"><i class="far fa-life-ring"></i></span> 
				   <span class="hidden-xs">Assigning</span>
				  </a>
				 </li>
				 <li class="tab fancyTab" style="width: 25%">
					 	<a style="padding: 35px 60px;;" id="assignTab" href="#returningTabBody" data-toggle="tab"> 
					   		<span class="fa"><i class="fas fa-exchange-alt"></i></span> 
					   		<span class="hidden-xs">Returning</span>
					  </a>
				 </li>

			</ul>
			<div id="myTabContent" class="tab-content fancyTabContent"
				aria-live="polite">
				<div class="tab-pane  fade active show" id="vehicleTabBody"
					role="tabpanel" aria-labelledby="vehicleTab" aria-hidden="false"
					tabindex="0">

					<div class="vehicle-form-container">
						<div class="form-inner-vehicle">
							<span id="vehicle-success"
								style="color: green; margin-left: 340px;"></span> <span
								id="vehicle-fail-msg" style="color: red; margin-left: -25px;"></span>
							<form action="VehicleController" method="POST" id="add-vehicle" class="form-cnt" enctype="multipart/form-data">
							 <input type="hidden" id="action" name="action" value="add-vehicle" />
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="plate_no">Plate Number : </label>
										<input type="text" class="form-control" id="plate_no"
											name="plate_no" maxlength="8" required>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="permit_date">Permit Expiry Date
											: </label> <input type="date" class="form-control" id="permit_date"
											name="permit_date" required>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="make">Make : </label>
										 <select class="form-control" id="make" name="make" required >
											<c:forEach var="make" items="<%=makes %>">
												<option value="${make.getId()}"  >${make.getMake()}</option>
											</c:forEach>
										</select>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Model : </label>
										<select class="form-control" id="model" name="model" required >
											<c:forEach var="model" items="<%=models %>">
												<option value="${model.getId()}"  >${model.getModel()}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 1:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="vehicle_img" previewId="vehicleImgPreview" name="vehicle_img" accept=".png, .jpg, .jpeg" required>
											<label class="custom-file-label" for="vehicle_img" aria-describedby="vehicle_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 2:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="vehicle_img2" previewId="vehicleImgPreview2" name="vehicle_img2" accept=".png, .jpg, .jpeg" required>
											<label class="custom-file-label" for="vehicle_img2" aria-describedby="vehicle_img2">Choose Image</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<img id="vehicleImgPreview" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image"> 
									</div>
									<div class="form-group col-lg-6">
										<img id="vehicleImgPreview2" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image"> 
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 3:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="vehicle_img3" previewId="vehicleImgPreview3" name="vehicle_img3" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="vehicle_img3" aria-describedby="vehicle_img3">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 4:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="vehicle_img4" previewId="vehicleImgPreview4" name="vehicle_img4" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="vehicle_img4" aria-describedby="vehicle_img2">Choose Image</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<img id="vehicleImgPreview3" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image"> 
									</div>
									<div class="form-group col-lg-6">
										<img id="vehicleImgPreview4" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image"> 
									</div>
								</div>
								
									
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Isthimara Front Side:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="permit_img" previewId="permitImgPreview" name="permit_img" accept=".png, .jpg, .jpeg" required>
											<label class="custom-file-label" for="permit_img" aria-describedby="permit_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Isthimara Back Side:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="permit_img2" previewId="permitImgPreview2" name="permit_img2" accept=".png, .jpg, .jpeg" required>
											<label class="custom-file-label" for="permit_img2" aria-describedby="permit_img">Choose Image</label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-6">
										<img id="permitImgPreview" src="#" class="img-fluid"
									style="border-radius: 20px; border: 2px solid #772b49; display: none;"
									alt="Responsive image"> 
									</div>
									<div class="form-group col-lg-6">
										<img id="permitImgPreview2" src="#" class="img-fluid"
									style="border-radius: 20px; border: 2px solid #772b49; display: none;"
									alt="Responsive image">  
									</div>
								</div>
								
								
								<button type="submit" class="btn cmd-vehicle">Submit</button>
							</form>
						</div>
					</div>
					
				</div>

				<div class="tab-pane  fade" id="driverTabBody" role="tabpanel"
					aria-labelledby="driverTab" aria-hidden="false" tabindex="0">
					<div class="vehicle-form-container">

						<div class="form-inner-vehicle">
							<span id="driver-success" style="color: green; margin-left: 340px;"></span>
							 <span id="driver-fail-msg" style="color: red; margin-left: 340px;"></span>
							<form action="VehicleController" method="POST" id="add-driver" class="form-cnt" enctype="multipart/form-data">
								<input type="hidden" id="action" name="action" value="add-driver" />
								<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Driver Name : </label>
										<input type="text" class="form-control" id="driver_name" name="driver_name" required>
									</div>
									
									<div class="form-group col-lg-6">
									   <label class="tx-label" for="given_date">Joined Date :</label> 
									   <input type="date" class="form-control" id="given_date" name="given_date" required> 
									</div>
								</div>
								
								<div class="row">									
									<div class="form-group col-lg-6">
								        <label class="tx-label" for="model">Driver Image :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="profile_img" previewId="profileImgPreview" name="profile_img" accept=".png, .jpg, .jpeg" required>
											<label class="custom-file-label" for="profile_img" aria-describedby="profile_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="permit_date">License
											Number : </label> <input type="text" class="form-control"
											id="license_no" name="license_no" maxlength="20" required>
									</div>
								</div>
								<div class="row" >
									<div class="form-group col-lg-6">
										<img id="profileImgPreview" src="#" class="img-fluid"
											style="border-radius: 20px; border: 2px solid #772b49; display: none;"
											alt="Responsive image"> 
									</div>
								</div>
								
								<div class="row">
									<div class="form-group col-lg-12">
										<label class="tx-label" for="model">License Image :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="license_img" previewId="licenseImgPreview" name="license_img" accept=".png, .jpg, .jpeg" required> 
											<label class="custom-file-label" for="license_img" aria-describedby="license_img">Choose Image</label>
											
										</div>
									</div>
								</div>
								<img id="licenseImgPreview" src="#" class="img-fluid"
									style="border-radius: 20px; border: 2px solid #772b49; display: none;"
									alt="Responsive image"> 
								
								<button type="submit" class="btn cmd-driver">Submit</button>
							</form>
						</div>
					</div>
				</div>

				<div class="tab-pane  fade" id="assignTabBody" role="tabpanel"
					aria-labelledby="assignTab" aria-hidden="false" tabindex="0">
					<div class="vehicle-form-container">

						<div class="form-inner-vehicle">
							<span id="assign-success"
								style="color: green; margin-left: 340px;"></span> <span
								id="assign-fail-msg" style="color: red; margin-left: 340px;"></span>
							<form action="VehicleController" method="POST"
								name="assign-driver" id="assign-driver" class="form-cnt"
								role="form">
								<div class="row">
									<div class="form-group col-lg-12">
										<label class="tx-label" for="vehicle_id">Select Vehicle : </label> 
										<select class="form-control" id="vehicle_id" name="vehicle_id">
											<option value="0"  driverId="0" >-- Select Vehicle--</option>
											<c:forEach var="vehicles_plateno" items="${vehicles_plateno}">
												<c:if test="${empty vehicles_plateno.getDriverId() or vehicles_plateno.getDriverId() eq 0 }">
													<option value="${vehicles_plateno.getId()}" 
													 driverId="${vehicles_plateno.getDriverId() }" vehicle="${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() }" 
													 driverName="${vehicles_plateno.getDriver_name() }" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
												</c:if>
											</c:forEach>
										</select>
										
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-12">
										<label class="tx-label" for="model">Select Driver : </label>
											 <select class="form-control" id="driver_id"
											name="driver_id">
											<option value="0"  vehicleId="0" >-- Select Driver--</option>
											<c:forEach var="drivers" items="${drivers}">
											<c:if test="${empty drivers.getVehicle_id() or drivers.getVehicle_id() eq 0 }">
												<option value="${drivers.getId()}" vehicleId="${drivers.getVehicle_id() }" 
												 plateNo="${drivers.getPlate_no() }" vehicle="${drivers.getVehicle() }" >${drivers.getDriver_name() }</option>
											</c:if>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="row">
									<input type="hidden" name="action" id="actionVal" value="assign-driver" />
									<div class="form-group col-lg-4">										
										<button type="submit" id="btnSubmit" class="btn cmd-vehicle">Assign Driver</button>
									</div>									
								</div>
							</form>
						</div>
					</div>
				</div>
				
				
				<div class="tab-pane  fade" id="returningTabBody" role="tabpanel"
					aria-labelledby="assignTab" aria-hidden="false" tabindex="0">
					<div class="vehicle-form-container">

						<div class="form-inner-vehicle">
							<span id="return-success"
								style="color: green; margin-left: 340px;"></span> <span
								id="assign-fail-msg" style="color: red; margin-left: 340px;"></span>
							<form action="VehicleController" method="POST"
								name="return-vehicle" id="return-vehicle" class="form-cnt"
								role="form">
								<div class="row">
									<div class="form-group col-lg-12">
										<label class="tx-label" for="vehicle_id">Select Vehicle : </label> 
										<select class="form-control" id="vehicle_return" name="vehicle_id">
											<option value="0"  driverId="0" >-- Select Vehicle--</option>
											<c:forEach var="vehicles_plateno" items="<%=vehicles_plateno %>">
												<c:if test="${not empty vehicles_plateno.getDriverId() and vehicles_plateno.getDriverId() ne 0}">
													<option value="${vehicles_plateno.getId()}" 
													 driverId="${vehicles_plateno.getDriverId() }" vehicle="${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() }" 
													 driverName="${vehicles_plateno.getDriver_name() }" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
													 
												 </c:if>
											</c:forEach>
										</select>
										<input type="hidden" name="driver_id" id="assignedDriverId" value="0" />
										<label class="tx-label assigned_driver" for="vehicle_id" style="font-size: 18px;color: #17a1b7;text-decoration: underline;">Assigned Driver</label> 
										<label class="tx-label assigned_driver" id="vehicleNo" ></label> 
										<label class="tx-label assigned_driver" id="vehicleName" ></label> 
										<label class="tx-label assigned_driver" id="assignedDriver" ></label> 
										
									</div>
								</div>
								<div class="row">
									<div class="form-group col-lg-12">
										<label class="tx-label" for="vehicle_id">Description : </label> 
										<textarea  style="min-height: 150px;" name="remark" class="form-control"  ></textarea>
									</div>
								</div>
								
								<div class="row">
									<input type="hidden" name="action" id="actionVal" value="return-vehicle" />
									<div class="form-group col-lg-4">										
										<button type="submit" id="btnReturn" class="btn cmd-vehicle">Return Vehicle</button>
									</div>
									
								</div>
							</form>
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

$(".assigned_driver").css("display","none");
$(".assigned_vehicle").css("display","none");


	$("#make").change(function() {
		var makeId=$(this).val();
		populateModelList(makeId);
	});

	
	$("#permit_img").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#permit_img2").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#vehicle_img").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#vehicle_img2").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#vehicle_img3").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#vehicle_img4").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	

	$("#profile_img").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	$("#license_img").change(function() {
		var previewId=$(this).attr("previewId");
		previewImgURL(this,previewId);
	});
	
	function previewImgURL(input,previewId) {
		if (input.files && input.files[0]) {
			let reader = new FileReader();
			reader.onload = function(e) {
				$('#'+previewId).attr('src', e.target.result);
				$('#'+previewId).hide();
				$('#'+previewId).fadeIn(650);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	
	function populateModelList(makeId) {
		$("#model").empty();
		
		$.get("VehicleController?action=fetch-models&makeId="+makeId,function(data, status) {
				data.forEach(function(model, key) { 
					$("#model").append("<option value="+model.id+" >"+model.model+"</option>");
					
				})

			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log('error');
			});
	}
	
	$(".assigned_driver").css("display","none");
	$(".assigned_vehicle").css("display","none");
	
	
	
	
   $("#vehicle_return").change(function() {
		
		var dId=$("#vehicle_return option:selected").attr("driverId");
		
		if(dId != 0){
			$("#assignedDriverId").val(dId);
			$(".assigned_driver").css("display","block");
			var plateNo=$("#vehicle_return option:selected").text();
			var vehicleName=$("#vehicle_return option:selected").attr("vehicle");
			var driverName=$("#vehicle_return option:selected").attr("driverName");				
			$("#assignedDriver").text(driverName);
			$("#vehicleName").text(vehicleName);
			$("#vehicleNo").text(plateNo);
			
		}else{			
			$(".assigned_driver").css("display","none");
			$("#assignedDriver").text("");
			$("#vehicleName").text("");
			$("#vehicleNo").text("");
		}
		
		
	});
	
$(document).on("submit","#add-vehicle",function(e){
	
	let form = $('#add-vehicle');
	$.ajax({
		type : "POST",
		url : "VehicleController?action=add-vehicle",
		enctype : "multipart/form-data",
		data: new FormData(this), // important
        contentType: false, // important
        processData: false, // important
		success : function(data) {
			console.log("data--------->",data);
			 if(data == 'true'){		    	 
				$("#vehicle-success").text("Vehicle Added Successfully !!");
				$("#vehicle-fail-msg").text("");
				$("input#plate_no").val("");
				$("input#make").val("");
				$("input#model").val("");
				$("input#permit_date").val("");
				$('#vehicleImgPreview').hide();
				
				location.reload(true);
			 }else{
				$("#vehicle-fail-msg").text("Plate No is already added !!");
				$("#vehicle-success").text("");
			 }
		},
		error : function(){
			alert("ajax error");
		}
	});
	e.preventDefault();
});

$(document).on("submit","#add-driver",function(e){
	e.preventDefault();
	let form = $('#add-driver');
	
	$.ajax({
		type : "POST",
		url : "VehicleController?action=add-driver",
		enctype : "multipart/form-data",
		data: new FormData(this), // important
        contentType: false, // important
        processData: false, // important
		success : function(data) {
	     if(data == 'true'){
	
	    	 
			$("#driver-success").text("Driver Added Successfully !!");
			$("#driver-fail-msg").text("");
			$("input#driver_name").val("");
			$("input#given_date").val("");
			$("input#returned_date").val("");
			$("input#license_no").val("");
			$('#driverImgPreview').hide();
			
			location.reload(true);
	      }else{
				$("#driver-fail-msg").text("License is already added !!");
				$("#driver-success").text("");
			 }
		},
		error : function(){
			alert("ajax error");
		}
	});
	return false
});
	
	$('#assign-driver').submit(function() {

		let form = $('#assign-driver');

		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				console.log("success------>", typeof (data));
		    	
				$("#assign-success").text("Driver Assigned Successfully !!");
				$("#vehicle_id").val("0");
				$("#driver_id").val("0");
				 location.reload(true);
			}
		});

		return false
	});
	
	$('#return-vehicle').submit(function() {

		let form = $('#return-vehicle');

		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				console.log("success------>", typeof (data));
		    
				$("#return-success").text("Vehicle Returned Successfully !!");
				$("#vehicle_id").val("0");
				$("#driver_id").val("0");
				 location.reload(true);
			}
		});

		return false
	});

	$("ul li").click(function() {
		$(this).siblings('li').removeClass('active');
		$(this).addClass('active');
	});

	function notifyResult(title, msg, type) {

		Swal.fire({
			title : title,
			text : msg,
			type : type,

		})
	}
</script>

