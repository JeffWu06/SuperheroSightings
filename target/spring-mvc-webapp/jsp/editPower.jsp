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
/*        {
            text-align:left;
        }*/
    </style>
    <body>
        <div class="container">
            <h2>Edit Superpower: ${superpowerToEdit.superpowerDescription}</h2>
            <hr/>
            <sf:form class="form-horizontal" role="form" modelAttribute="superpowerToEdit" action="editPower" method="POST">
                <div class="form-group">
                    <label for="edit-power-description" class="col-md-5 control-label">Power description:</label>
                    <div class="col-md-4">
                        <sf:input type="text" class="form-control" path="superpowerDescription" placeholder="Enter superpower" required="true"/>
                        <sf:errors path="superpowerDescription" cssClass="error"></sf:errors>
                        <sf:hidden path="superpowerId"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4">
                    <input type="button" class="btn btn-default" onclick="location.href='displaySuperpowersPage'" value="Cancel"/>
                    <input type="submit" class="btn btn-default" value="Add Superpower"/>
                    </div>
                </div>
            </sf:form>
        </div>
    </body>
</html>
