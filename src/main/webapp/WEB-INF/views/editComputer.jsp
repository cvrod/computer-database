<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
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
				href="${pageContext.request.contextPath}/computer?page=0">
				Application - <spring:message code="header.title"
					text="Computer Database" />
			</a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">
						id:
						<c:out value="${id}" />
					</div>
					<h1>
						<spring:message code="header.editComputer" text="Edit Computer" />
					</h1>

					<form id="computerForm" action="edit" method="POST">
						<input id="id" type="hidden" value="${id}" name="id" /> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" class="form-control" />
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="computer.name" text="Computer name" /></label> <input
									type="text" class="form-control" id="computerName"
									placeholder="<spring:message code="computer.name" text="Computer name" />"
									name="computerName" value="${computer.name}">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="computer.introduced" text="Introduced date" /></label> <input
									type="date" class="form-control" id="introduced"
									placeholder="<spring:message code="computer.introduced" text="Introduced date" />"
									name="introduced" value="${computer.introduced}">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="computer.discontinued" text="discontinued date" /></label> <input
									type="date" class="form-control" id="discontinued"
									placeholder="<spring:message code="computer.discontinued" text="discontinued date" />"
									name="discontinued" value="${computer.discontinued}">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message
										code="computer.company" text="Company" /></label> <select
									class="form-control" name="companyId" id="companyId">
									<option value="0">--</option>
									<c:forEach items="${companies}" var="company">
										<option value="${company.id}"
											${company.id == computer.idCompany ? 'selected' : ''}>${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="${pageContext.request.contextPath}/computer?page=0"
								class="btn btn-default">Cancel</a>
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