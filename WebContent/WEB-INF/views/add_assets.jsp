<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@page import="com.beans.AssetType"%>
<%@page import="com.beans.EmpInfo"%>
<%@page import="com.beans.Make"%>

<jsp:useBean id="assetDAO" class="com.dao.AssetDAO"></jsp:useBean>

<%
	ArrayList<AssetType> asset_type = assetDAO.getAssetType();
    ArrayList<EmpInfo> emp_info = assetDAO.getAssetOwnerList();
    ArrayList<Make> asset_make = assetDAO.getAssetMakeList();
%>

<c:set var="asset_type" value="<%=asset_type%>" />
<c:set var="emp_info" value="<%=emp_info%>" />
<c:set var="asset_make" value="<%=asset_make%>" />

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper">

<div class="container" style="margin-top: 100px;margin-bottom: 120px;">
    <div class="stepwizard">
        <div class="stepwizard-row setup-panel">
            <div class="stepwizard-step col-xs-3"> 
                <a href="#step-1" type="button" class="btn btn-success btn-circle">1</a>
                <p><small>Asset Information</small></p>
            </div>
            <div class="stepwizard-step col-xs-3"> 
                <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
                <p><small>Technical Information</small></p>
            </div>
            <div class="stepwizard-step col-xs-3"> 
                <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
                <p><small>Employee Information</small></p>
            </div>
        </div>
    </div>
    
    <form action="AssetController" method="POST" id="add-assets" enctype="multipart/form-data">
        <input type="hidden" id="action" name="action" value="add-assets" />
        <div class="panel panel-primary setup-content active" id="step-1">
                    <div class="panel-heading">
                 <h3 class="panel-title">Asset Information</h3>
            </div>
            <div class="panel-body">
            <div class="row">
              <div class="form-group col-lg-12">
                    <select id="asset_type" name="asset_type" class="form-control form-control-sm select-shipment">
						<option value="0">--Select Asset Type--</option>
						<c:forEach var="asset_type" items="${asset_type}">
						    <option value="${asset_type.getId()}">${asset_type.getAsset_type()}</option>
						</c:forEach>
					</select>
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Country</label>
                    <select id="country" name="country" class="form-control">
						<option value="0" id="0">--Select Country--</option>
						<option value="Qatar">Qatar</option>
						<option value="USA">USA</option>
					</select>
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">City</label>
                    <input maxlength="100" type="text" name="city" id="city" required="required" class="form-control" placeholder="Enter City" />
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Site</label>
                    <input maxlength="100" type="text" name="site" id="site" required="required" class="form-control" placeholder="Enter Site" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Barcode</label>
                    <input maxlength="100" type="text" required="required" name="barcode" id="barcode" class="form-control" placeholder="Enter Asset Number" />
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Asset Tag Name</label>
                    <input maxlength="100" type="text" required="required" name="tag_name" id="tag_name" class="form-control" placeholder="Enter Asset Tag Name" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">FA Code</label>
                    <input maxlength="100" type="text" required="required" name="fa_code" id="fa_code" class="form-control" placeholder="Enter FA Code" />
              </div>
            </div>
                <button class="btn next-btn nextBtn pull-right" type="button">Next</button>
            </div>
        </div>
        
        <div class="panel panel-primary setup-content" id="step-2">
            <div class="panel-heading">
                 <h3 class="panel-title">Technical Information</h3>
            </div>
            <div class="panel-body">
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Make</label>
                    <select id="make" name="make" class="form-control">
						<option value="">--Select Make--</option>
						 <c:forEach var="asset_make" items="${asset_make}">
						    <option value="${asset_make.getId()}">${asset_make.getMake()}</option>
						</c:forEach>
					</select>
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Model</label>
                     <select id="model" name="model" class="form-control" disabled>
						<option value="">--Select Model--</option>
					</select>
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Serial Number</label>
                    <input maxlength="100" type="text" required="required" name="serial_no" id="serial_no" class="form-control" placeholder="Enter Serial Number" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Mac Address</label>
                    <input maxlength="100" type="text" required="required" name="mac_address" id="mac_address" class="form-control" placeholder="Enter Mac Address" />
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Processor</label>
                    <input maxlength="100" type="text" required="required" name="processor" id="processor" class="form-control" placeholder="Enter Processor" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Operating System</label>
                      <input maxlength="100" type="text" required="required" name="os" id="os" class="form-control" placeholder="Enter Operating System" />
                   <!--   <select id="os" name="os" class="form-control">
						<option value="0" id="0">--Select Operating System--</option>
					</select> -->
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Hard Disk</label>
                    <input maxlength="100" type="text" required="required" name="hard_disk" id="hard_disk" class="form-control" placeholder="Enter Hard Disk" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Memory</label>
                    <input maxlength="100" type="text" required="required" name="memory" id="memory" class="form-control" placeholder="Enter Memory" />
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Procurred Date</label>
                    <input maxlength="100" type="date" required="required" name="procurred_date" id="procurred_date" class="form-control" placeholder="Enter Procurred Date" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Warranty Expired Date</label>
                    <input maxlength="100" type="date" required="required" name="warranty_exp_date" id="warranty_exp_date" class="form-control" placeholder="Enter Warranty Expired Date" />
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">Warranty/AMC/No AMC</label>
                    <input maxlength="100" type="text" required="required" name="warranty_amc" id="warranty_amc" class="form-control" placeholder="Enter Warranty/AMC/No AMC" />
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Remarks</label>
                    <input maxlength="100" type="text" required="required" name="remarks" id="remarks" class="form-control" placeholder="Enter Remarks" />
              </div>
            </div>
           	 	<button class="btn next-btn prevBtn pull-left" type="button">Previous</button>
                <button class="btn next-btn nextBtn pull-right" type="button">Next</button>
            </div>
        </div>
        
        <div class="panel panel-primary setup-content" id="step-4">
            <div class="panel-heading">
                 <h3 class="panel-title">Employee Information</h3>
            </div>
            <div class="panel-body">
            <div class="row">
              <div class="form-group col-lg-12">
                    <select id="emp" name="emp" class="form-control form-control-sm">
						<option value="0">--Select an Employee--</option>
						<c:forEach var="emp_info" items="${emp_info}">
						    <option value="${emp_info.getEmp_id()}">${emp_info.getFirst_name()} ${emp_info.getLast_name()} - Mobile No : ${emp_info.getMobile_no()}</option>
						</c:forEach>
					</select>
              </div>
            </div>
            <div class="row">
              <div class="form-group col-lg-6">
                    <label class="control-label">First Name</label>
                    <input maxlength="100" type="text" required="required" name="first_name" id="first_name" class="form-control" readonly/>
              </div>
              <div class="form-group col-lg-6">
                    <label class="control-label">Last Name</label>
                    <input maxlength="100" type="text" required="required" name="last_name" id="last_name" class="form-control" readonly />
              </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-6">
                    <label class="control-label">Email Address</label>
                    <input maxlength="100" type="text" required="required" name="email" id="email" class="form-control" readonly />
                </div>
                <div class="form-group col-lg-6">
                    <label class="control-label">Mobile Number</label>
                    <input maxlength="100" type="text" required="required" name="mobile_no" id="mobile_no" class="form-control" readonly />
                </div>
            </div>
            <div class="row">
                <div class="form-group col-lg-6">
                    <label class="control-label">Department</label>
                    <input maxlength="100" type="text" required="required" name="department" id="department" class="form-control" readonly />
                </div>
             <!--    <div class="form-group col-lg-6">
                    <label class="control-label">Department/Cost Center Code</label>
                    <input maxlength="100" type="text" name="dep_center" id="dep_center" class="form-control" readonly />
                </div> -->
            </div>
            	<button class="btn next-btn prevBtn pull-left" type="button">Previous</button>
                <button class="btn finish-btn pull-right" type="submit">Finish!</button>
            </div>
        </div>
    </form>
    
    <div>
   <h1>Import from excel </h1>
    <button class="cmd-import"> Import </button>
   </div>
    
