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
        function pushMessage(t){
            var mes = 'Info|Implement independently';
            $.Notify({
                caption: mes.split("|")[0],
                content: mes.split("|")[1],
                type: t
            });
        }

        $(function(){
            $('.sidebar').on('click', 'li', function(){
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
       
        <div style="margin-top: -320px; margin-left: 250px">
             <h1>Modification d'un utilisateur</h1>
        <form name="ModifierUtilisateurForm" action="ModifierUtilisateurValidation" method="POST">
            <fieldset>
                <legend>Modification de l'Utilisateur</legend>

                <label> Numéro de l'utilisateur </label> 
                <input type="text" id="idUtilisateur" name="idUtilisateur" value="${utilisateur.idUser}" size="30" maxlength="30" readonly/>
                <br />
                <br />

                <label> Login </label> 
                <input type="text" id="loginUtilisateur" name="loginUtilisateur" value="${utilisateur.login}" size="30" maxlength="30" />
                <br />
                <br />
                <label> Mot de passe </label> 
                <input type="text" id="motdepasseUtilisateur" name="motdepasseUtilisateur" value="${utilisateur.motdepasse}" size="30" maxlength="30" />
                <br />
                <br />
                <label> Agent </label> 
                <div class="input-control select">
                        <select name="choixAgent" id="choixAgent">
                            <option value="">Choisissez l'Agent</option>
                            <c:forEach items="${ listAgent }" var="listAgents">
                                <option value="${ listAgents.matriculeAgent }">${ listAgents.nomAgent } ${ listAgents.prenomAgent }</option>
                                
                            </c:forEach>
                        </select>
                    </div>
                
                <br />
                <br />

                <input type="submit" value="Valider" name="UpdateUtilisateur"/> <br/> <br />
                <br />
                <br />
                ${msg}
                <br />
                <a href="ListeUtilisateur">Voir la liste de tout les Utilisateurs</a>

        </form>

    </fieldset>
</form>
</div>
</body>
</html>
