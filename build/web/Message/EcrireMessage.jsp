<%-- 
    Document   : EcriceMessage
    Created on : 18 août 2016, 08:39:42
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Envoyer un message</title>
    </head>
    <body>
        <h1>écrire un message</h1>
        <form name="EcrireMessageForm" action="EcrireMessage" method="POST" enctype="multipart/form-data">

            Destinataire : 
            <div class="input-control select">
                <select name="choixAgent" id="choixAgent" multiple="multiple">
                    <option value="">Choisissez l'Agent</option>
                    <c:forEach items="${ listAgent }" var="listAgents">
                        <option value="${ listAgents.matriculeAgent }">${ listAgents.nomAgent } ${ listAgents.prenomAgent }</option>
                    </c:forEach>

                </select>
            </div>
            <br/>
            <br/>
            Fichier : <input type="file" name="fichierMessage" />
            <br/>
            <br/>
            Message : <textarea name="message" rows="4" cols="20">
            </textarea>
            <input type="hidden" value="${sessionScope.sessionUtilisateur.login}" name="expediteur" id="expediteur"/>
            <br/>
            <br/>
            <button type="submit" name="Envoyer" value="Envoyer">Envoyer</button>
            <button type="reset" name="Remise à zéro">Remise à zéro</button>
            ${msg}
            <a href="BoiteMessage">Boite de Réception</a>
        </form>
    </body>
</html>
