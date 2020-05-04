<%-- 
    Document   : ModeleCreationVisiteur
    Created on : 28 juil. 2016, 16:48:41
    Author     : Gauss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<label> Nom du visiteur <span class="requis">*</span></label>
<div class="input-control text info" style="margin-left: 110px"><input type="text" id="nomVisiteur" name="nomVisiteur" value="" size="30" maxlength="30" placeholder="Nom du visiteur"> </div>
<%-- <span class="erreur"> ${form.erreurs['nomClient']} </span> --%>
<br />
<br />
<label> Prénom du Visiteur </label> 
<div class="input-control text info" style="margin-left: 100px"><input class="input-control text" type="text" id="prenomVisiteur" name="prenomVisiteur" value="" size="30" maxlength="30" placeholder="Prenom du visiteur" /></div>
<br />
<br />

