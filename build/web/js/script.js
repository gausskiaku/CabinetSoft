$(document).ready(function() {
//      Connexion
    $(function() {
        $("#connexion").on('click', function() {
            $.Dialog({
                overlay: false,
                shadow: true,
                flat: true,
                draggable: true,
                icon: '<img src="images/ico/key.png">',
                title: 'Formulaire de Connexion',
                content: '',
                padding: 10,
                width: 60,
                height: 300,
                onShow: function(_dialog) {

                    var content = '<div class="grid">' +
                            '<div class="row">' +
                            '<div class="span4">' +
                            '<center><img src="images/ico/user.png"></center>' +
                            '<form class="user-input" method="post">' +
                            '<label>Login</label>' +
                            '<div class="input-control text"><input id="login" type="text" name="login"/></div>' +
                            '<label>Password</label>' +
                            '<div class="input-control password"><input id="password" type="password" name="password"/></div>' +
                            '<br><br>' +
                            '<input id="valider" class="button primary place-right" value="login"/>' +
                            '</form>' +
                            '</div>' +
                            '</div>' +
                            '</div>';
                    $("#continuer").hide('slow');
                    $.Dialog.title("Connexion");
                    $.Dialog.content(content);
                    $("#valider").click(function() {
                        var login = $("#login").val();
                        var password = $("#password").val();
                        var message = "";
                        alert(login);
                        $.ajax({
                            url: "controllers/login.php",
                            type: "post",
                            data: "login=" + login + "&password=" + password,
                            success: function(data) {
                                switch (data) {
                                    case "user":
                                        message="Vous êtes connecté"
                                        window.location = location.pathname;
                                        break;
                                    default :
                                        message="Erreur, nous n'arrivons pas à vous connecté"
                                        break;
                                }

                                //affichage de la notificatin
                                var not = $.Notify({
                                    caption: "Confirmation",
                                    content: "Vous êtes connecté",
                                    timeout: 5000, // 5 seconds
                                    style: {background: '#4390df', color: 'white'}
                                });
                            },
                            error: function(data) {

                                var not = $.Notify({
                                    caption: "Erreur !",
                                    content: "erreur",
                                    timeout: 5000, // 5 seconds
                                    style: {background: '#4390df', color: 'white'}
                                });
                            }
                        });
                    });
                }
            });
        });
    });
//      Deconnexion
    $(function() {
        $("#logout").on('click', function() {

            $.ajax({
                url: "controllers/logout.php",
                success: function(data) {
                    window.location = 'index.php';
                    var not = $.Notify({
                        caption: "Confirmation",
                        content: "Vous êtes maintenant deconnecté",
                        timeout: 5000, // 5 seconds
                        style: {background: '#4390df', color: 'white'}
                    });
                }
            });
        });
    });
});