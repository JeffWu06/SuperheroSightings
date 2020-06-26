<%-- 
    Document   : addHero
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
            <h2>Add Hero</h2>
            <hr/>
            <sf:form class="form-horizontal" role="form" modelAttribute="superhuman" action="createHero" method="POST">
                <div class="form-group">
                    <label for="add-alterEgo" class="col-md-5 control-label">Alter ego:</label>
                    <div class="col-md-4">
                        <sf:input type="text" class="form-control" path="alterEgo" placeholder="Enter alter ego" required="true"/>
                        <sf:errors path="alterEgo" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description" class="col-md-5 control-label">Hero description:</label>
                    <div class="col-md-6">
                        <sf:textarea type="text" class="form-control input-md" name="description" path="description" placeholder="Enter description" required="true"></sf:textarea>
                        <sf:errors path="description" cssClass="error"></sf:errors>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-villain" class="col-md-5 control-label">Is this person a villain?</label>
                    <div class="col-md-4">
                        <input type="radio" class="radio-inline" name="villain" path="villain" value="no" checked/>
                        <label class="radio-inline" for="villain" style="padding:10px 50px 0px 0px">No/Unsure</label>
                        <input type="radio" class="radio-inline" name="villain" path="villain" value="yes"/>
                        <label class="radio-inline" for="villain" style="padding:10px 50px 0px 0px">Yes</label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-superpowers" class="col-md-5 control-label">Known superpowers possessed (select all that apply):</label>
                    <div class="col-md-6">
                        <c:forEach var="currentPower" items="${superpowers}">
                            <input type="checkbox" name="powers" value="${currentPower.superpowerId}"/>
                            <label class="checkbox-inline">${currentPower.superpowerDescription}</label>
                            <br>
                        </c:forEach>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-organizations" class="col-md-5 control-label">Known organizations belonged to (select all that apply):</label>
                    <div class="col-md-6">
                        <c:forEach var="currentOrg" items="${organizations}">
                            <input type="checkbox" name="orgs" value="${currentOrg.organizationId}"/>
                            <label class="checkbox-inline">${currentOrg.organizationName}</label>
                            <br>
                        </c:forEach>                        
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-3">
                    <input type="button" class="btn btn-default" onclick="location.href='displaySuperheroesPage'" value="Cancel"/>
                    <input type="submit" class="btn btn-default" value="Add Hero"/>
                    </div>
                </div>
            </sf:form>
        </div>
    </body>
</html>