</div>




</div>
	<!-- content-wrapper ends -->
</div>
<!-- main-panel ends -->

<%@include file="../includes/footer.jsp"%>

<script>
$(document).ready(function () {
	
    var navListItems = $('.stepwizard-step a'),
        allWells = $('.setup-content'),
        allNextBtn = $('.nextBtn'),
        allPrevBtn = $(".prevBtn")
    
   
    var CURRENT_ACTIVE = 1
    	PREV_SEP = 0;

    allWells.not(":nth-child(2)").hide();
    

    allNextBtn.click(function () {
    	console.log("Clicked Next!");
    	
    	const doNext = function() {
    		let activePanel = $(".setup-content.active"),
    		activeStepWizard = $('.stepwizard-step a.btn-success');
    	
	    	activePanel
	    		.removeClass("active")
	    		.hide()
	    		.next()
	    		.show()
	    		.addClass("active");
	    	
	    	activeStepWizard
		    	.removeClass("btn-success").addClass('btn-default')
		    	.parent()
				.next()
				.children().first()
				.removeClass('btn-default').addClass("btn-success")
    	};
    	
    	
        var curStep = $(this).closest(".setup-content"),
            curStepBtn = curStep.attr("id"),
            nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
            curInputs = curStep.find("input[type='text'],input[type='url']"),
            isValid = true;

        $(".form-group").removeClass("has-error");
        for (var i = 0; i < curInputs.length; i++) {
            if (!curInputs[i].validity.valid) {
                isValid = false;
                $(curInputs[i]).closest(".form-group").addClass("has-error");
            }
        }
        
        if (asset_type.value == "0") {
            //If the "Please Select" option is selected display error.
             Swal.fire(
					    'fail!',
					    'Please Select An Asset Type !!',
					    'error'
					  )
            return false;
        }

        if (isValid) doNext();
    });

    allPrevBtn.click(function() {
    	let activePanel = $(".setup-content.active"),
			activeStepWizard = $('.stepwizard-step a.btn-success');
		
	    	activePanel
	    		.removeClass("active")
	    		.hide()
	    		.prev()
	    		.show()
	    		.addClass("active");
	    	
	    	activeStepWizard
		    	.removeClass("btn-success").addClass('btn-default')
		    	.parent()
				.prev()
				.children().first()
				.removeClass('btn-default').addClass("btn-success")
    });
    
	$("#emp").change(function() {
	   let id = document.getElementById("emp").value;
 	   $.get("AssetController?action=getEmployeeDetails&id="+ id,
		function(data,status) {
		
		data.forEach(function(emp,key) {
			
           $("input#first_name").val(emp.first_name);
           $("input#last_name").val(emp.last_name);      
           $("input#email").val(emp.email);
           $("input#mobile_no").val(emp.mobile_no);
           $("input#department").val(emp.department);
           
         })}).fail(function(jqXHR,textStatus,errorThrown) {
			console.log('error');
			console.log(jqXHR);
			console.log(textStatus);
			console.log(errorThrown);
		 });
	  });
});

