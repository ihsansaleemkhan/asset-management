<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<style>
.add-deliveryPerson-btn {
	margin: 8px 35px;
	z-index: 9;
	position: relative;
}
</style>
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="delivery-person-container">
			<div class="user-inner-list">
				<h5 class="card-header info-color white-text text-center py-4"
					style="padding-bottom: 10px !important;">
					<strong>Manage Delivery Person Info</strong>
				</h5>
				<c:if test="${not empty success_message}">
					<span id="success_message"
						style="color: green; margin: 0 800px; top: 38px; position: relative;">${success_message}</span>
					<br>
				</c:if>
				<c:if test="${not empty fail_message}">
					<span id="fail_message"
						style="color: red; margin: 0 700px; top: 38px; position: relative;">${fail_message}</span>
				</c:if>
				
				<button type="button" data-toggle="modal" data-target="#add-delivery-person-modal" class="btn btn-outline-secondary add-deliveryPerson-btn"><i class="fas fa-people-carry"></i> Add Delivery Person </button>

				<div class="row" style="padding: 0 35px; position: relative; top: -40px;">
					<div class="col-md-12">
						<table id="manage_delivery_person_table" class="display table table-striped table-bordered" style="text-align: center;">
							<thead>
								<tr>
									<th>Name</th>
									<th>Qatar ID</th>
									<th>Mobile Number</th>
									<th style="width: 200px !important;">Action</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
								<tr>
									<th>Name</th>
									<th>Qatar ID</th>
									<th>Mobile Number</th>
									<th style="width: 200px !important;">Action</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
				
			</div>
		</div>
	</div>
</div>

<div class="modal fade" id="add-delivery-person-modal" tabindex="-1" role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-people-carry"></i> Add Delivery Person
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="CardTarckingController" method="POST" id="add-delivery-person-form" class="add-form" enctype="multipart/form-data">
						<fieldset>
							<input type="hidden" id="action" name="action" value="add-delivery-person" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="name" required placeholder="Enter Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Qatar ID</p>
								</div>
								<div class="col-md-8">
									<input type="number" min="0" max="999999999999999" maxlength="15" class="form-control limit" name="qid" required placeholder="Enter Qatar ID" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Mobile Number</p>
								</div>
								<div class="col-md-8">
									<input type="number" min="0" max="99999999" maxlength="8" class="form-control limit" name="mobile" required placeholder="Enter Mobile Number" />
								</div>
							</div>
							<!-- <div class="row">
								<div class="col-md-4 add-form-label">
									<p>Image</p>
								</div>
								<div class="col-md-8">
									<input type="file" class="form-control" name="image"
										required placeholder="" />
								</div>
							</div> -->
						</fieldset>
						<button style="margin-left: 368px; margin-top: 8px;" type="submit"
							class="btn btn-secondary position-right">
							<i class="far fa-paper-plane"></i> Submit
						</button>
					</form>
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
	
	<div class="modal fade" id="update-delivery-person-modal" tabindex="-1"
		role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-people-carry"></i> Update Delivery Person
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="CardTrackingController" method="POST" id="update-delivery-person-form"
						class="update-form" enctype="multipart/form-data">
						
						<fieldset>
							<input type="hidden" name="action" value="update-delivery-person-detail" />
							<input type="hidden" id="currentQID" name="currentQID" value="" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="name" required placeholder="Enter Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Qatar ID</p>
								</div>
								<div class="col-md-8">
									<input type="number" min="0" max="999999999999999" maxlength="15" class="form-control limit" name="qid" required placeholder="Enter Qatar ID" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Mobile Number</p>
								</div>
								<div class="col-md-8">
									<input type="number" min="0" max="99999999" maxlength="8" class="form-control limit" name="mobile" required placeholder="Enter Mobile Number" />
								</div>
							</div>
						</fieldset>
						<button style="margin-left: 367px; margin-top: 8px;" type="submit"
							class="btn btn-secondary position-right">
							<i class="far fa-paper-plane"></i> Update
						</button>
					</form>
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

<script type="text/javascript">

$(document).ready(function() {
	getDeliveryPersonList();
});

function getDeliveryPersonList() {
  $.ajax({
	  'url' : "CardTarckingController?action=get-all-delivery-person-list",
	  'method' : "GET",
	  'contentType' : 'application/json'
  }).done( function(data){
	  populateDeliveryPersonTable(data);
  })	
}

function populateDeliveryPersonTable(data) {
    $('#manage_delivery_person_table').dataTable({
    	 destroy: true,
		 data: data,
		 responsive: true,
		 lengthChange: false,
	     pageLength: 08,
	     pagingType: "full_numbers",
		    columns: [
		    	{ data: "name" },
		    	{ data: "qid"},
		    	{ data: "mobile" },
		    	{ data: getEditButton},
		    ],
		    columnDefs: [
	            {
	              data: null,
	              defaultContent: "<i>N/A</i>",
	              targets: "_all"
	            }
	            
	          ]
	  });	
	}
	
	
function getEditButton(data) {
	let insertString = "";
	insertString += "<button type='button' class='btn btn-outline-success btn-update-person'> <i class='fas fa-edit'></i> Update </button>"
	               +"&nbsp;<button type='button' onclick='deleteDeliveryPerson("+data.id+")' class='btn btn-outline-danger'> <i class='far fa-trash-alt'></i> Delete </button>"
	
	return insertString;
}

