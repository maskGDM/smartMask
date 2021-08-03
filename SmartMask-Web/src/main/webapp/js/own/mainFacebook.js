/* global FB */
// Load the SDK asynchronously
(function (d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id))
        return;
    js = d.createElement(s);
    js.id = id;
    js.src = "https://connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
$(function () {
    var app_id = '349151312939203';// id Local - Geovanny
    //var app_id = '';// id Remoto -

    var scopes = 'email, public_profile';
    var version = 'v7.0';
//   var btn_login = '<a href="#" id="facebook" class="btn btn-primary">Iniciar con Facebook</a>';
//    var div_session = "<div id='facebook-session'>" +
//            "<strong id=\"nombre\"></strong>" +
//            "<strong id=\"email\"></strong>" +
//            "<img>" +
//            "<a href='#' id='logout' class='btn btn-danger'>Cerrar sesión</a>" +
//            "</div>";

    window.fbAsyncInit = function () {

        FB.init({
            appId: app_id,
            status: true,
            cookie: true,
            xfbml: true,
            version: version
        });


        FB.getLoginStatus(function (response) {
            statusChangeCallback(response, function () { });
        });
    };

    var statusChangeCallback = function (response, callback) {
        // console.log(response);
        if (response.status === 'connected') {
            getFacebookData();
        } else {
            callback(false);
        }
    };

    var checkLoginState = function (callback) {
        FB.getLoginStatus(function (response) {
            callback(response);
        });
    };

    var name, email, gender, birthday;
    var getFacebookData = function () {
        FB.api('/me?fields=id, birthday, name, gender, email', function (response) {
            // $('#facebook').after(div_session);
            //$('#facebook').remove();
            //            $('#facebook-session strong').text("Bienvenido: " + response.name);
            //            $('#facebook-session strong').text("Su Email: " + response.email);
            //$('#nombre').text("Bienvenido: " + response.name);
            // name = response.name;
            console.log(response.name);
            //$('#birthday').text("Bienvenido: " + response.birthday);
            console.log(response.birthday);
            //$('#gender').text("Bienvenido: " + response.gender);
            console.log(response.gender);
            //$('#email').text("Su Email: " + response.email);
            //email = response.email;
            console.log(response.email);
            //$('#facebook-session img').attr('src', 'http://graph.facebook.com/' + response.id + '/picture?type=large');

            loginFacebook(response.id, response.name, response.email, 'http://graph.facebook.com/' + response.id + '/picture?type=large');
        });
    };

    var facebookLogin = function () {
        checkLoginState(function (data) {
            if (data.status !== 'connected') {
                FB.login(function (response) {
                    if (response.status === 'connected')
                        getFacebookData();
                }, {scope: scopes});
            }
        });
    };

    var facebookLogout = function () {
        //alert("He salido Facebook");
        checkLoginState(function (data) {
            if (data.status === 'connected') {
                FB.logout(function (response) {
                    document.getElementById("imageuser2").src = null;
                    document.getElementById("imageuser").src = null;
                    // $('#facebook-session').before(btn_login);
                    // $('#facebook-session').remove();
                });
            }
        });
    };

    function loginFacebook(id, name, email, img) {
        document.getElementById("imageuser2").src = "http://graph.facebook.com/" + id + "/picture?type=large";
        document.getElementById("imageuser").src = "http://graph.facebook.com/" + id + "/picture?type=large";

        var jsonData = {"Data": {"socialnetworkcode": id, "names": name, "email": email, "imguser": img, "socialnetworkid": 2}};
        console.log(jsonData);
        $.ajax({
            type: "Post",
            url: '../UserServlet',
            data: {"Type": 2, "Data": JSON.stringify(jsonData)},
            beforeSend: function (xhr) {
                //  loadingSwal();
            },
            success: function (data) {
                var dataR = JSON.parse(data);
                if (dataR.status === 200) {
                    swal({
                        title: "Welcome!",
                        text: "We are glad that you returned " + name,
                        icon: "success",
                        timer: 10000,
                        buttons: ["Return", "¡Ok!"]
                    }).then((result) => {
                        switch (result) {
                            case null:
                                facebookLogout();
                                break;
                            case true:
                                location.href = "../pages/initiation.html?iduser=" + dataR.user_id;
                                break;
                        }
                    });
                } else if (dataR.status === 300) {
                    swal("Hello " + name + ", you are not registered with Facebook in our application. Do you want to register?", {
                        buttons: ["Out", "Register"]
                    }).then((option) => {
                        if (option) {
                            registerFacebook(jsonData);
                        } else {
                            facebookLogout();
                        }
                    });
                }
            },
            error: function (objXMLHttpRequest) {
                console.log("Error:" + objXMLHttpRequest);
            }
        });
    }

    function registerFacebook(jsonData) {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 3, "Data": JSON.stringify(jsonData)},
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var dataRF = JSON.parse(data);
                if (dataRF.float === 200) {
                    swal({
                        title: "Welcome!",
                        text: "Now it's part of us",
                        icon: "success",
                        button: "Ok!"
                    });
                } else {
                    swal({
                        title: "Error!",
                        text: "Could not register with Facebook",
                        icon: "error",
                        button: "Ok!"
                    });
                }
            },
            error: function (objXMLHttpRequest) {
                console.log("Error:" + objXMLHttpRequest);
            }
        });
    }

    $(document).on('click', '#facebook', function (e) {
        e.preventDefault();
        facebookLogin();
    });


//    $(document).on('click', '#logout', function (e) {
//        e.preventDefault();
//        if (confirm("¿Está seguro?"))
//            facebookLogout();
//        else
//            return false;
//    });
//
});
