<%@include file="../includes/header.jsp"%>
<%@include file="../includes/nav-header.jsp"%>

<%@ page import="com.beans.Shipment"%>

<jsp:useBean id="dashboardDAO" class="com.dao.DashboardDAO"></jsp:useBean>

<%
	String sessionShipment = null;

	ArrayList<Shipment> shipments = dashboardDAO.getShipment();

	String shipmentIdNo = null;

	if (request.getParameter("shipmentId") == null) {
		shipmentIdNo = shipments.get(0).getShipment();
	} else {
		shipmentIdNo = request.getParameter("shipmentId");
	}

	Map<String, Integer> requests = new HashMap<String, Integer>();
	requests.put("totalCardboxes", dashboardDAO.getCardboxesCountsByShipment(shipmentIdNo));
	requests.put("newCardboxes", dashboardDAO.getNewboxesCountsByShipment(shipmentIdNo));
	requests.put("pendingCardboxes", dashboardDAO.getPendingboxesCountsByShipment(shipmentIdNo));
	requests.put("deliveredCardboxes", dashboardDAO.getDeliveredboxesCountsByShipment(shipmentIdNo));
	
	Map<String, String> cardBoxCountBySchool = dashboardDAO.getdeliveredBoxCountbySchool(shipmentIdNo);
	
	Map<String, Integer> summary = new HashMap<String, Integer>();
	summary.put("newCards", dashboardDAO.getNewCardsCount(shipmentIdNo));
	summary.put("returnedCards", dashboardDAO.getReturnedCardsCount(shipmentIdNo));
	summary.put("damagedCards", dashboardDAO.getDamagedCardsCount(shipmentIdNo));
	summary.put("deliveredCards", dashboardDAO.getDeliveredCardsCount(shipmentIdNo));
	summary.put("readyCards", dashboardDAO.getReadyCardsCount(shipmentIdNo));
	
	
%>

<c:set var="shipments" value="<%=shipments%>" />
<c:set var="requests" value="<%=requests%>" />
<c:set var="summary" value="<%=summary%>" />
<c:set var="shipmentIdNo" value="<%=shipmentIdNo%>"></c:set>
<c:set var="cardBoxCountBySchool" value="<%=cardBoxCountBySchool%>"></c:set>

<!-- partial -->
<div class="main-panel">
	<div class="content-wrapper" style="padding: 30px;">
	
		<div class="row">
			<div class="col-md-12 grid-margin">
				<div class="d-flex justify-content-between align-items-center">
					<div>
						<h4 class="font-weight-bold mb-0">Dashboard</h4>
					</div>
				</div>
			</div>
		</div>

 		<div class="row">
			<div class="col-md-12 grid-margin stretch-card">
				<div class="ds-card position-relative">
					<p class="card-title">Select Shipment</p>
					<select id="shipment" name="shipment" class="form-control form-control-sm select-shipment" onchange="changeShipment();">
						<c:forEach var="shipments" items="${shipments}">
							<option value="${shipments.getCarton_id()}"
						    	<c:if test="${shipments.getShipment() eq shipmentIdNo}" >selected</c:if>>${shipments.getShipment()}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-md-12 grid-margin stretch-card">
				<div class="ds-card position-relative">
					<div class="card-body">
						<div class="row">
							<div class="col-md-6 mt-3 col-xl-5">
								<p class="card-title">Applicant Card Box Delivered To School</p>
								<div id="deliveredCardBySc"></div>
							</div>
							<div class="col-md-6 col-xl-7">
								<p class="card-title">Applicant Card Box Report</p>
								<div class="table-responsive mb-3 mb-md-0">
									<div id="applicantCardReportChart"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

 	   <div class="row">
			<div class="col-md-12 grid-margin stretch-card">
				<div class="ds-card position-relative">
					<div class="card-body">
						<div class="row">
							<div class="col-md-12">
								<p class="card-title">Cards Summary</p>
								<div id="summary"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
	<!-- content-wrapper ends -->
</div>
<!-- main-panel ends -->

<%@include file="../includes/footer.jsp"%>


<!--Chart Resources -->
<script src="https://www.amcharts.com/lib/4/core.js"></script>
<script src="https://www.amcharts.com/lib/4/charts.js"></script>
<script src="https://www.amcharts.com/lib/4/themes/dataviz.js"></script>
<script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>

