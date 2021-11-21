<%@page import="com.beans.Model"%>
<%@page import="com.beans.Make"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="vDAO" class="com.dao.VehicleDAO"></jsp:useBean>
<%@ page import="java.io.*,java.util.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%
List<Make> makes=vDAO.getAllMake();
List<Model> models=vDAO.getAllModelsByMake("1");
%>
<style type="text/css">
.modal input_fields_wrap input[type=text] {
	padding: 0px 5px 0px 5px;
	margin: 0 3px 3px 0;
	font-size: 0.90em;
	line-height: 0.90em;
	font-weight: normal;
}

.modal form {
	margin: 20px 0;
}

.modal form input {
	padding: 0px 5px 0px 5px;
	margin: 0 3px 3px 0;
	font-size: 0.90em;
	line-height: 2em;
	font-weight: normal;
	border-radius: 25px;
	width: 245px;
}

.modal table {
	width: 100%;
	margin-bottom: 20px;
	border-collapse: collapse;
}

.modal table thead {
	background-color: #ececec;
}

.modal table, th, td {
	border-bottom: 1px solid #ccc;
}

.modal table th, table td {
	text-align: left;
	padding: 4px;
}

.modal add-row-btn {
	margin: 5px 5px;
	width: 80px;
	border-radius: 10px;
	background: #004253;
	color: aliceblue;
	font-size: 13px;
}

.modal remove-row-btn {
	margin: 5px 5px;
	width: 130px;
	border-radius: 15px;
	background: #004253;
	color: aliceblue;
}

.modal req-msg {
	color: red;
	display: flex;
}

.modal err-span {
	margin: 0 10px;
}
</style>