$("#make").change(function() {
	let make_id = $(this).val();
	
	if(make_id == "") {
		$("select[name='model']").prop("disable", true);
		return;
	}
	
	let jsonFormData = {
			  action: "get-models-by-make",
			  make_id: make_id
	}
	
	$.ajax({
		'url':"AssetController",
		"data": jsonFormData,
		'method': "GET",
		'contentType': 'application/json'
	}).done(function(data) {
	    populateModel(data);
	})
	
	$("select[name='model']").prop("disabled", false);
});

function populateModel(models) {
	let insertString = "<option value=''>--Select Model--</option>";
	
	$.each(models, function(key, value) {
		insertString += "<option value='"+value.id+"'>"+value.model+"</option>";
	});
	
	$("select[name='model']").empty().append(insertString);
}

$(document).on("submit","#add-assets", function(e) {
	e.preventDefault();
	let form = $("#add-assets").serialize();
	
	$.ajax({
		type : "POST",
		url : "AssetController?"+form,
	    success : function(data) {
	    	console.log("data-------->",data);
	    	if(data == 'true') {
	         	Swal.fire(
	       			    'Done!',
	       			    'Assets Details Added Successfully!!',
	       			    'success'
					  ).then((val) => {
						  if(val) {
							  location.reload(true);
						  }
					  })
	    	}else{
	    		 Swal.fire(
						    'fail!',
						    'Something Went Wrong!!',
						    'error'
						  )
	    	}
	    },
	    error : function(){
	    	alert("ajax error");
	    }
	});
	return false
});

</script>