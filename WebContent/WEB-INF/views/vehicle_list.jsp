<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@page import="com.dao.VehicleDAO"%>
<%@page import="com.beans.Vehicleholoteq"%>

<%
	List<Vehicleholoteq> vehicleList = null;
	String dateString = request.getParameter("date");

	vehicleList = VehicleDAO.fetchVehicleReportList(dateString);
%>
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="record-container">
			<div class="container-record">
				<section id="fancyTabWidget" class="tabs t-tabs">
					<ul class="nav nav-tabs fancyTabs" role="tablist">
						<li class="tab fancyTab active" style="width: 33%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a style="padding: 30px 0px;" id="readyTab" href="#readyTabBody"
							role="tab" aria-controls="readyTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span class="fa"><i
									class="fas fa-shuttle-van"></i></span> <span class="hidden-xs">Vehicle
									Details</span></a>
						</li>
						<li class="tab fancyTab" style="width: 33%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a style="padding: 30px 0px;" id="deliveredTab"
							href="#deliveredTabBody" role="tab"
							aria-controls="deliveredTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span class="fa"><i
									class="fas fa-user-tie"></i></span> <span class="hidden-xs">Staff
									Details</span></a>
						</li>
						<li class="tab fancyTab" style="width: 34%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a style="padding: 30px 0px;" id="vehicleReport"
							href="#vehicleAssignedList" role="tab"
							aria-controls="deliveredTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span class="fa"><i
									class="fas fa-list"></i></span> <span class="hidden-xs">Vehicle
									Reports</span></a>
						</li>
					</ul>
					<div id="myTabContent" class="tab-content fancyTabContent"
						aria-live="polite">
						<div class="tab-pane  fade active show" id="readyTabBody"
							role="tabpanel" aria-labelledby="readyTab" aria-hidden="false"
							tabindex="0">
							<div>
								<div style="padding: 16px">

									<div class="pop-vehicle vehicle-wrapper" id="vehicle_div">

									</div>

								</div>
							</div>
						</div>


						<div class="tab-pane  fade" id="deliveredTabBody" role="tabpanel"
							aria-labelledby="deliveredTab" aria-hidden="false" tabindex="0">
							<div>
								<div style="padding: 16px">

									<div class="pop-driver driver-wrapper"></div>

								</div>
							</div>
						</div>

						<div class="tab-pane  fade" id="vehicleAssignedList"
							role="tabpanel" aria-labelledby="vehicleReport"
							aria-hidden="false" tabindex="0">
							<div class="row">
								<div class="col-md-12">

									<table id="ready_to_deliver"
										class="display table table-striped table-bordered"
										style="width: 100%">
										<thead>
											<tr
												style="color: white; background-color: #772b49; text-align: center;">
												<th>No</th>
												<th>Vehicle</th>
												<th>Plate No</th>
												<th>Date</th>
												<th>Driver</th>
												<th>Started On</th>
												<th>Returned On</th>
											</tr>
										</thead>
										<tbody>

											<c:forEach var="vehicle" items="<%=vehicleList%>"
												varStatus="counter">
												<tr style="text-align: center;">
													<td>${counter.count }</td>
													<td>${vehicle.getMake() }${vehicle.getModel() }</td>
													<td>${vehicle.getPlate_no() }</td>
													<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm a"
															value="${vehicle.getcDate() }" /></td>
													<td>${vehicle.getDriver_name() }</td>
													<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm a"
															value="${vehicle.getsDate() }" /></td>
													<c:choose>
														<c:when test="${vehicle.getStatus() eq 1 }">
															<td>With Driver</td>
														</c:when>
														<c:otherwise>
															<td><fmt:formatDate pattern="dd-MM-yyyy hh:mm a"
																	value="${vehicle.getrDate() }" /></td>
														</c:otherwise>
													</c:choose>

												</tr>
											</c:forEach>

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
<script>
	$(document).ready(function() {
		populateVehicleList();
		populateDriverList();

		$('#vehicle_details').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'excel', 'pdf', 'print' ]
		});
	    
	    $("#update_permit_img").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
	    $("#update_permit_img2").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
	    
		$("#update_vehicle_img").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$("#update_vehicle_img2").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$("#update_vehicle_img3").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$("#update_vehicle_img4").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$("#update_profile_img").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$("#update_license_img").change(function() {
			var previewId=$(this).attr("previewId");
			previewImgURL(this,previewId);
		});
		
		$(".vehicleImageModel").click(function() {
			$(".vehicleImageModel").removeClass("active");
			$("#carousel-item").removeClass("active");
			
			$(this).addClass("active");
			var no=$(this).attr("no");
			var id=$(this).parent().parent().attr("vId");	
			$("#vehicleImagePreview").attr("src",getVehicleImage(id,no));
		});
		
		$(".isthimaraImageModel").click(function() {
			$(".isthimaraImageModel").removeClass("active");
			$(this).addClass("active");
			var no=$(this).attr("no");
			var id=$(this).parent().parent().attr("vId");
			
			$("#permit-image").attr("src",getVehicleIsthimaraImage(id,no));
		});
		
		$("#updateMake").change(function() {
			var makeId=$(this).val();
			populateModelList(makeId);
		});
		
	});
	
	function populateModelList(makeId) {
		$("#updateModel").empty();
		$.get("VehicleController?action=fetch-models&makeId="+makeId,function(data, status) {
				data.forEach(function(model, key) { $("#updateModel").append("<option value="+model.id+" >"+model.model+"</option>");})

			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log('error');
			});
		
	}
	
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
	
	$(document).on("click", ".vehicleImage-view", function() {

		let id = $(this).attr("vehicleId");
		let vName = $(this).attr("vName");
		let modal = $("#vehicle_images")[0];
		$(modal).find(".imagePopupContainer").attr("vId",id);
		
		$(modal).find(".vehicleImageModel").removeClass("active");
		$(modal).find(".firstImg").addClass("active");
		$(modal).find(".imageVehicleName").text(vName);
		$(modal).find("#vehicleImagePreview").css("display","block");
		
		
		let images = "";
		
		let nos = [0,2,3,4];
		
		for(let num of nos) {
			let active = num === 0 ? "active" : '';
			let image = '<img src="'+getVehicleImage(id,num)+'" class="img-fluid vehicle-img-model" style="border-radius: 20px; border: 2px solid #772b49; max-height: 750px;" alt="Responsive image">';
			let imageContainer = '<div class="carousel-item ' + active + '">' + image + '</div>';
			
			
			images += imageContainer;
		}
		
		$(modal).find(".carousel-inner").empty().append(images);
		
		$('#vehicle_images').modal('show');
	});

	function getVehicleImage(id,no) {
		var srcval="${context}/VehicleController?action=get-vehicle-pro-image&id="+ id;	
		if(no != 0){
			srcval +="&no="+no;	
		}
		return srcval;
	}
	
	function getVehicleIsthimaraImage(id,no) {
		var srcval="${context}/VehicleController?action=get-vehicle-image&id="+ id;	
		if(no != 0){
			srcval +="&no="+no;	
		}
		return srcval;
	}

	function populateVehicleList() {
		$.get("VehicleController?action=fetch-vehicle-list",function(data, status) {
				$(".pop-vehicle").html("");
		data.forEach(function(vehicle, key) {
					console.log("vehicle.id", vehicle.id);
					let driver = "";
					if (vehicle.driver_name == "undefined") {
						driver = "Not Assigned";
					} else {
						driver = vehicle.driver_name;
					}

					$(".pop-vehicle").append(
						"<div>"
					        +"<h4>Plate Number : "
							+ vehicle.plate_no +"</h4>"
							+ "<img  src='${context}/VehicleController?action=get-vehicle-pro-image&id="+vehicle.id+"' height='200px' width='250px' vehicleId='"+vehicle.id+"' class='img-fluid vehicleImage-view' "
							+ " vName='"+vehicle.make +" "+ vehicle.model+" (Plate No :"+vehicle.plate_no+")"+"'  style='border-radius: 20px; border: 2px solid #772b49;cursor: pointer;'"
							+ "alt='Responsive image'>"						
							+ "<p> "+ vehicle.make +" "+ vehicle.model
							+ "</p><p>Isthimara Date : "
							+ vehicle.permit_date
							+ "</p><p>Driver : "
							+ driver
							+"</p><button class='btn btn-info btn-view isthimara-view' data-id='"+vehicle.id+"'><i class='fas fa-eye'></i> View Isthimara</button>"
					     	+"<button style='margin-left: 40px;' class='btn btn-edit btn-secondary vehicle-edit' data-id='"+vehicle.id+"'><i class='fas fa-edit'></i></button>"
							+"<button style='margin-left: 4px;' class='btn btn-delete btn-danger vehicle-delete' data-id='"+vehicle.id+"'><i class='fas fa-trash-alt'></i></button>"
						+"</div>");
				})

			}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log('error');
	});
	}

	function populateDriverList() {
		$.get("VehicleController?action=fetch-driver-list",function(data, status) {

				data.forEach(function(driver, key) {
					console.log("driver------>",driver);
					$(".pop-driver").append(
						"<div>"
						    +"<h4 style='text-align:center;font-size: 1rem;' >"
							+ driver.driver_name
							+ "</h4><p style='text-align:center;' >License Number : "
							+ driver.license_no
							+ "</p>"
							+ "<img  src='${context}/VehicleController?action=get-driver-image&id="+driver.id+"' height='150px' width='150px' class='img-fluid"
							+ "style='border-radius: 20px; border: 2px solid #772b49;'"
							+ "alt='Responsive image'>"	
							+"<p style='text-align:center;' >Plate No : "
							+ driver.plate_no
							+ "</p><p style='text-align:center;'><span class='badge badge-warning'>Penalties : "+driver.penalty_count+" <span class='fas fa-exclamation checked'></span>"
						    + "</span></p><p style='text-align:center;'><span class='badge badge-warning' style='text-align:center;' >Accidents : "+driver.accident_count+" <span class='fas fa-exclamation checked'></span>"
							+ "</span></p><p style='text-align:center;' >Appointed Date : "
							+ driver.given_date
							+ "</p><button class='btn btn-info btn-view driver-view' data-id='"+driver.id+"'><i class='fas fa-eye'></i> View License</button>"
						  	+"<button style='margin-left: 40px;' class='btn btn-edit btn-secondary driver-edit' data-id='"+driver.id+"'><i class='fas fa-edit'></i></button>"
							+"<button style='margin-left: 4px;' class='btn btn-delete btn-danger driver-delete' data-id='"+driver.id+"'><i class='fas fa-trash-alt'></i></button>"
						 +"</div>");
						})

			}).fail(function(jqXHR, textStatus, errorThrown) {
		console.log('error');
	   });
		
	}

	$(document).on("click", ".isthimara-view", function() {
		let id = $(this).data("id");
		let modal = $("#ithimara_image")[0];

		$(modal).find(".imagePopupContainer").attr("vId",id);
		
		$(modal).find(".isthimaraImageModel").removeClass("active");
		$(modal).find(".firstImg").addClass("active");
		$(modal).find("#permit-image").css("display","block");
		$(modal).find("#permit-image").attr("src",getVehicleIsthimaraImage(id,0));
		$('#ithimara_image').modal('show');
	});

	

	$(document).on("click", ".driver-view", function() {
		let id = $(this).data("id");
		let modal = $("#license_image")[0];

		$(modal).find(".license-image").empty().append(getLicenseImage(id));
		$('#license_image').modal('show');
	});

	function getLicenseImage(id) {

		return '<img style="max-height: 385px;" src="${context}/VehicleController?action=get-license-image&id='
				+ id
				+ '" '
				+ ' onerror="this.onerror=null;this.src=\'images/not.png\';" '
				+ 'onclick="window.open(\'${context}/VehicleController?action=get-license-image&id='
				+ id + '\')" />';
	}
	
	$(document).on("click",".vehicle-edit", function() {
		let id = $(this).data("id");
		let modal = $("#update_vehicle")[0];
		console.log("vehicle id-------->",id);

		   $('#vehicle_id').val(id);
		   
			$.get("VehicleController?action=fetch-vehicle&id="+id, function(data, status) {

			   console.log("vehicle details json data-->", data);
			   console.log("status--->", status);
			   
				data.forEach(function(vehicle,key) {
	                  $("#plate_no").val(vehicle.plate_no);
	                  $("#vehiclePlateNo").val(vehicle.plate_no);
					  $("#make").val(vehicle.make);
					  $("#model").val(vehicle.model);
					  $("#permit_date").val(vehicle.permit_date);
					  $("#updateVehicleImgPreview").css("display","block");
					  $("#updateVehicleImgPreview2").css("display","block");
					  $("#updateVehicleImgPreview3").css("display","block");
					  $("#updateVehicleImgPreview4").css("display","block");
					  $("#updateIsthimaraImage").css("display","block");
					  $("#updateIsthimaraImage2").css("display","block");
					  
					  $("#updateVehicleImgPreview").attr("src","${context}/VehicleController?action=get-vehicle-pro-image&id="+id);
					  $("#updateVehicleImgPreview2").attr("src","${context}/VehicleController?action=get-vehicle-pro-image&no=2&id="+id);
					  $("#updateVehicleImgPreview3").attr("src","${context}/VehicleController?action=get-vehicle-pro-image&no=3&id="+id);
					  $("#updateVehicleImgPreview4").attr("src","${context}/VehicleController?action=get-vehicle-pro-image&no=4&id="+id);
					  
					  $("#updateIsthimaraImage").attr("src","${context}/VehicleController?action=get-vehicle-image&id="+id);
					  $("#updateIsthimaraImage2").attr("src","${context}/VehicleController?action=get-vehicle-image&no=2&id="+id);
	             })

			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log('error');
			   });
		
		$('#update_vehicle').modal('show');
	});
	
	
	$(document).on("submit","#update-vehicle",function(e){
	 e.preventDefault();
		let form = $('#update-vehicle');
		$.ajax({
			type : "POST",
			url : "VehicleController?action=update-vehicle",
			enctype : "multipart/form-data",
			data: new FormData(this), // important
	        contentType: false, // important
	        processData: false, // important
			success : function(data) {
			  if(data == 'true'){
				console.log("data--------->",data);
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

	});
	
	$(document).on("click",".driver-edit", function() {
		let id = $(this).data("id");
		let modal = $("#update_driver")[0];
		console.log("driver id-------->",id);

		   $('#driver_id').val(id);
		   
			$.get("VehicleController?action=fetch-driver&id="+id, function(data, status) {

			   console.log("driver details json data-->", data);
			   console.log("status--->", status);
			   
				data.forEach(function(driver,key) {
					
	                  $("#driver_name").val(driver.driver_name);
					  $("#license_no").val(driver.license_no);
					  $("#licenseNo").val(driver.license_no);
					  $("#given_date").val(driver.given_date);
					  $("#updateProfileImgPreview").css("display","block");
					  $("#updateLicenseImgPreview").css("display","block");
					  $("#updateProfileImgPreview").attr("src","${context}/VehicleController?action=get-driver-image&id="+id);
					  $("#updateLicenseImgPreview").attr("src","${context}/VehicleController?action=get-license-image&id="+id);
	             })

			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log('error');
			   });
		
		$('#update_driver').modal('show');
	});
	
	$(document).on("submit","#update-driver",function(e){
		 e.preventDefault();
			let form = $('#update-driver');
			$.ajax({
				type : "POST",
				url : "VehicleController?action=update-driver",
				enctype : "multipart/form-data",
				data: new FormData(this), // important
		        contentType: false, // important
		        processData: false, // important
				success : function(data) {
					if(data == 'true'){
						console.log("data--------->",data);
						 location.reload(true);
					  }else{
							$("#driver-fail-msg").text("License No is already added !!");
							$("#driver-success").text("");
				      }
				},
				error : function(){
					alert("ajax error");
				}
			});

		});
	
	$(document).on("click",".vehicle-delete", function() {
		let id = $(this).data("id");
	
    	Swal.fire({
    	  title: 'Reason For Delete ?',
    	  type: 'warning',
    	  input: 'text',
    	  confirmButtonColor: '#3085d6',
    	  confirmButtonText: 'Delete'
    	}).then((result) => {
    	  if (result.value) {
    		  let reason = result.value;
    			console.log("vehicle id-------->",id);
    	        console.log("reason---->",reason);
    	        
    			$.ajax({
    			    url: "VehicleController?action=delete-vehicle&id="+id+"&reason="+reason,
    			    type: "POST",
    			    success: function(result) {
	   	    	            Swal.fire(
	   	    	              'Deleted!',
	   	    	              'Vehicle Details has been deleted.',
	   	    	              'success'
	   	    	            )
	   	    	            
	   	    	         location.reload(true);
    			    }
    			 });
    			populateVehicleList();
    	        
    	  }else{
    		  console.log("no value");
    		     Swal.fire(
       	              'Cancelled!',
       	              'Reason field is required!!',
       	              'error'
       	            )
    	  }
    	})
    	
	});
	
	$(document).on("click",".driver-delete", function() {
		let id = $(this).data("id");
	
    	Swal.fire({
      	  title: 'Reason For Delete ?',
      	  type: 'warning',
      	  input: 'text',
      	  confirmButtonColor: '#3085d6',
      	  confirmButtonText: 'Delete'
      	}).then((result) => {
      	  if (result.value) {
      		  let reason = result.value;
      			console.log("Driver id-------->",id);
      	        console.log("reason---->",reason);
      	        
      			$.ajax({
      			    url: "VehicleController?action=delete-driver&id="+id+"&reason="+reason,
      			    type: "POST",
      			    success: function(result) {
  	   	    	            Swal.fire(
  	   	    	              'Deleted!',
  	   	    	              'Vehicle Details has been deleted.',
  	   	    	              'success'
  	   	    	            )
  	   	    	       location.reload(true);
      			    }
      			 });
      	        
      	  }else{
      		  console.log("no value");
      		     Swal.fire(
         	              'Cancelled!',
         	              'Reason field is required!!',
         	              'error'
         	            )
      	  }
      	})	
		
	});
	
	$("ul li").click(function() {
		$(this).siblings('li').removeClass('active');
		$(this).addClass('active');
	});
	
</script>

<%@include file="../includes/modals.jsp"%>
<%@include file="../includes/footer.jsp"%>