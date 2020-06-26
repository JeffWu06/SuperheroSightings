<%-- 
    Document   : addOrganization
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
            <h2>Add Organization</h2>
            <hr/>
            <div>
                <p name="errorMessages">${errorMessages}</p>
            </div>
            <sf:form class="form-horizontal" role="form" modelAttribute="organization" action="addOrganization" method="POST">
                <div class="form-group">
                    <label for="add-name" class="col-md-5 control-label">Name:</label>
                    <div class="col-md-4">
                        <sf:input type="text" class="form-control" name="organizationName" path="organizationName" placeholder="Enter org name" required="true"/>
                        <sf:errors path="organizationName" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description" class="col-md-5 control-label">Organization description:</label>
                    <div class="col-md-6">
                        <sf:textarea type="text" class="form-control input-md" name="orgDescription" path="orgDescription" placeholder="Enter description" required="true"></sf:textarea>
                        <sf:errors path="orgDescription" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-villain" class="col-md-5 control-label">Is this organization villainous?</label>
                    <div class="col-md-4">
                        <input type="radio" class="radio-inline" name="villain" path="villain" value="no" checked/>
                        <label class="radio-inline" for="villain" style="padding:10px 50px 0px 0px">No/Unsure</label>
                        <input type="radio" class="radio-inline" name="villain" path="villain" value="yes"/>
                        <label class="radio-inline" for="villain" style="padding:10px 50px 0px 0px">Yes</label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-phone" class="col-md-5 control-label">Contact phone:</label>
                    <div class="col-md-4">
                        <sf:input type="text" class="form-control" name="phone" path="phone" placeholder="Enter phone number"/>
                        <sf:errors path="phone" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-email" class="col-md-5 control-label">Contact email:</label>
                    <div class="col-md-4">
                        <sf:input type="email" class="form-control" path="email" placeholder="e.g. someone@domain.com"/>
                        <sf:errors path="email" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location" class="col-md-5 control-label">Location:</label>
                    <div class="col-md-4">
                        <select name="orgLocation">
                            <c:forEach var="currentLoc" items="${existingLocations}">
                                <option name="orgLocation" value="${currentLoc.locationId}">${currentLoc.locationName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4">
                    <input type="button" class="btn btn-default" onclick="location.href='displayOrganizationsPage'" value="Cancel"/>
                    <input type="submit" class="btn btn-default" value="Add Organization"/>
                    </div>
                </div>
            </sf:form>
        </div>
    </body>
</html>
