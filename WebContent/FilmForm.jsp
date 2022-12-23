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
        	<a href="list">List All Films</a>
        	
        </h2>
	</center>
    <div align="center">
		<c:if test="${film != null}">
			<form action="update" method="post">
        </c:if>
        <c:if test="${film == null}">
			<form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
            	<h2>
            		<c:if test="${film != null}">
            			Edit Film
            		</c:if>
            		<c:if test="${film == null}">
            			Add New Film
            		</c:if>
            	</h2>
            </caption>
        		<c:if test="${film != null}">
        			<input type="hidden" name="id" value="<c:out value='${film.id}' />" />
        		</c:if>            
            <tr>
                <th>Title: </th>
                <td>
                	<input type="text" name="title" size="45"
                			value="<c:out value='${film.title}' />"
                		/>
                </td>
            </tr>
            <tr>
                <th>Year: </th>
                <td>
                	<input type="text" name="year" size="45"
                			value="<c:out value='${film.year}' />"
                	/>
                </td>
            </tr>
            <tr>
                <th>Director: </th>
                <td>
                	<input type="text" name="director" size="45"
                			value="<c:out value='${film.director}' />"
                	/>
                </td>
            </tr>
            <tr>
                            <th>Stars: </th>
                            <td>
                            	<input type="text" name="stars" size="45"
                            			value="<c:out value='${film.stars}' />"
                            	/>
                            </td>
                        </tr>
                        <tr>
                                        <th>Review: </th>
                                        <td>
                                        	<input type="text" name="review" size="45"
                                        			value="<c:out value='${film.review}' />"
                                        	/>
                                        </td>
                                    </tr>
            <tr>
            	<td colspan="2" align="center">
            		<input type="submit" value="Save" />
            	</td>
            </tr>
        </table>
        </form>
    </div>	
</body>
</html>
