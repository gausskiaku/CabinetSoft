<%-- 
    Document   : CreationVisiter
    Created on : 26 juil. 2016, 13:18:52
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
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
        <title>Nouveau Visiteur</title>

    </head>
    <body>
        <div>
            <form name="FormVisiter" action="CreationVisiter" enctype="multipart/form-data" method="POST">
                <br />
                <div>
                    <c:import url="/DeconnexionModele.jsp" />
                </div>
                <div style="margin-top: -320px; margin-left: 250px">
                    <h1>Enregistrement d'un nouveau visiteur</h1>
                    <br />
                    ${msgVisite}
                    ${msgVisiteur}
                    <div class="panel">
    <div class="heading">
        <span class="title">Information du visiteur</span>
    </div>
    <div class="content">
                        <%-- Si et seulement si la liste de visiteur sur la BD n'est
                        pas vide, alors on propose un choix à l'utilisateur --%>
                        <c:if test="${ !empty listVisiteur }">
                            <label for="choixNouveauVisiteur">Nouveau visiteur ? <span class="requis">*</span></label>

                            <label class="input-control radio small-check">
                                <input type="radio" id="choixNouveauVisiteur" name="choixNouveauVisiteur" value="nouveauVisiteur" checked >
                                <span class="check"></span>
                                <span class="caption">Oui</span>
                            </label>

                            <label class="input-control radio small-check">
                                <input type="radio" id="choixNouveauVisiteur" name="choixNouveauVisiteur" value="ancienVisiteur">
                                <span class="check"></span>
                                <span class="caption">Non</span>
                            </label>
                            <br/>
                            <br />
                        </c:if>
                        <%-- <c:set var="visiteur" value="${ commande.client }" scope="request" /> --%>
                        <div id="nouveauVisiteur">
                            <c:import url="/Visiteur/ModeleCreationVisiteur.jsp" />
                        </div>
                        <%-- Si et seulement si la la liste de visiteur sur la BD n'est
                        pas vide, alors on crée la liste déroulante --%>
                        <c:if test="${ !empty listVisiteur }">
                            <div id="ancienVisiteur" class="input-control select" style="margin-left: 150px">
                                <select name="listeVisiteur" id="listeVisiteur">
                                    <option value="">Choisissez un visiteur...</option>
                                    <%-- Boucle sur la liste des visiteurs --%>
                                    <c:forEach items="${ listVisiteur }" var="listVisiteurs">
                                        <%--  L'expression EL ${listVisiteurs.value} permet de
                                        cibler l'objet Client stocké en tant que valeur dans la Map,
                                        et on cible ensuite simplement ses propriétés
                                        nom et prenom comme on le ferait avec n'importe quel bean. --%>
                                        <option value="${ listVisiteurs.idVisiteur }">${ listVisiteurs.nomVisiteur } ${ listVisiteurs.prenomVisiteur }</option>

                                    </c:forEach>
                                </select>
                            </div>
                                    <br/>
                        </c:if>
                    </div>
                    </div>
                    <div class="panel">
    <div class="heading">
        <span class="title">Information de la visite</span>
    </div>
    <div class="content">
                        <label> Message </label>
                        <div class="input-control textarea" style="margin-left: 165px"><textarea id="messageVisiter" name="messageVisiter" maxlength="250" placeholder="Ex : Vous etês ..."> 
                            </textarea> </div>
                        <br />
                        <br />
                        <label> Fichier </label> 
                        <div class="input-control file" data-role="input" style="margin-left: 180px">
                            <input id="fichierVisiter" name="fichierVisiter" tabindex="-1" style="z-index: 0;" type="file">
                            <button type="button" class="button"><img src="Metro/images/Blank_Folder.png"></button>
                        </div>
                        <br />
                        <br />
                        <label> Disponibilité </label> 
                        <div class="input-control select" style="margin-left: 137px">
                            <select name="disponibilite" id="disponibilite">
                                <option value="">  </option>
                                <option value="Oui"> Oui </option>
                                <option value="Non"> Non </option>
                            </select>
                        </div>
                        <br />
                        <br />
                        <label> Agent visité </label>
                        <div class="input-control select" style="margin-left: 143px">
                            <select name="listeAgent" id="listeAgent">
                                <option value="">Choisissez l'Agent</option>
                                <c:forEach items="${ listAgent }" var="listAgents">
                                    <option value="${ listAgents.matriculeAgent }">${ listAgents.nomAgent } ${ listAgents.prenomAgent }</option>
                                </c:forEach>

                            </select>
                        </div>
                        <br/><div>
                        <button value="Enregistrer" name="Enregistrer" type="submit" class="button success block-shadow-success text-shadow"> Enregistrer </button>
                        <button type="reset" value="Remettre à zero" class="button danger block-shadow-danger text-shadow"> Remettre à zéro </button>
                        </div>
                        <br />
    </div> <br />
                    ${msgVisite}
                </div>
            </form>
        </div>
        <%-- Inclusion de la bibliothèque jQuery. Vous trouverez des cours sur
JavaScript et jQuery aux adresses suivantes :
- http://www.siteduzero.com/tutoriel-3-309961-dynamisez-vossites-web-avec-javascript.html
- http://www.siteduzero.com/tutoriel-3-659477-un-site-webdynamique-avec-jquery.html
Si vous ne souhaitez pas télécharger et ajouter jQuery à votre
projet, vous pouvez utiliser la version fournie directement en ligne par Google
:
<script
src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
        --%>
        <script src="<c:url value="/js/jquery.js"/>"></script>
        <%-- Petite fonction jQuery permettant le remplacement de la première
        partie du formulaire par la liste déroulante, au clic sur le bouton radio. --%>
        <script>
        jQuery(document).ready(function () {
            /* 1 - Au lancement de la page, on cache le bloc d'éléments du
             formulaire correspondant aux clients existants */
            $("div#ancienVisiteur").hide();
            /* 2 - Au clic sur un des deux boutons radio "choixNouveauClient", on
             affiche le bloc d'éléments correspondant (nouveau ou ancien client) */
            jQuery('input[name=choixNouveauVisiteur]:radio').click(function () {
                $("div#nouveauVisiteur").hide();
                $("div#ancienVisiteur").hide();
                var divId = jQuery(this).val();
                $("div#" + divId).show();
            });
        });
        </script>


    </body>
</html>
