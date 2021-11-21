<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Schools"%>
<%@ page import="com.beans.DeliveryPerson" %>

<jsp:useBean id="cardboxDAO" class="com.dao.CardboxDAO"></jsp:useBean>
<jsp:useBean id="cardTrackingDao" class="com.dao.CardTrackingDAO"></jsp:useBean>

<%
ArrayList<Schools> schools = cardboxDAO.getSchools();
ArrayList<Schools> schools_list = cardboxDAO.getSchools();

ArrayList<DeliveryPerson> delivery_person = cardTrackingDao.getAllDeliveryPerson();
%>


<c:set var="schools" value="<%=schools%>" />
<c:set var="schools1" value="<%=schools%>" />
<c:set var="schools_list" value="<%=schools_list%>" />
<c:set var="allDeliveryPerson" value="<%=delivery_person%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

<div class="record-container">
	<div class="container-record">
		<section id="fancyTabWidget" class="tabs t-tabs">
			<ul class="nav nav-tabs fancyTabs" role="tablist">
				<li class="tab fancyTab active" style="width: 50%">
				    <a style="padding: 30px 330px;" id="trainersTab" href="#readyTabBody" data-toggle="tab" >
				    <span class="fa"> <i class="fas fa-chalkboard-teacher"></i></span>
				    <span class="hidden-xs">Trainer</span></a>
				</li>
				<li class="tab fancyTab" style="width: 50%">
				    <a style="padding: 30px 330px;" id="vehiclesTab" href="#deliveredTabBody" data-toggle="tab" >
				    <span class="fa"><i class="fas fa-car"></i></span> 
				    <span class="hidden-xs">Vehicle</span></a>
				</li>
			</ul>

			<div id="myTabContent" class="tab-content fancyTabContent"
				aria-live="polite">
				<div class="tab-pane  fade active show" id="readyTabBody"
					role="tabpanel" aria-labelledby="readyTab" aria-hidden="false"
					tabindex="0">
					<div>
						<div class="row">
						
							<form class="filter-form" id="trainerCardStatusListFilterForm">
			                <div class="row">
								<div class="col-lg-2">${text['school.name'] }</div>
								<div class="col-lg-3">
									<div class="input-group">
										<select class="form-control" name="school_filter">
											<option value="100">--Select School--</option>
								                 <c:forEach var="schools" items="${schools}">
													<option value="${schools.getId()}">${schools.getSchool_name()}</option>
												</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-lg-1"></div>
								<div class="col-lg-3">
									<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Seach Here">
								</div>
								<input class="btn-filter" type="button" value="Apply Filter" id="filterButton" />
								<input class="btn-filter" type="reset" value="Clear Filter" />
							</div>

							<br> <br />
								<div class="row">
									<div class="col-lg-12" style="text-align: right;">
										<input type="hidden" name="action"
											value="fetch-trainers-card-status-list" />

									</div>
								</div>
							</form>
							
							<div class="col-md-12">
								<table id="trainerCardStatusTable"
									class="table table-striped table-bordered"
									style="width: 100%; text-align: center">
									<thead>
										<tr style="background-color: #772b49; color: white;">
											<th colspan="4">Trainers Details</th>
											<th colspan="4">Card Status</th>
										</tr>
										<tr style="background: #0b5d80; color: #fff;">
											<th></th>
											<th><i class='fas fa-link'></i>Trainer's ID</th>
											<th>Name</th>
											<th>School</th>
											<th>Active</th>
											<th>Approved</th>
											<th>Printed</th>
											<th>Deliver</th>
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

				<div class="tab-pane fade" id="deliveredTabBody" role="tabpanel"
					aria-labelledby="deliveredTab" aria-hidden="false" tabindex="0">
					<div>
						<div class="row ">

						<form class="filter-form" id="vehicleCardStatusListFilterForm">
							<div class="row">
								<div class="col-lg-2">${text['school.name'] }</div>
								<div class="col-lg-3">
									<div class="input-group">
										<select class="form-control" name="school_filter">
											<option value="100">--Select School--</option>
								                 <c:forEach var="schools1" items="${schools1}">
													<option value="${schools1.getId()}">${schools1.getSchool_name()}</option>
												</c:forEach>
										</select>
									</div>
								</div>

								<div class="col-lg-1"></div>
								<div class="col-lg-3">
									<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="vsearch_filter" placeholder="Seach Here">
								</div>
								
								<input class="btn-filter" type="button" value="Apply Filter" id="vehiclefilterButton" />
								<input class="btn-filter" type="reset" value="Clear Filter" />
							</div>
								<div class="row">
									<div class="col-lg-12" style="text-align: right;">
										<input type="hidden" name="action"
											value="fetch-vehicles-card-status-list" />
									</div>
								</div>
							</form>

							<div class="col-md-12">
								<table id="vehicleCardStatusTable"
									class="display table table-striped table-bordered"
									style="width: 100%; text-align: center">
									<thead>
										<tr style="background-color: #772b49; color: white;">
											<th colspan="4">Trainers Details</th>
											<th colspan="4">Card Status</th>
										</tr>
										<tr style="background: #0b5d80; color: #fff;">
										<th></th>
											<th>Plate No</th>
											<th>Vehicle Type</th>
											<th>School</th>
											<th>Active</th>
											<th>Approved</th>
											<th>Printed</th>
											<th>Deliver</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td colspan="100%" align="center"></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</div>

