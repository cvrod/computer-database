<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@ attribute name="target" required="true" %> 
<%@ attribute name="page" required="true" %> 
<%@ attribute name="offset" required="true" %>
href="${target}?page=${page}&offset=${offset}"