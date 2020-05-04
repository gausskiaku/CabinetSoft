<%-- 
    Document   : Connexion
    Created on : 26 juil. 2016, 08:50:12
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Création Service</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="css/style.css"/>"/>
    </head>
    <body> 
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <form name="CreationServiceForm" action="CreationService" method="POST">
        <fieldset>
                    <legend>Information du Service</legend>
                    
        <label> Nom du service </label> 
        <input type="text" id="nomService" name="nomService" value="" size="30" maxlength="30" />
        <br />
        <br />
        <label> Description service </label> 
        <textarea id="descriptionService" name="descriptionService"></textarea>
        <br />
        <br />
        
        
        <input type="submit" value="Valider"  />
        <input type="reset" value="Remettre à zéro" /> <br/> <br />
        <br />
        ${msg}
        
        <a href="ListeService">Voir la liste de tout les Services</a>
        
     </fieldset> 
        
    </form>
        </body>
</html>
