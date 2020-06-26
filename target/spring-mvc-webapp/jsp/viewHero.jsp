<%-- 
    Document   : viewHero
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
                <c:out value="${superhuman.alterEgo}"></c:out>
            </h2>
            <hr/>
            <table>
                <tr>
                    <td>Hero description:</td>
                    <td>${superhuman.description}</td>
                </tr>
                <tr>
                    <td>Is this person a villain?</td>
                    <td>
                        <c:if test="${superhuman.villain}">
                            Yes
                        </c:if>
                        <c:if test="${!superhuman.villain}">
                            No
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>Superpowers possessed:</td>
                    <td>
                        <c:forEach var="power" items="${superhuman.superpowers}">
                            <c:out value="${power.superpowerDescription}"/>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td>Member of:</td>
                    <td>
                        <c:forEach var="org" items="${superhuman.organizations}">
                            <c:out value="${org.organizationName}"/>
                            <br>
                        </c:forEach>
                    </td>
                </tr>
            </table>
            <div class="col-md-offset-1">
                <input type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/displaySuperheroesPage'" value="Back"/>
            </div> 
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
