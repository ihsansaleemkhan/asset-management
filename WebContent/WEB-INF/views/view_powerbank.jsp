<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="record-container">
		  <div class="container-record" style="border-radius: 18px;">

				<div class="row">
					<div class="col-md-12 grid-margin stretch-card">
						<div class="title-card position-relative">
							<h5>List of Power Banks</h5>
						</div>
					</div>
				</div>

				<div class="row">
				   <form class="filter-form" id="powebanksFilterForm">
					  <div class="row">
					        <div class="col-lg-10">
								<input type="text" class="form-control" style="width: 100%;" name="search_filter" id="search_filter" placeholder="Search Asset Here">
							</div>
							
							<input class="btn-filter" type="button" value="Apply Filter" id="filterButton" />
							<input class="btn-filter" type="reset" value="Clear Filter" />
		              </div>
		              <input type="hidden" name="asset_type" value="20" />
					  <input type="hidden" name="action" value="fetch-powerbank-list" />
				   </form>
				   
				   <div class="col-md-12" style="padding: 10px 48px;">
						<table id="powerbanksTable" class="table table-striped table-bordered" style="width: 100%; text-align: center">
							<thead>
								<tr style="background-color: #772b49; color: white;">
									<th colspan="3">Asset Information</th>
									<th colspan="3">Technical Information</th>
								    <th colspan="4">Owner Information</th>
								    <th colspan="1"></th>
								</tr>
								<tr style="background: #0b5d80; color: #fff;">
									<th><i class='fas fa-link'></i>#No</th>
									<th>Tag Name</th>
									<th>Site</th>
									<th>Make</th>
									<th>Model</th>
									<th>Serial Number</th>
									<th>Name</th>
									<th>E-mail</th>
									<th>Mobile</th>
									<th>Department</th>
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

<%@include file="../includes/footer.jsp"%>
<%@include file="../includes/modals.jsp"%>

<script type="text/javascript">
"use strict";

$(document).ready(function(){
	
  populatePowerBankstTable();
  populatePowerBanksListFilterTags();
  
});

$("#powebanksFilterForm").submit( function(event) {
  event.preventDefault();
  
  populatePowerBankstTable();	
  populatePowerBanksListFilterTags();
  
  return false;
  
});

$("#powebanksFilterForm").on("click", ".filter-tag-remove", function() {
	let inputName = $(this).parents(".filter-tag").data("name");
	$(this).parents(".filter-tag").remove();
	$("#powebanksFilterForm select[name='" + inputName + "'] option:first-child").prop("selected", true);
	populatePowerBankstTable();
 });

function populatePowerBanksListFilterTags() {
	let tags = "";
    $.each($("#powebanksFilterForm select"), function(key, value) {
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

function populatePowerBankstTable() {
	
	let table = $('#powerbanksTable').dataTable({
		destroy : true,
		scrollX : true,
		responsive : true,
		"searching" : false,
		lengthMenu : [ [ 12, 30, 100 ], [ 12, 30, 100 ] ],
		pageLength : 12,
		pagingType : "full_numbers",

		serverSide : true,
		
		ajax : {
			url : "AssetController",
			data : getPowerBanksListParam(),
			type : "GET",
			dataFilter : function(data) {
				let newData = JSON.parse(new Object(data));
				let json = {};
				
				json.draw = newData.draw;
				json.recordsTotal = newData.recordsTotal;
				json.recordsFiltered = newData.recordsFiltered;
				json.data = JSON.parse(newData.data);
				$(".table-header-title").html("<h4>Servers :<span class='count_badge'>"+ json.recordsTotal+ "</span></h4>");
				return JSON.stringify(json);
			}
		},
		columns : [
			{ data : "s_no" },
			{ data : "tag_name" },
			{ data : "site" },
			{ data : "make" },
			{ data : "model" },
			{ data : "serial_no" },
			{ data : getName },
			{ data : "email" },
			{ data : "mobile" },
			{ data : "department" },
			{ data : getViewButton },
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

function getPowerBanksListParam() {
	 let formData = $("#powebanksFilterForm").serializeArray();
	 let jsonFormData = {};
	 
	 $.map(formData, function(n, i) {
		 if(n['value'] != "") {
			 jsonFormData[n['name']] = n['value'];
		 }
	 });
	 
	 return jsonFormData;
}

function getViewButton(data) {
	let insertString = "";
	insertString += "<button type='button' onclick='getAssetInfo("+data.asset_id+")' class='btn btn-outline-success'> <i class='fas fa-eye'></i> View </button>"
	
	return insertString;
}

function getName(data) {
	return data.first_name + " " + data.last_name;
}
</script>