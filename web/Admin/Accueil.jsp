<%-- 
    Document   : Accueil
    Created on : 26 juil. 2016, 09:30:04
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
        <title>Accueil Admin</title>
        
    </head>
    <body>
        
        <div>
              <c:import url="/DeconnexionModele.jsp" />
        </div>
        <div style="margin-top: -320px; margin-left: 250px"> 
            <h1 style="text-align: center"> Bienvenu(e) cher(e) administrateur(trice)
                <small><br/>Vous avez la possibilité de tout gérer ici</small>
                <small><br/> Toutes les options sont à votre porté</small>
            </h1>
            <br/>
           <!-- 
        <div class="tile-wide bg-orange fg-white" data-role="tile">
                <div class="tile-content">
                    <div style="width: 100%; height: 150px;" class="carousel" data-role="carousel" data-controls="false" data-markers="true">
                        <div style="display: block; left: -310px;" class="slide"><img src="Metro/images/1.jpg"></div>
                        <div style="display: block; left: -310px;" class="slide"><img src="Metro/images/2.jpg"></div>
                        <div style="display: block; left: -310px;" class="slide"><img src="Metro/images/3.jpg"></div>
                        <div style="display: block; left: -310px;" class="slide"><img src="Metro/images/4.jpg"></div>
                        <div style="display: block; left: 0px;" class="slide"><img src="Metro/images/5.jpg"></div>
                    <div class="carousel-bullets">
                        <a class="carousel-bullet" href="javascript:void(0)" data-num="0"></a>
                        <a class="carousel-bullet" href="javascript:void(0)" data-num="1"></a>
                        <a class="carousel-bullet" href="javascript:void(0)" data-num="2"></a>
                        <a class="carousel-bullet" href="javascript:void(0)" data-num="3"></a>
                        <a class="carousel-bullet bullet-on" href="javascript:void(0)" data-num="4"></a>
                    </div>
                    </div>
                    <div class="tile-label">Carousel</div>
                </div>
            </div>
            -->
        
        </div>
        
        
        
    </body>
</html>
