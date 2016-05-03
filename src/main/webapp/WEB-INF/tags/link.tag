<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="target" required="true" %> 
<%@ attribute name="page" required="true" %> 
<%@ attribute name="offset" required="true" %>
<%@ attribute name="search" required="false" %>
<%@ attribute name="order" required="false"%>
<%@ attribute name="dir" required="false"%>
href="${target}?page=${page}&offset=${offset}&search=${search}&order=${order}&dir=${dir}"