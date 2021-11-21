<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Schools"%>
<%@ page import="com.beans.DispatchedRibbon"%>

<jsp:useBean id="cardboxDAO" class="com.dao.CardboxDAO"></jsp:useBean>
<jsp:useBean id="ribbonDAO" class="com.dao.RibbonDAO"></jsp:useBean>

<%
	ArrayList<Schools> schools = cardboxDAO.getSchools();
	ArrayList<DispatchedRibbon> dispatched_ribbon = ribbonDAO.getAllDispatchedRibbonList();
%>

<c:set var="schools" value="<%=schools%>" />
<c:set var="dispatched_ribbon" value="<%=dispatched_ribbon%>" />
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
						<form action="RibbonController" method="POST"
							name="dispatch-ribbon" id="dispatch-ribbon" class="form-cnt"
							style="margin-top: -5px;" role="form">

							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="barcode">Barcode : </label> <input
										type="text" class="form-control" id="barcode" name="barcode"
										required>
								</div>
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
							<input type=hidden name="ribbon_id" id="ribbon_id" value="" /> <input
								type="hidden" name="sent_by" id="sent_by" value="<%=user.getUser_name()%>" />
							<input type="hidden" name="action" value="dispatch-ribbon" />
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
									<table id="dispatched_ribbon_table"
										class="display table table-striped table-bordered"
										style="width: 100%;">
										<thead>
											<tr style="text-align: center;">
												<th>Barcode</th>
												<th>Issuer</th>
												<th>Receiver</th>
												<th>Dispatched Date</th>
												<th>Dispatched Location</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="dispatched_ribbon"
												items="${dispatched_ribbon}">
												<tr style="text-align: center;">
													<td>${dispatched_ribbon.getBarcode()}</td>
													<td>${dispatched_ribbon.getSent_by()}</td>
													<td>${dispatched_ribbon.getDelivered_person()}</td>
													<td>${dispatched_ribbon.getDelivery_date()}</td>
													<td>${dispatched_ribbon.getLocation()}</td>
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
	$('#dispatched_ribbon_table').DataTable();

	$('#barcode').on(
			"change",
			function() {

				let barcode = $(this).val();

				$.get(
						"RibbonController?action=searchBarcode&barcode="
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
								$("input#ribbon_id").val(barcode.ribbon_id);
							})

						}).fail(function(jqXHR, textStatus, errorThrown) {
					console.log('error');
					console.log(jqXHR);
					console.log(textStatus);
					console.log(errorThrown);
				});

			});

	$('#dispatch-ribbon').submit(function() {

		var form = $('#dispatch-ribbon');

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