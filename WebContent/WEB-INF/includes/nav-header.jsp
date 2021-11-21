<%@page import="com.beans.User"%>
<%
	User user = (User)session.getAttribute("user");
// 	String username = (String) session.getAttribute("username");
// 	int userrole = 0;
	if (user == null) {
		response.sendRedirect("login");
		return;
	}
// 	else {
// // 		userrole = (int) session.getAttribute("userrole");
// // 		user = (User)session.getAttribute("user");
// 	} ;
%>

<body>
	<nav class="navbar navbar-expand-md navbar-light bg-light-blue">
		<a href="#" class="navbar-brand"> <img src="images/logo.png"
			height="28" alt="DTS Card Management">
		</a>
		<button type="button" class="navbar-toggler" data-toggle="collapse"
			data-target="#navbarCollapse">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarCollapse">
			<div class="navbar-nav">
				<h4>Asset Management</h4>
				<li class="nav-item">
					<div class="nav-link">
						<div class="nav-header-user-image">
							<img src="${context}/images/no_image_user.png" data-toggle="collapse" data-target="#userAction" class="collapsed">
						</div>
					</div>
					<ul class="user-action collapse second-nav" id="userAction">
						<% if (user.isHaveAddEmployeeAccess()) { %>
							<li><a class="link" href="manage-employee"><i class="fas fa-user-tie"></i>&nbsp; Add Employee</a></li>
						<% } %>
						
					    <% if (user.isHaveAddAssetAccess()) { %>
							<li><a class="link" href="add-assets"><i class="fas fa-calendar-plus"></i>&nbsp; Add Assets</a></li>
						<% } %>
						
						<% if (user.isHaveManageUsersAccess()) { %>
							<li><a class="link" href="manage-users"><i class="fas fa-users-cog"></i>&nbsp; Manage Users</a></li>
						<% } %>
						<% if (user.isHaveManageDeliveryPersonAccess()) { %>
							<li><a class="link" href="manage-delivery-person"><i class="fas fa-people-carry"></i>&nbsp; Delivery Person</a></li>
					    <% } %>
						<li><a href="logout"><i class="fas fa-sign-out-alt"></i>&nbsp; Logout</a></li>
					</ul>
				</li> <span class="user-name"><%=user.getUser_name()%></span>
			</div>
			<div class="navbar-nav ml-auto">
				<ul class="nav main-nav" style="margin: 0px 100px;">
				
					<% if (user.isHaveDashBoardAccess()) { %>
							<li class="link-item"><a class="link" href="dashboard">Dashboard</a></li>
					<% } %>
					
					<% if (user.isHaveMaterialAccess()) { %>
						<li class="link-item"><a>Material</a>
							<ul class="second-nav">
								<li><a>Printer Ink</a>
									<ul class="third-nav">
										<li><a class="link" href="request-order">Request Order</a></li>
										<li><a class="link" href="add-printer-ink">Stock</a></li>
										<li><a class="link" href="deliver-ink">Dispatch</a></li>
									</ul></li>
								<li><a>Printer Ribbon</a>
									<ul class="third-nav">
										<li><a class="link" href="request-ribbon">Request Order</a></li>
										<li><a class="link" href="ribbon-stock">Stock</a></li>
										<li><a class="link" href="ribbon-dispach">Dispatch</a></li>
									</ul></li>
							<!-- 	<li><a>Paper Bundle</a>
									<ul class="third-nav">
										<li><a class="link">Request Order</a></li>
										<li><a class="link">Stock</a></li>
										<li><a class="link">Dispatch</a></li>
									</ul></li> -->
							</ul>
						</li>
					<% } %>
					
					<% if (user.isHaveDtsCardAccess()) { %>
						<li class="link-item"><a>DTS Card</a>
							<ul class="second-nav">
								<li><a>Add</a>
									<ul class="third-nav">
										<li><a class="link" href="add-carton-box">Carton Box</a></li>
										<li><a class="link" href="add-returned-card">Returned Card</a></li>
									</ul>
								</li>
								<li><a class="link" href="carton-box">Card Boxes</a></li>
								<li><a>Card Tracking</a>
								   <ul class="third-nav">
								      <li><a class="link" href="cardTracking">Deliver</a></li>
								      <li><a class="link" href="card-delivery-report">Delivered Report</a></li>
								   </ul>
								</li>
								<li><a class="link" href="records">Summary</a></li>
								<li><a class="link" href="print-stock-details">Report</a></li>
							</ul>
						</li>
					<% } %>
					
					<% if (user.isHaveVehicleAccess()) { %>
						<li class="link-item"><a>Vehicle</a>
							<ul class="second-nav">
								<li><a class="link" href="add-vehicle">Add</a></li>
								<li><a class="link" href="vehicle-list">View</a></li>
							    <li><a>Services</a>
									<ul class="third-nav">
										<li><a class="link" href="add-services">Add</a></li>
										<li><a class="link" href="vehicle-services">View</a></li>
									</ul>
								</li>
								<li class="link-item"><a>Issues</a>
								   <ul class="third-nav">
								      <li><a class="link" href="enter-vehicle-issues">Add</a></li>
								      <li><a class="link" href="vehicle-issues">View</a></li>
								   </ul>
								</li>
							</ul>
						</li>
					<% } %>
					
					<% if (user.isHaveHardwareAccess()) { %>
						<li class="link-item"><a>Hardwares</a>
							<ul class="second-nav">
								<li><a class="link" href="servers">Server</a></li>
								<li><a class="link" href="mobile-phones">Mobile Phones</a>
								<li><a class="link" href="laptops">Laptop</a></li>
								<li><a class="link" href="desktops">Desktop</a></li>
								<li><a class="link" href="monitors">Monitor</a></li>
								<li><a class="link" href="printers">Printer</a></li>
								<li><a class="link" href="ups-list">UPS</a></li>
								<li><a class="link" href="tab-list">Tab</a></li>
								<li><a class="link" href="power-banks">Power Bank</a></li>
								<li><a class="link" href="asset-tracking">Tracking</a></li>
							</ul>
						</li>
					<% } %>

				</ul>
		       <!-- <div class="form-inline" style="margin: 0 2px 0 15px;">
					<input type="text" placeholder="Search.." name="search" id="search"
						class="form-control input-search">
					<button type="submit" id="searchButton" onclick='javascript: window.open("search?search="+ document.getElementById("search").value);'><i class="fa fa-search"></i></button>
					<button class="btn btn-info btn-search" type="submit"
						id="searchButton">
						<i class="fa fa-search"></i>
					</button>
				</div> -->
			</div>
		</div>
	</nav>
	<script type="text/javascript">
		$("#searchButton").click(
				function() {
					let searchCardBoxNo = $("input[name=search]").val();
					window.location = "search?search="
							+ document.getElementById("search").value;

				})

		$("#search").keyup(function(event) {
			if (event.keyCode === 13) {
				$("#searchButton").click();
			}
		});

		jQuery(function($) {
			let path = window.location.pathname; // because the 'href' property of the DOM element is the absolute path
			$('.navbar-nav .nav .link').each(
					function() {

						if (this.href.indexOf(path) >= 0) {

							$(this).parents(".main-nav li.link-item").addClass(
									'active');
							$(this).parents(".second-nav li").css("border",
									"1px solid #17a2b8");
							$(this).parents(".third-nav li").css("border",
									"1px solid #17a2b8");

						}
					});

		});
		
	</script>