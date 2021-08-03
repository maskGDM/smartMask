/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('ReportDataController', function ($scope, $http) {

    var flag = true;
    var dataTimeR = [];
    var ppm = true;
    var co2 = false;
    var co = false;
    var nh3 = false;
    var c4h10 = false;
    var hm = false;
    var pr = false;
    var te = false;
    var al = false;
    $scope.userData = [];
    $scope.alertData = [];
    $scope.dataairQuality = [];
    $scope.datacarbonDioxide = [];
    $scope.datacarbonMonoxide = [];
    $scope.dataammonia = [];
    $scope.dataButano = [];
    $scope.dataTemperature = [];
    $scope.dataHumidity = [];
    $scope.dataPressure = [];
    $scope.dataAltitude = [];

    $(document).ready(function () {
        InformationAdmin();
        selectData();
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

    $scope.airQualityGases = function (gas) {
        dataTimeR = [];
        dataPPMIR = [];
        var reportRange;
        if ($("#dateInit").val() !== "" && $("#dateEnd").val() !== "") {
            reportRange = {"Type": 7, "Datum": JSON.stringify({"Report": {"idUser": idUser[1], "dateInit": $("#dateInit").val(), "dateEnd": $("#dateEnd").val()}})};
        } else {
            reportRange = {"Type": 6, "Datum": idUser[1]};
        }
        switch (gas) {
            case 0:
                if (flag) {
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
                                        dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                        dataPPMIR.push(parsedData.result[i].ppminternal);
                                        if (parsedData.result[i].alert === 't') {
                                            $scope.alertData.push(parsedData.result[i]);
                                        }
                                    }
                                });
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
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
                        beforeSend: function (xhr) {
                        },
                        success: function (data) {
                            var parsedData = JSON.parse(data);
                            if (parsedData.status === 200) {
                                $scope.$apply(function () {
                                    $scope.datacarbonDioxide = parsedData.result;
                                });
                                for (var i = 0; i < parsedData.result.length; i++) {
                                    if ((parseInt(minCo2, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxCo2, 10))) {
                                        dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                        dataPPMIR.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo2, 10));
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
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
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
                                    if ((parseInt(minCo, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxCo, 10))) {
                                        dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                        dataPPMIR.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo, 10));
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
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
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
                                    if ((parseInt(minnh3, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxnh3, 10))) {
                                        dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                        dataPPMIR.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxnh3, 10));
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
                break;
            case 4:
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
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
                                    if ((parseInt(minC4h10, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxC4h10, 10))) {
                                        dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                        dataPPMIR.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxC4h10, 10));
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
            case 5:
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
                        beforeSend: function (xhr) {
                        },
                        success: function (data) {
                            var parsedData = JSON.parse(data);
                            if (parsedData.status === 200) {
                                $scope.$apply(function () {
                                    $scope.dataTemperature = parsedData.result;
                                    console.log($scope.dataTemperature);
                                });
                                for (var i = 0; i < parsedData.result.length; i++) {
                                    dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                    dataPPMIR.push((parseFloat(parsedData.result[i].temperature, 10)));// * 100) / parseInt(maxC4h10, 10));
                                }
                                graphTemperature();
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
            case 6:
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
                        beforeSend: function (xhr) {
                        },
                        success: function (data) {
                            var parsedData = JSON.parse(data);
                            if (parsedData.status === 200) {
                                $scope.$apply(function () {
                                    $scope.dataHumidity = parsedData.result;
                                    console.log($scope.dataHumidity);
                                });
                                for (var i = 0; i < parsedData.result.length; i++) {
                                    dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                    dataPPMIR.push((parseFloat(parsedData.result[i].humidity, 10)));// * 100) / parseInt(maxC4h10, 10));

                                }
                                graphHumidity();
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
            case 7:
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
                        beforeSend: function (xhr) {
                        },
                        success: function (data) {
                            var parsedData = JSON.parse(data);
                            if (parsedData.status === 200) {
                                $scope.$apply(function () {
                                    $scope.dataPressure = parsedData.result;
                                    console.log($scope.dataPressure);
                                });
                                for (var i = 0; i < parsedData.result.length; i++) {
                                    dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                    dataPPMIR.push((parseFloat(parsedData.result[i].pressure, 10)));// * 100) / parseInt(maxC4h10, 10));

                                }
                                graphPressure();
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
            case 8:
                if (flag) {
                    $.ajax({
                        type: "Post",
                        url: '../MaskServlet',
                        data: reportRange,
                        beforeSend: function (xhr) {
                        },
                        success: function (data) {
                            var parsedData = JSON.parse(data);
                            if (parsedData.status === 200) {
                                $scope.$apply(function () {
                                    $scope.dataAltitude = parsedData.result;
                                    console.log($scope.dataAltitude);
                                });
                                for (var i = 0; i < parsedData.result.length; i++) {
                                    dataTimeR.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                    dataPPMIR.push((parseFloat(parsedData.result[i].altitude, 10)));// * 100) / parseInt(maxC4h10, 10));

                                }
                                graphAltitude();
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
    };
    $scope.recargeAll = function () {
        flag = true;
        $scope.airQualityGases(0);

    };
    $scope.airQuality = function () {
        $scope.airQualityGases(0);
    };
    $scope.carbonDioxideData = function () {
        $scope.airQualityGases(1);
    };

    $scope.carbonMonoxideData = function () {
        $scope.airQualityGases(2);
    };

    $scope.ammoniaData = function () {
        $scope.airQualityGases(3);
    };

    $scope.butanoData = function () {
        $scope.airQualityGases(4);
    };
    $scope.temperatureData = function () {
        $scope.airQualityGases(5);
    };
    $scope.humidityData = function () {
        $scope.airQualityGases(6);
    };
    $scope.pressureData = function () {
        $scope.airQualityGases(7);
    };
    $scope.altitudeData = function () {
        $scope.airQualityGases(8);
    };
    function graphairQuality() {
        new Chart(ctxQualityR, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Internal air quality',
                        data: dataPPMIR,
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
        new Chart(ctxDioxideR, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Carbon Dioxide Concentration Internal',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(193, 247, 207, 0.5)'
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
        new Chart(ctxMonoxideR, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Monoxide Concentration Internal',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(237, 197, 185, 0.5)'
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
        new Chart(ctxAmmoniaR, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Amonia Concentration Internal',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(224, 187, 228, 0.5)'
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
        new Chart(ctxButanoR, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Butano Concentration Internal',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(171, 222, 237, 0.5)'
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
    function graphTemperature() {
        new Chart(ctxTemperature, {
            type: "line",
            data: {
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            return Number(tooltipItem.yLabel).toFixed(2);
                        }
                    }
                },
                legend: {
                    display: true,
                },
                labels: dataTimeR,
                datasets: [{
                        label: 'Temperature',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(227, 220, 161, 0.5)'
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
    function graphHumidity() {
        new Chart(ctxHumidity, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Humidity',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(255, 185, 179, 0.5)'
                        ]
                    }]
            },
            options: {
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            return Number(tooltipItem.yLabel).toFixed(2);
                        }
                    }
                },
                legend: {
                    display: true,
                },
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
    function graphPressure() {
        new Chart(ctxPressure, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Pressure',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(181, 206, 191, 0.5)'
                        ]
                    }]
            },
            options: {
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            return Number(tooltipItem.yLabel).toFixed(2);
                        }
                    }
                },
                legend: {
                    display: true,
                },
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
    function graphAltitude() {
        new Chart(ctxAltitude, {
            type: "line",
            data: {
                labels: dataTimeR,
                datasets: [{
                        label: 'Altitude',
                        data: dataPPMIR,
                        borderColor: [
                            'rgb(207, 201, 245, 19)'
                        ]
                    }]
            },
            options: {
                tooltips: {
                    callbacks: {
                        label: function (tooltipItem, data) {
                            return Number(tooltipItem.yLabel).toFixed(2);
                        }
                    }
                },
                legend: {
                    display: true,
                },
                animation: false,
                responsive: true,
                scales: {
                    xAxes: [{
                            display: true
                        },
//                        {
//                            ticks: {
//                                beginAtZero: true,
//                                stepSize: 3.5
//                            }}
                    ],
                    yAxes: [{
                            display: true
                        }]
                }
            }
        });
    }
    var ctxQualityR = document.getElementById("myChart0R").getContext("2d");//Air quality
    var ctxDioxideR = document.getElementById("myChartR").getContext("2d");//Dioxide
    var ctxMonoxideR = document.getElementById("myChart1R").getContext("2d");//Monoxide
    var ctxAmmoniaR = document.getElementById("myChart2R").getContext("2d");//Ammonia
    var ctxButanoR = document.getElementById("myChart3R").getContext("2d");//Nitrogen 
    var ctxTemperature = document.getElementById("myChart4R").getContext("2d");
    var ctxHumidity = document.getElementById("myChart5R").getContext("2d");
    var ctxPressure = document.getElementById("myChart6R").getContext("2d");
    var ctxAltitude = document.getElementById("myChart7R").getContext("2d");
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
function abrirModalPresion() {
    $("#staticBackdropTemperature").modal();
}
function abrirModalAltitud() {
    $("#staticBackdropHumidity").modal();
}