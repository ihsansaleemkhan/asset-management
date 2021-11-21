<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Schools"%>

<jsp:useBean id="cardboxDAO" class="com.dao.CardboxDAO"></jsp:useBean>


<%
ArrayList<Schools> schools = cardboxDAO.getSchools();
%>

<c:set var="schools" value="<%=schools%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

<div class="form-container">
          <c:if test="${not empty success_message}">
		  <span id="success_message" style="color:green;margin: 45px 192px;">${success_message}</span>
          </c:if>
           <c:if test="${not empty fail_message}">
		  <span id="fail_message" style="color:red;margin: 45px 200px;">${fail_message}</span>
		  </c:if>

	<div class="form-inner-return">
	
		<h5 class="card-header info-color white-text text-center py-4">
			<strong>Add Returned card Details</strong>
		</h5>

		<form action="CardboxController" method="POST" id="add-returned-card" class="form-cnt" role="form">
			<div class="form-group tx-input">
				<label class="tx-label" for="card_serial_no">Card Serial Number : </label> <input
					type="text" class="form-control" id="card_serial_no" name="card_serial_no" required>
			</div>
			<div class="form-group tx-input">
				<label class="tx-label" for="reason">Reason for Return : </label>
			      <select name="reason_drop" id="reason_drop" class="form-control form-control-sm" onchange='checkreason();'> 
				    <option>Select Reason</option>  
				    <option value="School Change">School Change</option>
				    <option value="Language Change">Language Change</option>
				    <option value="ID number change">ID Number Change</option>
				    <option value="Wrong Informatio">Wrong Information</option>
				    <option value="Printing mistake">Printing Mistake</option>
				      <option value="License Type Change">License Type Change</option>
				    <option value="Others">Others</option>
				  </select>
			<!-- 		<input type="text" name="color" id="color" style='display:none;'/> -->
			   <input type="hidden" class="form-control" id="reason" name="reason" style='margin-top: 6px;' required>
			</div> 
			<div class="form-group tx-input">
				<label class="tx-label" for="school">School : </label> 
				<select name="school_id" class="form-control form-control-sm">
			      <c:forEach var="schools" items="${schools}">
					 <option value="${schools.getId()}">${schools.getSchool_name()}</option>
				  </c:forEach>
                </select>
			</div>
			<div class="form-group tx-input">
				<label class="tx-label" for="received">Received By : </label> <input
					type="text" class="form-control" id="received" name="received" required>
			</div>
		    <input type="hidden" name="action" value="add-returned-card" /> 
			<button type="submit" class="btn submit-btn">Submit</button>
		</form>
	</div>
	
      <c:if test="${not empty message}">
       <span style="color:red;" id="message">${message}</span>
	  </c:if>
		  
</div>

</div>
</div>

<%@include file="../includes/footer.jsp" %>

<script type="text/javascript">
function checkreason(){
	 var val = document.getElementById('reason_drop').value;

	 if(val=='Others')
		 document.getElementById('reason').type = 'text';
	 else  
		 document.getElementById('reason').type = 'hidden';
}

</script> 