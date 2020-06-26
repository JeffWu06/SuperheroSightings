<%-- 
    Document   : editLocation
    Created on : May 16, 2020, 10:04:03 PM
    Author     : Jeff
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <h2>Edit Hero: ${locationToEdit.locationName}</h2>
            <hr/>
            <div>
                <p>${errorMessages}</p>
            </div>
            <sf:form class="form-horizontal" role="form" modelAttribute="locationToEdit" action="editLocation" method="POST">
                <div class="form-group">
                    <label for="edit-locationName" class="col-md-5 control-label">Location Name:</label>
                    <div class="col-md-4">
                        <sf:input type="text" class="form-control" path="locationName" placeholder="Enter location name" required="true"/>
                        <sf:errors path="locationName" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-description" class="col-md-5 control-label">Location description:</label>
                    <div class="col-md-6">
                        <sf:textarea type="text" class="form-control input-md" name="locationDescription" path="locationDescription" placeholder="Enter description" required="true"></sf:textarea>
                        <sf:errors path="locationDescription" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-street" class="col-md-5 control-label">House number and street:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="street" path="street" placeholder="Enter house and street" required="true"></sf:input>
                        <sf:errors path="street" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-city" class="col-md-5 control-label">City:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="city" path="city" placeholder="Enter city" required="true"></sf:input>
                        <sf:errors path="city" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-state" class="col-md-5 control-label">2-character state/province (if applicable):</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="state" path="state" placeholder="Enter state code"></sf:input>
                        <sf:errors path="state" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-zip" class="col-md-5 control-label">Postal code:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="zip" path="zip" placeholder="Enter postal code" required="true"></sf:input>
                        <sf:errors path="zip" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-country" class="col-md-5 control-label">2-character Country code:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="country" path="country" placeholder="Enter country code" required="true"></sf:input>
                        <sf:errors path="country" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-latitude" class="col-md-5 control-label">Latitude:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="latitude" path="latitude" placeholder="Enter latitude" required="true"></sf:input>
                        <sf:errors path="latitude" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-longitude" class="col-md-5 control-label">Longitude:</label>
                    <div class="col-md-6">
                        <sf:input type="text" class="form-control input-md" name="longitude" path="longitude" placeholder="Enter longitude" required="true"></sf:input>
                        <sf:errors path="longitude" cssClass="error"></sf:errors>
                        <sf:hidden path="locationId"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-3">
                    <input type="button" class="btn btn-default" onclick="location.href='displayLocationsPage'" value="Cancel"/>
                    <input type="submit" class="btn btn-default" value="Save changes"/>
                    </div>
                </div>
            </sf:form>
        </div>
    </body>
</html>
