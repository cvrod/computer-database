<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="offset" required="true"%>
<%@ attribute name="current" required="true"%>
<%@ attribute name="nbPages" required="true"%>

<ul class="pagination">
	<c:set var="indexStart" value="0" />

	<c:if test="${(current - 3) >= 0}">
		<c:set var="indexStart" value="${current - 3}" />
	</c:if>

	<c:set var="indexStop" value="${nbPages - 1}" />

	<c:if test="${(current + 3) <= (nbPages - 1)}">
		<c:set var="indexStop" value="${current + 3}" />
	</c:if>

	<c:choose>
		<c:when test="${current == 0}">
			<li class="disabled"><a aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span></a></li>
		</c:when>
		<c:otherwise>
			<li><a href="?page=${current - 1}" aria-label="Previous"> <span
					aria-hidden="true">&laquo;</span></a></li>
		</c:otherwise>
	</c:choose>

	<c:if test="${indexStart > 0}">
		<li><a href="?page=0">1</a></li>
		<li class="disabled"><a>&hellip;</a></li>
	</c:if>

	<c:forEach var="i" begin="${indexStart}" end="${indexStop}">
		<li ${current == i ? 'class="active"' : ''}><a href="?page=${i}">${i + 1}</a></li>
	</c:forEach>

	<c:if test="${indexStop < (nbPages - 1)}">
		<li class="disabled"><a>&hellip;</a></li>
		<li><a href="?page=${nbPages - 1}">${nbPages}</a></li>
	</c:if>

	<c:choose>
		<c:when test="${current == (nbPages - 1)}">
			<li class="disabled"><a aria-label="Next"> <span
					aria-hidden="true">&raquo;</span></a></li>
		</c:when>
		<c:otherwise>
			<li><a href="?page=${current + 1}" aria-label="Next"> <span
					aria-hidden="true">&raquo;</span></a></li>
		</c:otherwise>
	</c:choose>

</ul>
<div class="btn-group btn-group-sm pull-right" role="group">
	<a href="?page=0&offset=10"><button type="button"
			class="btn btn-default">10</button></a> <a
		href="?page=0&offset=50"><button type="button"
			class="btn btn-default">50</button></a> <a
		href="?page=0&offset=100"><button type="button"
			class="btn btn-default">100</button></a>
</div>