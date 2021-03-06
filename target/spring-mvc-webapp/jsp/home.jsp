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
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/displayMainPage">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySuperheroesPage">Superheroes</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySuperpowersPage">Powers</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayLocationsPage">Locations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displayOrganizationsPage">Organizations</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/displaySightingsPage">Sightings</a></li>
                </ul>
            </nav>
            <p>
                Sightings of superheroes and supervillains by ordinary citizens are on the rise. Keep tabs on your favorite
                heroes and supervillains here with the Hero Education and Relationship Organization's official hero sighting tracker.
            </p>
            <p>
                Superheroes and villains, their superpowers, their organizations, and locations where they have been spotted
                can be managed via the links above.
            </p>
            <p>
                Check out the latest reported sightings below!
            </p>
            <hr/>
            <table class="table table-hover" id="lastTenSightingsTable">
                <thead>
                    <tr>
                        <th width="10%">Date</th>
                        <th width="25%">Location</th>
                        <th width="15%">Hero(es) spotted</th>
                        <th width="25%"></th>
                        <th width="25%"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="currentSighting" items="${lastTenSightings}">
                        <tr>
                            <td>
                                <c:out value="${currentSighting.sightingDate}"/>
                            </td>
                            <td>
                                <c:out value="${currentSighting.location.locationName}"/>
                            </td>
                            <td>
                                <c:forEach var="hero" items="${currentSighting.heroes}">
                                    <c:out value="${hero.alterEgo}"/>
                                    <br>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

