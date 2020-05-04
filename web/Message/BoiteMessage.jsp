<%-- 
    Document   : BoiteMessage
    Created on : 19 août 2016, 23:39:26
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Boite de Message</title>
    </head>
    <body>
        <h1>Consulter vos messages</h1>
        <input type="hidden" value="${sessionScope.sessionUtilisateur.login}" name="userMessage" />

        <table border="1">
            <thead>
                <tr>
                    <th>N°</th>
                    <th>Message</th>
                    <th>Fichier</th>
                    <th>Date</th>
                    <th>Expéditeur</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <c:set var="count" scope="page" value="0"/>
            <c:forEach var="message" items="${listMessage}" varStatus="boucle">
                <c:set var="count" scope="page" value="${count + 1}"/>
                <tr class="${boucle.index % 2 == 0 ? 'odd' : 'even'}" role="row">
                    <td class="">${count}</td>
                    <td class="">${message.message}</td>
                    <td class="">${message.fichier}</td>
                    <td class="">${message.dateEnvoi}</td>
                    <td class="">${message.matriculeAgent.nomAgent} ${message.matriculeAgent.prenomAgent}</td>
                    <td class=""> 
                        <form name="FormActionVisiter" action="ActionVisiter" method="POST">
                            <input type="hidden" name="idVisiter" value="${message.message}" />
                            <button class="button primary" type="submit" value="Modifier" name="ActionVisiter">Modifier</button>
                            <button class="button danger" type="submit" value="Supprimer" name="ActionVisiter">Supprimer</button>
                            <button class="button info" type="submit" value="Voir" name="ActionVisiter">Voir</button>

                        </form> 
                    </td>


                </tr>
            </c:forEach>
        </tbody>
    </table>

</body>
</html>
