<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath}/resources/css/font-awesome.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath}/resources/css/main.css"
	rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="${pageContext.request.contextPath}/computer">Application -
				<spring:message code="header.title" text="Computer Database" />
			</a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="header.add" text="Add computer" />
					</h1>
					<form id="computerForm" action="add" method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" class="form-control" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="computer.name" text="Computer name" /></label> <input
									type="text" class="form-control" id="computerName"
									placeholder="<spring:message code="computer.name" text="Computer name" />"
									name="computerName">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="computer.introduced" text="Introduced date" /></label> <input
									type="date" class="form-control" id="introduced"
									placeholder="<spring:message code="computer.introduced" text="Introduced date" />"
									name="introduced">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.discontinued" text="discontinued date" /></label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="<spring:message code="computer.discontinued" text="discontinued date" />"
									name="discontinued">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="computer.company" text="Company" /></label> <select
									class="form-control" id="companyId" name="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}">${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit"
								value="<spring:message code="button.add" text="ADD" />"
								class="btn btn-primary">
							<spring:message code="button.or" text="or" />
							<a href="${pageContext.request.contextPath}/computer"
								class="btn btn-default"><spring:message code="button.cancel"
									text="Cancel" /></a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<!-- <script src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script> -->
	<script
		src="${pageContext.request.contextPath}/resources/js/computerFormValidation.js"></script>
</body>
</html>