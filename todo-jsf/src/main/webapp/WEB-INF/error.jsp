<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">

<%@ include file="/WEB-INF/templates/head.jsp" %>

<body>

<%@ include file="/WEB-INF/templates/header.jsp" %>

<div class="container">
    <h3>Error ${pageContext.errorData.statusCode}</h3>
    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
</div>

<%@ include file="/WEB-INF/templates/footer_scripts.jsp" %>

</body>

</html>