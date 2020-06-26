<%-- 
    Document   : editSighting
    Created on : May 22, 2020, 2:58:10 PM
    Author     : Jeff
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <title>H.E.R.O. Superhero Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <style>
    </style>
    <body>
        <div class="container">
            <h2>Edit Sighting</h2>
            <hr/>
            <div>
                <p name="errorMessages">${errorMessages}</p>
            </div>
            <sf:form class="form-horizontal" role="form" modelAttribute="sightingToEdit" action="editSighting" method="POST">
                <div class="form-group">
                    <label for="edit-date" class="col-md-5 control-label">Date of Sighting:</label>
                    <div class="col-md-4">
                        <sf:input type="date" class="form-control" name="sightingDate" path="sightingDate" required="true"/>
                        <sf:errors path="sightingDate" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-location" class="col-md-5 control-label">Location:</label>
                    <div class="col-md-4">
                        <select name="sightingLocation">
                            <c:forEach var="currentLoc" items="${existingLocations}">
                                <option name="sightingLocation" value="${currentLoc.locationId}">${currentLoc.locationName} - ${currentLoc.street}, ${currentLoc.city}, ${currentLoc.country}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-superhumans" class="col-md-5 control-label">Known superheroes spotted (select all that apply):</label>
                    <div class="col-md-6">
                        <c:forEach var="currentHero" items="${existingHeroes}">
                            <input type="checkbox" name="superhumans" value="${currentHero.superhumanId}"/>
                            <label class="checkbox-inline">${currentHero.alterEgo}</label>
                            <br>
                        </c:forEach>
                        <sf:hidden path="sightingId"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4">
                    <input type="button" class="btn btn-default" onclick="location.href='displaySightingsPage'" value="Cancel"/>
                    <input type="submit" class="btn btn-default" value="Save changes"/>
                    </div>
                </div>
            </sf:form>
        </div>
    </body>
</html>
