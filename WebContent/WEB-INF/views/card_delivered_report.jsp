<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

  <!-- jQuery UI Datepicker - Default functionality -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">

<%@ page import="com.beans.DeliveryPerson" %>

<jsp:useBean id="cardTrackingDao" class="com.dao.CardTrackingDAO"></jsp:useBean>

<%
ArrayList<DeliveryPerson> delivery_person = cardTrackingDao.getAllDeliveryPerson();
%>

<c:set var="allDeliveryPerson" value="<%=delivery_person%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
	
		<div class="record-container">
			<div class="container-record" style="border-radius: 18px;">

				<div class="row">
					<div class="col-md-12 grid-margin stretch-card">
						<div class="title-card position-relative">
							<h5>Delivered Trainer's/Vehicle's Card Report</h5>
						</div>
					</div>
				</div>

				<div class="row">
					<form class="filter-form" id="DeliveredCardReportFilterForm">
		                <div class="row">
		                    <div class="col-lg-2">
				                <select name="card_type" id="card_type" class="form-control">
								    <option value="">-- Select Card Type--</option>
									<option value="Vehicle" >Vehicle</option>
									<option value="Trainer" >Trainer</option>
								</select>
		                    </div>
		                    <div class="col-lg-2">
		                    		<select name="delivery_person" id="delivery_person" class="form-control">
									    <option value="">-- Select Delivery Person--</option>
									    <c:forEach var="allDeliveryPerson" items="${allDeliveryPerson}">
										 <option value="${allDeliveryPerson.getQid()}">${allDeliveryPerson.getQid()} - ${allDeliveryPerson.getName()} </option>
								     	</c:forEach>
									</select>
		                    </div>
		                    <div class="col-lg-2">
		                    	<input type="text" class="form-control" id="delivered_from_date" name="delivered_from_date" autocomplete="off" placeholder="Select From Date">
		                    </div>
		                    <div class="col-lg-2">
		                    	<input type="text" class="form-control" id="delivered_to_date" name="delivered_to_date" autocomplete="off" placeholder="Select To Date">
		                    </div>
		                	<div class="col-lg-2">
								<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Search by QID, Name Here">
							</div>
							
							<input class="btn-filter" type="submit" value="Apply Filter" id="filterButton" />
							<input class="btn-filter" type="reset" value="Clear Filter" />
		                </div>
						<input type="hidden" name="action" value="fetch-delivered-card-report" />
					</form>
					<div class="col-md-12" style="padding: 10px 48px;">
						<table id="deliveredCardReportTable" class="table table-striped table-bordered" style="width: 100%; text-align: center">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th>Delivered By</th>
									<th>Qatar ID</th>
									<th>Mobile Number</th>
									<th>Card Type</th>
									<th>No. of Cards</th>
									<th>Date</th>
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
		</div>
	
	</div>
</div>

	<div class="modal fade" id="view-delivered-cards-modal" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-id-card"></i> List of Delivered Cards
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
				
				 <ul class="list-group hidden">
				  <li class="list-group-item disabled" style="background-color: #eff6fe;">Card ID list :</li>
				 </ul>
				 
				 <div class="card-list">
				 
				 </div>
				
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info position-right"
						data-dismiss="modal">
						<i class="far fa-window-close"></i> Close
					</button>
				</div>
			</div>
		</div>
	</div>


<%@include file="../includes/footer.jsp"%>


<script>
"use strict";

$(document).ready(function(){
	
  populateCardDeliveredReportTable();
  populateCardDeliveredReportsFilterTags()
  
	$( "#delivered_from_date" ).datepicker({ 
		maxDate: new Date,
		dateFormat: 'yy-mm-dd'
	
	});
	$( "#delivered_to_date" ).datepicker({ 
		maxDate: new Date,
		dateFormat: 'yy-mm-dd'
	
	});
	
});

