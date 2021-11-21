<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Vehicleholoteq"%>
<%@ page import="com.beans.Drivers"%>
<%@ page import="com.beans.VehicleServiceType"%>

<jsp:useBean id="vehicleDAO" class="com.dao.VehicleDAO"></jsp:useBean>

<%
	ArrayList<Vehicleholoteq> vehicles_plateno = vehicleDAO.getVehiclePlateNo();
    ArrayList<Drivers> drivers = vehicleDAO.getDrivername();
    ArrayList<VehicleServiceType> service_type_list = vehicleDAO.getVehicleServicesList();
%>

<c:set var="vehicles_plateno" value="<%=vehicles_plateno%>" />
<c:set var="drivers" value="<%=drivers%>" />
<c:set var="service_type_list" value="<%=service_type_list%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

		<div class="form-container">
			<c:if test="${not empty success_message}">
				<span id="success_message" style="color: green; margin: 45px 192px;">${success_message}</span>
			</c:if>
			<c:if test="${not empty fail_message}">
				<span id="fail_message" style="color: red; margin: 45px 200px;">${fail_message}</span>
			</c:if>

			<div class="form-inner-service">

				<h5 class="card-header info-color white-text text-center py-4">
					<strong>Add Vehicle Services</strong>
				</h5>

				<form action="VehicleController" method="POST" id="add-vehicle-services" class="form-cnt" role="form">
					<div class="form-group tx-input">
						<label class="tx-label" for="vehicle">Vehicle : </label>
						<select name="vehicle" id="vehicle" class="form-control form-control-sm">
						    <option value="0"  vehicleId="0" >-- Select Vehicle--</option>
							<c:forEach var="vehicles_plateno" items="${vehicles_plateno}">
								<option value="${vehicles_plateno.getId()}" >${vehicles_plateno.getMake() } ${vehicles_plateno.getModel() } -${vehicles_plateno.getPlate_no() }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group tx-input">
						<label class="tx-label" for="driver">Driver : </label>
						<select name="driver" id="driver" class="form-control form-control-sm">
							 <option value="0"  driverId="0" >-- Select Driver--</option>
							<c:forEach var="drivers" items="${drivers}">
								<option value="${drivers.getId()}" >${drivers.getDriver_name() }</option> 
							</c:forEach>
						</select>
					</div>
					<div class="form-group tx-input">
						<label class="tx-label" for="service_type">Service Type : </label> 
						<select name="service_type" id="service_type" class="form-control form-control-sm">
						    <option value="0" >-- Select Service Type--</option>
							<c:forEach var="service_type_list" items="${service_type_list}">
								<option value="${service_type_list.getId()}" >${service_type_list.getService_type()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group tx-input">
						<label class="tx-label" for="date">Date : </label> 
						<input type="date" class="form-control" id="date" name="date" required>
					</div>
					<div class="form-group tx-input">
						<label class="tx-label" for="cost">Cost : </label> 
						<input type="number" min="0" step="0.01" data-number-to-fixed="2" data-number-stepfactor="100" class="form-control" id="cost" name="cost" required>
					</div>
					<div class="form-group tx-input">
						<label class="tx-label" for="date">Place : </label> 
						<input type="text" class="form-control" id="place" name="place" required>
					</div>
					<input type="hidden" name="action" value="add-vehicle-services" />
					<button type="submit" class="btn submit-btn">Submit</button>
				</form>
			</div>

			<c:if test="${not empty message}">
				<span style="color: red;" id="message">${message}</span>
			</c:if>

		</div>

	</div>
</div>
<%@include file="../includes/footer.jsp"%>