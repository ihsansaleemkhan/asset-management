<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>


<%@ page import="com.beans.Users"%>

<jsp:useBean id="UserDao" class="com.dao.UserDao"></jsp:useBean>

<%
	ArrayList<Users> Users = UserDao.getUsersList();
%>
<style>
.search_list_employee , .search_list_employee_update{
	position: absolute;
    background: rgb(255, 156, 156) none repeat scroll 0% 0%;
    min-width: 299px;
    width: auto;
    border-radius: 0;
    max-height: 123px;
    overflow-y: scroll;
    /* color: #000; */
    z-index: 1;
    display: none;
    /* margin-left: 908px !important; */
    /* margin-top: 0px;*/
}
.search_list_employee ul li:hover , .search_list_employee_update ul li:hover{
cursor:pointer;
background-color: #e7e9e9;
}
.search_list_employee::-webkit-scrollbar-track , .search_list_employee_update::-webkit-scrollbar-track
{
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,0.3);
	border-radius: 10px;
	background-color: #F5F5F5;
}

.search_list_employee::-webkit-scrollbar , .search_list_employee_update::-webkit-scrollbar
{
	width: 12px;
	background-color: #F5F5F5;
}

.search_list_employee::-webkit-scrollbar-thumb , .search_list_employee_update::-webkit-scrollbar-thumb
{
	border-radius: 10px;
	-webkit-box-shadow: inset 0 0 6px rgba(0,0,0,.3);
	background-color: #fcb726;
}
</style>
<c:set var="Users" value="<%=Users%>" />
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="user-container">
			<div class="user-inner-list">
				<h5 class="card-header info-color white-text text-center py-4"
					style="padding-bottom: 10px !important;">
					<strong>Manage-users</strong>
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
					data-target="#add-user-modal"
					class="btn btn-outline-secondary add-user-btn" id="open-user-modal-btn">
					<i class="fas fa-user-plus"></i> Add User
				</button>

				<div class="row"
					style="padding: 0 35px; position: relative; top: -40px;">
					<div class="col-md-12">
						<table id="manage_users_table"
							class="display table table-striped table-bordered"
							style="width: 100%">
							<thead>
								<tr style="text-align: center;">
									<th style="width: 5px;">ID#</th>
									<th>User Name</th>
									<th>Password</th>
									<th>Role</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="Users" items="${Users}">
									<tr style="text-align: center;">
										<td>${Users.getUserId()}</td>
										<td>${Users.getUserName()}</td>
										<td>${Users.getPassword()}</td>
										<td>${Users.getRole()}</td>
										<td style="width: 200px;">
										 <button type="button" onclick="editUsers(${Users.getUserId()})" class="btn btn-outline-success"> <i class="fas fa-edit"></i> Update </button>
										 <button type="button" onclick="deleteUser(${Users.getUserId()})" class="btn btn-outline-danger"> <i class="far fa-trash-alt"></i> Delete </button>
										</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr style="text-align: center;">
									<th>ID#</th>
									<th>User Name</th>
									<th>Password</th>
									<th>Role</th>
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
<%@include file="../includes/modals.jsp"%>
<%@include file="../includes/footer.jsp"%>

<script>
"use strict";

