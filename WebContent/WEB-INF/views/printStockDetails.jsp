<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@page import="com.beans.School"%>
<jsp:useBean id="utilDao" class="com.dao.UtilDao" />
<%
	List<School> school = utilDao.getAllSchool();
%>
<head>
	

<body style="background: rgba(0, 0, 0, 0.03);">
	<div class="dts-cardImage">
	<br/><br/>
	<div class="row ag-stock">
		<div class="col-12">
			<div id="printStock">
				<!-- <form action="getPrintData" > -->
				<div class="row">
					<div class="col-lg-1">School Name</div>
					<div class="col-lg-2">
						<select class="form-control" name="school_filter" id="school_filter">
							<option value="100">All Driving Schools</option>
							<c:forEach var="school" items="<%=school%>">
								<option value="${school.getId()}"
									<c:if test="${school.getId() eq schoolId}">selected</c:if>>
									${school.getSchoolName()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-lg-1">Start Date</div>
					<div class="col-lg-2">
						<input type="date" id="start-date" name="start-date"  />
						<label style="display: none; color: red;" id="startIdEmpty">Please enter start date</label>
					</div>
					<div class="col-lg-1">End Date</div>
					<div class="col-lg-2">
						<input type="date" id="end-date" name="end-date"  />
					</div>
					<div class="col-lg-1"><input type="button" id="getData" value="Apply Filter" class="btn-report"></div>
					<div class="col-lg-1"><input type="button" id="printButton" value="Print" class="btn-report"></div>
				</div>
				<!-- </form> -->
			</div>
		</div>
	</div>
	
	<div class="table-block" style="margin:15px">
		<table class="display table table-striped table-bordered"
			id="dtsCardTable" style="width: 100%; margin-top: 2%;">
			<thead>
				<tr style="background: #772b49;color: #fff;">

					<!-- <th scope="col">#</th> -->
					<th scope="col" class="text-center">Box Group</th>
					<th scope="col" class="text-center">Card Series No</th>
					<th scope="col" class="text-center">Created Date</th>
					<th scope="col" class="text-center">Delivered Card</th>
					<th scope="col" class="text-center">Returned Card</th>
					<th scope="col" class="text-center">Damaged Card</th>
					<th scope="col" class="text-center">Delivered Date</th>
					<th scope="col" class="text-center">Delivered School</th>
					<th scope="col" class="text-center">Delivered Person</th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td colspan="100%" align="center"></td>
				</tr>

			</tbody>
		</table>
	</div>
	
	
	
	<br/>
	<div id="printTable" style="display: none;">
		<div id="createTable"></div>
	</div>
	<label id="data_unavailable" style="display: none; color: red; margin-left:40%;"><h2>Data doesn't exist.</h2></label>
	<br/>
	
	</div>
</body>

<script type="text/javascript">

function genarateTable(){
	console.log('genarateTable');
	let startdate = $("#start-date").val();
	$("#startIdEmpty").hide();
/* 	if(startdate==''){
		$("#startIdEmpty").show();
		return;
	} */
	let school = $("#school_filter").val();
	let endDate = $("#end-date").val();
	console.log(startdate + " , "+ school + " , "+ endDate);
	
	  $('#dtsCardTable').DataTable( {
	   

		   destroy: true,
		   scrollX: true,
		   responsive: true,
		   lengthMenu: [[12, 25, 50, 75, 100], [12, 25, 50, 75, 100]],
	       "filter": false,
	       "info": true,
	       serverSide: true,
	       processing: true,


		  ajax : {

				url: "printController",
		       //	data: "action=getData&startdate="+startdate+"&school="+school+"&endDate="+endDate,
		       data: {action:"getData", startdate:startdate, school:school, endDate:endDate},
		       	type: "GET",
				dataFilter : function(data) {

					var newData = JSON.parse(new Object(data));

					let json = {};
					console.log(newData);
					json.draw = newData.draw;
					json.recordsTotal = newData.recordsTotal;
		            json.recordsFiltered = newData.recordsFiltered;
					json.data = JSON.parse(newData.data); 
					return JSON.stringify(json); // return JSON string
				}
			},
			columns : [
			{
				"data" : "box_group"
			},{
				"data" : "card_serial_no"
			}, {
				"data" : "printedDate"
			}, {
				"data" : "delivered_cards"
			}, {
				"data" : "returnedCard"
			}, {
				"data" : "damaged_card"
			}, {
				"data" : "deliveredDate"
			}, {
				"data" : "school"
			},{
				"data" : "delivered_person"
			} ],
			columnDefs : [
					{
						data : null,
						defaultContent : "<i>N/A</i>",
						targets : "_all"
					}, {
						targets : "_all",
						orderable : false,
					} ]
	    } );

	}



$("#getData").click(function(){
	let startdate = $("#start-date").val();
	let endDate = $("#end-date").val();
	if(startdate == '' && endDate != ''){
		$("#startIdEmpty").show();
		return;
	}
	genarateTable();

});
window.onload = (event) => {
	 genarateTable();
	};

$("#printButton").click(function(){
		let startdate = $("#start-date").val();
		$("#startIdEmpty").hide();
	/* 	if(startdate==''){
			$("#startIdEmpty").show();
			return;
		} */
		let school = $("#school_filter").val();
		let endDate = $("#end-date").val();
		console.log(startdate + " , "+ school + " , "+ endDate);
		var divToPrint=document.getElementById("printTable");
		$.get("printController?action=getData&startdate="+startdate+"&school="+school+"&endDate="+endDate+"&print=true", function(data){
			console.log('success :'+data);
			if(data.length>0){
				loopThroughData(data);
				//$("#printTable").show();
				$("#data_unavailable").hide();
				setTimeout(function() { 
					var newWin=window.open('','Print-Window');
					newWin.document.open();
					newWin.document.write('<html><body class="someclass" onload="window.print()"><img src="./images/dts-logo.png" width="100px"><br/><br/>');
					newWin.document.write(divToPrint.innerHTML+'</body></html>');
					newWin.document.close();
					newWin.focus();
				}, 1000);
			}else{
				$("#printTable").hide();
				$("#data_unavailable").show();
			}
		}).fail(function(){
			console.log('failure');
		});
		
	})
	
function loopThroughData(data){
	$('#createTable').html("");
	$('#createTable').append('<table border="1" id="L_P_training" style="border-collapse:collapse;"><tr style="text-align: center;"><th style="width:8%;"> Box Group </th><th style="width:19%;"> Card Series No </th><th style="width:12%;">Created Date</th><th>Delivered Card</th><th>Returned Card</th><th>Damaged Card</th><th style="width:12%;">Delivered Date</th><th>Delivered School</th><th>Delivered Person</th></tr></table>');
	data.forEach(function(key, value){
		$('#L_P_training tr:last')
			.after('<tr style="text-align: center;"><td>'+ key.box_group + '</td>' +
			'<td>'+ key.card_serial_no + '</td>' +
			'<td>' + key.printed_date + '</td>' +
			'<td>' + key.delivered_cards + '</td>' +
			'<td>' + key.returned_cards + '</td>' +
			'<td>' + key.damaged_card + '</td>' +
			'<td>' + key.delivered_date + '</td>' +
			'<td>' + key.school + '</td>' +
			'<td>' + key.delivered_person + '</td>' +
     		'</tr>');
		});
}

</script>
<%@include file="../includes/footer.jsp"%>