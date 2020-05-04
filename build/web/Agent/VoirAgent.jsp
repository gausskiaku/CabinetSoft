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
        <title>Agent : ${agent.nomAgent}</title>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
        <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
        <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

        <link rel="shortcut icon" type="image/x-icon" href="../favicon.ico">
        <link href="Metro/css/metro.css" rel="stylesheet">
        <link href="Metro/css/metro-icons.css" rel="stylesheet">
        <link href="Metro/css/metro-responsive.css" rel="stylesheet">

        <script src="Metro/js/jquery-2.1.3.min.js"></script>
        <script src="Metro/js/jquery.dataTables.min.js"></script>

        <script src="Metro/js/metro.js"></script>

        <style>
            html, body {
                height: 100%;
            }
            body {
            }
            .page-content {
                padding-top: 3.125rem;
                min-height: 100%;
                height: 100%;
            }
            .table .input-control.checkbox {
                line-height: 1;
                min-height: 0;
                height: auto;
            }

            @media screen and (max-width: 800px){
                #cell-sidebar {
                    flex-basis: 52px;
                }
                #cell-content {
                    flex-basis: calc(100% - 52px);
                }
            }
        </style>

        <script>
            function pushMessage(t) {
                var mes = 'Info|Implement independently';
                $.Notify({
                    caption: mes.split("|")[0],
                    content: mes.split("|")[1],
                    type: t
                });
            }

            $(function () {
                $('.sidebar').on('click', 'li', function () {
                    if (!$(this).hasClass('active')) {
                        $('.sidebar li').removeClass('active');
                        $(this).addClass('active');
                    }
                })
            })
        </script>
    </head>
    <body>
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <form name="VoirAgentForm" action="VoirAgent" method="POST">
            <fieldset>
                <legend>Modification de l'Agent</legend>
                <label> Matricule agent </label> : <b>${agent.matriculeAgent}</b>
                <br />
                <br />
                <label> Nom Agent </label> : <b> ${agent.nomAgent} </b>
                <br />
                <br />
                <label> Prenom Agent </label> : <b> ${agent.prenomAgent} </b>
                <br />
                <br />
                <label> Numero Agent </label> : <b> ${agent.numAgent} </b>
                <br />
                <br />
                <label> Service </label> : <b>
                    <c:choose>
                        <c:when test="${agent.idService.idService == 1}">
                            Courriel
                        </c:when>
                        <c:when test="${agent.idService.idService == 2}">
                            Directeur
                        </c:when>
                        <c:when test="${agent.idService.idService == 3}">
                            Dircab
                        </c:when>
                        <c:when test="${agent.idService.idService == 4}">
                            Admin
                        </c:when>
                        <c:otherwise>
                            Pas de service associé a cet agent !
                        </c:otherwise>
                    </c:choose>
                </b>
                <br />
                <br />
                <a href="ListeAgent">Retour à la liste des Agents</a>
            </fieldset>
        </form>
    </body>
</html>