$('#manage_users_table').DataTable({
	"pageLength": 8,
	"lengthChange": false

});
$(document).ready(function(){
	$(document).click(function (event) {            
	    $('.search_list_employee').hide();
	    $('.search_list_employee_update').hide();
	});
	$("#employee-id-input-add").keyup(function(){
		$(".search_list_employee").show();
		let searchField = $(this).val();
		searchForEmployee(searchField);
	});
	$("#employee-id-input-update").keyup(function(){
		$(".search_list_employee_update").show();
		let searchField = $(this).val();
		searchForEmployeeUpdate(searchField);
	});
	$(document).on("click",".search_list_employee ul li" , function(){
		var id = $(this).children(":first").text();
		$("#employee-id-input-add").val(id.trim());
	});
	$(document).on("click",".search_list_employee_update ul li" , function(){
		var id = $(this).children(":first").text();
		$("#employee-id-input-update").val(id.trim());
	});
	$(document).on("change","#role",function(){
		let role = $(this).val();
		if(role == 1){
			 $("#dash_board_tab").prop("checked", true);
			 $("#material_tab").prop("checked", true);
			 $("#dts_card_tab").prop("checked", true);
			 $("#vehicle_tab").prop("checked", true);
			 $("#hardware_tab").prop("checked", true);
			 $("#manage_user_tab").prop("checked", true);
			 $("#add_asset_tab").prop("checked", true);
			 $("#add_employee_tab").prop("checked", true);
		}else{
			 $("#dash_board_tab").prop("checked", false);
			 $("#material_tab").prop("checked", false);
			 $("#dts_card_tab").prop("checked", false);
			 $("#vehicle_tab").prop("checked", false);
			 $("#hardware_tab").prop("checked", false);
			 $("#manage_user_tab").prop("checked", false);
			 $("#add_asset_tab").prop("checked", false);
			 $("#add_employee_tab").prop("checked", false);
		}
	});
	$(document).on("click","#open-user-modal-btn",function(){
		 $("#role").val(1);
		 $("#password_add_user").val("");
		 $("#user_name_add_user").val("");
		 $("#employee-id-input").val("");
		 $("#dash_board_tab").prop("checked", true);
		 $("#material_tab").prop("checked", true);
		 $("#dts_card_tab").prop("checked", true);
		 $("#vehicle_tab").prop("checked", true);
		 $("#hardware_tab").prop("checked", true);
		 $("#manage_user_tab").prop("checked", true);
		 $("#add_asset_tab").prop("checked", true);
		 $("#add_employee_tab").prop("checked", true);
	});
});

 function editUsers(user_id) {
 	$('#edit-user-modal').modal('show');
    $.get("UserController?action=getUser&user_id="+user_id, function(data, status){
		console.log('status ::::::::::' + status);
    	console.log(data);
    	
    	data.forEach(function(Users, key) {
   		    
    		$("input#user_id").val(Users.id);
    		$("input#username").val(Users.user_name);
    		$("input#password").val(Users.password);
    		$("input#employee-id-input-update").val(Users.employee_id);
     		$('#user_role').val(Users.rold_id);
     		 $("#dash_board_tab_update").prop("checked", Users.haveDashBoardAccess);
    		 $("#material_tab_update").prop("checked", Users.haveMaterialAccess);
    		 $("#dts_card_tab_update").prop("checked", Users.haveDtsCardAccess);
    		 $("#vehicle_tab_update").prop("checked", Users.haveVehicleAccess);
    		 $("#hardware_tab_update").prop("checked", Users.haveHardwareAccess);
    		 $("#manage_user_tab_update").prop("checked", Users.haveManageUsersAccess);
    		 $("#add_asset_tab_update").prop("checked", Users.haveAddAssetAccess);
    		 $("#add_employee_tab_update").prop("checked", Users.haveAddEmployeeAccess);
    	
    	})
    	
	  }).fail(function( jqXHR, textStatus, errorThrown ) {
		    console.log('error');
	        console.log(jqXHR);
	        console.log(textStatus);
	        console.log(errorThrown );
	    });
     }
 
function deleteUser(user_id) {
	
	
	
	Swal.fire({
		  title: 'Are you sure?',
		  text: "You won't be able to revert this!",
		  type: 'warning',
		  showCancelButton: true,
		  confirmButtonColor: '#3085d6',
		  cancelButtonColor: '#d33',
		  confirmButtonText: 'Yes, delete it!'
		}).then((result) => {
		  if (result.value) {
			    $.post("UserController?action=deleteUser&user_id="+user_id, function(data, status){
			    	
				  }).fail(function( jqXHR, textStatus, errorThrown ) {
					    console.log('error');
				        console.log(jqXHR);
				        console.log(textStatus);
				        console.log(errorThrown );
				    });
		    Swal.fire({ title: 'Deleted',
		    			  text: "User has been deleted successfully!",
		    			  type: 'success',
		    }).then((result) => {
		    	location.reload();
		    });
		  }
		})
	
 }
 

function searchForEmployee(searchingField){
	
	
		var url = "UserController?action=search-employee&searchField="+searchingField; 
	// alert(url);
		$.ajax({
		    url: url,	
		    method: "POST",
		    contentType: 'application/json'
		}).done( function(result) {

			 $(".search_list_employee ul").contents().remove();
			if(result.length > 0){
				
			 $.each(result, function(index,std) {
				 	$(".search_list_employee ul").append("<li class='list-group-item'><span>"+std.emp_id+" </span><span class='email-search-list'>"+std.email+"</span></li>");

				}); 
				
			}else{
					$(".search_list_employee ul").append("<li>There is no employee match for this Id or name</li>");
				}
		}).fail(function() {
//				showFailedAjax();

	  	});
}
function searchForEmployeeUpdate(searchingField){
	
	
	var url = "UserController?action=search-employee&searchField="+searchingField; 
// alert(url);
	$.ajax({
	    url: url,	
	    method: "POST",
	    contentType: 'application/json'
	}).done( function(result) {

		 $(".search_list_employee_update ul").contents().remove();
		if(result.length > 0){
			
		 $.each(result, function(index,std) {
			 	$(".search_list_employee_update ul").append("<li class='list-group-item'><span>"+std.emp_id+" </span><span class='email-search-list'>"+std.email+"</span></li>");

			}); 
			
		}else{
				$(".search_list_employee_update ul").append("<li>There is no employee match for this Id or name</li>");
			}
	}).fail(function() {
//			showFailedAjax();

  	});
}
</script>