<div class="page-modals">

	<div id="add-damage-card-modal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Damaged Card</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<span id="success" style="color: green;"></span> <span id="fail"
						style="color: red;"></span> <span id="all_data"
						style="color: red;"></span>
					<form>
						<input type="hidden" class="form-control input-card" id="box_id"
							name=box_id value="" readonly> <input type="text"
							id="cardsn" placeholder="card Serial Number"> <select
							name="reason_drop" id="reason_drop" class="damage-card-drop"
							onchange='checkreason();'>
							<option value="select">Select Reason</option>
							<option value="RFID Not Reading">Not Reading</option>
							<!--  <option value="No RFID">No RFID</option> -->
							<option value="Printing Mistake">Printing Mistake</option>
							<option value="Card Damaged">Card Damaged</option>
							<option value="Card Damaged">Testing</option>
							<option value="Other">Other</option>
						</select> <input type="hidden" id="reason" name="reason"
							placeholder="Reason For Damaged"> <input type="button"
							class="btn add-row add-row-btn" value="Add">
						<div class="err-span">
							<span id="cardsn_rq" class="req-msg"></span> <span id="reason_rq"
								class="req-msg"></span> <span id="row_count" class="req-msg"></span>
						</div>

					</form>
					<table id="damaged_card_table">
						<thead>
							<tr>
								<th>Select</th>
								<th>Card Serial Number</th>
								<th>Reason for Damaged</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<button type="button" class="btn delete-row remove-row-btn">Delete
						Row</button>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn add_damaged_card btn-primary">Save</button>
				</div>
			</div>
		</div>
	</div>

	<div id="edit-damage-card-modal" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Update Damaged Card</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<span id="u_success" style="color: green;"></span> <span
						id="u_fail" style="color: red;"></span> <span id="u_all_data"
						style="color: red;"></span>
					<form id="">
						<input type="hidden" id="box-id-hidden" /> <input type="hidden"
							id="id-hidden" /> <input type="hidden"
							class="form-control input-card" id="box_id" name=box_id value=""
							readonly> <input style="width: 200px;" type="text"
							id="u_cardsn" placeholder="card Serial Number"> <select
							name="u_reason_drop" id="u_reason_drop" class="damage-card-drop"
							onchange='checkureason();'>
							<option value="select">Select Reason</option>
							<option value="Not Reading">Not Reading</option>
							<option value="Printing Mistake">Printing Mistake</option>
							<option value="Card Damaged">Card Damaged</option>
							<option value="Other">Other</option>
						</select> <input style="width: 155px;" type="hidden" id="u_reason"
							name="u_reason" placeholder="Reason For Damaged"> <input
							type="button" class="btn update-row add-row-btn" value="Add">
						<input type="hidden" id="edit-damage-card" name="edit-damage-card"
							class="btn edit-row add-row-btn" value="Update">
						<div class="err-span">
							<span id="u_cardsn_rq" class="req-msg"></span> <span
								id="u_reason_rq" class="req-msg"></span> <span id="u_row_count"
								class="req-msg"></span>
						</div>
					</form>
					<table id="update_damaged_card_table">
						<thead>
							<tr>
								<th>Card Serial Number</th>
								<th>Reason for Damaged</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="add-user-modal" tabindex="-1" role="dialog"
		data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-user-plus"></i> Add User
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="UserController" method="POST" id="user-form"
						class="add-form" enctype="multipart/form-data">
						<fieldset>
							<input type="hidden" id="action" name="action" value="add-user" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>User Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="username"
									id="user_name_add_user"
										required placeholder="Enter Username" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Password</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="password"
									id="password_add_user"
										required placeholder="Enter Pasword" />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Employee ID</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="employee-id" id="employee-id-input-add"
										required placeholder="Enter Employee email/name/id" />
										<div class="search_list_employee" style="">
											<ul class="list-group">
											</ul>
										</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>User Role</p>
								</div>
								<div class="col-md-8">
									<select class="form-control" id="role" name="role" required>
										<option value="1">Admin</option>
										<option value="2">Staff</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>accessible TAB</p>
								</div>
								<div class="col-md-8">
										<table class="table" style="box-shadow: 0 0 0 0;    table-layout: fixed;width: 100%;">
											<tbody>
												<tr>
													<td style="border: none;width: 43%;"><span>Dash board</span> </td>
													<td style="border: none"><input type="checkbox" name="dash_board_tab" id="dash_board_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Material</span></td>
													<td><input type="checkbox" name="material_tab" id="material_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>DTS Card</span></td>
													<td><input type="checkbox" name="dts_card_tab" id="dts_card_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Vehicle</span></td>
													<td><input type="checkbox" name="vehicle_tab" id="vehicle_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Hardware</span></td>
													<td><input type="checkbox" name="hardware_tab" id="hardware_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Manage User</span></td>
													<td><input type="checkbox" name="manage_user_tab" id="manage_user_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Add Asset</span></td>
													<td><input type="checkbox" name="add_asset_tab" id="add_asset_tab" value="allow"></td>
												</tr>
												<tr>
													<td><span>Add Employee</span></td>
													<td><input type="checkbox" name="add_employee_tab" id="add_employee_tab" value="allow"></td>
												</tr>
											</tbody>
										</table>
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

	<div class="modal fade" id="edit-user-modal" tabindex="-1"
		role="dialog" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="fas fa-user-edit"></i> Update User
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<form action="UserController" method="POST" id="user-form"
						class="add-form" enctype="multipart/form-data">
						<input type="hidden" class="form-control" id="user_id"
							name=user_id value="" readonly>
						<fieldset>
							<input type="hidden" id="action" name="action"
								value="update-user" />
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>User Name</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="username"
										id="username" value="" required placeholder="Enter Username" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Password</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="password"
										id="password" required placeholder="Enter Pasword" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>Employee ID</p>
								</div>
								<div class="col-md-8">
									<input type="text" class="form-control" name="employee-id" id="employee-id-input-update"
										required placeholder="Enter Employee email/name/id" />
										<div class="search_list_employee_update" style="">
											<ul class="list-group">
											</ul>
										</div>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>User Role</p>
								</div>
								<div class="col-md-8">
									<select class="form-control" id="user_role" name="user_role"
										required>
										<option value="1">Admin</option>
										<option value=2>Staff</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 add-form-label">
									<p>accessible TAB</p>
								</div>
								<div class="col-md-8">
										<table class="table" style="box-shadow: 0 0 0 0;    table-layout: fixed;width: 100%;">
											<tbody>
												<tr>
													<td style="border: none;width: 43%;"><span>Dash board</span> </td>
													<td style="border: none"><input type="checkbox" name="dash_board_tab" id="dash_board_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Material</span></td>
													<td><input type="checkbox" name="material_tab" id="material_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>DTS Card</span></td>
													<td><input type="checkbox" name="dts_card_tab" id="dts_card_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Vehicle</span></td>
													<td><input type="checkbox" name="vehicle_tab" id="vehicle_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Hardware</span></td>
													<td><input type="checkbox" name="hardware_tab" id="hardware_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Manage User</span></td>
													<td><input type="checkbox" name="manage_user_tab" id="manage_user_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Add Asset</span></td>
													<td><input type="checkbox" name="add_asset_tab" id="add_asset_tab_update" value="allow"></td>
												</tr>
												<tr>
													<td><span>Add Employee</span></td>
													<td><input type="checkbox" name="add_employee_tab" id="add_employee_tab_update" value="allow"></td>
												</tr>
											</tbody>
										</table>
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

	<div id="add-ink-barcodes" class="modal fade" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Barcodes</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<span id="b_success" style="color: green;"></span> <span
						id="b_fail" style="color: red;"></span> <span id="b_all_data"
						style="color: red;"></span>
					<form>
						<input type="hidden" class="form-control input-card"
							id="printer_ink_id" name="printer_ink_id" value="" readonly>
						<input type="text" id="barcode" placeholder="Enter Barcode">
						<input type="button" class="btn add-barcode add-row-btn"
							value="Add">
						<div class="err-span">
							<span id="barcode_rq" class="req-msg"></span>
						</div>
					</form>
					<table id="ink_barcode_table">
						<thead>
							<tr>
								<th width="10px">Select</th>
								<th width="25px">#Number</th>
								<th width="35px">Barcode</th>
							</tr>
						</thead>
						<tbody>
							<!-- <tr>
								<td><input type="checkbox" name="record"></td>
								<td></td>
								<td></td>
							</tr> -->
						</tbody>
					</table>
					<button type="button" class="btn delete-row remove-row-btn">Delete
						Row</button>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Done</button>
				</div>
			</div>
		</div>
	</div>

	<div id="ribbon_list" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Ribbon List</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<div id="ribbon_list_table"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<div id="license_image" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content" style="width: fit-content;">
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="far fa-id-badge"></i> <span>Driver License Image</span>
					</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">

					<div class="license-image">
						<img style="height: 465px;" src="${context}/images/not.png" />
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
	</div>

	<div id="update_vehicle" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Update Vehicle Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<span id="vehicle-success"
						style="color: green; margin-left: 340px;"></span> <span
						id="vehicle-fail-msg" style="color: red; margin-left: -25px;"></span>
					<form action="VehicleController" method="POST" id="update-vehicle"
						class="form-cnt" enctype="multipart/form-data">
						<input type="hidden" id="action" name="action" value="update-vehicle" /> 
						<input type="hidden" name="vehicle_id" id="vehicle_id" value="" />
						<input type="hidden" name="vehicle_plateno" id="vehiclePlateNo" value="" />
						<div class="row">
							<div class="form-group col-lg-6">
								<label class="tx-label" for="plate_no">Plate Number : </label> <input
									type="text" class="form-control" id="plate_no" name="plate_no"
									maxlength="8" required>
							</div>
							<div class="form-group col-lg-6">
								<label class="tx-label" for="permit_date">Permit Expiry
									Date : </label> <input type="date" class="form-control"
									id="permit_date" name="permit_date" required>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-lg-6">
								<label class="tx-label" for="make">Make : </label>
								 <select class="form-control" id="updateMake" name="make" required >
									<c:forEach var="make" items="<%=makes %>">
										<option value="${make.getId()}"  >${make.getMake()}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group col-lg-6">
								<label class="tx-label" for="model">Model : </label>
								<select class="form-control" id="updateModel" name="model" required >
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
											<input type="file" class="custom-file-input" id="update_vehicle_img" previewId="updateVehicleImgPreview" name="update_vehicle_img" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_vehicle_img" aria-describedby="update_vehicle_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 2:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="update_vehicle_img2" previewId="updateVehicleImgPreview2" name="update_vehicle_img2" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_vehicle_img2" aria-describedby="update_vehicle_img2">Choose Image</label>
										</div>
									</div>
					     </div>
                         
					   
						<div class="row">
							<div class="form-group col-lg-6">
							 	<img id="updateVehicleImgPreview" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
							<div class="form-group col-lg-6">
								<img id="updateVehicleImgPreview2" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
						</div> 
						
						<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 3:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="update_vehicle_img3" previewId="updateVehicleImgPreview3" name="update_vehicle_img3" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_vehicle_img3" aria-describedby="update_vehicle_img3">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Vehicle Image 4:</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="update_vehicle_img4" previewId="updateVehicleImgPreview4" name="update_vehicle_img4" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_vehicle_img4" aria-describedby="update_vehicle_img4">Choose Image</label>
										</div>
									</div>
					     </div>
                         
					   
						<div class="row">
							<div class="form-group col-lg-6">
							 	<img id="updateVehicleImgPreview3" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
							<div class="form-group col-lg-6">
								<img id="updateVehicleImgPreview4" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
						</div> 
						
						<div class="row">
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Isthimara Front Side :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" previewId="updateIsthimaraImage" id="update_permit_img"
												name="update_permit_img" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_permit_img"
												aria-describedby="update_permit_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">Isthimara Back Side :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" previewId="updateIsthimaraImage2" id="update_permit_img2"
												name="update_permit_img2" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_permit_img2"
												aria-describedby="update_permit_img2">Choose Image</label>
										</div>
									</div>
					     </div>
                         
					   
						<div class="row">
							<div class="form-group col-lg-6">
							 	<img id="updateIsthimaraImage" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
							<div class="form-group col-lg-6">
								<img id="updateIsthimaraImage2" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
						</div> 

						<button type="submit" class="btn cmd-vehicle">Submit</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	

	<div id="ithimara_image" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" style="max-width: fit-content;">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title ">Isthimara Images</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">	
					<div class="row imagePopupContainer">
						<div class="form-group col-lg-12" style="text-align: center;">
						<span class="badge_car_plate firstImg active isthimaraImageModel" no="0" >Front</span>
						<span class="badge_car_plate isthimaraImageModel" no="2">Back</span>
						</div>
					</div>				
					<div class="row">
						<div class="form-group col-lg-12">
							<img id="permit-image" src="#" class="img-fluid vehicle-img-model"style="border-radius: 20px; border: 2px solid #772b49; display: none;max-height: 750px;" alt="Responsive image">
						</div>							
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="vehicle_images" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" style="max-width: fit-content;">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title imageVehicleName"></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">

					<div class="container-fluid" style="width: 40em">
						<div id="vehicleImageCarousel" class="carousel">
							<ol class="carousel-indicators">
							    <li data-target="#vehicleImageCarousel" data-slide-to="0" class="active"></li>
							    <li data-target="#vehicleImageCarousel" data-slide-to="1"></li>
							    <li data-target="#vehicleImageCarousel" data-slide-to="2"></li>
							    <li data-target="#vehicleImageCarousel" data-slide-to="3"></li>
							</ol>
							
							<div class="carousel-inner">
							</div>
							<a class="carousel-control-prev" style="width: auto;background-color: #772b49;opacity: initial;height: 12%;top: 42%;" href="#vehicleImageCarousel" role="button" data-slide="prev"> 
								<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="sr-only">Previous</span>
							</a> 
							<a class="carousel-control-next" style="width: auto;background-color: #772b49;opacity: initial;height: 12%;top: 42%;" href="#vehicleImageCarousel" role="button" data-slide="next"> 
								<span class="carousel-control-next-icon" aria-hidden="true"></span> 
								<span class="sr-only">Next</span>
							</a>
						</div>
					</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="update_driver" class="modal fade bd-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Update Driver Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body scrollable">
					<span id="driver-success" style="color: green; margin-left: 340px;"></span>
					<span id="driver-fail-msg" style="color: red; margin-left: 340px;"></span>
					<form action="VehicleController" method="POST" id="update-driver"
						class="form-cnt" enctype="multipart/form-data">
						<input type="hidden" id="action" name="action"
							value="update-driver" /> <input type="hidden" name="driver_id"
							id="driver_id" value="" />
							<input type="hidden" name="licenseNo" id="licenseNo" value="" />
						<div class="row">
							<div class="form-group col-lg-6">
								<label class="tx-label" for="model">Driver Name : </label> <input
									type="text" class="form-control" id="driver_name"
									name="driver_name" required>
							</div>
							<div class="form-group col-lg-6">
								<label class="tx-label" for="given_date">Joined Date :</label>
								<input type="date" class="form-control" id="given_date"
									name="given_date" required>
							</div>
						</div>
						<div class="row">
							<div class="form-group col-lg-6">
								<label class="tx-label" for="license_no">License Number
									: </label> <input type="text" class="form-control" id="license_no"
									name="license_no" maxlength="20" required>
							</div>
						</div>

						<div class="row">
									<div class="form-group col-lg-6">
								        <label class="tx-label" for="model">Driver Image :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="update_profile_img" previewId="updateProfileImgPreview" name="update_profile_img" accept=".png, .jpg, .jpeg" >
											<label class="custom-file-label" for="update_profile_img" aria-describedby="update_profile_img">Choose Image</label>
										</div>
									</div>
									<div class="form-group col-lg-6">
										<label class="tx-label" for="model">License Image :</label>
										<div class="custom-file">
											<input type="file" class="custom-file-input" id="update_license_img" previewId="updateLicenseImgPreview" name="update_license_img" accept=".png, .jpg, .jpeg" > 
											<label class="custom-file-label" for="update_license_img" aria-describedby="license_img">Choose Image</label>
										</div>
									</div>
					  </div>
                         
					   
						<div class="row">
							<div class="form-group col-lg-6">
							 	<img id="updateProfileImgPreview" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
							<div class="form-group col-lg-6">
								<img id="updateLicenseImgPreview" src="#" class="img-fluid"
										style="border-radius: 20px; border: 2px solid #772b49; display: none;"
										alt="Responsive image">
							</div>
						</div> 
						<button type="submit" class="btn cmd-driver">Submit</button>
					</form>

				</div>
			</div>
		</div>
	</div>
	
	
	<div id="view-asset-information-modal" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content"  style="width: 144%;margin-left: -250px;">
				<div class="modal-header">
					<h5 class="modal-title" style="font-weight: bold;left: 44%;position: relative;">Asset Details</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body row">
					<div class="col-md-4">
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
					<div class="col-md-4">
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
							 				<th>AMC</th>
							 				<td>:</td>
							 				<td><span id="amc_card"></span></td>
							 			</tr>
							 		</tbody>
							 	</table>
							  </div>
						</div>
					</div>
					<div class="col-md-4">
						<div class="card border-danger mb-3" style="max-width: 33rem;margin:auto;height: 98%;">
						   <div class="card-header  text-center" style="font-weight: bold;">Employee Information</div>
							  <div class="card-body text-danger">
								<table class="table">
							 		<tbody>
							 			<tr>
							 				<th>First Name</th>
							 				<td>:</td>
							 				<td><span id="first_name"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Last Name</th>
							 				<td>:</td>
							 				<td><span id="last_name"></span></td>
							 			</tr>
							 			<tr>
							 				<th>E-mail</th>
							 				<td>:</td>
							 				<td><span id="email"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Mobile Number</th>
							 				<td>:</td>
							 				<td><span id="mobile"></span></td>
							 			</tr>
							 			<tr>
							 				<th>Department</th>
							 				<td>:</td>
							 				<td><span id="department"></span></td>
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

