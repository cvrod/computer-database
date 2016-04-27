<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="index" required="true" %>
<%@ attribute name="offset" required="true" %>

<ul class="pagination">
				<li><a href="?index=${index - offset}" aria-label="Previous"> <span aria-hidden="true">&laquo;</span></a></li>
				<li><a href="?index=0">1</a></li>
				<li><a href="?index=${offset}">2</a></li>
				<li><a href="?index=${offset*2}">3</a></li>
				<li><a href="?index=${offset*3}">4</a></li>
				<li><a href="?index=${offset*4}">5</a></li>
				<li><a href="?index=${index + offset}" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
</ul>
<div class="btn-group btn-group-sm pull-right" role="group">
	<a href="?index=${index}&offset=10"><button type="button" class="btn btn-default">10</button></a>
	<a href="?index=${index}&offset=50"><button type="button" class="btn btn-default">50</button></a>
	<a href="?index=${index}&offset=100"><button type="button" class="btn btn-default">100</button></a>
</div>