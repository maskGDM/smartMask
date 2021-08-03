/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function userJSON(tipoU) {
    var password = $("#txtPassword").val();
    var rpassworrd = $("#txtRepeatPassword").val();
    var email = $("#txtEmail").val();
    if ($("#dateDateofBirth").val().length !== 0 && $("#txtNames").val().length !== 0 && $("#txtLastNames").val().length !== 0 && email.length !== 0 &&
            $("#txtUser").val().length !== 0 && password.length !== 0 && rpassworrd.length !== 0) {
        if (password === (rpassworrd)) {
            if (validateEmail(email) === true) {
                swal({
                    title: "Invalid email!",
                    text: "Please enter a valid email",
                    icon: "error",
                    button: "Understood"
                });
            } else {
                var userInformation = {"User": {"names": $("#txtNames").val(), "lastnames": $("#txtLastNames").val(),
                        "email": email, "birthdaydate": $("#dateDateofBirth").val(), "user": $("#txtUser").val(), "password": password}};
                $.ajax({
                    type: "Post",
                    url: '../User_informationServlet',
                    data: {"Type": 1, "Rol": tipoU, "User": JSON.stringify(userInformation)},
                    beforeSend: function () {
                        cargando();
                    },
                    success: function (data) {
                        console.log(data);
                        cargado();
                        var parsedData = JSON.parse(data);
                        if (parsedData.float === 200) {
                            swal({
                                title: "Excellent!",
                                text: "You have successfully registered!",
                                icon: "success",
                                button: "Ready!"
                            });
                            if (tipoU === 1) {
                                location.href = parsedData.resultPage;
                            } else {
                                reload();
                            }
                        } else {
                            swal({
                                title: "Error!",
                                text: "There was a problem and it couldn't be registered. \n Please contact the administrators",
                                icon: "error",
                                button: "Ready!"
                            });
                        }
                    },
                    error: function (objXMLHttpRequest) {
                        console.log(objXMLHttpRequest);
                    }
                });
            }
        } else {
            swal({
                title: "Ups!",
                text: "The passwords entered are not the same",
                icon: "warning",
                button: "Understood"
            });
        }
    } else {
        swal({
            title: "Error!",
            text: "Please enter the required data",
            icon: "error",
            button: "Understood"
        });
    }
}


function login() {
    if ($("#txtinsertUser").val().length !== 0 && $("#txtInsertPassword").val().length !== 0) {
        var dat = {"User": {"user": $("#txtinsertUser").val(), "password": $("#txtInsertPassword").val()}};
        $.ajax({
            type: "Post",
            url: '../UserServlet',
            data: {"Type": 1, "User": JSON.stringify(dat)},
            beforeSend: function () {
                cargando();
            },
            success: function (data) {
                cargado();
                var parsedData = JSON.parse(data);
                if (parsedData.status === 400) {
                    swal({
                        title: "Ups",
                        text: "The data entered is not correct or you are not registered",
                        icon: "error",
                        button: "Understood"
                    });
                } else if (parsedData.status === 200) {
                    location.href = parsedData.access;
                }
            },
            error: function (objXMLHttpRequest) {
                cargado();
                console.log(objXMLHttpRequest);
            }
        });
    } else {
        swal({
            title: "Apparently you have not entered the required fields",
            text: "Please enter the required data",
            icon: "warning",
            button: "Understood"
        });
    }
}


