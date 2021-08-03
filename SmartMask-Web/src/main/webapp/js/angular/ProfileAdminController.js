/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//USER//
app.controller('ProfileAdminController', function ($scope, $http) {
    $(document).ready(function () {
        $scope.InformationAdmin();
    });
    $scope.userData = [];
    //var idUser = location.href.split('iduser=');
    $scope.InformationAdmin = function () {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 4, Datum: idAdmin[1]},
            beforeSend: function (xhr) {
                //  loadingSwal();
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.userData = parsedData.information[0];
                    console.log($scope.userData);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };
    $scope.editInformation = async function () {
        var password = $("#txtPasswordU").val();
        var rpassworrd = $("#txtRepeatPasswordU").val();
        var email = $("#txtEmailU").val();
        if ($("#dateDateofBirthU").val().length !== 0 && $("#txtNamesU").val().length !== 0 && $("#txtLastNamesU").val().length !== 0 && email.length !== 0 &&
                $("#txtUserU").val().length !== 0 && password.length !== 0 && rpassworrd.length !== 0) {
            if (password === (rpassworrd)) {
                var rutaImg = "";
                if ($('#selectdImg').get(0).files.length !== 0)
                {
                    const imageUploader = document.getElementById('selectdImg');
                    const CLOUDINARY_URL = 'https://api.cloudinary.com/v1_1/bricex/image/upload';
                    const CLOUDINARY_UPLOAD_PRESET = 'sobympqu';
                    const file = imageUploader.files[0];
                    const formData = new FormData();
                    formData.append('file', file);
                    formData.append('upload_preset', CLOUDINARY_UPLOAD_PRESET);
                    // Send to cloudianry
                    const res = await   axios.post(
                            CLOUDINARY_URL,
                            formData,
                            {headers: {
                                    'Content-Type': 'multipart/form-data'
                                }
                            }
                    );
                    rutaImg = res.data.secure_url;
                }
                if (rutaImg === "") {
                    rutaImg = $scope.userData.imguser;
                }
                $.ajax({
                    type: "Post",
                    url: '../User_informationServlet',
                    data: {"Type": 5, User: JSON.stringify({"User": {"names": $("#txtNamesU").val(), "lastnames": $("#txtLastNamesU").val(),
                                "email": email, "birthdaydate": $("#dateDateofBirthU").val(), "user": $("#txtUserU").val(), "password": password, "imguser": rutaImg, "user_informationid": idAdmin[1]}})},
                    beforeSend: function (xhr) {
                        //  loadingSwal();
                    },
                    success: function (data) {
                        var parsedData = JSON.parse(data);
                        if (parsedData.flag === 200) {
                            swal({
                                title: "Excellent!",
                                text: "It has been successfully updated!",
                                icon: "success",
                                button: "Ready!"
                            });
                            $scope.InformationAdmin();
                        } else {
                            swal({
                                title: "Error!",
                                text: "There was a problem and could not update. \n Please contact the administrators or try later",
                                icon: "error",
                                button: "Ok!"
                            });
                        }
                        // $scope.InformationAdmin();
                    },
                    error: function (objXMLHttpRequest) {
                        console.log(objXMLHttpRequest);
                    }
                });
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

    };

});