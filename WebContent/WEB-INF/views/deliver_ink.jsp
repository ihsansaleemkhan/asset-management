<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Schools"%>
<%@ page import="com.beans.DeliveredInk"%>

<jsp:useBean id="cardboxDAO" class="com.dao.CardboxDAO"></jsp:useBean>
<jsp:useBean id="printerinkDAO" class="com.dao.PrinterinkDAO"></jsp:useBean>

<%
	ArrayList<Schools> schools = cardboxDAO.getSchools();
	ArrayList<DeliveredInk> delivered_ink = printerinkDAO.getAllDeliveredInkList();
%>

<c:set var="schools" value="<%=schools%>" />
<c:set var="delivered_ink" value="<%=delivered_ink%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

		<div class="row" style="margin-right: 0px;">
			<div class="col-lg-3">

				<div class="delivered-form-container">

					<div class="form-deliver-inner">

						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Dispatch</strong>
						</h5>
						<span id="barcode-msg" style="color: green; margin-left: 130px;"></span>
						<span id="e-barcode-msg" style="color: red; margin-left: 32px;"></span>
						<form action="InkController" method="POST"
							name="deliver-printer-ink" id="deliver-printer-ink"
							class="form-cnt" style="margin-top: -5px;" role="form">

							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="barcode">Barcode : </label> <input
										type="text" class="form-control" style="width: 420px;" id="barcode" name="barcode"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								
								<div class="form-group printer-input">
									<label class="tx-label" for="model">Location : </label> <select
										name="location" id="location"
										class="form-control form-control-sm">
										<option value="Holoteq Group - Qatar">Holoteq Group -
											Qatar</option>
										<c:forEach var="schools" items="${schools}">
											<option value="${schools.getSchool_name()}">${schools.getSchool_name()}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="delivered_by">Device ID
										: </label> <input type="text" class="form-control" id="device_no"
										name="device_no" required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="delivered_date">Dispatched
										Date : </label> <input type="date" class="form-control"
										id="delivered_date" name="delivered_date" value="" required>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="delivered_by">Received By
										: </label> <input type="text" class="form-control" id="delivered_by"
										name="delivered_by" required>
								</div>
							</div>

							<input type="hidden" name="barcode_id" id="barcode_id" value="" />
							<input type=hidden name="ink_id" id="ink_id" value="" /> <input
								type="hidden" name="sent_by" id="sent_by" value="<%=user.getUser_name()%>" />
							<input type="hidden" name="action" value="deliver-printer-ink" />
							<button type="submit" class="btn delivered-submit-btn">Submit</button>
						</form>
					</div>
				</div>
			</div>

			<div class="col-lg-9">
				<div class="ink-container">
					<div class="delivred-ink-stock">
						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Dispatched Info</strong>
						</h5>

						<div class="row">
							<div class="col-md-12">
								<div id="printer_ink_stock_wrapper"
									class="dataTables_wrapper dt-bootstrap4" style="margin: 28px;">
									<table id="delivered_ink"
										class="display table table-striped table-bordered"
										style="width: 100%;">
										<thead>
											<tr style="text-align: center;">
												<th>Barcode</th>
												<th>Issuer</th>
												<th>Receiver</th>
												<th>Dispatched Date</th>
												<th>Dispatched Location</th>
												<th>Device ID</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="delivered_ink" items="${delivered_ink}">
												<tr style="text-align: center;">
													<td>${delivered_ink.getBarcode()}</td>
													<td>${delivered_ink.getSent_by()}</td>
													<td>${delivered_ink.getDelivered_person()}</td>
													<td>${delivered_ink.getDelivery_date()}</td>
													<td>${delivered_ink.getLocation()}</td>
													<td>${delivered_ink.getDevice_no()}</td>
												</tr>
											</c:forEach>
										</tbody>
										<tfoot>
											<tr style="text-align: center;">
												<th>Barcode</th>
												<th>Issuer</th>
												<th>Receiver</th>
												<th>Dispatched Date</th>
												<th>Dispatched Location</th>
												<th>Device ID</th>
											</tr>
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</div>
</div>
<%@include file="../includes/footer.jsp"%>

<script type="text/javascript">
	$('#delivered_ink').DataTable();

	$('#barcode').on(
			"change",
			function() {

				let barcode = $(this).val();

				$
						.get(
								"InkController?action=searchBarcode&barcode="
										+ barcode,
								function(data, status) {
									console.log('status ::::::::::' + status);
									console.log("sdkjfbaslkfnalfkn", data);

									if (data.length === 0) {
										console.log("wrong barcode");
										$("#e-barcode-msg").text(
												"Barcode is not Available !!");
										$("#barcode-msg").text("");
									}

									data.forEach(function(barcode, key) {
										console.log("correct");
										$("#e-barcode-msg").text("");
										$("#barcode-msg").text("");
										$("input#barcode_id").val(barcode.id);
										$("input#ink_id").val(barcode.ink_id);
									})

								}).fail(
								function(jqXHR, textStatus, errorThrown) {
									console.log('error');
									console.log(jqXHR);
									console.log(textStatus);
									console.log(errorThrown);
								});

			});

	$('#deliver-printer-ink').submit(function() {

		var form = $('#deliver-printer-ink');

		$.ajax({
			type : form.attr('method'),
			url : form.attr('action'),
			data : form.serialize(),
			success : function(data) {
				$("#barcode-msg").text("Ribbon Delivered Successfully !!");
				location.reload();
			}
		});

		return false;
	});
</script>