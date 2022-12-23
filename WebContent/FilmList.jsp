<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Film Application</title>
</head>
<body>
	<center>
		<h1>Film Management</h1>
        <h2>
        	<a href="new">Add New Film</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="list">List All Film</a>
        </h2>
	</center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Films</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Year</th>
                <th>Director</th>
                <th>Star</th>
                <th>Review</th>
            </tr>
            <c:forEach var="film" items="${listFilm}">
                <tr>
                    <td><c:out value="${film.id}" /></td>
                    <td><c:out value="${film.title}" /></td>
                    <td><c:out value="${film.year}" /></td>
                    <td><c:out value="${film.director}" /></td>
                    <td><c:out value="${film.stars}" /></td>
                    <td><c:out value="${film.review}" /></td>
                    <td>
                    	<a href="edit?id=<c:out value='${film.id}' />">Edit</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="delete?id=<c:out value='${film.id}' />">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
