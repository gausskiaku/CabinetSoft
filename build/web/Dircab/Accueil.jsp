<%-- 
    Document   : Accueil
    Created on : 26 juil. 2016, 10:05:22
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Accueil : Dircab </title>
        <link type="text/css" rel="stylesheet" href="<c:url value="css/style.css"/>"/>
    </head>
    <body>
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <h1>Salut : DirCab</h1>
        ${date}
        <br />
        
    </body>
</html>
