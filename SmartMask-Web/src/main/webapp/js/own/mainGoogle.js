//
//    
//    $(document).on('click', '#my-signin', function (e) {
//     
//        init();
//    });

$(document).ready(function () {
    
    init();
    init2();

});
function init() {
    gapi.load('auth2', function () {
        auth2 = gapi.auth2.init({
            client_id: '996301579723-guoefskp57e2ss5a4cbp571amb1af643.apps.googleusercontent.com', //Remoto Brito   AIzaSyCh4e1i06KcGmFwGeTHQ5VFy7fBTVvyW2A
            //   client_id: '8208404572-ctmv73p9vknn6juvhum3rldnccn27qff.apps.googleusercontent.com', //Local Brito
            fetch_basic_profile: true, //datos extras
            scope: 'profile'
        });
        gapi.signin2.render("my-signin", {
            scope: 'email',
            width: 250,
            height: 50,
            longtitle: true,
            //theme: 'none',

            onsuccess: onSignIn,
            onfailure: onFailure
        });
    });
}
function init2() {
    gapi.load('auth2', function () {
        auth2 = gapi.auth2.init({
            client_id: '996301579723-guoefskp57e2ss5a4cbp571amb1af643.apps.googleusercontent.com', //Remoto Brito 
            //   client_id: '8208404572-ctmv73p9vknn6juvhum3rldnccn27qff.apps.googleusercontent.com', //Local Brito
            fetch_basic_profile: true, //datos extras
            scope: 'profile'
        });
        gapi.signin2.render("my-signin2", {
            scope: 'email',
            width: 250,
            height: 50,
            longtitle: true,
            //theme: 'none',

            onsuccess: onSignIn,
            onfailure: onFailure
        });
    });
}
function onFailure(error) {
    console.log(error);
}

function signOut() {
    // alert("He salido Google");
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        document.getElementById("imageuser").src = null;
        document.getElementById("imageuser2").src = null;
        console.log('User signed out.');
    });
}
function onSignIn(googleUser) {
    console.log("on sign in, granted scopes: " + googleUser.getGrantedScopes());
    console.log("ID token: " + googleUser.getAuthResponse().id_token);
    var profile = googleUser.getBasicProfile();
    var message = 'ID: ' + profile.getId() + "\n"
            + 'Name: ' + profile.getName() + "\n"
            + 'Image URL: ' + profile.getImageUrl() + "\n"
            + 'Email: ' + profile.getEmail();
    console.log(message);
    // alert(profile.getImageUrl());
    loginGoogle(profile.getId(), profile.getName(), profile.getEmail(), profile.getImageUrl());
    document.getElementById("imageuser").src = profile.getImageUrl();
    document.getElementById("imageuser2").src = profile.getImageUrl();
}

function loginGoogle(id, name, email, img) {
    document.getElementById("imageuser2").src = img;
    document.getElementById("imageuser").src = img;
    var jsonData = {"Data": {"socialnetworkcode": id, "names": name, "email": email, "imguser": img, "socialnetworkid": 3}};
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
                            signOut();
                            break;
                        case true:
                            location.href =  "../pages/initiation.html?iduser=" + dataR.user_id;
                            break;
                    }
                });
            } else if (dataR.status === 300) {
                swal("Hello " + name + ", you are not registered with Facebook in our application. Do you want to register?", {
                    buttons: ["Out", "Register"]
                }).then((option) => {
                    if (option) {
                        registerGoogle(jsonData);
                    } else {
                        signOut();
                    }
                });
            }
        },
        error: function (objXMLHttpRequest) {
            console.log("Error:" + objXMLHttpRequest);
        }
    });
}
function registerGoogle(jsonData) {
    $.ajax({
        type: "Post",
        url: '../User_informationServlet',
        data: {"Type": 3, "Data": JSON.stringify(jsonData)},
        beforeSend: function (xhr) {
        },
        success: function (data) {
            var dataRF = JSON.parse(data)
            if (dataRF.float === 200) {
                swal({
                    title: "Welcome!",
                    text: "Now it's part of us",
                    icon: "success",
                    button: "Ok!",
                });
            } else {
                swal({
                    title: "Error!",
                    text: "Could not register with Google",
                    icon: "error",
                    button: "Ok!",
                });
            }
        },
        error: function (objXMLHttpRequest) {
            console.log("Error:" + objXMLHttpRequest);
        }
    });
  
}