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
				   <span class="hidden-xs">Add Accident Details</span>
				 </a>
			   </li>
			   	<li class="tab fancyTab" style="width: 50%">
				 <a style="padding: 35px 60px;;" id="penaltyTab" href="#penaltyTabBody" data-toggle="tab"> 
				   <span class="fa"> <i class="fas fa-hand-holding-usd"></i></span> 
				   <span class="hidden-xs">Add Penalty fees Details</span>
				 </a>
			   </li>
	         </ul>
	         
	         <div id="myTabContent" class="tab-content fancyTabContent" aria-live="polite">
				<div class="tab-pane  fade active show" id="accidentTabBody" role="tabpanel" aria-labelledby="accidentTab" aria-hidden="false" tabindex="0">
			      <div class="vehicle-form-container">
					<div class="form-inner-vehicle">
						<span id="added-success" style="color: green; margin-left: 340px;"></span>
						<span id="fail-msg" style="color: red; margin-left: 340px;"></span>
						
						<form action="VehicleController" method="POST" id="add-accident" class="form-cnt" enctype="multipart/form-data">
						   <input type="hidden" id="action" name="action" value="add-accident" />
						   	<div class="row">
							   <div class="form-group col-lg-6">
									 <label class="tx-label" for="vehicle">Vehicle : </label>
									 <select name="vehicle" id="vehicle" class="form-control form-control-sm">
									    <option value="0"  vehicleId="0" >-- Select Vehicle--</option>
										<c:forEach var="vehicles_plateno" items="${vehicles_plateno}">
											<option value="${vehicles_plateno.getId()}" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
										</c:forEach>
									 </select>
								</div>
								
								<div class="form-group col-lg-6">
								    <label class="tx-label" for="driver">Driver : </label>
									<select name="driver" id="driver" class="form-control form-control-sm">
										 <option value="0"  driverId="0" >-- Select Driver--</option>
										<c:forEach var="drivers" items="${drivers}">
											<option value="${drivers.getId()}" >${drivers.getDriver_name() }</option> 
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="row">
							    <div class="form-group col-lg-6">
								  <label class="tx-label" for="given_date">Date :</label> 
								  <input type="date" class="form-control" id="date" name="date" required> 
								</div>
								<div class="form-group col-lg-6">
									<label class="tx-label" for="accident_img">Accident Image 1:</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="accident_img" previewId="accidentImgPreview" name="accident_img" accept=".png, .jpg, .jpeg" required>
										<label class="custom-file-label" for="accident_img" aria-describedby="accident_img">Choose Image</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-lg-12">
									<img id="accidentImgPreview" src="#" class="img-fluid" style="border-radius: 20px; border: 2px solid #772b49; display: none;" alt="Responsive image"> 
								</div>
							</div>
							<div class="row">
								<div class="form-group col-lg-6">
									<label class="tx-label" for="accident_img2">Accident Image 2:</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="accident_img2" previewId="accidentImgPreview2" name="accident_img2" accept=".png, .jpg, .jpeg" required>
										<label class="custom-file-label" for="accident_img2" aria-describedby="accident_img2">Choose Image</label>
									</div>
								</div>
								<div class="form-group col-lg-6">
									<label class="tx-label" for="accident_img3">Accident Image 3:</label>
									<div class="custom-file">
										<input type="file" class="custom-file-input" id="accident_img3" previewId="accidentImgPreview3" name="accident_img3" accept=".png, .jpg, .jpeg" required>
										<label class="custom-file-label" for="accident_img3" aria-describedby="accident_img3">Choose Image</label>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-lg-6">
									<img id="accidentImgPreview2" src="#" class="img-fluid" style="border-radius: 20px; border: 2px solid #772b49; display: none;" alt="Responsive image"> 
								</div>
								<div class="form-group col-lg-6">
									<img id="accidentImgPreview3" src="#" class="img-fluid" style="border-radius: 20px; border: 2px solid #772b49; display: none;" alt="Responsive image"> 
								</div>
							</div>
							<div class="row">
								<div class="form-group col-lg-12">
								  <label class="tx-label" for="description">Description : </label> 
								  <textarea  style="min-height: 150px;" name="remark" class="form-control"></textarea>
								</div>
							</div>
							
							<button type="submit" class="btn cmd-vehicle">Submit</button>
						</form>
					</div>
				  </div>
				</div>
				
				<div class="tab-pane  fade" id="penaltyTabBody" role="tabpanel" aria-labelledby="penaltyTab" aria-hidden="false" tabindex="0">
				  <div class="vehicle-form-container">
					<div class="form-inner-vehicle">
					<span id="added-penalty" style="color: green; margin-left: 340px;"></span>
					<span id="fail" style="color: red; margin-left: 340px;"></span>
					
				     <form action="VehicleController" method="POST" id="add-penalty" class="form-cnt" enctype="multipart/form-data">
				        <input type="hidden" id="action" name="action" value="add-penalty" />
					   	<div class="row">
						   <div class="form-group col-lg-6">
								 <label class="tx-label" for="vehicle">Vehicle : </label>
								 <select name="pvehicle" id="pvehicle" class="form-control form-control-sm">
								    <option value="0"  vehicleId="0" >-- Select Vehicle--</option>
									<c:forEach var="penalty_vehicles" items="${penalty_vehicles}">
										<option value="${penalty_vehicles.getId()}" >${penalty_vehicles.getMake() } ${penalty_vehicles.getModel() } -${penalty_vehicles.getPlate_no() }</option>
									</c:forEach>
								 </select>
							</div>
							<div class="form-group col-lg-6">
							    <label class="tx-label" for="driver">Driver : </label>
								<select name="pdriver" id="pdriver" class="form-control form-control-sm">
									 <option value="0"  driverId="0" >-- Select Driver--</option>
									<c:forEach var="penalty_drivers" items="${penalty_drivers}">
										<option value="${penalty_drivers.getId()}" >${penalty_drivers.getDriver_name() }</option> 
									</c:forEach>
								</select>
							</div>
							</div>
							<div class="row">
						    	<div class="form-group col-lg-6">
									<label class="tx-label" for="penalty_cat">Penalty Type/Category : </label> 
									<input type="text" class="form-control" id="penalty_cat" name="penalty_cat" required>
								</div>
								<div class="form-group col-lg-6">
									<label class="tx-label" for="penalty_date">Date : </label> 
									<input type="date" class="form-control" id="penalty_date" name="penalty_date" required>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-lg-12">
								  <label class="tx-label" for="description">Description : </label> 
								  <textarea  style="min-height: 150px;" name="remark" class="form-control"></textarea>
								</div>
							</div>
							
							<button type="submit" class="btn cmd-vehicle">Submit</button>
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
$("ul li").click(function() {
	$(this).siblings('li').removeClass('active');
	$(this).addClass('active');
});

