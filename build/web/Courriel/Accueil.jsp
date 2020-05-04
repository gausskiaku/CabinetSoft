<%-- 
    Document   : Accueil
    Created on : 26 juil. 2016, 10:17:36
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil : Courriel</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="css/style.css"/>"/>
    </head>
    <body>
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <h1>Hello for Courriel</h1>
        
        
       <a href="CreationVisiter">Creation d'une visite</a>
       <a href="ListeVisiteur">Voir la liste des Visiteurs</a>
       <a href="">Discussion avec un agent</a>
    </body>
</html>