</div>

<script type="text/javascript">
$(document).ready(function() {
	$(document).on("click","#delete_damaged_card",function(){
	
			let id = $(this).parent().data("id");
			let boxId = $(this).data("boxid");
			console.log("id ----> " , id , " boxID ----> " , boxId);
			
			Swal.fire({
	        	  title: 'Are you sure?',
	        	  text: "Damaged card will be remove in this list!",
	        	  type: 'warning',
	        	  showCancelButton: true,
	        	  confirmButtonColor: '#3085d6',
	        	  cancelButtonColor: '#d33',
	        	  confirmButtonText: 'Yes'
	        	}).then((result) => {
	        	  if (result.value) {
	        	
	        	       $.post("CardboxController?action=delete-damage-card&id="+id, function(data, status){
	        	    	   console.log("Success Updated");
	        	  		     $("#u_success").text("Successfully Deleted Damaged Card");
	        	  		     $("#u_fail").text("");
	        	    		 $("#u_all_data").text("");
	        	    		 editDamageCards();
	               	  }).fail(function( jqXHR, textStatus, errorThrown ) {
	               		  console.log('error');
	               	        console.log(jqXHR);
	               	        console.log(textStatus);
	               	        console.log(errorThrown );
	               	    });
	        		  
	        	  }
	        	  
	        	})
			return false;
	});
	
	$(document).on("click","#edit_damaged_card",function(){
		try{
			let id = $(this).parent().data("id");
			let boxId = $(this).data("boxid");
			let card_serial_number = $(this).parent().parent().find(".cardSerialNumberTh").text();
			let reason = $(this).parent().parent().find(".reasonTh").text();
			
			$("#id-hidden").val(id);
			$("#box-id-hidden").val(boxId);
			$("#u_cardsn").val(card_serial_number);
			$("#u_reason_drop").val(reason);
			
			let val = document.getElementById('u_reason_drop').value;
			console.log("value of reason----",val);
			
			if(val == ""){
			   $("#u_reason_drop").val("Other")
			   document.getElementById('u_reason').type = 'text';
			   $("#u_reason").val(reason);
			}else{
			   document.getElementById('u_reason').type = 'hidden';
			}
			
			document.getElementById('edit-damage-card').type = 'button';
			
		}catch(e){
			alert(e);
		}
		
	});
	
	 $(".add-barcode").click(function(){
		  let rowCount = $('#ink_barcode_table tbody tr').length;
		  let qty = parseInt(document.getElementById('qty').value);
			  if(rowCount < qty){
				  let no = rowCount+1;
				  let barcode = $("#barcode").val();
				  if(barcode != "")
				  {
					  let markup = "<tr><td><input type='checkbox' name='record'></td> <td>"+ no +"</td> <td>"+ barcode +" </td></tr>";
					  $("#ink_barcode_table tbody").append(markup);
				  }else{
					  $("#b_fail").text("Barcode is Required!!");
				  }
			  }else{
					 $("#b_fail").text("Barcode must be less QTY");
			  }
		
	 });
	 
	 var $SERIALNUMS = [];
	 

	 function checkIfSerialNumExists(serialNum) {
		 console.log("--- check ", $SERIALNUMS)
		 if($SERIALNUMS.includes(Number(serialNum))) { console.log("TRUE")
			 return true;
		 } else {
			 console.log("FALSE")
			 return false;
		 }
		 
		
	 }
	 
	 function addRowsToArray() {
		 console.log("Row count --> ", $('#damaged_card_table tbody tr').length);
		 $SERIALNUMS = [];
		 $("#damaged_card_table tbody tr").each(function() {
			
			 let num = $(this).find("td:nth-child(2)").text();
			 console.log("Row Num --> ", num);
			
			 $SERIALNUMS.push(Number(num));
			
		 });
		 
		 console.log("--- add ", $SERIALNUMS)
	 }
	 
	 $(".add-row").click(function(){
		 let serial_no = document.getElementById('cardsn').value;
		 let e = document.getElementById("reason_drop");
		 let reason = e.options[e.selectedIndex].value;

		 if(checkIfSerialNumExists(serial_no)) {
			  $("#success").text("");
			  $("#reason_rq").text(""); 
			  $("#fail").text("Card Serial Number Should be Unique !!");
	 		  $("#all_data").text("");
			 return;
		 }
		 
		 if(reason == "Other"){
		    let other_reason = document.getElementById('reason').value;
		    reason = other_reason;
		 }
		 
	     let count = parseInt(document.getElementById('ready_count').value);
         let box_id = parseInt(document.getElementById('box_id').value);
        	
         let damage_cards = 200-count;
         
         let rowCount = $('#damaged_card_table tbody tr').length;
		 
         console.log("row count of the damaged cards table : "+rowCount );
         
         if(serialNoRange(serial_no))
         {
		      if( serial_no != "")
				{
		    	 if(reason != "select" && reason != ""){
		    		  
				  if(rowCount <= damage_cards){
			         let card_sn = $("#cardsn").val();
			         let markup = "<tr><td><input type='checkbox' name='record'></td><td>" + card_sn + "</td><td>" + reason + "</td></tr>";
			         $("table tbody").append(markup);
			         
			 		  $("input#cardsn").val("");
			 		  $("input#reason").val("");
			 		  $("#success").text("");
			 		  $("#fail").text("");
			 		  $("#all_data").text("");
			 		  $("#cardsn_rq").text("");
					  $("#reason_rq").text(""); 
					  $("#row_count").text("");
				      var temp="select"; 
				      $("#reason_drop").val(temp);
			      
				      addRowsToArray();
				 }else{
					 
					 $("#row_count").text("Damaged Card must be only in out of 200 !!"); 
					 $("#cardsn_rq").text("");
					 $("#reason_rq").text(""); 
					 $("#fail").text("");
			 		 $("#all_data").text("");
				 }
		        }else{
		        	  $("#success").text("");
					  $("#reason_rq").text("Reason is Required!"); 
					  $("#fail").text("");
			 		  $("#all_data").text("");
			 		  $("#row_count").text("");
		         }
               }else{
            	  $("#success").text("");
		       	  $("#cardsn_rq").text("Serial Number is Required!"); 
				  $("#fail").text("");
		 		  $("#all_data").text("");
		 		  $("#row_count").text("");
               }
		      
         }else{
        	 
         	  $("#success").text("");
			  $("#reason_rq").text(""); 
			  $("#fail").text("Serial Number must be within a range !!");
	 		  $("#all_data").text("");
	 		  $("#row_count").text("");
         }
 		 
    });
	 
    // Find and remove selected table rows
	$(".delete-row").click(function() {
	  $("table tbody").find(
			'input[name="record"]').each(
			function() {
				if ($(this).is(":checked")) {
					$(this).parents("tr").remove();
					
				}
			});
	  
	  	addRowsToArray();
	    $("#row_count").text("");
	});
					
	$(".add_damaged_card").click(function() {
		console.log("insert function");
		
		let box_id = document.getElementById('box_id').value;
		
		let count = parseInt(document.getElementById('ready_count').value);

	    let damage_cards = 200-count;
	    
		console.log("box id  = "+ box_id);
		if(box_id != ""){
		
	    //Loop through the Table rows and build a JSON array.
        let total = 0;
        let jsonTable = $('#damaged_card_table tbody tr:has(td)').map(function () {
            let $td = $('td', this);
            total += parseFloat($td.eq(2).text());
            return {
            	box_id: box_id,
                SerialNo: $td.eq(1).text(),
                Reason: $td.eq(2).text(),
            }
        }).get();

        $('#damaged_card_table > tfoot > tr > td:nth-child(1)').html(total);

        data = {};
        data = jsonTable;
        let array = new Array();
        for(var i=0; i<data.length; i++){
        	console.log(i +'::::'+data[i].box_id);
        	array.push(data[i]);
        }
        console.log('array: '+JSON.stringify(array));
        console.log(data);
	      if(jsonTable.length != 0)
	       {
	    	  if(jsonTable.length == damage_cards)
	    	  {
			       $.ajax({
			            url: 'CardboxController?action=add-damaged-card',
			            method: 'POST',
			            dataType: 'JSON',
			            data:  {json:JSON.stringify(data)}
			        }).done(function (result) {
			        	console.log("ajax function data : ");
			            $('#jsonresult').html(JSON.stringify(result));
				      	 $("#success").text("Successfully Added Damaged Cards");
				 		 $("#row_count").text(""); 
						 $("#cardsn_rq").text("");
						 $("#reason_rq").text("");
						 $("#fail").text("");
					     $('#damaged_card_table tbody tr').remove();
			        });
	    	  }else
	    		  {
	    		     $("#fail").text("");
				     $("#all_data").text("Damaged Card(s) number must be "+" "+ damage_cards +" !!");
					 $("#row_count").text(""); 
					 $("#cardsn_rq").text("");
					 $("#reason_rq").text(""); 
	    		  }
			 }else{
				  $("#fail").text("Please Add Damaged Card Details !!");
				  $("#all_data").text("");
					 $("#row_count").text(""); 
					 $("#cardsn_rq").text("");
					 $("#reason_rq").text(""); 
			 }
        
		}else{
			console.log("please select one cardbox");
			  $("#fail").text("Please Select One Cardbox !!");
				 $("#row_count").text(""); 
				 $("#cardsn_rq").text("");
				 $("#reason_rq").text(""); 
		}
           
	});
	
	$(".update-row").click(function() {

		let box_id = document.getElementById('box_id').value;
		let reason =  document.getElementById('u_reason_drop').value;
		let reason2 =  document.getElementById('u_reason').value;
		let serial_no = document.getElementById('u_cardsn').value;
		console.log("box-id----->",box_id);
		
		if (reason == "Other"){
			reason = reason2;
		}	
		
		let count = parseInt(document.getElementById('e_ready_count').value);
		let damaged_cards = 200-count;
		console.log("count"+count);
		let table_length = document.getElementById("update_damaged_card_table").rows.length;
        if(serialNoRange(serial_no)){		
			 if(damaged_cards >= table_length )
		       {
				   $.post("CardboxController?action=update-damage-card&box_id="+box_id+"&reason="+reason+"&serial_no="+serial_no, function(data, status){
					   console.log("update status",typeof data);
					       if(data == 'true'){
					    	   console.log("Success Added");
					    		$("#u_success").text("Successfully Added Damaged Card");
					    		$("#u_fail").text("");	
					    		$("#u_all_data").text("");
					    		editDamageCards();
					       }else{
					    	   $("#u_fail").text("Card already addes as return or damaged!!");
					    	   $("#u_success").text("");
					    	   $("#u_all_data").text("");
					       }
			   	    }).fail(function( jqXHR, textStatus, errorThrown ) {
			   		  console.log('error');
			   	        console.log(jqXHR);
			   	        console.log(textStatus);
			   	        console.log(errorThrown );
			   	    });  
		       }else{
		    		$("#u_fail").text("Damaged Card Count should be equals to "+ damaged_cards );
		    		$("#u_all_data").text("");
		    		$("#u_success").text("");
		    		 $("#row_count").text("");
		       }
            }else {
            	$("#u_fail").text("Card Serial no should be within a range!!" );
	    		$("#u_all_data").text("");
	    		$("#u_success").text("");
	    		 $("#row_count").text("");
            }
	    });
	
	$(".edit-row").click(function() {

		let id = document.getElementById('id-hidden').value;
		let box_id = document.getElementById('box-id-hidden').value;
		let reason =  document.getElementById('u_reason_drop').value;
		let reason2 =  document.getElementById('u_reason').value;
		let serial_no = document.getElementById('u_cardsn').value;
		console.log("id ---->",id, "box-id----->",box_id);
		
		if (reason == "Other"){
			reason = reason2;
		}		

	    $.post("CardboxController?action=edit-damage-card&id="+id+"&box_id="+box_id+"&reason="+reason+"&serial_no="+serial_no, function(data, status){
	    	   console.log("Success edited");
	    		$("#u_cardsn").val("");
	    		$("#u_reason_drop").val("select");
	    		$("#u_reason").val("");
	    	    $("#row_count").text("");
	    		document.getElementById('edit-damage-card').type = 'hidden';
	    		editDamageCards();
    	  }).fail(function( jqXHR, textStatus, errorThrown ) {
    		  console.log('error');
    	        console.log(jqXHR);
    	        console.log(textStatus);
    	        console.log(errorThrown );
    	    });
	 
	});
	 
});

