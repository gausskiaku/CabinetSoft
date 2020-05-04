<%-- 
    Document   : ListeVisiter
    Created on : 26 juil. 2016, 11:05:59
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liste des visites</title>
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
        <div style="margin-top: -280px; margin-left: 250px">
            <h1>Liste des visites</h1>
        <br />
        <br />
        Nombre des agents sur la BD : ${nbreVisiter}
        <br />
        <br />
        <!--
        <form name="rechercheFormListeVisite" action="RechercheVisiter" method="POST">
           <table border="1">
                <tr>
                    <td> Recherche par Date : </td>
                    <td> <input type="text" name="dateVisiter" value="" size="30" /></td>
                    <td> <input type="submit" value="Rechercher" name="btRecherche" /></td>
                    <td> <input type="submit" value="Voir tout" name="btRecherche" /></td>
                    <td> <input type="submit" value="Inserer" name="btRecherche" /></td>
                </tr>
            </table> 
        </form> -->
        <table aria-describedby="example_table_info" role="grid" id="example_table" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                    <tr role="row">
                        <th aria-label="Name: activate to sort column ascending" style="width: 30px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">N°</th>
                        <th aria-label="Position: activate to sort column ascending" style="width: 80px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Date et heure</th>
                        <th aria-label="Office: activate to sort column ascending" style="width: 40px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Message initial</th>
                        <th aria-label="Age: activate to sort column ascending" style="width: 27px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Fichier</th>
                        <th aria-label="Start date: activate to sort column ascending" style="width: 40px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Disponibilité</th>
                        <th aria-label="Salary: activate to sort column ascending" style="width: 30px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting_desc">Agent visité</th>                        
                        <th aria-label="Salary: activate to sort column ascending" style="width: 30px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting_desc">Visiteur</th>      
                        <th style="width: 150px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0">Action</th>

                    </tr>
                </thead>


                <tfoot>
                    <tr><th colspan="1" rowspan="1">N°</th>
                        <th colspan="1" rowspan="1">Date et heure</th>
                        <th colspan="1" rowspan="1">Message initial</th>
                        <th colspan="1" rowspan="1">Fichier</th>
                        <th colspan="1" rowspan="1">Disponibilité</th>
                        <th colspan="1" rowspan="1">Agent visité</th>
                        <th colspan="1" rowspan="1">Visiteur</th>
                        <th colspan="1" rowspan="1">Action</th>
                    </tr>

                </tfoot>
        
                 <tbody>

                    <c:set var="count" scope="page" value="0"/>
                <c:forEach var="visiter" items="${listVisiter}" varStatus="boucle">
                    <c:set var="count" scope="page" value="${count + 1}"/>
                        <tr class="${boucle.index % 2 == 0 ? 'odd' : 'even'}" role="row">
                            <td class="">${count}</td>
                        <td class="">${visiter.dateHeure}</td>
                        <td class="">${visiter.message}</td>
                        <td class=""> <form action="VoirFichier">
                                <input type="hidden" name="voirFichier" value="${visiter.fichier}" />
                                <button class="button info" type="submit" value="Voir" name="ActionVisiter">Voir le fichier</button>
                            </form></td>
                        <td class="">${visiter.disponibilite}</td>
                        <td class="">${visiter.agent.nomAgent} ${visiter.agent.prenomAgent}</td>
                        <td class="">${visiter.visiteur.nomVisiteur} ${visiter.visiteur.prenomVisiteur}</td>
                            <td class=""> <form name="FormActionVisiter" action="ActionVisiter" method="POST">
                                <input type="hidden" name="idVisiter" value="${visiter.message}" />
                                <button class="button primary" type="submit" value="Modifier" name="ActionVisiter">Modifier</button>
                                <button class="button danger" type="submit" value="Supprimer" name="ActionVisiter">Supprimer</button>
                                <button class="button info" type="submit" value="Voir" name="ActionVisiter">Voir</button>
                                 
                                </form> 
                            </td>


                        </tr>
                    </c:forEach>

                </tbody>
            </table>     
      
        </div>
    </body>
</html>
