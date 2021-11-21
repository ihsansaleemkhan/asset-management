<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

<div id="pageloader">
   <img src="http://cdnjs.cloudflare.com/ajax/libs/semantic-ui/0.16.1/images/loader-large.gif" alt="processing..." />
</div>

<!-- jQuery UI Datepicker - Default functionality -->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  
<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">
	
<div class="form-container">
	<div class="form-inner-cardbox">


		<h5 class="card-header info-color white-text text-center py-4">
			<strong>Add Carton Box</strong>
		</h5>

		<form action="CardboxController" method="POST" id="add-cartonbox" class="form-cnt" role="form">
		
		        <c:if test="${not empty false_msg}">
				   <span id="success_carton_message" style="color:red;margin: 52px 228px;">${false_msg}</span>
			    </c:if>
		
			<div class="form-group tx-input">
				<label class="tx-label" for="shipment">Shipment : </label> 
				<input type="text" class="form-control" id="shipment" name="shipment" required>
			</div>
			<div style="display:flex;justify-content: space-between;">
				<div style="width: 312px;" class="form-group tx-input">
					<label class="tx-label" for="carton_no">Number of Carton : </label> 
					<input type="number" min="0" class="form-control" id="carton_no" name="carton_no" required>
				</div>
				<div style="width: 312px;" class="form-group tx-input">
					<label class="tx-label" for="carton_no">Number of Boxes per Carton : </label> 
					<input type="number" min="0" class="form-control" id="no_of_boxes" name="no_of_boxes" required>
				</div>
			</div>
			<div style="display:flex;justify-content: space-between;">
				<div style="width: 312px;" class="form-group tx-input">
					<label class="tx-label" for="stock_in_date">Ordered Date :
					</label> <input type="text" class="form-control" id="ordered_date" name="ordered_date" autocomplete="off" placeholder="Select Date" required>
				</div>
				<div style="width: 312px;" class="form-group tx-input">
					<label class="tx-label" for="stock_in_date">Stock In Date :
					</label> <input type="text" class="form-control" id="stock_in_date" name="stock_in_date" autocomplete="off" placeholder="Select Date" required>
				</div>
			</div>
		    <input type="hidden" name="action" value="add-cartonbox" /> 
			<button type="submit" class="btn submit-btn">Submit</button>
		</form>
	</div>
	
      <c:if test="${not empty message}">
       <span style="color:red;" id="message">${message}</span>
	  </c:if>
		  
</div>

</div>
</div>

<%@include file="../includes/footer.jsp"%>

<script>
"use strict";

$(document).ready(function(){
	  $("#add-cartonbox").on("submit", function(){
	    $("#pageloader").fadeIn();
	  });//submit
	});//document ready
	
 $( function() { $( "#ordered_date" ).datepicker({ maxDate: new Date, minDate: new Date(2007, 6, 12) ,
	    onClose: function (selected) {
	        if(selected.length <= 0) {
	            // selected is empty
	            $("#stock_in_date").datepicker('disable');
	        } else {
	            $("#stock_in_date").datepicker('enable');
	        }
	        $("#stock_in_date").datepicker("option", "minDate", selected);
	      }
    });
    
    $( "#stock_in_date" ).datepicker({ maxDate: new Date });
    
    });
</script>