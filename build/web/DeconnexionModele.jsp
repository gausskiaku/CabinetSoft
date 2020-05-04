<%-- 
    Document   : DeconnexionModele
    Created on : 1 août 2016, 23:14:40
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<div class="app-bar fixed-top darcula" data-role="appbar">

    <a class="app-bar-element branding" href="Connexion">
        <img src="Metro/images/rdc.jpg" style="height: 28px; display: inline-block; margin-right: 10px;">
        Ministère
    </a>
    <span class="app-bar-divider"></span>
    <ul class="app-bar-menu">
        <li data-flexorder="1" data-flexorderorigin="0"><a href="CreationVisiter">Création Visite</a></li>

        <li class="" data-flexorder="2" data-flexorderorigin="1">
            <a href="" class="dropdown-toggle">Administration</a>
            <ul style="display: none;" class="d-menu" data-role="dropdown">
                <!--   <li><a href="">New project</a></li> -->
                <li class="divider"></li>
                <li class="">
                    <a href="" class="dropdown-toggle">Utilisateur</a>
                    <ul style="display: none;" class="d-menu" data-role="dropdown">
                        <li><a href="CreationUtilisateur">Création d'un utilisateur </a></li>
                        <li><a href="ListeUtilisateur">Voir les utilisateurs</a></li>
                    </ul>
                </li>

                <li class="divider"></li>
                <li class="">
                    <a href="" class="dropdown-toggle">Agent</a>
                    <ul style="display: none;" class="d-menu" data-role="dropdown">
                        <li><a href="CreationAgent">Création d'un agent </a></li>
                        <li><a href="ListeAgent">Voir les agents</a></li> 
                    </ul>
                </li>

                <!--          <li class="divider"></li>
                          <li class="">
                              <a href="" class="dropdown-toggle">Service</a>
                              <ul style="display: none;" class="d-menu" data-role="dropdown">
                                  <li><a href="CreationService">Création d'un service </a></li>
                                  <li><a href="">Gestion des services</a></li>
                              </ul>
                          </li>
                -->

                <li class="divider"></li>
                <li><a href="ListeVisiteur">Liste des visiteurs</a></li>

                <li class="divider"></li>
                <li><a href="ListeVisiter">Liste des visites effectuées</a></li>

            </ul>
        </li>
        <li data-flexorder="3" data-flexorderorigin="2"><a href="EcrireMessage">Messagerie</a></li>
        <!--   <li data-flexorder="4" data-flexorderorigin="3"><a href="">Système</a></li> -->
        <li class="active-container" data-flexorder="5" data-flexorderorigin="4">
            <a href="" class="dropdown-toggle">Système</a>
            <ul style="display: none;" class="d-menu" data-role="dropdown">
                <li><a href="">Aide</a></li>
                <li><a href="">Nous écrire</a></li>
                <li class="divider"></li>
                <li><a href="">A propos</a></li>
            </ul>
        </li>
    </ul>

    <div class="app-bar-element place-right">
        <span class="dropdown-toggle"><span class="mif-cog"></span> ${sessionScope.sessionUtilisateur.login}</span>
        <div class="app-bar-drop-container padding10 place-right no-margin-top block-shadow fg-dark" data-role="dropdown" data-no-close="true" style="width: 220px">
            <h2 class="text-light">Espace User</h2>
            <ul class="unstyled-list fg-dark">
                <li><a href="" class="fg-white1 fg-hover-yellow">Profile</a></li>
                <li><a href="" class="fg-white2 fg-hover-yellow">Security</a></li>
                <li><a href="Deconnexion" class="fg-white3 fg-hover-yellow">Exit</a></li>
            </ul>
        </div>
    </div>
    <div style="display: none;" class="app-bar-pullbutton automatic"></div><div class="clearfix" style="width: 0;"></div><nav style="display: none;" class="app-bar-pullmenu hidden flexstyle-app-bar-menu"><ul class="app-bar-pullmenubar hidden app-bar-menu"></ul></nav></div>


        <br>
        <br/>
        <br/>
        <br/>
<div class="flex-grid no-responsive-future" style="height: 100%;">
    <div class="row" style="height: 100%">
        <div class="cell size-x200" id="cell-sidebar" style="background-color: #71b1d1; height: 100%">

            <ul class="v-menu navy min-size-required">
                <li class="menu-title">First Title</li>
                <li><a href="#"><span class="mif-home icon"></span> Accueil </a></li>
                <li class="menu-title">Second Title</li>
                <li><a href="#"><span class="mif-user icon"></span> Profile</a></li>
                <li><a href="#"><span class="mif-calendar icon"></span> Calendar</a></li>
                <li><a href="#"><span class="mif-image icon"></span> Photo</a></li>
                <li class="menu-title">Third Title</li>
                <li>
                    <a href="#" class="dropdown-toggle"><span class="mif-my-location icon"></span> Location</a>
                    <ul class="d-menu" data-role="dropdown">
                        <li class="menu-title">Title for dropdown</li>
                        <li><a href="#">Коллеги</a></li>
                        <li><a href="#">Интересно</a></li>
                        <li>
                            <div class="item-block text-center">
                                <button class="square-button"><img class="icon" src="Metro/images/round.png"></button>
                                <button class="square-button"><img class="icon" src="Metro/images/location.png"></button>
                                <button class="square-button"><img class="icon" src="Metro/images/group.png"></button>
                            </div>
                        </li>
                        <li>
                            <a href="#" class="dropdown-toggle">Еще...</a>
                            <ul class="d-menu" data-role="dropdown">
                                <li><a href="#">Коллеги</a></li>
                                <li><a href="#">Интересно</a></li>
                                <li>
                                    <div class="item-block text-center">
                                        <button class="round-button"><img class="icon" src="Metro/images/round.png"></button>
                                        <button class="round-button"><img class="icon" src="Metro/images/location.png"></button>
                                        <button class="round-button"><img class="icon" src="Metro/images/group.png"></button>
                                        <button class="round-button"><img class="icon" src="Metro/images/power.png"></button>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li><a href="#"><span class="mif-bubbles icon"></span> Community</a></li>
            </ul>
        </div>
    </div>
</div>