function checkreason(){
	 let val = document.getElementById('reason_drop').value;

	 if(val=='Other')
		 document.getElementById('reason').type = 'text';
	 else  
		 document.getElementById('reason').type = 'hidden';
}

function checkureason(){
	 let val = document.getElementById('u_reason_drop').value;

	 if(val=='Other')
		 document.getElementById('u_reason').type = 'text';
	 else  
		 document.getElementById('u_reason').type = 'hidden';
}

function serialNoRange(serial_no){
	
	 let serial_no_range = document.getElementById('card_sn').value;
	 console.log("Range - -- >",serial_no_range);
	 console.log("Entered Value -- >",serial_no);
	 
	 let min = parseInt(serial_no_range.split(" - ")[0]);
	 let max = parseInt(serial_no_range.split(" - ")[1]);
	 console.log("Maximum -->",max);
	 console.log("Mininmum -->",min);
	 
	 let entered_value = parseInt(serial_no);
	 console.log("Entered value Int",entered_value);
	 
	 function between(entered_value, min, max) {
		  return entered_value >= min && entered_value <= max;
		}
	 
	 if (between(entered_value, min, max)) {
		    return true;
		}else{
			return false;
		}
	 
}

function vehicleImgURL(input) {
	if (input.files && input.files[0]) {
		let reader = new FileReader();
		reader.onload = function(e) {
			$('#vehicleImgPreview').attr('src', e.target.result);
			$('#vehicleImgPreview').hide();
			$('#vehicleImgPreview').fadeIn(650);
		}
		reader.readAsDataURL(input.files[0]);
	}
}
$("#permit_img").change(function() {
	vehicleImgURL(this);
});


