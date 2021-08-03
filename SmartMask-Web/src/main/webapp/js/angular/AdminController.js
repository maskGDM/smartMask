/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
app.controller('AdminController', function ($scope, $http) {
    $scope.userData = [];
    $scope.selectDataMask = [];
    $scope.tabletAdmin = [];
    $scope.tableUser = [];
    $scope.alertData = [];
    $scope.dataairQuality = [];
    $scope.tableMasks = [];

    $scope.tableCountry = [];
    $scope.tableProvince = [];
    $scope.tableCity = [];
    $(document).ready(function () {
        //bringCountryCMBX();
        InformationAdmin();
        $scope.selectData();
        $scope.airQualityGases();
    });

    $scope.informationAdmin = function () {
    };
    var idAdmin = location.href.split('idadmin=');
    function InformationAdmin() {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 2, Datum: idAdmin[1]},
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
    }

    $scope.configureDataAdmin = function () {
        if ($.isNumeric($("#maxvalueco2").val()) && $.isNumeric($("#minvalueco2").val()) && $.isNumeric($("#maxvalueco").val()) && $.isNumeric($("#minvalueco").val()) &&
                $.isNumeric($("#maxvaluec4h10").val()) && $.isNumeric($("#minvaluec4h10").val()) && $.isNumeric($("#maxvaluenh3").val()) && $.isNumeric($("#minvaluenh3").val())) {
            var jsonsend = {"Type": 2, "Datum": JSON.stringify({"data": {"user_informationid": idAdmin[1],
                        "maxvalueco2": $("#maxvalueco2").val(), "minvalueco2": $("#minvalueco2").val(),
                        "maxvalueco": $("#maxvalueco").val(), "minvalueco": $("#minvalueco").val(),
                        "maxvaluec4h10": $("#maxvaluec4h10").val(), "minvaluec4h10": $("#minvaluec4h10").val(),
                        "maxvaluenh3": $("#maxvaluenh3").val(), "minvaluenh3": $("#minvaluenh3").val()}})};
            $.ajax({
                type: "Post",
                url: '../Default_dataServlet',
                data: jsonsend,
                beforeSend: function (xhr) {
                },
                success: function (data) {
                    var parsedData = JSON.parse(data);
                    if (parsedData.status === 200) {
                        swal({
                            title: "Excellent!",
                            text: "The default settings have been set correctly",
                            icon: "success",
                            button: "Ready!"
                        });
                    } else {
                        swal({
                            title: "Oh No!",
                            text: "The default settings have not been set correctly.",
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

    $scope.selectData = function () {
        $.ajax({
            type: "Post",
            url: '../Default_dataServlet',
            data: {"Type": 1, Datum: idAdmin[1]},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.selectDataMask = parsedData.result[0];
                    console.log($scope.selectDataMask);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };
    $scope.viewMask = function () {
        $.ajax({
            type: "Post",
            url: '../Mask_producedServlet',
            data: {"Type": 3},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableMasks = parsedData.result;
                    console.log($scope.tableMasks);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }
    ;

    $scope.insertMask = function () {
        $.ajax({
            type: "Post",
            url: '../Mask_producedServlet',
            data: {"Type": 2, Datum: $("#txtMaskCode").val()},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                if (parsedData.status === 200) {
                    swal({
                        title: "Excellent!",
                        text: "The mask has been entered successfully!",
                        icon: "success",
                        button: "Ready!"
                    });
                    $scope.viewMask();
                } else {
                    swal({
                        title: "Oh No!",
                        text: "The mask could not be entered successfully",
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
    
    $scope.viewAdmins = function () {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 6},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tabletAdmin = parsedData.result;
                    console.log($scope.tabletAdmin);
                    $scope.viewUsers();
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };
    $scope.viewUsers = function () {
        $.ajax({
            type: "Post",
            url: '../User_informationServlet',
            data: {"Type": 7},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableUser = parsedData.result;
                    console.log($scope.tableUser);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    };

    $scope.airQualityGases = function () {
        dataTimeA = [];
        dataPPMEA = [];
        dataPPMIA = [];
        if ($.isNumeric($("#limitAdmin").val()) || $("#limitAdmin").val() === "") {
            if ($("#dateInit").val() !== "" && $("#dateEnd").val() !== "") {
                reportRange = {"Type": 9, "Limit": $("#limitAdmin").val(), "Datum": JSON.stringify({"Report": {"dateInit": $("#dateInit").val(), "dateEnd": $("#dateEnd").val()}})};
            } else {
                reportRange = {"Type": 8, "Limit": $("#limitAdmin").val()};
            }
            $.ajax({
                type: "Post",
                url: '../MaskServlet',
                data: reportRange,
                beforeSend: function (xhr) {
                },
                success: function (data) {
                    var parsedData = JSON.parse(data);
                    if (parsedData.status === 200) {
                        $scope.alertData = [];
                        $scope.$apply(function () {
                            $scope.dataairQuality = parsedData.result;
                            for (var i = 0; i < parsedData.result.length; i++) {
                                dataTimeA.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                dataPPMEA.push(parsedData.result[i].ppmexternal);
                                dataPPMIA.push(parsedData.result[i].ppminternal);
                                if (parsedData.result[i].alert === 't') {
                                    $scope.alertData.push(parsedData.result[i]);
                                }
                            }
                        });
                        console.log($scope.alertData);
                        graphairQualityReport();
                    } else {
                        flag = false;
                    }
                },
                error: function (objXMLHttpRequest) {
                    console.log(objXMLHttpRequest);
                }
            });
        }
    };
    var ctxQualityReportA = document.getElementById("ChartReport").getContext("2d");//Air quality 
    function graphairQualityReport() {
        new Chart(ctxQualityReportA, {
            type: "bar",
            data: {
                labels: dataTimeA,
                datasets: [{
                        label: 'External air quality',
                        data: dataPPMEA,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ],
                        backgroundColor: 'rgb(244, 176, 66, 0.5)'
                    }, {
                        label: 'Internal air quality',
                        data: dataPPMIA,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ],
                        backgroundColor: 'rgb(66, 134, 244, 0.5)'
                    }]
            },
            options: {
                animation: false,
                responsive: true,
                scales: {
                    xAxes: [{
                            display: true
                        }],
                    yAxes: [{
                            display: true
                        }]
                }
            }
        });
    }

    function bringCountryCMBX() {
        $.ajax({
            type: "Post",
            url: '../CountryServlet', //Country - Province - City
            data: {"Type": 1}, // Type 1 Country Subtype 2 select
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableCountry = parsedData.result;
                    console.log($scope.tableCountry);
                });
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }
    $('#selectcountry').change(function () {
        $.ajax({
            type: "Post",
            url: '../ProvinceServlet', //Country - Province - City
            data: {"Type": 1, "Value": $('#selectcountry').val()}, // Type 2 Province Subtype 2 select
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableProvince = parsedData.result;
                    console.log($scope.tableProvince);
                });
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    });
    $('#selectprovince').change(function () {
        $.ajax({
            type: "Post",
            url: '../CityServlet', //Country - Province - City
            data: {"Type": 1, "Value": $('#selectprovince').val()}, // Type 3 Province Subtype 2 select
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableCity = parsedData.result;
                    console.log($scope.tableCity);
                });
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    });

    /**/

    $('#selectcountryadd-city').change(function () {
        $.ajax({
            type: "Post",
            url: '../ProvinceServlet', //Country - Province - City
            data: {"Type": 1, "Value": $('#selectcountryadd-city').val()}, // Type 2 Province Subtype 2 select
            beforeSend: function (xhr) {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                $scope.$apply(function () {
                    $scope.tableProvince = parsedData.result;
                    console.log($scope.tableProvince);
                });
                $('.selectpicker').selectpicker('refresh');
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    });
});
function abrirModal0() {
    $("#staticBackdrop0").modal();
}
function abrirModalThingspeak() {
    $("#staticBackdropTS").modal();
}
