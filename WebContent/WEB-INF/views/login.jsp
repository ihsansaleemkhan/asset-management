<%@include file="../includes/header.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page language="java" import="java.util.List"%>
<%@ page import="com.beans.Role"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="context" value="${pageContext.request.contextPath}" />

<jsp:useBean id="roleDao" class="com.dao.RoleDao" />

<%
	List<Role> roles = roleDao.getRole();
%>

<style>
@import url('https://fonts.googleapis.com/css?family=Numans');

html, body {
	background-image: url("./images/login-bg.jpeg");
	background-size: cover;
	background-repeat: no-repeat;
	height: 100%;
	font-family: 'Numans', sans-serif;
}

.container {
	height: 100%;
	align-content: center;
}

.lg-card {
	height: 370px;
	margin-top: auto;
	margin-bottom: auto;
	width: 400px;
	background-color: rgba(0, 0, 0, 0.5) !important;
}

.social_icon span {
	font-size: 60px;
	margin-left: 10px;
	color: #FFC312;
}

.social_icon span:hover {
	color: white;
	cursor: pointer;
}

.card-header h3 {
	color: white;
}

.social_icon {
	position: absolute;
	right: 20px;
	top: -45px;
}

.input-group-prepend span {
	width: 50px;
	background-color: #2288cc;
	color: black;
	border: 0 !important;
}

input:focus {
	outline: 0 0 0 0 !important;
	box-shadow: 0 0 0 0 !important;
}

.remember {
	color: white;
}

.remember input {
	width: 20px;
	height: 20px;
	margin-left: 15px;
	margin-right: 5px;
}

.login_btn {
	color: black;
	background-color: #2288cc;
	width: 100px;
}

.login_btn:hover {
	color: black;
	background-color: white;
}

.links {
	color: white;
}

.links a {
	margin-left: 4px;
}

.alert {
	color: red;
	border-radius: 5px;
 /*    background-color: #60a3bc; */
    text-align: center;
}

.brand_logo_container {
	position: absolute;
	/* height: 170px; */
	/* width: 200px; */
	top: 218px;
	border-radius: 50%;
	background: #60a3bc;
	padding: 5px;
	text-align: center;
}

.brand_logo {
	/* height: 150px; */
	/* width: 180px; */
	border-radius: 50%;
	border: 2px solid white;
}

.lg-card .card-body {
    padding: 7.25rem 1.437rem;
}
</style>

<body>
	<div class="container">
		<div class="d-flex justify-content-center h-100">
			<div class="lg-card">
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="images/holoteq-logo.png" class="brand_logo" alt="Logo">
					</div>
				</div>
				<div class="card-body">
					<form action="${context}/loginCont" method="POST">
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fas fa-user"></i></span>
							</div>
							<input type="text" class="form-control" name="username"
								placeholder="username">

						</div>
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fas fa-key"></i></span>
							</div>
							<input type="password" name="password" class="form-control"
								placeholder="password">
						</div>
						<div class="input-group form-group">
							<div class="input-group-prepend">
								<span class="input-group-text"><i class="fas fa-user-tag"></i></span>
							</div>
							<select class="form-control" id="role" name="userRole" required>
								<option value="">--Select Role--</option>
								<%
									for (Role role : roles) {
								%>
								<option value=<%=role.getRoleId()%>><%=role.getRole()%></option>
								<%
									}
								%>
							</select>
						</div>
						<div class="form-group">
							<input type="submit" value="Login"
								class="btn float-right login_btn">
						</div>
					</form>
				</div>
				<div class="card-footer">
					<h4 class="alert">${msg}</h4>
				</div>
			</div>
		</div>
	</div>
</body>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
	document.title = "Login - DTS Card Management
	";
	//hide alerts after 5 seconds
	setTimeout(function() {
		console.log('fadeout');
		$("h4.alert").fadeOut();

	}, 5000);
</script>

