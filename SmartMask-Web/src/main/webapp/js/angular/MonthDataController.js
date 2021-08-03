/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('MonthDataController', function ($scope, $http) {

    var flag = true;
    var dataTimeM = [];
    var dataPPMEM = [];
    var ppm = true;
    var co2M = false;
    var coM = false;
    var nh3M = false;
    var c4h10 = false;
    $scope.userData = [];
    $scope.alertData = [];
    $scope.dataairQuality = [];
    $scope.datacarbonDioxide = [];
    $scope.datacarbonMonoxide = [];
    $scope.dataammonia = [];
    $scope.dataButano = [];

    $(document).ready(function () {
        InformationAdmin();
        selectData();
        airQualityGases(0);
        $scope.airQuality();
    });
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
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }
    var maxCo2 = 100, minCo2 = 0, maxCo = 100, minCo = 0, maxC4h10 = 100, minC4h10 = 100, maxnh3 = 100, minnh3 = 0;
    function selectData() {
        $.ajax({
            type: "Post",
            url: '../DataServlet',
            data: {"Type": 1, Datum: idUser[1]},
            beforeSend: function () {
            },
            success: function (data) {
                var parsedData = JSON.parse(data);
                maxCo2 = parsedData.result[0].maxvalueco2;
                minCo2 = parsedData.result[0].minvalueco2;
                maxCo = parsedData.result[0].maxvalueco;
                minCo = parsedData.result[0].minvalueco;
                maxC4h10 = parsedData.result[0].maxvaluec4h10;
                minC4h10 = parsedData.result[0].minvaluec4h10;
                maxnh3 = parsedData.result[0].maxvaluenh3;
                minnh3 = parsedData.result[0].minvaluenh3;
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }
    $scope.recargeAllMonth = function () {
        airQualityGases(0);
        airQualityGases(1);
        airQualityGases(2);
        airQualityGases(3);
        airQualityGases(4);
    };
    function airQualityGases(gas) {
        dataTimeM = [];
        dataPPMEM = [];
        dataPPMIM = [];
        switch (gas) {
            case 0:
                if (ppm)
                    if (flag) {
                        if ($.isNumeric($("#limitMonth").val()) || $("#limitMonth").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 5, "Datum": idUser[1], "Limit": $("#limitMonth").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.alertData = [];
                                        $scope.$apply(function () {
                                            $scope.dataairQuality = parsedData.result;
                                            for (var i = 0; i < parsedData.result.length; i++) {
                                                dataTimeM.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                dataPPMEM.push(parsedData.result[i].ppmexternal);
                                                dataPPMIM.push(parsedData.result[i].ppminternal);
                                                if (parsedData.result[i].alert === 't') {
                                                    $scope.alertData.push(parsedData.result[i]);
                                                }
                                            }
                                        });
                                        console.log($scope.alertData);
                                        graphairQuality();
                                    } else {
                                        flag = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 1:
                if (co2M)
                    if (flag) {
                        if ($.isNumeric($("#limitMonth").val()) || $("#limitMonth").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 5, "Datum": idUser[1], "Limit": $("#limitMonth").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.$apply(function () {
                                            $scope.datacarbonDioxide = parsedData.result;
                                            console.log($scope.datacarbonDioxide)
                                        });
                                        for (var i = 0; i < parsedData.result.length; i++) {
                                            if (parseInt(minCo2, 10) <= parseInt(parsedData.result[i].ppmexternal) && parseInt(parsedData.result[i].ppmexternal) <= parseInt(maxCo2, 10)
                                                    || (parseInt(minCo2, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxCo2, 10))) {
                                                dataTimeM.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                dataPPMEM.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxCo2, 10));
                                                dataPPMIM.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo2, 10));
                                            }
                                        }
                                        graphcarbonDioxide();
                                    } else {
                                        flag = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 2:
                if (coM)
                    if (flag) {
                        if ($.isNumeric($("#limitMonth").val()) || $("#limitMonth").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 5, "Datum": idUser[1], "Limit": $("#limitMonth").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    $scope.$apply(function () {
                                        $scope.datacarbonMonoxide = parsedData.result;
                                        console.log($scope.datacarbonMonoxide);
                                    });
                                    if (parsedData.status === 200) {
                                        for (var i = 0; i < parsedData.result.length; i++) {
                                            if (parseInt(minCo, 10) <= parseInt(parsedData.result[i].ppmexternal) && parseInt(parsedData.result[i].ppmexternal) <= parseInt(maxCo, 10)
                                                    || (parseInt(minCo, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxCo, 10))) {
                                                dataTimeM.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                dataPPMEM.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxCo, 10));
                                                dataPPMIM.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo, 10));
                                            }
                                        }
                                        graphcarbonMonoxide();
                                    } else {
                                        flag = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 3:
                if (nh3M) {
                    if (flag) {
                        if ($.isNumeric($("#limitMonth").val()) || $("#limitMonth").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 5, "Datum": idUser[1], "Limit": $("#limitMonth").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.$apply(function () {
                                            $scope.dataammonia = parsedData.result;
                                            console.log($scope.dataammonia);
                                        });
                                        for (var i = 0; i < parsedData.result.length; i++) {
                                            if ((parseInt(minnh3, 10) <= parseInt(parsedData.result[i].ppmexternal) && parseInt(parsedData.result[i].ppmexternal) <= parseInt(maxnh3, 10))
                                                    || (parseInt(minnh3, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxnh3, 10))) {

                                                dataTimeM.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                dataPPMEM.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxnh3, 10));
                                                dataPPMIM.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxnh3, 10));
                                            }
                                        }
                                        grapammonia();
                                    } else {
                                        flag = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                }
                break;
            case 4:
                if (c4h10)
                    if (flag) {
                        if ($.isNumeric($("#limitMonth").val()) || $("#limitMonth").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 5, "Datum": idUser[1], "Limit": $("#limitMonth").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.$apply(function () {
                                            $scope.dataButano = parsedData.result;
                                            console.log($scope.dataButano);
                                        });
                                        for (var i = 0; i < parsedData.result.length; i++) {
                                            if ((parseInt(minC4h10, 10) <= parseInt(parsedData.result[i].ppmexternal) && parseInt(parsedData.result[i].ppmexternal) <= parseInt(maxC4h10, 10))
                                                    || (parseInt(minC4h10, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxC4h10, 10))) {
                                                dataTimeM.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                dataPPMEM.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                                dataPPMIM.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            }
                                        }
                                        graphButano();
                                    } else {
                                        flag = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            default:
                break;
        }
    }

    $scope.airQuality = function () {
        ppm = true;
        //setInterval(airQualityGases, 5000, 0);
        co2M = false;
        coM = false;
        nh3M = false;
        c4h10 = false;
    };
    $scope.carbonDioxideData = function () {
        co2M = true;
        //setInterval(airQualityGases, 5000, 1);
        coM = false;
        nh3M = false;
        c4h10 = false;
        ppm = false;
        airQualityGases(1);
    };

    $scope.carbonMonoxideData = function () {
        coM = true;
        //setInterval(airQualityGases, 5000, 2);
        co2M = false;
        nh3M = false;
        c4h10 = false;
        ppm = false;
        airQualityGases(2);
    };

    $scope.ammoniaData = function () {
        nh3M = true;
        //setInterval(airQualityGases, 5000, 3);
        co2M = false;
        coM = false;
        c4h10 = false;
        ppm = false;
        airQualityGases(3);
    };

    $scope.butanoData = function () {
        c4h10 = true;
        //setInterval(airQualityGases, 5000, 4);
        co2M = false;
        coM = false;
        nh3M = false;
        ppm = false;
        airQualityGases(4);
    };

    function graphairQuality() {
        new Chart(ctxQualityM, {
            type: "line",
            data: {
                labels: dataTimeM,
                datasets: [{
                        label: 'External air quality',
                        data: dataPPMEM,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ]
                    }, {
                        label: 'Internal air quality',
                        data: dataPPMIM,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ]
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

    function graphcarbonDioxide() {
        new Chart(ctxDioxideM, {
            type: "line",
            data: {
                labels: dataTimeM,
                datasets: [{
                        label: 'Carbon Dioxide Concentration External',
                        data: dataPPMEM,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ]
                    }, {
                        label: 'Carbon Dioxide Concentration Internal',
                        data: dataPPMIM,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ]
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
    function graphcarbonMonoxide() {
        new Chart(ctxMonoxideM, {
            type: "line",
            data: {
                labels: dataTimeM,
                datasets: [{
                        label: 'Monoxide Concentration External',
                        data: dataPPMEM,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ]
                    }, {
                        label: 'Monoxide Concentration Internal',
                        data: dataPPMIM,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ]
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
    function grapammonia() {
        new Chart(ctxAmmoniaM, {
            type: "line",
            data: {
                labels: dataTimeM,
                datasets: [{
                        label: 'Amonia Concentration External',
                        data: dataPPMEM,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ]
                    }, {
                        label: 'Amonia Concentration Internal',
                        data: dataPPMIM,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ]
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
    function graphButano() {
        new Chart(ctxButanoM, {
            type: "line",
            data: {
                labels: dataTimeM,
                datasets: [{
                        label: 'Butano Concentration External',
                        data: dataPPMEM,
                        borderColor: [
                            'rgb(244, 176, 66, 0.5)'
                        ]
                    }, {
                        label: 'Butano Concentration Internal',
                        data: dataPPMIM,
                        borderColor: [
                            'rgb(66, 134, 244, 0.5)'
                        ]
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
    var ctxQualityM = document.getElementById("myChart0M").getContext("2d");//Air quality
    var ctxDioxideM = document.getElementById("myChartM").getContext("2d");//Dioxide
    var ctxMonoxideM = document.getElementById("myChart1M").getContext("2d");//Monoxide
    var ctxAmmoniaM = document.getElementById("myChart2M").getContext("2d");//Ammonia
    var ctxButanoM = document.getElementById("myChart3M").getContext("2d");//Nitrogen 
    //   $("#selectOutInData").change(function () {
//        if ($("#selectOutInData").val() === 1) {
//
//        } else {
//
//        }
//    });
});

function abrirModal3() {
    $("#staticBackdrop4").modal();
}
function abrirModal2() {
    $("#staticBackdrop3").modal();
}
function abrirModal1() {
    $("#staticBackdrop2").modal();
}
function abrirModal() {
    $("#staticBackdrop1").modal();
}
function abrirModal0() {
    $("#staticBackdrop0").modal();
}
function openModalAlert() {
    $("#modalAlert").modal();
}
function abrirModalTemperatura() {
    $("#staticBackdropTemperature").modal();
}
function abrirModalHumidity() {
    $("#staticBackdropHumidity").modal();
}