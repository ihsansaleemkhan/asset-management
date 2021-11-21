<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>


<%@ page import="com.beans.RequestInk"%>
<%@ page import="com.beans.Printerink"%>

<jsp:useBean id="PrinterinkDAO" class="com.dao.PrinterinkDAO"></jsp:useBean>

<%
ArrayList<RequestInk> order_no_list = PrinterinkDAO.getOrderNoList();
ArrayList<Printerink> printerink = PrinterinkDAO.getPritnerInkStockDetails();
%>
<c:set var="printerink" value="<%=printerink%>" />
<c:set var="order_no_list" value="<%=order_no_list%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

		<div class="row" style="margin-right: 0px;">
			<div class="col-lg-3">

				<div class="received-form-container">

					<div class="form-inner-addink">

						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Add Stock</strong>
						</h5>
						<span id="barcode-msg" style="color: green; margin-left: 135px;"></span>
						<span id="e-barcode-msg" style="color: red; margin-left: 8px;"></span>
						<form action="InkController" method="POST" name="add-printer-ink" id="add-printer-ink" class="form-cnt" role="form">

							<div style="display: flex; justify-content: space-between;">

								<label class="tx-label" for="received_by">Received By :
								</label> <input type="text" class="form-control" style="width: 290px;" id="received_by" name="received_by" required value="<%=user.getUser_name()%>" readonly>

							</div>

							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="shipment">Order No : </label> <select
										name="order_no_id" id="order_no_id"
										class="form-control form-control-sm">
										<option>Select Order No</option>
										<c:forEach var="order_no_list" items="${order_no_list}">
											<option value="${order_no_list.getId()}">${order_no_list.getOrder_no()}</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="model">Model : </label> <input
										type="text" class="form-control" id="model" name="model"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="qty">QTY : </label>
									<div style="display: flex; width: 130px;">
										<input type="number" min="0" class="form-control"
											onchange="addBarcodes()" style="margin-right: 10px;" id="qty"
											name="qty" required>
										<button type="button" onclick="addBarcodes()"
											class="btn damage-card-btn">Add</button>
									</div>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="shipment">Shipment : </label> <input
										type="text" class="form-control" id="shipment" name="shipment"
										required>
								</div>
							</div>
							<div style="display: flex; justify-content: space-between;">
								<div class="form-group printer-input">
									<label class="tx-label" for="category">Category : </label> <input
										type="text" class="form-control" id="category" name="category"
										readonly>
								</div>
								<div class="form-group printer-input">
									<label class="tx-label" for="stock_in_date">Stock In
										Date: </label> <input type="date" class="form-control"
										id="stock_in_date" name="stock_in_date" required>
								</div>
							</div>
							<input type="hidden" name="supplier" id="supplier" value="" /> <input
								type="hidden" name="action" value="add-printer-ink" />
							<button type="submit" class="btn add-submit-btn">Submit</button>
						</form>
					</div>

				</div>
			</div>

			<div class="col-lg-9">
				<div class="ink-container">
					<div class="printer-ink-stock">
						<h5 class="card-header info-color white-text text-center py-4">
							<strong>Stock Info</strong>
						</h5>

						<div class="row">
							<div class="col-md-12">
								<div id="printer_ink_stock_wrapper"
									class="dataTables_wrapper dt-bootstrap4" style="margin: 28px;">
									<table id="printer_ink_stock"
										class="display table table-striped table-bordered"
										style="width: 100%;">
										<thead>
											<tr style="text-align: center;">
												<th>Shipment</th>
												<th>Model</th>
												<th>Category</th>
												<th>Supplier</th>
												<th>QTY</th>
												<th>Received By</th>
												<th>Stock In Date</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="printerink" items="${printerink}">
												<c:if test="${printerink.getQty() != 0}">
													<tr style="text-align: center;">
														<td>${printerink.getShipment()}</td>
														<td>${printerink.getModel()}</td>
														<td>${printerink.getCat()}</td>
														<td>${printerink.getSupplier()}</td>
														<td class="td-fx">${printerink.getQty()}
														  <span class="ribbon-list" onclick="viewRibbonList(${printerink.getId()} )">
														    <i class="fas fa-plus-circle"></i>
														  </span>
													    </td>
														<td>${printerink.getReceived_by()}</td>
														<td>${printerink.getDate()}</td>
													</tr>
												</c:if>
											</c:forEach>
										</tbody>
										<tfoot>
											<tr style="text-align: center;">
												<th>Shipment</th>
												<th>Model</th>
												<th>Category</th>
												<th>Supplier</th>
												<th>QTY</th>
												<th>Received By</th>
												<th>Stock In Date</th>
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