$( function() { $( "#delivered_from_date" ).datepicker({ maxDate: new Date, minDate: new Date(2007, 6, 12) ,
    onClose: function (selected) {
        if(selected.length <= 0) {
            // selected is empty
            $("#delivered_to_date").datepicker('disable');
        } else {
            $("#delivered_to_date").datepicker('enable');
        }
        $("#delivered_to_date").datepicker("option", "minDate", selected);
      }
});

$( "#delivered_to_date" ).datepicker({ maxDate: new Date });

});
  
$("#DeliveredCardReportFilterForm").submit( function(event) {
  event.preventDefault();
  populateCardDeliveredReportTable();	
  populateCardDeliveredReportsFilterTags();
  
  return false;
  
});

$("#DeliveredCardReportFilterForm").on("click", ".filter-tag-remove", function() {
	let inputName = $(this).parents(".filter-tag").data("name");
	$(this).parents(".filter-tag").remove();
	$("#DeliveredCardReportFilterForm select[name='" + inputName + "'] option:first-child").prop("selected", true);
	populateCardDeliveredReportTable();
	
 });

function populateCardDeliveredReportsFilterTags() {
	let tags = "";
    $.each($("#DeliveredCardReportFilterForm select"), function(key, value) {
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
	
function populateCardDeliveredReportTable() {
	
	let table = $('#deliveredCardReportTable').dataTable({
		destroy : true,
		scrollX : true,
		responsive : true,
		"searching" : false,
		lengthMenu : [ [ 12, 30, 100 ], [ 12, 30, 100 ] ],
		pageLength : 12,
		pagingType : "full_numbers",

		serverSide : true,
		
		ajax : {
			url : "CardTarckingController",
			data : getCardDeliveredReportParam(),
			type : "GET",
			dataFilter : function(data) {
				let newData = JSON.parse(new Object(data));
				let json = {};
				
				json.draw = newData.draw;
				json.recordsTotal = newData.recordsTotal;
				json.recordsFiltered = newData.recordsFiltered;
				json.data = JSON.parse(newData.data);
				console.log(json)
				$(".table-header-title").html("<h4>delivered cards :<span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
				
				return JSON.stringify(json);
			}
		},
		columns : [
			{ data : "name" },
			{ data : "qid" },
			{ data : "mobile" },
			{ data : "card_type" },
			{ data : "card_count" },
			{ data : "date" },
			{ data : getEditButton },
		],
		columnDefs : [
			 {
				data : null,
				defaultContent : "<i>N/A</i>",
				targets : "_all"
			 },
			 {
				targets : "_all",
				orderable : false,
			 }
		]
		
	});
	
}

function getCardDeliveredReportParam() {
	 let formData = $("#DeliveredCardReportFilterForm").serializeArray();
	 let jsonFormData = {};
	 
	 $.map(formData, function(n, i) {
		 if(n['value'] != "") {
			 jsonFormData[n['name']] = n['value'];
		 }
	 });
	 
	 return jsonFormData;
}
	
function getEditButton(data) {
	let insertString = "";
	insertString += "<button type='button' onclick='viewDeliveredCardList("+data.id+")' class='btn btn-outline-success'> <i class='fas fa-eye'></i> View </button>"
	
	return insertString;
}	

function viewDeliveredCardList(delivery_id) {
	  
	  $("#view-delivered-cards-modal .card-list").empty();
	   const $listGroup = $("#view-delivered-cards-modal .list-group");
	  
	   $.get("CardTarckingController?action=get-delivered-cards&delivery_id="+delivery_id,
	    function(data,status){
		   
		  const $cloneListGroup =  $listGroup.clone().removeClass("hidden");
		  
		  data.forEach(function(item) {
			  $cloneListGroup.append(`<li class="list-group-item">\${item.card_id}</i>`);
		  });
		  
		  $("#view-delivered-cards-modal .card-list").append($cloneListGroup);
		  	
		  $('#view-delivered-cards-modal').modal('show');
	  }).fail(
	  function(jqXHR,textStatus,errorThrown) {
		console.log('error');
		console.log(jqXHR);
		console.log(textStatus);
		console.log(errorThrown);
	   });
}

</script>