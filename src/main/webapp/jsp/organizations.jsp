<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>H.E.R.O. Superhero Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet"> 
    </head>
    <body>
        <div class="container">
            <h1>Superhero Sightings</h1>
            <hr/>
            <nav class="navbar navbar-default">
                <ul class="nav navbar-nav">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayMainPage">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySuperheroesPage">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySuperpowersPage">Powers</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayLocationsPage">Locations</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/displayOrganizationsPage">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySightingsPage">Sightings</a></li>
                </ul>
            </nav>
            <h2>Organizations</h2>
            <div id="errorMessages" name="errorMessages">
                <c:out value="${errorMessages}"></c:out>
            </div>
            <input style="margin:0px 0px 20px 20px" type="button" class="btn btn-default" onclick="location.href='${pageContext.request.contextPath}/displayAddOrganizationPage'" value="Add New">
            <table class="table table-hover" id="organizationsList">
                <thead>
                    <tr>
                        <th width="15%">Name</th>
                        <th width="30%">Description</th>
                        <th width="20%">Email</th>
                        <th width="13%">Phone</th>
                        <th width="7%">Villain?</th>
                        <th width="5%"></th>
                        <th width="7%"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="currentOrg" items="${orgsList}">
                        <tr>
                            <td>
                                <a href="displayOrganizationDetails?organizationId=${currentOrg.organizationId}">
                                    <c:out value="${currentOrg.organizationName}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${currentOrg.orgDescription}"/>
                            </td>
                            <td>
                                <c:out value="${currentOrg.email} "/>
                            </td>
                            <td>
                                <c:out value="${currentOrg.phone}"/>
                            </td>
                            <td>
                                <c:if test="${currentOrg.villain}">
                                    Yes
                                </c:if> 
                                <c:if test="${!currentOrg.villain}">
                                    No
                                </c:if>
                            </td>
                            <td>
                                <a href="displayEditOrganizationPage?organizationId=${currentOrg.organizationId}">
                                    Edit
                                </a>
                            </td>
                            <td>
                                <a href="#" data-id="${currentOrg.organizationId}" class="deleteOrganization">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div class="modal" role="dialog" id="confirmOrganizationDelete" style="display:none">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Confirm delete organization</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p>Are you sure you want to delete this organization? This action cannot be undone.</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-primary" id="deleteOrganizationSubmit">Yes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/superheroSighting.js"></script>
    </body>
</html>

