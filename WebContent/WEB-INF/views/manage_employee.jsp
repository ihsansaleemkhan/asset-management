<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>



<style>
.add-employeeInfo-btn {
	margin: 8px 33px;
	z-index: 9;
	position: relative;
}
</style>
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="user-container">
			<div class="user-inner-list">
				<h5 class="card-header info-color white-text text-center py-4"
					style="padding-bottom: 10px !important;">
					<strong>Manage Employee Info</strong>
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

				<button type="button" data-toggle="modal"
					data-target="#add-employee-modal"
					class="btn btn-outline-secondary add-employeeInfo-btn">
					<i class="fas fa-user-plus"></i> Add Employee
				</button>

				<div class="row"
					style="padding: 0 35px; position: relative; top: -40px;">
					<div class="col-md-12">
						<table id="manage_employees_table"
							class="display table table-striped table-bordered"
							style="width: 100%">
							<thead>
								<tr style="text-align: center;">
									<th style="width: 5px;">ID#</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
									<th>Mobile Number</th>
									<th>Department</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								
							</tbody>
							<tfoot>
								<tr style="text-align: center;">
									<th style="width: 5px;">ID#</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
									<th>Mobile Number</th>
									<th>Department</th>
									<th>Action</th>
								</tr>
							</tfoot>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

   <div class="modal fade" id="add-employee-modal" tabindex="-1" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-user-plus"></i> Add Employee
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="UserController" method="POST" id="employee-form"
						class="add-form" enctype="multipart/form-data">
						<fieldset>
							<input type="hidden" id="action" name="action" value="add-employee" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>First Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="firstName"
										required placeholder="Enter First Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Last Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="lastName"
										required placeholder="Enter Last Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Email</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="email"
										required placeholder="Enter Email" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Mobile Number</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="mobileNumber"
										required placeholder="Enter Mobile Number" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Department</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="department"
										required placeholder="Enter Department" />
								</div>
							</div>
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
	
	
	<div class="modal fade" id="edit-employee-modal" tabindex="-1"
		role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-user-edit"></i> Update Employee
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="UserController" method="POST" id="edit-employee-form"
						class="add-form" enctype="multipart/form-data">
						
						<fieldset>
							<input type="hidden"  name="action" value="update-employee" />
							<input type="hidden" id="update-employeeId" name="employeeId" value="" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>First Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="firstName" id="update-firstName"
										required placeholder="Enter First Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Last Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="lastName" id="update-lastName"
										required placeholder="Enter Last Name" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Email</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="email" id="update-email"
										required placeholder="Enter Email" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Mobile Number</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="mobileNumber" id="update-mobileNumber"
										required placeholder="Enter Mobile Number" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Department</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="department" id="update-department"
										required placeholder="Enter Department" />
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
<script>


$(document).ready(function(){
	
	 showDataTable();
	 
	$("#employee-form").submit(function(e){
		e.preventDefault();
		let data = $(this).serialize();
		console.log(data);
		addEmployee(data);
	});
	$("#edit-employee-form").submit(function(e){
		e.preventDefault();
		let data = $(this).serialize();
		console.log(data);
		updateEmployee(data);
	});
	$(document).on("click",".update-employee-btn",function(e){
		let empId = $(this).parent().parent().find('.emp-id').text();
		let firstName = $(this).parent().parent().find('.first-name').text();
		let lastName = $(this).parent().parent().find('.last-name').text();
		let email = $(this).parent().parent().find('.email').text();
		let mobileNumber = $(this).parent().parent().find('.mobile-number').text();
		let department = $(this).parent().parent().find('.department').text();
		
		

		$("#update-employeeId").val(empId);
		$("#update-firstName").val(firstName);
		$("#update-lastName").val(lastName);
		$("#update-email").val(email);
		$("#update-mobileNumber").val(mobileNumber);
		$("#update-department").val(department);
		$("#edit-employee-modal").modal();
	});
});
var table ;
function showDataTable(){
// 	console.log("inside function now");
	
 	if ( ! $.fn.DataTable.isDataTable('#manage_employees_table') ) {
 			table = $("#manage_employees_table").DataTable({});
 			table.destroy();
 		}else{
 			table.destroy();
 		}
	$.ajax({
		url : "UserController?action=get-employee-info",
		method: "POST"
	}).done(function(result){
		$("#manage_employees_table tbody").empty();
		$.each(result,function(key,std){
		
			var row = "<tr style='text-align: center;'>";
			row +="<td class='emp-id'>"+std.emp_id+"</td>";
			row +="<td class='first-name'>"+std.first_name+"</td>";
			row +="<td class='last-name'>"+std.last_name+"</td>";
			row +="<td class='email'>"+std.email+"</td>";
			row +="<td class='mobile-number'>"+std.mobile_no+"</td>";
			row +="<td class='department'>"+std.department+"</td>";
			
			row +="<td style='width: 200px;'>";
			row +="<button type='button' class='btn btn-outline-success update-employee-btn'> <i class='fas fa-edit'></i> Update </button>";
			row +="&nbsp;<button type='button' class='btn btn-outline-danger' onClick='deleteEmployee("+std.emp_id+")'> <i class='far fa-trash-alt'></i> Delete </button>";
			row +="</td>"
			
			row +="</tr>";
			$("#manage_employees_table tbody").append(row);
		});

			table = $("#manage_employees_table").DataTable({
				"pageLength": 8,
				"lengthChange": false
	
			});
		
		
	}).fail(function(error){
		 Swal.fire(
				    'fail!',
				    'somthing went wrong !!',
				    'error'
				  )
	});
	
}
function addEmployee(data){
	$.ajax({
		url : "UserController",
		data : data  ,
		method :"POST",
		success : function(result){
			if(result){
				 Swal.fire(
						    'Done!',
						    'Successfuly Added .',
						    'success'
						  );
				showDataTable();
			}else{
				 Swal.fire(
						    'fail!',
						    'somthing went wrong !!',
						    'error'
						  )
			}
			$("#add-employee-modal").modal("toggle");
		},
		fail : function(error){
			Swal.fire(
					    'fail!',
					    'somthing went wrong !!',
					    'error'
					  )
		}
	})
}
function updateEmployee(data){
	$.ajax({
		url : "UserController",
		data : data  ,
		method :"POST",
		success : function(result){
			if(result){
				 Swal.fire(
						    'Done!',
						    'Successfuly Updated .',
						    'success'
						  );
				showDataTable();
			}else{
				 Swal.fire(
						    'fail!',
						    'somthing went wrong !!',
						    'error'
						  )
			}
			$("#edit-employee-modal").modal("toggle");
		},
		fail : function(error){
			Swal.fire(
					    'fail!',
					    'somthing went wrong !!',
					    'error'
					  )
		}
	})
}

function deleteEmployee(emp_id){
	Swal.fire({
		  title: 'Are you sure?',
		  text: "You won't be able to revert this!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
			if(result.value){
			let action = "deleteEmployee";
			$.ajax({
				url : "UserController",
				data : {"action":action,"emp_id":emp_id}  ,
				method :"POST",
				success : function(result){
					if(result){
						 Swal.fire(
								    'Done!',
								    'Successfuly Deleted .',
								    'success'
								  );
						showDataTable();
					}else{
						 Swal.fire(
								    'fail!',
								    'somthing went wrong !!',
								    'error'
								  )
					}
				},
				fail : function(error){
					Swal.fire(
							    'fail!',
							    'somthing went wrong !!',
							    'error'
							  )
				}
			});
			}
		});
}
	
</script>