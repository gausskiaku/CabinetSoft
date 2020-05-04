<%-- 
    Document   : ListeAgent
    Created on : 26 juil. 2016, 11:04:27
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Liste des Agents</title>
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
            <h1>Recherche d'un agent</h1>
            
            <br />


            <table aria-describedby="example_table_info" role="grid" id="example_table" class="dataTable striped border bordered" data-role="datatable" data-searching="true">
                <thead>
                    <tr role="row">
                        <th aria-label="Name: activate to sort column ascending" style="width: 30px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">N°</th>
                        <th aria-label="Position: activate to sort column ascending" style="width: 40px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Matricule Agent</th>
                        <th aria-label="Office: activate to sort column ascending" style="width: 40px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Nom Agent</th>
                        <th aria-label="Age: activate to sort column ascending" style="width: 27px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Prénom Agent</th>
                        <th aria-label="Start date: activate to sort column ascending" style="width: 40px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting">Numéro Agent</th>
                        <th aria-label="Salary: activate to sort column ascending" style="width: 30px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0" class="sorting_desc">Service</th>
                        <th style="width: 200px;" colspan="1" rowspan="1" aria-controls="example_table" tabindex="0">Action</th>

                    </tr>
                </thead>


                <tfoot>
                    <tr><th colspan="1" rowspan="1">N°</th>
                        <th colspan="1" rowspan="1">Matricule Agent</th>
                        <th colspan="1" rowspan="1">Nom Agent</th>
                        <th colspan="1" rowspan="1">Prénom Agent</th>
                        <th colspan="1" rowspan="1">Numéro Agent</th>
                        <th colspan="1" rowspan="1">Service</th>
                        <th colspan="1" rowspan="1">Action</th>
                    </tr>

                </tfoot>

                <tbody>

                    <c:set var="count" scope="page" value="0"/>
                    <c:forEach var="agent" items="${listAgent}" varStatus="boucle">
                        <c:set var="count" scope="page" value="${count + 1}"/>
                        <tr class="${boucle.index % 2 == 0 ? 'odd' : 'even'}" role="row">
                            <td class="">${count}</td>
                            <td class="">${agent.matriculeAgent}</td>
                            <td class="">${agent.nomAgent}</td>
                            <td class="">${agent.prenomAgent}</td>
                            <td class="">${agent.numAgent}</td>
                            <td class="">${agent.idService.nomService}</td>
                            <td> <form name="ActionAgent" action="ActionAgent" method="POST">
                                    <input type="hidden" name="idModifierAgent" value="${agent.matriculeAgent}" />
                                    <button class="button primary" type="submit" value="Modifier" name="ActionAgent">Modifier</button>
                                <button class="button danger" type="submit" value="Supprimer" name="ActionAgent">Supprimer</button>
                                <button class="button info" type="submit" value="Voir" name="ActionAgent">Voir</button>
                                 
                                </form> 
                            </td>


                        </tr>
                    </c:forEach>

                </tbody>
            </table>
            <!--
    <div aria-live="polite" role="status" id="example_table_info" class="dataTables_info">Voir de 1 à 10 de ${count} agents
    </div>
    <div id="example_table_paginate" class="dataTables_paginate paging_simple_numbers">
        <a id="example_table_previous" tabindex="0" data-dt-idx="0" aria-controls="example_table" class="paginate_button previous disabled">Précèdent</a>
        <span>
            <a tabindex="0" data-dt-idx="1" aria-controls="example_table" class="paginate_button current">1</a>
            <a tabindex="0" data-dt-idx="2" aria-controls="example_table" class="paginate_button ">2</a>
            <a tabindex="0" data-dt-idx="3" aria-controls="example_table" class="paginate_button ">3</a>
            <a tabindex="0" data-dt-idx="4" aria-controls="example_table" class="paginate_button ">4</a>
            <a tabindex="0" data-dt-idx="5" aria-controls="example_table" class="paginate_button ">5</a>
            <a tabindex="0" data-dt-idx="6" aria-controls="example_table" class="paginate_button ">6</a>
            <a tabindex="0" data-dt-idx="7" aria-controls="example_table" class="paginate_button ">7</a>

        </span>
        <a id="example_table_next" tabindex="0" data-dt-idx="7" aria-controls="example_table" class="paginate_button next">Suivant</a>
    </div>
            -->










            <!--
            <table border="1">
                <thead>
                    <tr>
                        <th> N° </th>
                        <th>Matricule Agent</th>
                        <th>Nom Agent</th>
                        <th>Prénom Agent</th>
                        <th>Numéro Agent</th>
                        <th>Service</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
            <c:set var="count" scope="page" value="0"/>
            <c:forEach var="agent" items="${listAgent}" varStatus="boucle">
                <c:set var="count" scope="page" value="${count + 1}"/>
                <tr class="${boucle.index % 2 == 0 ? 'pair' : 'impair'}">
                    <td>${count}</td>
                    <td>${agent.matriculeAgent}</td>
                    <td>${agent.nomAgent}</td>
                    <td>${agent.prenomAgent}</td>
                    <td>${agent.numAgent}</td>
                    <td>${agent.idService.nomService}</td>
                    <td> <form name="ActionAgent" action="ActionAgent" method="POST">
                            <input type="hidden" name="idModifierAgent" value="${agent.matriculeAgent}" />
                            <input type="submit" value="Modifier" name="ActionAgent" />
                            <input type="submit" value="Supprimer" name="ActionAgent" />
                            <input type="submit" value="Voir" name="ActionAgent" />
                        </form> 
                    </td>


                </tr>
            </c:forEach>
        </tbody>
    </table>
            -->
        </div>
    </body>
</html>