$("#accident_img").change(function() {
	let previewId=$(this).attr("previewId");
	previewImgURL(this,previewId);
});

$("#accident_img2").change(function() {
	let previewId=$(this).attr("previewId");
	previewImgURL(this,previewId);
});

$("#accident_img3").change(function() {
	let previewId=$(this).attr("previewId");
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

$(document).on("submit","#add-accident",function(e) {
	e.preventDefault();
	let form = $('#add-accident');
	$.ajax({
		type : "POST",
		url : "VehicleController?action=add-accident",
		enctype : "multipart/form-data",
		data: new FormData(this), // important
        contentType: false, // important
        processData: false, // important
		success : function(data) {
	     if(data == 'true'){
			$("#added-success").text("Accident Details Added Successfully !!");
			$("#fail-msg").text("");
			$("input#vehicle").val("0");
			$("input#driver").val("0");
			$("input#date").val("");
			$("input#remark").val("");
			$('#accidentImgPreview').hide();
			$('#accidentImgPreview2').hide();
			$('#accidentImgPreview3').hide();
			
			location.reload(true);
	      }else{
				$("#fail-msg").text(" Something Went Wrong !!");
				$("#added-success").text("");
			 }
		},
		error : function(){
			alert("ajax error");
		}
	});
	return false
});

$(document).on("submit","#add-penalty",function(e) {
	e.preventDefault();
	let form = $('#add-penalty');
	
	$.ajax({
		type : "POST",
		url : "VehicleController?action=add-penalty",
		enctype : "multipart/form-data",
		data: new FormData(this), // important
        contentType: false, // important
        processData: false, // important
		success : function(data) {
	     if(data == 'true'){
			$("#added-penalty").text("Penalty Details Added Successfully !!");
			$("#fail").text("");
			$("#pvehicle").val("0");
			$("#pdriver").val("0");
			$("input#penalty_cat").val("");
			$("input#penalty_date").val("");
			$("input#remark").val("");
			
	      }else{
				$("#fail").text(" Something Went Wrong !!");
				$("#added-penalty").text("");
			 }
		},
		error : function(){
			alert("ajax error");
		}
	});
	return false
});
</script>