function getAssetInfo(asset_id){
	console.log("Asset Id -- > ",asset_id);
	$.ajax({
		url: "AssetController?action=getAssetDetails&asset_id="+asset_id,
		method : "GET",
	}).done(function(data){
      $('#view-asset-information-modal').modal('show');
      	let parsedData = JSON.parse(data.data);
        let assetId = parsedData.asset_id;
        console.log(parsedData);
        $("#asset_id_card").text(assetId);
	    let s_no = parsedData.s_no;
		let country = parsedData.country;
		let city = parsedData.city;
		let site = parsedData.site;
		let asset_type = parsedData.asset_type;
		let tag_name = parsedData.tag_name;
		let fa_code = parsedData.fa_code;
		let owner = parsedData.first_name + " "+parsedData.last_name ;
		let make = parsedData.make;
		let model = parsedData.model;
		let serial_no = parsedData.serial_no;
		let mac_address = parsedData.mac_address;
		let processor = parsedData.processor;
		let os = parsedData.os;
		let memory = parsedData.memory;
		let hard_disk = parsedData.hard_disk;
		let procurred_date = parsedData.procurred_date;
		let warranty_exp_date = parsedData.warranty_exp_date;
		let amc = parsedData.amc;
		let fist_name = parsedData.first_name;
		let last_name = parsedData.last_name;
		let e_mail = parsedData.email;
		let mobile = parsedData.mobile;
		let department = parsedData.department;
		
		
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
		
		$("#first_name").text(fist_name);
		$("#last_name").text(last_name);
		$("#email").text(e_mail);
		$("#mobile").text(mobile);
		$("#department").text(department);
	
	}).fail(function(error){
	 Swal.fire(
			    'fail!',
			    'somthing went wrong !!',
			    'error'
			  )
	});
}
</script>

