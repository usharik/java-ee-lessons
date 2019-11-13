<%@ page isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">

<body>

<div class="container">
    <h3>Error ${pageContext.errorData.statusCode}</h3>
    <p><b>Request URI:</b> ${pageContext.request.scheme}://${header.host}${pageContext.errorData.requestURI}</p>
</div>

</body>

</html>