</div>
</div>

     <!--  add delivery details modal -->
	<div class="modal fade" id="add-deliver-details-modal" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered"role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title"><i class="far fa-id-card"></i> Add Card Delivery Details</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				</div>
				<div class="modal-body scrollable">
					<form method="POST" id="add-delivered-cards-details" class="add-form" enctype="form-data">
					<fieldset>
						<input type="hidden" id="action" name="action" value="add-card-delivery-details" />
						<div class="row">
						  <div class="col-md-4 add-form-label"><p>Delivery Person</p></div>
						  <div class="col-md-8">
								<select class="form-control" name="delivery_person" required>
								<option value="">--Select Delivery Person--</option>
								    <c:forEach var="allDeliveryPerson" items="${allDeliveryPerson}">
										<option value="${allDeliveryPerson.getId()}">${allDeliveryPerson.getQid()} - ${allDeliveryPerson.getName()} </option>
									</c:forEach>
								</select>
						   </div>
						 </div>
						 <div class="row">
								<div class="col-md-4 add-form-label">
									<p>Delivery Date</p>
								</div>
								<div class="col-md-8">
									<input type="date" class="form-control date-picker no-future" name="date" autocomplete="off" required />
								</div>
						</div>
						<input type="hidden" name="card_type" value="" /> 
						<button type="submit" class="btn btn-secondary position-right"><i class="far fa-paper-plane"></i> Submit</button>
					</fieldset>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info position-right" data-dismiss="modal"><i class="far fa-window-close"></i> Close</button>
				</div>
			</div>
		</div>
	</div>

