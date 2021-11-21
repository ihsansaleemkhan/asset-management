<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Damagedcard"%>
<%@ page import="com.beans.Returnedcard"%>
<%@ page import="com.beans.Summary"%>

<jsp:useBean id="summaryDAO" class="com.dao.SummaryDAO"></jsp:useBean>

<%
	ArrayList<Damagedcard> damagedcards = summaryDAO.getDamagedCards();
	ArrayList<Returnedcard> returnedcards = summaryDAO.getReturnedCards();
	ArrayList<Summary> ready_to_deliver = summaryDAO.getReadyToDeliverCardBoxes();
	ArrayList<Summary> delivered_cards = summaryDAO.getDeliveredCardBoxes();
%>

<c:set var="damagedcards" value="<%=damagedcards%>" />
<c:set var="returnedcards" value="<%=returnedcards%>" />
<c:set var="ready_to_deliver" value="<%=ready_to_deliver%>" />
<c:set var="delivered_cards" value="<%=delivered_cards%>" />
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
		<div class="record-container">
			<div class="container-record">
				<section id="fancyTabWidget" class="tabs t-tabs">
					<ul class="nav nav-tabs fancyTabs" role="tablist">
						<li class="tab fancyTab active" style="width: 25%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a id="readyTab" href="#readyTabBody" role="tab"
							aria-controls="readyTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span class="fa"><i
									class="fas fa-boxes"></i></span> <span class="hidden-xs">Ready
									To Deliver Boxes</span></a>
						</li>
						<li class="tab fancyTab" style="width: 25%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a id="deliveredTab" href="#deliveredTabBody" role="tab"
							aria-controls="deliveredTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span class="fa"><i
									class="fas fa-people-carry"></i></span> <span class="hidden-xs">Delivered
									Boxes</span></a>
						</li>

						<li class="tab fancyTab" style="width: 25%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a id="damagedTab" href="#damagedTabBody" role="tab"
							aria-controls="damagedTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span
								class="fa fa-exclamation-circle"></span><span class="hidden-xs">Damaged
									Cards</span></a>
						</li>

						<li class="tab fancyTab" style="width: 25%">
							<div class="arrow-down">
								<div class="arrow-down-inner"></div>
							</div> <a id="returnedTab" href="#returnedTabBody" role="tab"
							aria-controls="returnedTabBody" aria-selected="true"
							data-toggle="tab" tabindex="0"><span
								class="fa fa-exchange-alt"></span><span class="hidden-xs">Returned
									Cards</span></a>
						</li>
					</ul>
					<div id="myTabContent" class="tab-content fancyTabContent"
						aria-live="polite">
						<div class="tab-pane  fade active show" id="readyTabBody"
							role="tabpanel" aria-labelledby="readyTab" aria-hidden="false"
							tabindex="0">
							<div>
								<div class="row">

									<div class="col-md-12">

										<table id="ready_to_deliver"
											class="display table table-striped table-bordered"
											style="width: 100%">
											<thead>
												<tr
													style="color: white; background-color: #772b49; text-align: center;">
													<th>Shipment#</th>
													<th>Carton Group</th>
													<th>Box Group</th>
													<th>Serial Number</th>
													<!-- <th>Printed Date</th> -->
													<th>Note</th>
													<th>Card Ready Count</th>
													<th>School</th>
													<th>Completed Date</th>
													<!-- <th>Stock Out Date</th> -->
												</tr>
											</thead>
											<tbody>

												<c:forEach var="ready_to_deliver"
													items="${ready_to_deliver}">
													<tr style="text-align: center;">
														<td>${ready_to_deliver.getShipment()}</td>
														<td>${ready_to_deliver.getCartonGroup()}</td>
														<td>${ready_to_deliver.getBoxGroup()}</td>
														<td>${ready_to_deliver.getCardSerialNo()}</td>
														<%-- <td>${ready_to_deliver.getPrintedDate()}</td> --%>
														<td>${ready_to_deliver.getNote()}</td>
														<td>${ready_to_deliver.getCardReadyCount()}</td>
														<td>${ready_to_deliver.getSchool()}</td>
														<td>${ready_to_deliver.getCompletedDate()}</td>
														<%-- <td>${ready_to_deliver.getStockOutDate()}</td> --%>
													</tr>
												</c:forEach>

											</tbody>
										</table>

									</div>

								</div>
							</div>
						</div>
						<div class="tab-pane  fade" id="deliveredTabBody" role="tabpanel"
							aria-labelledby="deliveredTab" aria-hidden="false" tabindex="0">
							<div>
								<div class="row">

									<div class="col-md-12">
										<table id="delivered_cards"
											class="display table table-striped table-bordered"
											style="width: 100%">
											<thead>
												<tr
													style="color: white; background-color: #772b49; text-align: center;">
													<th>Shipment#</th>
													<th>Carton Group</th>
													<th>Box Group</th>
													<th>Serial Number</th>
													<!-- 	<th>Printed Date</th> -->
													<th>Note</th>
													<th>Delivered Cards</th>
													<th>School</th>
													<!-- 	<th>Stock Out Date</th> -->
													<th>Completed Date</th>
													<th>Delivered Date</th>
													<th>Delivered By</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="delivered_cards" items="${delivered_cards}">
													<tr style="text-align: center;">
														<td>${delivered_cards.getShipment()}</td>
														<td>${delivered_cards.getCartonGroup()}</td>
														<td>${delivered_cards.getBoxGroup()}</td>
														<td>${delivered_cards.getCardSerialNo()}</td>
														<%-- <td>${delivered_cards.getPrintedDate()}</td> --%>
														<td>${delivered_cards.getNote()}</td>
														<td>${delivered_cards.getCardReadyCount()}</td>
														<td>${delivered_cards.getSchool()}</td>
														<%-- <td>${delivered_cards.getStockOutDate()}</td> --%>
														<td>${delivered_cards.getCompletedDate()}</td>
														<td>${delivered_cards.getDeliveredDate()}</td>
														<td>${delivered_cards.getDeliveredPerson()}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>

								</div>
							</div>
						</div>
						<div class="tab-pane  fade" id="damagedTabBody" role="tabpanel"
							aria-labelledby="damagedTab" aria-hidden="true" tabindex="0">
							<div class="row">

								<div class="col-md-12">


									<table id="damaged_card"
										class="display table table-striped table-bordered"
										style="width: 100%">
										<thead>
											<tr
												style="color: white; background-color: #772b49; text-align: center;">
												<th>#Shipment</th>
												<th>Carton Group</th>
												<th>Box Group</th>
												<th>Box Serial Number</th>
												<th>Card Serial Number</th>
												<th>Reason For Damaged</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="damagedcards" items="${damagedcards}">
												<tr style="text-align: center;">
													<td>${damagedcards.getShipment()}</td>
													<td>${damagedcards.getCarton_group()}</td>
													<td>${damagedcards.getBox_group()}</td>
													<td>${damagedcards.getBox_serial_no()}</td>
													<td>${damagedcards.getCard_serial_no()}</td>
													<td>${damagedcards.getReason()}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>

								</div>
							</div>
						</div>
						<div class="tab-pane  fade" id="returnedTabBody" role="tabpanel"
							aria-labelledby="returnedTab" aria-hidden="true" tabindex="0">
							<div class="row">
								<div class="col-md-12">

									<table id="returned_card"
										class="display table table-striped table-bordered"
										style="width: 100%">
										<thead>
											<tr
												style="color: white; background-color: #772b49; text-align: center;">
												<th>#Shipment</th>
												<th>Carton Group</th>
												<th>Box Group</th>
												<th>Box Serial Number</th>
												<th>Card Serial Number</th>
												<th>School</th>
												<th>Reason for Return</th>
												<th>Received By</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="returnedcards" items="${returnedcards}">
												<tr style="text-align: center;">
													<td>${returnedcards.getShipment()}</td>
													<td>${returnedcards.getCarton_group()}</td>
													<td>${returnedcards.getBox_group()}</td>
													<td>${returnedcards.getBox_serial_no()}</td>
													<td>${returnedcards.getCard_serial_no()}</td>
													<td>${returnedcards.getSchool_name()}</td>
													<td>${returnedcards.getReason()}</td>
													<td>${returnedcards.getRecived_by()}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>

								</div>
							</div>
						</div>

					</div>

				</section>
			</div>
		</div>
	</div>
</div>
<script>
	$(document).ready(function() {

		$('#damaged_card').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'excel', 'pdf', 'print' ]
		});

		$('#returned_card').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'excel', 'pdf', 'print' ]
		});

		$('#ready_to_deliver').DataTable({

			dom : 'Bfrtip',
			buttons : [ 'excel', 'pdf', 'print' ]
		});

		$('#delivered_cards').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'excel', 'pdf', 'print' ]
		});

		$("ul li").click(function() {
			$(this).siblings('li').removeClass('active');
			$(this).addClass('active');
		});

	});
</script>


<%@include file="../includes/footer.jsp"%>