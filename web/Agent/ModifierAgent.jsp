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
        <form name="ModifierAgentForm" action="ModifierAgentValidation" method="POST">
        <fieldset>
                    <legend>Modification de l'Agent</legend>
        <label> Matricule agent <span class="requis">*</span></label>
        <input type="text" id="matriculeAgent" name="matriculeAgent" value="${agent.matriculeAgent}" size="30" maxlength="30" readonly>
        <%-- <span class="erreur"> ${form.erreurs['nomClient']} </span> --%>
        <br />
        <br />
        <label> Nom Agent </label> 
        <input type="text" id="nomAgent" name="nomAgent" value="${agent.nomAgent}" size="30" maxlength="30" />
        <br />
        <br />
        <label> Prenom Agent </label> 
        <input type="text" id="prenomAgent" name="prenomAgent" value="${agent.prenomAgent}" size="30" maxlength="30" />
        <br />
        <br />
        <label> Numero Agent </label> 
        <input type="text" id="numAgent" name="numAgent" value="${agent.numAgent}" />
       
        <br />
        <br />
        <label> Service </label> 
        <select name="choixService" id="choixService">
            <c:choose>
                <c:when test="${agent.idService.idService == 1}">
                    <option value="1" selected>Courriel</option>
                    <option value="2" >Directeur</option>
                    <option value="3" >Dircab</option>
                    <option value="4" >Admin</option>
                </c:when>
                <c:when test="${agent.idService.idService == 2}">
                    <option value="1" >Courriel</option>
                    <option value="2" selected>Directeur</option>
                    <option value="3" >Dircab</option>
                    <option value="4" >Admin</option>
                </c:when>
                <c:when test="${agent.idService.idService == 3}">
                     <option value="1" >Courriel</option>
                    <option value="2" >Directeur</option>
                    <option value="3" selected>Dircab</option>
                    <option value="4" >Admin</option>
                </c:when>
                <c:when test="${agent.idService.idService == 4}">
                   <option value="1" >Courriel</option>
                    <option value="2" >Directeur</option>
                    <option value="3" >Dircab</option>
                    <option value="4" selected>Admin</option>
                </c:when>
                <c:otherwise>
                    <option>Choissisez le Service</option>
                </c:otherwise>
            </c:choose>

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