<%@include file="../includes/modals.jsp"%>
<%@include file="../includes/footer.jsp"%>


<script type="text/javascript">
	$(document).ready(function() {

	$("#order_no_id").change(function() {
		let id = document.getElementById("order_no_id").value;
              $.get("InkController?action=getPrinterInkDetail&id="+ id,
		function(data,status) {
			console.log('status ::::::::::'+ status);
			console.log(data);
			
			data.forEach(function(printerink,key) {
                  $("input#model").val(printerink.model);
				  $("input#qty").val(printerink.qty);
				  $("input#price").val(printerink.price);
				  $("#category").val(printerink.cat);
				  $("#supplier").val(printerink.supplier);
                      })
                           }).fail(
						function(jqXHR,textStatus,errorThrown) {
							console.log('error');
							console.log(jqXHR);
							console.log(textStatus);
							console.log(errorThrown);
						});

		   });

		});

	$('#printer_ink_stock').DataTable();
	
	  function addBarcodes() {
     		$('#add-ink-barcodes').modal('show');
         	}
	  
	  function viewRibbonList(id) {
		  console.log("id-------------",id);
		  $('#ribbon_list').modal('show');
		  $.get("InkController?action=getRibbonBarcode&id="+id,
		    function(data,status){
			  console.log(data);
			    let list = "";
			  	for(let count = 1; count <= data.length; count++) {
			  		console.log("ID -> ", count, " Barcode --> ", data[count-1].barcode);

			  	    list += '<li>'+data[count-1].barcode+'</li>';
			  	    
			  	}
			  	if(data.length == 0)
			  	   list = "No Data";
			  		
			  	let string = '<ul>'+list+'</ul>'
				$("#ribbon_list_table").empty().html(string);
			  	
		  }).fail(
			 function(jqXHR,textStatus,errorThrown) {
				console.log('error');
				console.log(jqXHR);
				console.log(textStatus);
				console.log(errorThrown);
			  });
	  }
	  
	  
	  $('#add-printer-ink').submit(function () {
	    	
		    let qty = parseInt(document.getElementById("qty").value);
		  
	    	let form = $('#add-printer-ink');
	    	
	    	//Loop through the Table rows and build a JSON array.
	        let total = 0;
	        let jsonTable = $('#ink_barcode_table tbody tr:has(td)').map(function () {
	            let $td = $('td', this);
	            total += parseFloat($td.eq(2).text());
	            return {
	                barcode: $td.eq(2).text(),
	            }
	        }).get();

	        $('#ink_barcode_table > tfoot > tr > td:nth-child(1)').html(total);

	        data = {};
	        data = jsonTable;
	        let array = [];
	        for(let i=0; i<data.length; i++){
	        	
	        	array.push(data[i].barcode);
	        }
	    	
	    	let formData = {...getParam(), ...{barcodes: array}};
	    	 
	    	console.log("sdaffafs le",data.length);
	    	console.log("qty",qty);
	    	
	    	if(data.length === qty)
	    	{
		       $.ajax({
		    		type: form.attr('method'),
		            url: form.attr('action'),
		            method: 'POST',
		            dataType: 'JSON',
		            data: formData
		        }).done(function (result) {
		             $("#barcode-msg").text("Ribbon Added Successfully !!");
		             $("#e-barcode-msg").text("");
				     $('#ink_barcode_table tbody tr').remove();
				     location.reload();
		        });
	    	}else
	    		{
	    		    $("#e-barcode-msg").text("Barcodes QTY is not Matching !!");
	    		    $("#barcode-msg").text("");
	    		}
		       
	    		return false;
	     });
	  
	  function getParam() {
			let formData = $("#add-printer-ink").serializeArray(); 
		 	let jsonFormData = {};

		    $.map(formData, function(n, i){
		    	
		    	if(n['value'] != "") {
		    		jsonFormData[n['name']] = n['value'];
		    	}
		    	
		    });
		    
		    return jsonFormData;
		}
</script>
