
<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>


<%@ page import="com.beans.Ribbonreq"%>

<jsp:useBean id="ribbonDAO" class="com.dao.RibbonDAO"></jsp:useBean>

<%
	int order_no = ribbonDAO.getOrderNo();
	ArrayList<Ribbonreq> req_list = ribbonDAO.getAllOrderRequestedRibbonList();
%>

<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="date" value="${now}" pattern="yyyy-MM-dd" />

<c:set var="req_list" value="<%=req_list%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

		<div class="row" style="margin-right: 0px;">

			<div class="col-lg-3">

				<div class="req-form-container">

					<div class="form-inner-reqink">

						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Request Order</strong>
						</h5>
						<span id="barcode-msg" style="color: green; margin-left: 106px;"></span>
						<span id="barcode-fail-msg"
							style="color: red; margin-left: 106px;"></span>
						<form action="RibbonController" method="POST"
							name="ribbon-request" id="ribbon-request" class="form-cnt"
							role="form">

							<div style="display: flex; justify-content: space-between;">

								<input type="hidden" class="form-control" id="order_no"
									name="order_no" value="<%=order_no + 1%>" readonly> <label
									class="tx-label" for="requested_by">Requested By : </label> <input
									type="text" class="form-control" style="width: 290px;"
									id="requested_by" name="requested_by" required
									value="<%=user.getUser_name()%>" readonly>


							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="shipment">QTY : </label> <input
										type="number" min="1" class="form-control" id="qty" name="qty"
										required>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="model">Model : </label> <input
										type="text" class="form-control" id="model" name="model"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="category">Category : </label> <select
										class="form-control" id="category" name="category">
										<option value="Colour">Colour</option>
										<option value="Black & White">Black & White</option>
									</select>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="requested_date">Requested
										Date : </label> <input type="date" class="form-control"
										id="requested_date" name="requested_date" value="${date}"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="order_recieved">Order
										Received Person : </label> <input type="text" class="form-control"
										id="order_recieved" name="order_recieved" required>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="supplier">Supplier : </label><input
										type="text" class="form-control" id="supplier" name="supplier"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<label class="tx-label" for="category">Via : </label>
								<div style="margin: 8px;">
									Call: <input type="checkbox" name="call" value="call" checked>
									&nbsp;&nbsp;E-mail: <input type="checkbox" name="email"
										value="email"> &nbsp;&nbsp;Message: <input
										type="checkbox" name="message" value="message">
								</div>
							</div>
							<input type="hidden" name="action" value="ribbon-request" />
							<button type="submit" class="btn req-submit-btn">Submit</button>
						</form>
					</div>

				</div>

			</div>

			<div class="col-lg-9">
				<div class="ink-req-container">
					<div class="printer-ink-stock">
						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Requested Order List</strong>
						</h5>

						<div class="row">
							<div class="col-md-12">
								<div id="printer_ink_stock_wrapper"
									class="dataTables_wrapper dt-bootstrap4" style="margin: 28px;">
									<table id="ribbon_req_order"
										class="display table table-striped table-bordered"
										style="width: 100%;">
										<thead>
											<tr style="text-align: center;">
												<th>#No</th>
												<th>Model</th>
												<th>QTY</th>
												<th>Category</th>
												<th>Requested Date</th>
												<th>Order Received Person</th>
												<th>Order Via</th>
												<th>Supplier</th>
												<th>Requested By</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="req_list" items="${req_list}">
												<tr style="text-align: center;">
													<td>${req_list.getOrder_no()}</td>
													<td>${req_list.getModel()}</td>
													<td>${req_list.getQty()}</td>
													<td>${req_list.getCat()}</td>
													<td>${req_list.getReq_date()}</td>
													<td>${req_list.getOrder_recieved_person()}</td>
													<td>${req_list.getOrder_via()}</td>
													<td>${req_list.getSupplier()}</td>
													<td>${req_list.getReq_by()}</td>
												</tr>
											</c:forEach>
										</tbody>
										<tfoot>
											<tr style="text-align: center;">
												<th>#No</th>
												<th>Model</th>
												<th>QTY</th>
												<th>Category</th>
												<th>Requested Date</th>
												<th>Order Received Person</th>
												<th>Order Via</th>
												<th>Supplier</th>
												<th>Requested By</th>
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
	$("input:checkbox").on('click', function() {
		let $box = $(this);
		if ($box.is(":checked")) {
			let group = "input:checkbox[name='" + $box.attr("name") + "']";

			$box.prop("checked", true);
		} else {
			$box.prop("checked", false);
		}
	});

	var value = $('#requested_date').val();
	console.log("date :" + value);

	var table = $('#ribbon_req_order').DataTable();

	$('#ribbon-request').submit(
			function() {

				var form = $('#ribbon-request');

				$.ajax({
					type : form.attr('method'),
					url : form.attr('action'),
					data : form.serialize(),
					success : function(data) {
						console.log(typeof data);
						if (data == 'true') {
							$("#barcode-msg").text(
									"Ribbon Order Requested Successfully !!");
							location.reload();
						} else {
							$("#barcode-fail-msg")
									.text("Order via required !!");
						}
					}
				});
				return false
			});
</script>
