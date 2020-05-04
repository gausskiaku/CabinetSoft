<%-- 
    Document   : VoirAgent
    Created on : 1 août 2016, 21:36:25
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Utilisateur : ${utilisateur.login}</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="css/style.css"/>"/>
    </head>
    <body>
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <form name="VoirAgentForm" action="VoirAgent" method="POST">
            <fieldset>
                <legend>Information de l'utilisateur</legend>
                <label> Login </label> : <b>${utilisateur.login}</b>
                <br />
                <br />
                <label> Mot de passe </label> : <b> ${utilisateur.motdepasse} </b>
                <br />
                <br />
                
                <label> Service </label> : <b>
                    <c:choose>
                        <c:when test="${utilisateur.idService.idService == 1}">
                            Courriel
                        </c:when>
                        <c:when test="${utilisateur.idService.idService == 2}">
                            Directeur
                        </c:when>
                        <c:when test="${utilisateur.idService.idService == 3}">
                            Dircab
                        </c:when>
                        <c:when test="${utilisateur.idService.idService == 4}">
                            Admin
                        </c:when>
                        <c:otherwise>
                            Pas de service associé a cet utilisateur !
                        </c:otherwise>
                    </c:choose>
                </b>
                <br />
                <br />
                <a href="ListeUtilisateur">Retour à la liste des Utilisateurs</a>
            </fieldset>
        </form>
    </body>
</html>