<%@include file="../includes/footer.jsp"%>
<script type="text/javascript">
	"use strict"
  var tagIds = [];
  var cardNo = [];
  // var webUrl="http://192.168.1.123/drivingqatar-admin/CardTarckingController";
  var webUrl="http://admin.drivingqatar.com/CardTarckingController";
	$(document).ready(function() {
		
		$("#filterButton").click(function(){
			populateTrainerCardStatusTable();
		});
		
		$("#vehiclefilterButton").click(function(){
			populateVehicleCardStatusTable();
		});
		
		populateTrainerCardStatusTable();
		populateTrainerFilterTags();

		populateVehicleCardStatusTable();
		populateVehicleFilterTags();

		$('#trainersTab').on('shown.bs.tab', function(e) {
			$($.fn.dataTable.tables(true)).DataTable().columns.adjust();
		});

		$('#vehiclesTab').on('shown.bs.tab', function(e) {
			$($.fn.dataTable.tables(true)).DataTable().columns.adjust();
		});
		
		//Add Card Delivered Details Scripts
		$("#add-delivered-cards-details").submit(function (event){
		    event.preventDefault();
			let form = gettrainersParam("add-delivered-cards-details");
			
			form['selectedTagIds'] =  tagIds;
			form['selectedCardNo'] = cardNo;
			console.log("form--->", form);
			
			$.ajax({
				type : "POST",
				url : "CardTarckingController",
				data : form,
		        success: function (data) {
		  	    	if(data == '1') {
	  	    	   	 Swal.fire(
			       			    'Done!',
			       			    'Successfully Delivered Trainers Cards!!',
			       			    'success'
							  ).then((val) => {
								  if(val) {
								    populateTrainerCardStatusTable();
									populateTrainerFilterTags();
									
									populateVehicleCardStatusTable();
									populateVehicleFilterTags();
								  }
							  })
		  	    	}else{
		  	    		 Swal.fire(
								    'fail!',
								    'Something Went Wrong!!',
								    'error'
								  )
		  	    	}
		  	    },
		  	    error : function(){
		  	    	console.log("Failed to add Card Delivered  Details!", "error");
		  	    }
		  	}).done(function() {
		  		console.log("reset after done ");
		        $('#add-deliver-details-modal').modal('hide');
		      });  
		  	return false
		  });

	});

	const getSelectedIds = (selectedIds, position) => {
		   
		   let tempSelectedIds = [];
		   
		   for(let key = 0; key < selectedIds.length; key++) {
			   
			   let tempIds = selectedIds[key].split("_")[position].trim();
			   
			   
			   tempSelectedIds.push(tempIds);
		   };
		   
		   return tempSelectedIds;
	   }

	function populateTrainerCardStatusTable() {
		
		let table = $('#trainerCardStatusTable').dataTable(
				{
					
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
						data : gettrainersParam("trainerCardStatusListFilterForm"),
						type : "GET",
						
						dataFilter : function(data) {
					     console.log("DATA ----> ", data);
                         
							var newData = JSON.parse(new Object(data));
							let json = {};

							json.draw = newData.draw;
							json.recordsTotal = newData.recordsTotal;
							json.recordsFiltered = newData.recordsFiltered;
							json.data = JSON.parse(newData.data)
                    
							$(".table-header-title").html("<h4>${text['applicants']} <span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
						    console.log("data-->" ,JSON.stringify(json));
							return JSON.stringify(json); // return JSON string
                         
						}
					},
					columns : [
 					{ "data" : getCheckboxId }, 
					{ "data" : "id"},
					{ "data" : "name", "width": "500px"}, 
					{ "data" : "school" }, 
					{ "data" : getActiveStatus },
					{ "data" : getApprovedStatus },
					{ "data" : getPrintedStatus },
					{ "data" : getDeliverStatus },
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
			            },
			            {
			                targets: 0,
			                checkboxes: {
			                   selectRow: true,
			                   selectAllPages: false,
			                   selectCallback: function() {
			                	   
			                     let selected = table.api().column(0).checkboxes.selected();
			                	   
			               	     if(selected.length > 0) {
			               	    	$(".action-btn").fadeIn().css("display", "inline-block");
			               	     } else {
			               	    	 $(".action-btn").fadeOut();
			               	     }
			                	   
			                   }
			                },
			                
			                createdCell:  function (td, cellData, rowData, row, col){
								if(rowData.isActive == 1 && rowData.isCardPrinted == 1 && rowData.isApprove == 1){ 
									if(rowData.delivery_status != 0 ){
										this.api().cell(td).checkboxes.disable();		
				                	}
			                        
			                	}else{
			                		this.api().cell(td).checkboxes.disable();	
			                	}
			                	
			                }
			            }
			            
			          ],
			  		buttons: [
			        	  { 
			        		  text: "<i class='far fa-thumbs-up'></i> Delivered to Traffic Department",
			        		  className: "back-green action-btn",
			        		  action: function () {
			        			  
			        			  let selected = table.api().column(0).checkboxes.selected();
			        			  
			        			  let actionObj = { selected: selected }
			        			  
			        			  let selectedtagIds = getSelectedIds(selected, 0);
			                	  let selectedtrainerIds = getSelectedIds(selected, 1);
			                	   
			                	   console.log("------------CLICKED-----------");
		                	       console.log("Selected Tag ID ----> ", selectedtagIds);
		                	       console.log("Selected Trainer ID ", selectedtrainerIds);
			        		       console.log("Action obj ---> ", actionObj);	  
			        			
			        		       doStoreTempTrainer(selectedtagIds, selectedtrainerIds);
			       			   		
			        		  } 
			        	  }
			        ],
			        select: {
			      	  style: "multi",
			    	  selector: "td:not(.not-selectable)"
			         },
			        order: [[1, "asc"]],
			        dom: 'lBftipr'
					
				});
	}
	
	function doStoreTempTrainer(selectedtagIds, selectedtrainerIds) {
		
		$('#add-deliver-details-modal').modal('show');
		
		let modal =  $("#add-deliver-details-modal")[0];
		    
		$(modal).find("input[name='card_type']").val("Trainer");
		
        tagIds = selectedtagIds;
        cardNo = selectedtrainerIds;
        console.log("------------------ Assinged to Global -----------------------");
	    console.log("Tag Ids", tagIds);
	    console.log("size of Tag Ids  : " ,  tagIds.length);
	    
	    console.log("Card Ids", cardNo);
	    console.log("size of Card Ids  : " ,  cardNo.length);	
    }
	
	function getCheckboxId(data) {
		return data.tagId + "_" + data.id;
	}

	function gettrainersParam(id) {
		var formData = $("#" + id).serializeArray();
		var jsonFormData = {};

		$.map(formData, function(n, i) {

			if (n['value'] != "") {
				var val=n['value'];
				jsonFormData[n['name']] = val;
			}

		});
		return jsonFormData;
	}

	$("#trainerCardStatusListFilterForm").on("click",".filter-tag-remove",function() {
				let inputName = $(this).parents(".filter-tag").data("name");

				$(this).parents(".filter-tag").remove();

				$("#trainerCardStatusListFilterForm select[name='"+ inputName + "'] option:first-child").prop("selected", true);

				populateTrainerCardStatusTable();
			});
	function populateTrainerFilterTags() {

		let tags = "";

		$.each($("#trainerCardStatusListFilterForm select"), function(key, value) {

							if ($(value).val() != "" && Number($(value).val()) != 100) {

								let tagText = $(value).find("option:selected")
										.text().trim();

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

	};

	function getActiveStatus(data) {

		if (Number(data.isActive) === 1)  {
			return `<i style="font-size: 40px; color: #28a745;" class="far fa-check-circle"></i>`;
		}else{
			return `<i style="font-size: 40px; color: #cc3300;" class="far fa-times-circle"></i>`;
		}
		/* if (Number(data.isActive) === 1  && Number(data.isApprove === 1) && Number(data.isCardPrinted === 0) && Number(data.delivery_status === 0)) {
			return `<span class="badge badge-success"> Printed</span> <span class="badge badge-success">Ready to Delivered</span>`;
		}
		if (Number(data.isActive) === 1  && Number(data.isApprove === 0) && Number(data.isCardPrinted === 0) && Number(data.delivery_status === 0)){
			return `<span class="badge badge-success"> Printed</span> <span class="badge badge-danger">Delivered</span>`;

		}
		if (Number(data.isActive) === 1  && Number(data.isApprove === 0) && Number(data.isCardPrinted === 0) && Number(data.delivery_status === 0)){
			return `<span class="badge badge-success"> Printed</span> <span class="badge badge-danger">Delivered</span>`;

		} */
	}
	
	function getApprovedStatus(data) {
		
		if (Number(data.isApprove) === 1 )  {
			return `<i style="font-size: 40px; color: #28a745;" class="far fa-check-circle"></i>`;
		}else{
			return `<i style="font-size: 40px; color: #cc3300;" class="far fa-times-circle"></i>`;
		}
		
	}

	function getPrintedStatus(data) {

		if ( Number(data.isCardPrinted)=== 1)  {
			return `<i style="font-size: 40px; color: #28a745;" class="far fa-check-circle"></i>`;
		}else{
			return `<i style="font-size: 40px; color: #cc3300;" class="far fa-times-circle"></i>`;
		}
		
	}
	
function getDeliverStatus(data) {

		if (Number(data.delivery_status) === 1)  {
			return `<i style="font-size: 40px; color: #28a745;" class="far fa-check-circle"></i>`;
		}else{
			return `<i style="font-size: 40px; color: #cc3300;" class="far fa-times-circle"></i>`;
		}
		
	}
	

	function populateVehicleCardStatusTable() {
		
		let table = $('#vehicleCardStatusTable')
				.dataTable(
						{
							destroy : true,
							scrollX : true,
							responsive : true,
							"searching" : false,
							lengthMenu : [ [ 12, 30, 100 ], [ 12, 30, 100 ] ],
							pageLength : 12,
							pagingType : "full_numbers",

							serverSide : true,
							// 							processing : true,

							ajax : {
								url : webUrl,
								data : getVehiclesParam(),
								type : "GET",
								dataFilter : function(data) {
									var newData = JSON.parse(new Object(data));

									let json = {};

									json.draw = newData.draw;
									json.recordsTotal = newData.recordsTotal;
									json.recordsFiltered = newData.recordsFiltered;
									json.data = JSON.parse(newData.data)

									$(".table-header-title").html("<h4>${text['applicants']} <span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");

									return JSON.stringify(json); // return JSON string
								}
							},
							columns : [

							{ "data" : getCheckboxPlateNo },
							{ "data" : "plate_no" , "width": "325px"  }, 
							{ "data" : "school"	, "width": "425px" }, 
							{ "data" : "vehicleType" , "width": "425px" }, 
							{ "data" : getActiveStatus },
							{ "data" : getApprovedStatus },
							{ "data" : getPrintedStatus },
							{ "data" : getDeliverStatus }
							
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
					            },
					            {
					                targets: 0,
					                checkboxes: {
					                   selectRow: true,
					                   selectAllPages: false,
					                   selectCallback: function() {
					                	   
					                	 let selected = table.api().column(0).checkboxes.selected();
					                	
					               	     if(selected.length > 0) {
					               	    	$(".action-btn").fadeIn().css("display", "inline-block");
					               	     } else {
					               	    	 $(".action-btn").fadeOut();
					               	     }
					                	   
					                   }
					                },
					                
					                createdCell:  function (td, cellData, rowData, row, col){
										if(rowData.isActive == 1 && rowData.isCardPrinted == 1 && rowData.isApprove == 1){ 
											if(rowData.delivery_status != 0 ){
												this.api().cell(td).checkboxes.disable();		
						                	}
					                	}else{
					                		this.api().cell(td).checkboxes.disable();	
					                	}
					                	
					                }
					            }
					            
					          ],
					  		buttons: [
					        	  { 
					        		  text: "<i class='far fa-thumbs-up'></i> Delivered to Traffic Department",
					        		  className: "back-green action-btn",
					        		  action: function () { 
					        			  
					        			  let selected = table.api().column(0).checkboxes.selected();
					        			  
					        			  let actionObj = { selected: selected }
					        			  
					        			  console.log("seleceted ----> ", actionObj);
					        			  
					        			  let selectedtagIds = getSelectedIds(selected, 0);
					                	  let selectedPlateNo = getSelectedIds(selected, 1);
					                	   
					                	   console.log("------------CLICKED-----------");
				                	       console.log("Selected Tag ID ----> ", selectedtagIds);
				                	       console.log("Selected Plate No ", selectedPlateNo);
					        		       console.log("Action obj ---> ", actionObj);	  
					        			
					        		       doStoreTempVehicle(selectedtagIds, selectedPlateNo);
					       			   		
					        		  } 
					        	  }
					        ],
					        select: {
					      	  style: "multi",
//		 			    	  selector: "td:not(.not-selectable)"
					         },
					        order: [[1, "asc"]],
					        dom: 'lBftip'
							
						});
	}
	
	function doStoreTempVehicle(selectedtagIds, selectedPlateNo) {
		
		$('#add-deliver-details-modal').modal('show');
		
		let modal =  $("#add-deliver-details-modal")[0];
		    
		$(modal).find("input[name='card_type']").val("Vehicle");
		
        tagIds = selectedtagIds;
        cardNo = selectedPlateNo;
        console.log("------------------ Assinged to Global -----------------------");
	    console.log("Tag Ids", tagIds);
	    console.log("size of Tag Ids  : " ,  tagIds.length);
	    
	    console.log("Card No", cardNo);
	    console.log("size of Card Nos  : " ,  cardNo.length);	
    }
	
	function getCheckboxPlateNo(data) {
		return data.tagId + "_" + data.plate_no;
	}

	function getVehiclesParam() {
		var formData = $("#vehicleCardStatusListFilterForm").serializeArray();
		var jsonFormData = {};

		$.map(formData, function(n, i) {

			if (n['value'] != "") {
				jsonFormData[n['name']] = n['value'];
			}

		});

		return jsonFormData;
	}

	$("#vehicleCardStatusListFilterForm").on(
			"click",
			".filter-tag-remove",
			function() {
				let inputName = $(this).parents(".filter-tag").data("name");

				$(this).parents(".filter-tag").remove();

				$(
						"#trainerCardStatusListFilterForm select[name='"
								+ inputName + "'] option:first-child").prop(
						"selected", true);

				populateVehicleCardStatusTable();
			});

	function populateVehicleFilterTags() {

		let tags = "";

		$.each($("#vehicleCardStatusListFilterForm select"),function(key, value) {

							if ($(value).val() != ""
									&& Number($(value).val()) != 100) {

								let tagText = $(value).find("option:selected")
										.text().trim();

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

	$("ul li").click(function() {
		$(this).siblings('li').removeClass('active');
		$(this).addClass('active');
	});
	
	function notifyResult(title, msg, type) {
		
		Swal.fire({
	   		  title: title,
	   		  text: msg,
	   		  type:  type,
	   		
	   		})
	}
</script>