<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%
	String searchCritera = request.getParameter("search");
	System.out.println("search criteria : "+request.getParameter("search"));
%>

	<h2 id="carton_message" style="color:red;margin: 52px 420px;"></h2>
<div id="red" class="dtscard-container" >
							<div class="dtscard-inner" style="margin: 12px 420px;">
							  
								<form>
								   
								   <input type="hidden" class="form-control input-card" id="box_id" name=box_id value="" readonly>
												
								   
									<div class="dts-card red">
										<div class="dts-card-header" style="background-color: #772b49">
											<h5 class="dts-card-title">DTS Card Box Info</h5>
										</div>
										<div class="dts-card-body">
											<div class="row">
												<div class="col-6">
													<p>
														<span class="sp-card">Card Number :</span> <input
															type="text" class="form-control input-card" id="card_no"
															name="card_no" value="" readonly>
													</p>
													<p>
														<span class="sp-card">Card Serial Number :</span> <input
															type="text" class="form-control input-card" id="card_sn"
															name="card_sn" value="" readonly>
													</p>
													<p>
														<span class="sp-card">Box Group :</span> <input
															type="text" class="form-control input-card" id="box_group"
															name=box_group value="" readonly>
													</p>
													
													
													<p>
														<span class="sp-card">Delivered Date :</span> <input type="text"
															class="form-control input-card" id="delivered_date"
															name="delivered_date" value="" readonly>
													</p>
												</div>
												<div class="col-6">
													<p>
													<span class="sp-card">School :</span> <input
															type="text" class="form-control input-card" id="school"
															name=shool value="" readonly>
													</p>
													<p>
														<span class="sp-card">Delivered Cards:</span> <input
															type="text" class="form-control input-card" id="ready_count"
															name="ready_count" value="" readonly>
													</p>
													<p>
														<span class="sp-card">Note :</span> <input type="text"
															class="form-control input-card" id="note"
															name="note" value="" readonly>
													</p>
													<p>
														<span class="sp-card">Delivered Person :</span> <input type="text"
															class="form-control input-card" id="delivered_by"
															name="note" value="" readonly>
													</p>
													<p>
														<span class="sp-card">Status :</span> <input
															type="text" class="form-control input-card" id="status"
															name="status" value="" readonly>
															
													</p>
													<span class="" id="error-msg" style="color:red;"></span>
												</div>
												<!-- 			<p>
												<span class="sp-card">Damaged in Office :</span> <input type="text" class="form-control input-card" id="dm_office" name="box_no" value="0" readonly>
											</p>
											<p>
												<span class="sp-card">Allotted Cards :</span> <input type="text" class="form-control input-card" id="box_no" name="allottled" value="0" readonly>
											</p>
											<p>
												<span class="sp-card">Returned Count :</span> <input type="text" class="form-control input-card" id="box_no" name="box_no" value="0" readonly>
											</p> -->
											
											</div>
											<div class="dts-card-footer" style="background-color: #772b49"></div>
											
										</div>
									</div>
								</form>
							</div>
						</div>

<script type="text/javascript">
$(document).ready(function(){
	var criteria = "<%=searchCritera%>";
	console.log('criteria:'+criteria)
	$.get("searchController?action=search&card_serial_no="+criteria, function(data){
		console.log('data length1: '+Object.keys(data).length);
		//console.log('data length2: '+data.length());
		//console.log('data length3: '+data.size);
		//console.log('data length4: '+data.size());
/* 		data.foreach(function(key, value){
			console.log('key:'+key);
			
		}); */
		
		if(Object.keys(data).length > 8){
			
			$("input#card_no").val(data.enteredCardSerialNo);
			$("input#box_group").val(data.box_group);
			$("input#ready_count").val(data.deliverdCardCount);
			$("input#card_sn").val(data.card_serial_no);
			$("input#delivered_date").val(data.delivery_date);
			$("input#school").val(data.school);
			$("input#note").val(data.box_type);
			$("input#delivered_by").val(data.delivery_person);
			
			if(data.status==1)
				$("input#status").val("Stock");
			else if(data.status==3)
				$("input#status").val("Delivered");
			else if(data.status==4)
				$("input#status").val("Damaged");
		}else{
			$("#red").hide();
		    $("#carton_message").text("No Data !!");
		}
		
	})
})
</script>
<%@include file="../includes/footer.jsp"%>