$("body").on("click", ".btn-update-person", function() {
	
	let row = $(this).parents("tr")[0];
	let $dataTable = $('#manage_delivery_person_table').DataTable();
	let rowdata = $dataTable.row(row).data();
	
    let modal =  $("#update-delivery-person-modal")[0];
    
    $(modal).find("input[name='name']").val(rowdata.name);
    $(modal).find("input[name='qid']").val(rowdata.qid);
    $(modal).find("input[name='currentQID']").val(rowdata.qid);
    $(modal).find("input[name='mobile']").val(rowdata.mobile);
    
    $("#update-delivery-person-modal").modal("show");
	
});

function deleteDeliveryPerson(id) {
	console.log("ID -- >",id);
	
	Swal.fire({
		  title: "Do You Realy Want to Delete Delivery Person!!",
		  text: "Select 'YES' to Delete Delivery Person Details, 'NO' to Cancel the Operation.",
		  type: 'question',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: "<i class='fas fa-check-circle'></i> YES",
	      cancelButtonText: "<i class='fas fa-times-circle'></i> NO"
		}).then((result) => {
		 if (result.value) {
			$.ajax({
				type : "POST",
				url : "CardTarckingController?action=delete-delivery-person-detail&id="+id,
		        success: function (result) {
		       	 Swal.fire(
		       			    'Done!',
		       			    'Successfully Deleted Delivery Person!!',
		       			    'success'
						  ).then((val) => {
							  if(val) {
								  getDeliveryPersonList();
							  }
						  })
		        },
		        error: function (result) {
		        	 Swal.fire(
							    'fail!',
							    'Something Went Wrong!!',
							    'error'
							  )
		        },
		        
		    }).done(function() {
		    	resetAfterDone();
		    });
		  } 
	});
	return false;
}

$(document).on("submit","#add-delivery-person-form", function (event) {
	event.preventDefault();
	let form = $("#add-delivery-person-form").serialize();
	console.log("form--->",form);
	
	$.ajax({
		type : "POST",
		url : "CardTarckingController?"+form,
        success: function (result) {
        	console.log("result--->",result);
			if(Number(result) == 1){
				 Swal.fire(
						    'Done!',
						    'Successfuly Added .',
						    'success'
						  ).then((val) => {
							  if(val) {
								  console.log("val ---> ", val);
								  getDeliveryPersonList();
							  }
						  })
			}else if(Number(result) == 2){
				 Swal.fire(
						    'fail!',
						    'QID is Already Exists!!',
						    'error'
						  )
			}
			else {
				 Swal.fire(
						    'fail!',
						    'Something Went Wrong!!',
						    'error'
						  )
			}
			$("#add-delivery-person-modal").modal("toggle");
        },
        error: function () {
        	Swal.fire(
				    'fail!',
				    'somthing went wrong !!',
				    'error'
				  )
        	
        },
        
    }).done(function() {
    	resetAfterDone();
		$("#add-delivery-person-modal").modal("hide");
		
    });
    return false;
});


function resetAfterDone() {
	$("form").trigger("reset");
	$("form .error").remove();
	$("form .check-box-error").addClass("hidden");
	$(".custom-file-label").empty().text("Choose file...");
	$(".photo-preview").prop("src", "images/not.png");
}

var inputQuantity = [];
$(function() {
  $(".limit").each(function(i) {
    inputQuantity[i]=this.defaultValue;
     $(this).data("idx",i); // save this field's index to access later
  });
  $(".limit").on("keyup", function (e) {
        let $field = $(this),
        val=this.value,
        $thisIndex=parseInt($field.data("idx"),10); // retrieve the index
//    window.console && console.log($field.is(":invalid"));
      //  $field.is(":invalid") is for Safari, it must be the last to not error in IE8
    if (this.validity && this.validity.badInput || isNaN(val) || $field.is(":invalid") ) {
        this.value = inputQuantity[$thisIndex];
        return;
    } 
    if (val.length > Number($field.attr("maxlength"))) {
      val=val.slice(0, 5);
      $field.val(val);
    }
    inputQuantity[$thisIndex]=val;
  });      
});


$(".modal").on("submit", "#update-delivery-person-form", function (event){
	event.preventDefault();
	let form = $("#update-delivery-person-form").serialize();
	console.log("form--->",form);
	
	Swal.fire({
 		  title: "Do You Realy Want to Update Delivery Person!!",
 		  text: "Select 'YES' to Update Delivery Person Info, 'NO' to Cancel the Operation.",
 		  type: 'question',
 		  showCancelButton: true,
 		  confirmButtonColor: '#3085d6',
 		  cancelButtonColor: '#d33',
 		  confirmButtonText: "<i class='fas fa-check-circle'></i> YES",
	      cancelButtonText: "<i class='fas fa-times-circle'></i> NO"
 		}).then((result) => {
 		 if (result.value) {
 			$.ajax({
 				type : "POST",
 				url : "CardTarckingController?"+form,
		        success: function (result) {
		        	console.log(result);
		       	 Swal.fire(
		       			    result.title,
		       			    result.msg,
		       			    result.type
						  ).then((val) => {
							  if(val) {
								  console.log("val ---> ", val);
								  getDeliveryPersonList();
							  }
						  })
		        },
		        error: function (result) {
		        	 Swal.fire(
							    'fail!',
							    'Something Went Wrong!!',
							    'error'
							  )
		        },
		        
		    }).done(function() {
		    	resetAfterDone();
 				$("#update-delivery-person-modal").modal("hide");
		    });
 		  } 
 	});
	return false;
});

</script>