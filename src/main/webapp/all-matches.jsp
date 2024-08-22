<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>All Matches</title>
</head>
<body>
<h1>All Matches</h1>
<table border="1">
    <thead>
    <tr>
        <th>Player 1</th>
        <th>Player 2</th>
        <th>Winner</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="match" items="${matches}">
        <tr>
            <td>${match.player1.name}</td>
            <td>${match.player2.name}</td>
            <td>${match.winner.name}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<a href="${pageContext.request.contextPath}/index.jsp">Back to Home</a>--%>
</body>
</html>