<c:set var="cardsInBox" value="200"></c:set>
<!-- Chart code -->
<script>
	var noOfCardsnBox=200;
	
	am4core.ready(function() {

				// Themes begin
				/* am4core.useTheme(am4themes_dataviz); */
				am4core.useTheme(am4themes_animated);
				// Themes end
				
				
				// Create doughnut chart for delivered card by school
				var chart = am4core.create("deliveredCardBySc", am4charts.PieChart);
				
				// Add data
				
				let schoolList = '${cardBoxCountBySchool.get("schoolList")}'; 
				let schoolListArray = schoolList.substring(1,schoolList.length-1).split(","); 
                let boxCountList = ${cardBoxCountBySchool.get("countList")};
                
                console.log("------------schoolList-----------",schoolListArray);
                console.log("------------countList------------",boxCountList);
				
                let displayData = [];
                
                for(let count = 0; count < schoolListArray.length; count++) {
                	let obj = {
                		school: schoolListArray[count],
                		count: boxCountList[count]
                	}
                	
                	displayData.push(obj);
                }
                
                console.log("Display data --> ", displayData);
                
                chart.data = displayData;
				
				
				// Set inner radius
				chart.innerRadius = am4core.percent(50);
				
				// Add and configure Series
				var pieSeries = chart.series.push(new am4charts.PieSeries());
				pieSeries.dataFields.value = "count";
				pieSeries.dataFields.category = "school";
				pieSeries.slices.template.stroke = am4core.color("#fff");
				pieSeries.slices.template.strokeWidth = 2;
				pieSeries.slices.template.strokeOpacity = 1;
				
				// This creates initial animation
				pieSeries.hiddenState.properties.opacity = 1;
				pieSeries.hiddenState.properties.endAngle = -90;
				pieSeries.hiddenState.properties.startAngle = -90;

				chart.legend = new am4charts.Legend();
				chart.legend.position = "bottom";
				chart.legend.itemContainers.template.togglable = false;
				chart.legend.labels.template.truncate = true;
				chart.legend.labels.template.text = "{category}:";	
				chart.legend.valueLabels.template.text ="Boxs={value} ( {value.percent.formatNumber('#.0')}% )"; // "Boxs={value} ( Cards =({value})*200 )" {value.percent.formatNumber('#.0')}%
				chart.legend.itemContainers.template.events.on("hit", function(ev) {
				  var slice = ev.target.dataItem.dataContext.slice;
				  slice.isActive = !slice.isActive;
				})
				
				
				// Create Pie chart for Card Box report
				var aplicantCardReportChart = am4core.create("applicantCardReportChart", am4charts.PieChart);

				// Add data
				aplicantCardReportChart.data = [ {
					"name" : "Available Card Boxes",
					"value" : ${requests.get("newCardboxes")}
				}, {
					"name" : "Ready To Deliver Boxes",
					"value" : ${requests.get("pendingCardboxes")},
				}, {
					"name" : "Delivered Boxes",
					"value" : ${requests.get("deliveredCardboxes")}
				} ];

				// Add and configure Series
				var pieSeries = aplicantCardReportChart.series.push(new am4charts.PieSeries());
				pieSeries.dataFields.value = "value";
				pieSeries.dataFields.category = "name";
				pieSeries.slices.template.stroke = am4core.color("#fff");
				pieSeries.slices.template.strokeWidth = 2;
				pieSeries.slices.template.strokeOpacity = 1;

				// This creates initial animation
				pieSeries.hiddenState.properties.opacity = 1;
				pieSeries.hiddenState.properties.endAngle = -90;
				pieSeries.hiddenState.properties.startAngle = -90;
				
				aplicantCardReportChart.legend = new am4charts.Legend();
				aplicantCardReportChart.legend.position = "bottom";
				aplicantCardReportChart.legend.itemContainers.template.togglable = false;
				aplicantCardReportChart.legend.labels.template.text = "{category}:";
				aplicantCardReportChart.legend.valueLabels.template.text = "{value} ({value.percent.formatNumber('#.0')}%)";
				aplicantCardReportChart.legend.itemContainers.template.events.on("hit", function(ev) {
				  var slice = ev.target.dataItem.dataContext.slice;
				  slice.isActive = !slice.isActive;
				})
				
			
				
				
				// Create Doughnut chart for Card Box Summary
				var data = [{
				    "status": "Dummy",
				    "disabled": true,
				    "count": 1000,
				    "color": am4core.color("#dadada"),
				    "opacity": 0.3,
				    "strokeDasharray": "4,4"
				}, {
				    "status": "Available Cards",
				    "count": ${summary.get("newCards")}
				}, {
				    "status": "Ready To Deliver Cards",
				    "count": ${summary.get("readyCards")}
				}, {
				    "status": "Delivered Cards",
				    "count": ${summary.get("deliveredCards")}
				}, {
				    "status": "Damaged Cards",
				    "count": ${summary.get("damagedCards")}
				}, {
				    "status": "Returned Cards",
				    "count": ${summary.get("returnedCards")}
				}];


				// cointainer to hold both charts
				var container = am4core.create("summary", am4core.Container);
				container.width = am4core.percent(100);
				container.height = am4core.percent(100);
				container.layout = "horizontal";

				container.events.on("maxsizechanged", function () {
				    chart1.zIndex = 0;
				    separatorLine.zIndex = 1;
				    dragText.zIndex = 2;
				    chart2.zIndex = 3;
				})

				var chart1 = container.createChild(am4charts.PieChart);
				chart1 .fontSize = 12;
				chart1.hiddenState.properties.opacity = 0; // this makes initial fade in effect
				chart1.data = data;
				chart1.radius = am4core.percent(70);
				chart1.innerRadius = am4core.percent(40);
				chart1.zIndex = 1;

				var series1 = chart1.series.push(new am4charts.PieSeries());
				series1.dataFields.value = "count";
				series1.dataFields.category = "status";
				series1.colors.step = 2;
				series1.alignLabels = false;
				series1.labels.template.bent = true;
				series1.labels.template.radius = 3;
				series1.labels.template.padding(0,0,0,0);
				
				chart1.legend = new am4charts.Legend();
				chart1.legend.position = "bottom";
				chart1.legend.itemContainers.template.togglable = true;
				chart1.legend.labels.template.text = "{category}:";
				chart1.legend.valueLabels.template.text = "{value} ({value.percent.formatNumber('#.0')}%)";
				chart1.legend.itemContainers.template.events.on("hit", function(ev) {
				  var slice = ev.target.dataItem.dataContext.slice;
				  slice.isActive = !slice.isActive;
				})

				var sliceTemplate1 = series1.slices.template;
				sliceTemplate1.cornerRadius = 5;
				sliceTemplate1.draggable = true;
				sliceTemplate1.inert = true;
				sliceTemplate1.propertyFields.fill = "color";
				sliceTemplate1.propertyFields.fillOpacity = "opacity";
				sliceTemplate1.propertyFields.stroke = "color";
				sliceTemplate1.propertyFields.strokeDasharray = "strokeDasharray";
				sliceTemplate1.strokeWidth = 1;
				sliceTemplate1.strokeOpacity = 1;

				var zIndex = 5;

				sliceTemplate1.events.on("down", function (event) {
				    event.target.toFront();
				    // also put chart to front
				    var series = event.target.dataItem.component;
				    series.chart.zIndex = zIndex++;
				})

				series1.ticks.template.disabled = true;

				sliceTemplate1.states.getKey("active").properties.shiftRadius = 0;

				sliceTemplate1.events.on("dragstop", function (event) {
				    handleDragStop(event);
				})

				// separator line and text
				var separatorLine = container.createChild(am4core.Line);
				separatorLine.x1 = 0;
				separatorLine.y2 = 300;
				separatorLine.strokeWidth = 3;
				separatorLine.stroke = am4core.color("#dadada");
				separatorLine.valign = "middle";
				separatorLine.strokeDasharray = "5,5";


				var dragText = container.createChild(am4core.Label);
				dragText.text = "Drag slices over the line";
				dragText.rotation = 90;
				dragText.valign = "middle";
				dragText.align = "center";
				dragText.paddingBottom = 5;

				// second chart
				var chart2 = container.createChild(am4charts.PieChart);
				chart2.hiddenState.properties.opacity = 0; // this makes initial fade in effect
				chart2 .fontSize = 11;
				chart2.radius = am4core.percent(70);
				chart2.data = data;
				chart2.innerRadius = am4core.percent(40);
				chart2.zIndex = 1;
				
				
				chart2.legend = new am4charts.Legend();
				chart2.legend.position = "bottom";
				chart2.legend.itemContainers.template.togglable = true;
				chart2.legend.labels.template.text = "{category}:";
				chart2.legend.valueLabels.template.text = "{value} ({value.percent.formatNumber('#.0')}%)";
				chart2.legend.itemContainers.template.events.on("hit", function(ev) {
				  var slice = ev.target.dataItem.dataContext.slice;
				  slice.isActive = !slice.isActive;
				})

				var series2 = chart2.series.push(new am4charts.PieSeries());
				series2.dataFields.value = "count";
				series2.dataFields.category = "status";
				series2.colors.step = 2;

				series2.alignLabels = false;
				series2.labels.template.bent = true;
				series2.labels.template.radius = 3;
				series2.labels.template.padding(0,0,0,0);
				series2.labels.template.propertyFields.disabled = "disabled";

				var sliceTemplate2 = series2.slices.template;
				sliceTemplate2.copyFrom(sliceTemplate1);

				series2.ticks.template.disabled = true;

				function handleDragStop(event) {
				    var targetSlice = event.target;
				    var dataItem1;
				    var dataItem2;
				    var slice1;
				    var slice2;

				    if (series1.slices.indexOf(targetSlice) != -1) {
				        slice1 = targetSlice;
				        slice2 = series2.dataItems.getIndex(targetSlice.dataItem.index).slice;
				    }
				    else if (series2.slices.indexOf(targetSlice) != -1) {
				        slice1 = series1.dataItems.getIndex(targetSlice.dataItem.index).slice;
				        slice2 = targetSlice;
				    }


				    dataItem1 = slice1.dataItem;
				    dataItem2 = slice2.dataItem;

				    var series1Center = am4core.utils.spritePointToSvg({ x: 0, y: 0 }, series1.slicesContainer);
				    var series2Center = am4core.utils.spritePointToSvg({ x: 0, y: 0 }, series2.slicesContainer);

				    var series1CenterConverted = am4core.utils.svgPointToSprite(series1Center, series2.slicesContainer);
				    var series2CenterConverted = am4core.utils.svgPointToSprite(series2Center, series1.slicesContainer);

				    // tooltipY and tooltipY are in the middle of the slice, so we use them to avoid extra calculations
				    var targetSlicePoint = am4core.utils.spritePointToSvg({ x: targetSlice.tooltipX, y: targetSlice.tooltipY }, targetSlice);

				    if (targetSlice == slice1) {
				        if (targetSlicePoint.x > container.pixelWidth / 2) {
				            var value = dataItem1.value;

				            dataItem1.hide();

				            var animation = slice1.animate([{ property: "x", to: series2CenterConverted.x }, { property: "y", to: series2CenterConverted.y }], 400);
				            animation.events.on("animationprogress", function (event) {
				                slice1.hideTooltip();
				            })

				            slice2.x = 0;
				            slice2.y = 0;

				            dataItem2.show();
				        }
				        else {
				            slice1.animate([{ property: "x", to: 0 }, { property: "y", to: 0 }], 400);
				        }
				    }
				    if (targetSlice == slice2) {
				        if (targetSlicePoint.x < container.pixelWidth / 2) {

				            var value = dataItem2.value;

				            dataItem2.hide();

				            var animation = slice2.animate([{ property: "x", to: series1CenterConverted.x }, { property: "y", to: series1CenterConverted.y }], 400);
				            animation.events.on("animationprogress", function (event) {
				                slice2.hideTooltip();
				            })

				            slice1.x = 0;
				            slice1.y = 0;
				            dataItem1.show();
				        }
				        else {
				            slice2.animate([{ property: "x", to: 0 }, { property: "y", to: 0 }], 400);
				        }
				    }

				    toggleDummySlice(series1);
				    toggleDummySlice(series2);

				    series1.hideTooltip();
				    series2.hideTooltip();
				}

				function toggleDummySlice(series) {
				    var show = true;
				    for (var i = 1; i < series.dataItems.length; i++) {
				        var dataItem = series.dataItems.getIndex(i);
				        if (dataItem.slice.visible && !dataItem.slice.isHiding) {
				            show = false;
				        }
				    }

				    var dummySlice = series.dataItems.getIndex(0);
				    if (show) {
				        dummySlice.show();
				    }
				    else {
				        dummySlice.hide();
				    }
				}

				series2.events.on("datavalidated", function () {

				    var dummyDataItem = series2.dataItems.getIndex(0);
				    dummyDataItem.show(0);
				    dummyDataItem.slice.draggable = true;
				    dummyDataItem.slice.tooltipText = undefined;

				    for (var i = 1; i < series2.dataItems.length; i++) {
				        series2.dataItems.getIndex(i).hide(0);
				    }
				})

				series1.events.on("datavalidated", function () {
				    var dummyDataItem = series1.dataItems.getIndex(0);
				    dummyDataItem.hide(0);
				    dummyDataItem.slice.draggable = true;
				    dummyDataItem.slice.tooltipText = undefined;
				})


			}); 
	// end am4core.ready()
			
			
</script>
<script>
"use strict";

function changeShipment() {
	
    var selectedShipment = $('#shipment :selected').text();
    $("#selectedShipmentHidden").val(selectedShipment);
    
    var seletectedCarton_id = $('#shipment :selected').val();
    $("#selectedCartonIdHidden").val(seletectedCarton_id);
    
    <% 
    session.setAttribute("shipment", shipmentIdNo);%>
    window.location = "dashboard?shipmentId="+selectedShipment;
}
</script>