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
        <title>Modifier Agent</title>
        <link type="text/css" rel="stylesheet" href="<c:url value="css/style.css"/>"/>
    </head>
    <body>
        <div>
            <c:import url="/DeconnexionModele.jsp" />
        </div>
        <form name="ModifierVisiterForm" action="ModifierVisiterValidation" method="POST">
            <fieldset>
                <legend>Modification d'une visite</legend>
                <label> Date et heure <span class="requis">*</span></label>
                <input type="text" id="dateHeureVisiter" name="dateHeureVisiter" value="${visiter.dateHeure}" size="30" maxlength="30" readonly>
                <%-- <span class="erreur"> ${form.erreurs['nomClient']} </span> --%>
                <br />
                <br />
                <label> Fichier </label> 
                <input type="file" id="fichierVisiter" name="fichierVisiter"  />
                <br />
                <br />
                <label> Disponibilité </label> 
                <select>
                    <c:choose>
                        <c:when test="${visiter.disponibilite == Oui} " >
                            <option value="Oui" selected> Oui </option>
                            <option value="Non"> Non </option>
                        </c:when>
                        <c:when test="${visiter.disponibilite == Non} ">
                            <option value="Oui" > Oui </option>
                            <option value="Non" selected> Non </option>
                        </c:when>
                        <c:otherwise>
                            <option value="" selected>Disponibilité non connu</option>
                            <option value="Oui" > Oui </option>
                            <option value="Non" > Non </option>
                        </c:otherwise>
                    </c:choose>
                </select>
                <br />
                <br />
                <label> Agent visité </label> 
                <select name="listeAgent" id="listeAgent">
                    <option value="">Choisissez l'Agent</option>
                    <c:forEach items="${ listAgent }" var="listAgents">
                        <option value="${ listAgents.matriculeAgent }">${ listAgents.nomAgent } ${ listAgents.prenomAgent }</option>
                    </c:forEach>
                </select>
                <br />
                <br />
                <label> Visiteur </label> 
                <select name="listeVisiteur" id="listeVisiteur">
                    <option value="">Choisissez un visiteur...</option>
                    <c:forEach items="${ listVisiteur }" var="listVisiteurs">
                        <option value="${ listVisiteurs.idVisiteur }">${ listVisiteurs.nomVisiteur } ${ listVisiteurs.prenomVisiteur }</option>
                    </c:forEach>
                </select>
                <br />
                <br />

                <input type="submit" value="Valider" name="UpdateAgent"/> <br/> <br />
                <br />
                <br />
                ${msg}
                <br />
                <a href="ListeAgent">Voir la liste de tout les Agents</a>

        </form>

    </fieldset>
</form>
</body>
</html>
