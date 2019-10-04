<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">

<%@ include file="head.jsp"%>

<body>

<%@ include file="header.jsp"%>

<div class="container">
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/new" var="todoNewUrl"/>
            <a class="btn btn-primary" href="${todoNewUrl}">Add Todo</a>
        </div>

        <div class="col-12">
            <table class="table table-bordered my-2">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Description</th>
                    <th scope="col">Target date</th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="todo" items="${requestScope.todos}">
                <%-- for (ToDo toDo : (List<ToDo>) request.getAttribute("todos")) { --%>
                <tr>
                    <th scope="row">
                        <%--= toDo.getId() --%>
                        <c:out value="${todo.id}"/>
                    </th>
                    <td>
                        <%--= toDo.getDescription() --%>
                        <c:out value="${todo.description}"/>
                    </td>
                    <td>
                        <%--= toDo.getTargetDate() --%>
                        <c:out value="${todo.targetDate}"/>
                    </td>
                    <td>
                        <c:url value="/edit" var="todoEditUrl">
                            <c:param name="id" value="${todo.id}"/>
                        </c:url>
                        <a class="btn btn-success" href="${todoEditUrl}"><i class="fas fa-edit"></i></a>
                        <c:url value="/delete" var="todoDeleteUrl">
                            <c:param name="id" value="${todo.id}"/>
                        </c:url>
                        <a class="btn btn-danger" href="${todoDeleteUrl}"><i class="far fa-trash-alt"></i></a>
                    </td>
                </tr>
                <%-- } --%>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="footer_scripts.jsp" %>

</html>