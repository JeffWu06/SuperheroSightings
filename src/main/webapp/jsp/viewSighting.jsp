<%-- 
    Document   : viewSighting
    Created on : May 18, 2020, 1:56:40 PM
    Author     : Jeff
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>H.E.R.O. Superhero Sightings</title>
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/detailpages.css" rel="stylesheet"> 
    </head>
    <body>
        <div class="container">
            <h2>
                View Sighting
            </h2>
            <hr/>
            <table>
                <tr>
                    <td>Date:</td>
                    <td>${sighting.sightingDate}</td>
                </tr>                
                <tr>
                    <td>Location:</td>
                    <td>${sighting.location.locationName}</td>
                </tr>
                <tr>
                    <td>Heroes spotted:</td>
                    <td>
                        <c:forEach var="hero" items="${sighting.heroes}">
                            <c:out value="${hero.alterEgo}"><br></c:out>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <div class="col-md-offset-1">
                <input type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/displaySightingsPage'" value="Back"/>
            </div> 
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
