<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="header.title" text="Computer Database" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
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
				Application - <spring:message code="header.title" text="Computer Database" /></a>
				<span class="navbar-brand navbar-right">
				<a href="${pageContext.request.contextPath}/computer?lang=fr">
					<img src="${pageContext.request.contextPath}/resources/images/france_flag.png" alt="FR" style="width:30px;"/>
				</a> / 
				<a href="${pageContext.request.contextPath}/computer?lang=en">
					<img src="${pageContext.request.contextPath}/resources/images/uk_flag.png" alt="EN" style="width:30px;"/>
				</a></span>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<span id="countComputer"><c:out value="${pageParam.countComputer}" /></span>
				<spring:message code="header.compFound" text="Computers found" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="header.search" text="Search name" />" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="header.filter" text="Filter by name" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="${pageContext.request.contextPath}/add"><spring:message code="header.add" text="Add computer" /></a> 
					<a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode('<spring:message code="header.edit" text="Edit" />','<spring:message code="header.view" text="View" />');"><spring:message code="header.edit" text="Edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm"
			action="${pageContext.request.contextPath}/delete"
			method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected('<spring:message code="confirmation.delete" text="test" />');"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a
							<tags:link target="" offset="${pageParam.offset}" 
							page="0" search="${ pageParam.search }" 
							order="name" dir="${pageParam.dir == 'asc' && pageParam.order == 'name' ? 'desc' : 'asc'}"/>>
							<spring:message code="computer.name" text="Computer name" /></a></th>
						<th><a
							<tags:link target="" offset="${pageParam.offset}" 
							page="0" search="${ pageParam.search }" 
							order="introduced" dir="${pageParam.dir == 'asc' && pageParam.order == 'introduced' ? 'desc' : 'asc'}"/>>
							<spring:message code="computer.introduced" text="Introduced date" /></a></th>
						<!-- Table header for Discontinued Date -->
						<th><a
							<tags:link target="" offset="${pageParam.offset}" 
							page="0" search="${ pageParam.search }" 
							order="discontinued" dir="${pageParam.dir == 'asc' && pageParam.order == 'discontinued' ? 'desc' : 'asc'}"/>>
							<spring:message code="computer.discontinued" text="discontinued date" /></a></th>
						<!-- Table header for Company -->
						<th><a
							<tags:link target="" offset="${pageParam.offset}" 
							page="0" search="${ pageParam.search }" 
							order="company" dir="${pageParam.dir == 'asc' && pageParam.order == 'company' ? 'desc' : 'asc'}"/>>
							<spring:message code="computer.company" text="Company" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${pageParam.computerDtoPage.elementList}" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}" id="${computer.name}_id"></td>
							<td><a
								href="edit?id=${computer.id}"
								onclick="" id="${computer.name}_name">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.nameCompany}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<tags:pagination offset="${pageParam.offset}" current="${pageParam.currentPage}"
				nbPages="${nbPages}" search="${pageParam.search}" />
		</div>
	</footer>
	<script
		src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/dashboard.js"></script>
</body>
</html>