<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 
    Document   : ListeVisiteur
    Created on : 26 juil. 2016, 11:07:50
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liste Visiteur</title>
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
        <div style="margin-top: -280px; margin-left: 250px">
            <h1>Liste des visiteurs</h1>
            Nombre des personnes ayant visités sur la BD : ${nbreVisiteur}

            <table aria-describedby="example_table_info" role="grid" id="example_table" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                    <tr role="row">
                        <th aria-label="Name: activate to sort column ascending" style="width: 20px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">N°</th>
                        <th aria-label="Position: activate to sort column ascending" style="width: 60px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Nom du Visiteur</th>
                        <th aria-label="Office: activate to sort column ascending" style="width: 60px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Prénom du Visiteur</th>
                        <th aria-label="Age: activate to sort column ascending" style="width: 150px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Visite effectuées</th>
                        <th style="width: 150px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0">Action</th>
                    </tr>
                </thead>


                <tfoot>
                    <tr><th colspan="1" rowspan="1">N°</th>
                        <th colspan="1" rowspan="1">Nom du Visiteur</th>
                        <th colspan="1" rowspan="1">Prénom du Visiteur</th>
                        <th colspan="1" rowspan="1">Visite effectuées</th>
                        <th colspan="1" rowspan="1">Action</th>
                    </tr>

                </tfoot>

                <tbody>

                    <c:set var="compteur" scope="page" value="0"/>
                    <c:forEach var="visiteur" items="${listVisiteur}" varStatus="boucle">
                        <c:set var="compteur" scope="page" value="${compteur + 1}"/>
                        <tr class="${boucle.index % 2 == 0 ? 'odd' : 'even'}" role="row">
                            <td>${compteur}</td>
                            <td>${visiteur.nomVisiteur}</td>
                            <td>${visiteur.prenomVisiteur}</td>
                            <td>
                                <c:set var="count" scope="page" value="0"/>
                                <c:forEach var="visite" items="${visiteur.visiterList}">
                                    <c:set var="count" scope="page" value="${count + 1}"/>
                                    Visite ${count} le ${visite.dateHeure} <br />
                                </c:forEach>                        
                            </td>
                            <td> <form name="Update" action="Update" method="POST">
                                    <input type="hidden" name="idVisiteur" value="${visiteur.idVisiteur}" />

                                    <button class="button primary" type="submit" value="Modifier" name="UpdateVisiteur" title="Modifier le visiteur">Modifier</button>
                                    <button class="button danger" type="submit" value="Supprimer" name="UpdateVisiteur" title="Supprimer le visiteur">Supprimer</button>
                                    <button class="button info" type="submit" value="Voir" name="UpdateVisiteur" title="Voir le visiteur">Voir</button>
                                </form> </td>


                        </tr>
                    </c:forEach>

                </tbody>
            </table>

            <br/>
            <br />

        </div>
    </body>
</html>
