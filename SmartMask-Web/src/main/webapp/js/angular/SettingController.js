/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function openAddModal() {
    $("#modalAddMask").modal();
}

function modalmyMask() {
    $("#modalInformationMask").modal();
}

app.controller('SettingController', function ($scope, $http) {
    $(document).ready(function () {
        InformationAdmin();
        MaskAdd();
        $scope.selectData();
    });
    $scope.userData = [];
    $scope.countMask = [];
    $scope.selectDataMask = [];
    //var idUser = location.href.split('iduser=');
    function InformationAdmin() {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 2, Datum: idUser[1]},
            beforeSend: function (xhr) {
                //  loadingSwal();
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.userData = parsedData.information[0];
                    // console.log($scope.userData);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }

    function MaskAdd() {
        document.getElementById('dataMask').style.display = 'none';
        $.ajax({
            type: "Post",
            url: '../MaskServlet',
            data: {"Type": 1, Datum: idUser[1]},
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.countMask = parsedData.result;
                    console.log($scope.countMask);
                    if ($scope.countMask.length === 0) {
                        document.getElementById("addMask").innerHTML += "<div class=\"card text-center\">\n" +
                                "  <div class=\"card-header\">\n" +
                                "    You don't have our product added\n" +
                                "  </div>\n" +
                                "  <div class=\"card-body\">\n" +
                                "    <h5 class=\"card-title\">¡Add your smart mask!</h5>\n" +
                                "    <p class=\"card-text\">You will find the unique code of the mask in the instruction booklet</p>\n" +
                                "    <button  class=\"btn btn-primary\" id=\"btnaddMask\" onclick=\"openAddModal();\">Add now</button>\n" +
                                "  </div>\n" +
                                "  <div class=\"card-footer text-muted\">\n" +
                                "    If you don't have one, what are you waiting to buy it?\n" +
                                "  </div>\n" +
                                "</div>";
                    } else {
                        document.getElementById('dataMask').style.display = 'block';
                    }
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }

    $scope.configureData = function () {
        if ($.isNumeric($("#maxvalueco2").val()) && $.isNumeric($("#minvalueco2").val()) && $.isNumeric($("#maxvalueco").val()) && $.isNumeric($("#minvalueco").val()) &&
                $.isNumeric($("#maxvaluec4h10").val()) && $.isNumeric($("#minvaluec4h10").val()) && $.isNumeric($("#maxvaluenh3").val()) && $.isNumeric($("#minvaluenh3").val())) {
            var jsonsend = {"Type": 2, "Datum": JSON.stringify({"data": {"user_informationid": idUser[1],
                        "maxvalueco2": $("#maxvalueco2").val(), "minvalueco2": $("#minvalueco2").val(),
                        "maxvalueco": $("#maxvalueco").val(), "minvalueco": $("#minvalueco").val(),
                        "maxvaluec4h10": $("#maxvaluec4h10").val(), "minvaluec4h10": $("#minvaluec4h10").val(),
                        "maxvaluenh3": $("#maxvaluenh3").val(), "minvaluenh3": $("#minvaluenh3").val()}})};
            $.ajax({
                type: "Post",
                url: '../DataServlet',
                data: jsonsend,
                beforeSend: function (xhr) {
                },
                success: function (data) {
                    var parsedData = JSON.parse(data);
                    if (parsedData.status === 200) {
                        swal({
                            title: "Excellent!",
                            text: "Your settings have been saved successfully!",
                            icon: "success",
                            button: "Ready!"
                        });
                    } else {
                        swal({
                            title: "Oh No!",
                            text: "Your settings have not been saved correctly",
                            icon: "warning",
                            button: "Ok!"
                        });
                    }
                },
                error: function (objXMLHttpRequest) {
                    console.log(objXMLHttpRequest);
                }
            });
        } else {
            swal({
                title: "Error!",
                text: "Please enter the required data",
                icon: "error",
                button: "Understood"
            });
        }
    };

    $scope.addMaskCode = function () {
        if ($.isNumeric($("#txtMask").val())) {
            var jsonsend = {"Type": 2, "Datum": JSON.stringify({"mask": {"user_informationid": idUser[1], "mask_code": $("#txtMask").val()}})};
            $.ajax({
                type: "Post",
                url: '../MaskServlet',
                data: jsonsend,
                beforeSend: function () {
                },
                success: function (data) {
                    var parsedData = JSON.parse(data);
                    if (parsedData.status === 200) {
                        swal({
                            title: "Excellent!",
                            text: "Your Mask has been successfully registered!",
                            icon: "success",
                            button: "Ready!"
                        });
                        document.getElementById('addMask').style.display = 'none';
                        document.getElementById('dataMask').style.display = 'block';
                        MaskAdd();
                    } else if (parsedData.status === 300) {
                        swal({
                            title: "Ups!",
                            text: "This mask is already registered",
                            icon: "warning",
                            button: "Ok!"
                        });
                    } else if (parsedData.status === 400) {
                        swal({
                            title: "Oh No!",
                            text: "You are entering the wrong code. Please try again with the mask code",
                            icon: "error",
                            button: "Ok!"
                        });
                    }
                },
                error: function (objXMLHttpRequest) {
                    console.log(objXMLHttpRequest);
                }
            });
        } else {
            swal({
                title: "Oh No!",
                text: "You are entering the wrong code. Please try again with the mask code",
                icon: "warning",
                button: "Ok!"
            });
        }
    };

    $scope.restoreMask = function () {

        $.ajax({
            type: "Post",
            url: '../MaskServlet',
            data: {"Type": 4, Datum: idUser[1]},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                if (parsedData.status === 200) {
                    swal({
                        title: "Excellent!",
                        text: "Data has been reset successfully!",
                        icon: "success",
                        button: "Ready!"
                    });
                    $scope.selectData();
                } else {
                    swal({
                        title: "Oh No!",
                        text: "Data could not be restored",
                        icon: "warning",
                        button: "Ok!"
                    });
                }
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };

    $scope.selectData = function () {
        $.ajax({
            type: "Post",
            url: '../DataServlet',
            data: {"Type": 1, Datum: idUser[1]},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.selectDataMask = parsedData.result[0];
                    //console.log($scope.selectDataMask);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };

    $scope.deleteMask = function () {
        $.ajax({
            type: "Post",
            url: '../Mask_producedServlet',
            data: {"Type": 1, Datum: $("#maskCode").text()},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                if (parsedData.status === 200) {
                    swal({
                        title: "It's okay!",
                        text: "Your mask has been removed",
                        icon: "success",
                        button: "Ready!"
                    });
                    MaskAdd();
                } else {
                    swal({
                        title: "Ups!",
                        text: "Could not delete your mask has been deleted",
                        icon: "error",
                        button: "Ok!"
                    });
                }
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });

    };



});