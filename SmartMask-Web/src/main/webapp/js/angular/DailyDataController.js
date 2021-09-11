/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
try {
    Push.Permission.request();
} catch (e) {
    console.log(e);
}



app.controller('DailyDataController', function ($scope, $http) {

    var flagD = true;
    var dataTimeD = [];
    // var dataPPMED = [];
    var ppmD = true;
    var co2D = false;
    var coD = false;
    var nh3D = false;
    var c4h10D = false;
    var hm = false;
    var pr = false;
    var te = false;
    var al = false;
    var alertpushnotification = true;
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
    var maxCo2M = 100, minCo2 = 0, maxCo = 100, minCo = 0, maxC4h10 = 100, minC4h10 = 100, maxnh3 = 100, minnh3 = 0;
    function selectData() {
        $.ajax({
            type: "Post",
            url: '../DataServlet',
            data: {"Type": 1, Datum: idUser[1]},
            beforeSend: function () {
            },
            success: function (data) {

                var parsedData = JSON.parse(data);
                console.log(parsedData);
                maxCo2M = parsedData.result[0].maxvalueco2;
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
    $scope.recargeAllDaily = function () {
        flag = true;
        airQualityGases(0);

        airQualityGases(2);
        airQualityGases(3);
        airQualityGases(4);
        airQualityGases(5);
        airQualityGases(6);
        airQualityGases(7);
        airQualityGases(8);
    };

    function airQualityGases(gas) {
        dataTimeD = [];
        dataPPMID = [];
        switch (gas) {
            case 0:
                if (ppmD)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.alertData = [];
                                        $scope.$apply(function () {
                                            $scope.dataairQuality = parsedData.result;
                                            console.log($scope.dataairQuality);
                                            for (var i = 0; i < parsedData.result.length; i++) {
                                                dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                //dataPPMED.push(parsedData.result[i].ppmexternal);
                                                dataPPMID.push(parsedData.result[i].ppminternal);
                                                if (parsedData.result[i].alert === 't') {
                                                    $scope.alertData.push(parsedData.result[i]);
                                                }
                                            }
                                            try {
                                                if (alertpushnotification) {
                                                    if ($scope.alertData.length) {
                                                        Push.create('You may be in danger', {
                                                            body: 'Your smart mask has detected danger with ' + $scope.alertData[$scope.alertData.length - 1].ppminternal + 'ppmD internal',
                                                            icon: '../img/alertpush.jpg',
                                                            timeout: 4000, // Timeout before notification closes automatically.
                                                            vibrate: [100, 100, 100], // An array of vibration pulses for mobile devices.
                                                            onClick: function () {
                                                                // Callback for when the notification is clicked. 
                                                                openModalAlert();
                                                            }
                                                        });
                                                    }
                                                    alertpushnotification = false;
                                                }
                                            } catch (e) {
                                                console.log(e);
                                            }
                                            console.log($scope.alertData);
                                        });
                                        graphairQuality();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 1:
                if (co2D)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
                                beforeSend: function (xhr) {
                                },
                                success: function (data) {
                                    var parsedData = JSON.parse(data);
                                    if (parsedData.status === 200) {
                                        $scope.$apply(function () {
                                            $scope.datacarbonDioxide = parsedData.result;
                                            console.log($scope.datacarbonDioxide);
                                        });
                                        for (var i = 0; i < parsedData.result.length; i++) {
                                            if ((parseInt(minCo2, 10) <= parseInt(parsedData.result[i].ppminternal) && parseInt(parsedData.result[i].ppminternal) <= parseInt(maxCo2M, 10))) {
                                                dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxCo2M, 10));
                                                dataPPMID.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo2M, 10));
                                            }
                                        }
                                        graphcarbonDioxide();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 2:
                if (coD)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                                dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                // dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxCo, 10));
                                                dataPPMID.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxCo, 10));
                                            }
                                        }
                                        graphcarbonMonoxide();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 3:
                if (nh3D)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                                dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                // dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxnh3, 10));
                                                dataPPMID.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxnh3, 10));
                                            }
                                        }
                                        grapammonia();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 4:
                if (c4h10D)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                                dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                                //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                                dataPPMID.push((parseInt(parsedData.result[i].ppminternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            }
                                        }
                                        graphButano();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 5:
                if (te)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                            dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                            //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            dataPPMID.push((parseFloat(parsedData.result[i].temperature, 10)));// * 100) / parseInt(maxC4h10, 10));
                                        }
                                        graphTemperature();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 6:
                if (hm)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                            dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                            //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            dataPPMID.push((parseFloat(parsedData.result[i].humidity, 10)));// * 100) / parseInt(maxC4h10, 10));

                                        }
                                        graphHumidity();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 7:
                if (pr)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                            dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                            //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            dataPPMID.push((parseFloat(parsedData.result[i].pressure, 10)));// * 100) / parseInt(maxC4h10, 10));

                                        }
                                        graphPressure();
                                    } else {
                                        flagD = false;
                                    }
                                },
                                error: function (objXMLHttpRequest) {
                                    console.log(objXMLHttpRequest);
                                }
                            });
                    }
                break;
            case 8:
                if (al)
                    if (flagD) {
                        if ($.isNumeric($("#limitDaily").val()) || $("#limitDaily").val() === "")
                            $.ajax({
                                type: "Post",
                                url: '../MaskServlet',
                                data: {"Type": 3, "Datum": idUser[1], "Limit": $("#limitDaily").val()},
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
                                            dataTimeD.push(parsedData.result[i].datetimegistration.split('.')[0]);
                                            //dataPPMED.push((parseInt(parsedData.result[i].ppmexternal, 10)));// * 100) / parseInt(maxC4h10, 10));
                                            dataPPMID.push((parseFloat(parsedData.result[i].altitude, 10)));// * 100) / parseInt(maxC4h10, 10));

                                        }
                                        graphAltitude();
                                    } else {
                                        flagD = false;
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
        ppmD = true;
        setInterval(airQualityGases, 7000, 0);
        co2D = false;
        coD = false;
        nh3D = false;
        c4h10D = false;
        hm = false;
        pr = false;
        te = false;
        al = false;
    };
    $scope.carbonDioxideData = function () {
        co2D = true;
        setInterval(airQualityGases, 7000, 1);
        coD = false;
        nh3D = false;
        c4h10D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = false;
        al = false;
        airQualityGases(1);
    };

    $scope.carbonMonoxideData = function () {
        coD = true;
        setInterval(airQualityGases, 7000, 2);
        co2D = false;
        nh3D = false;
        c4h10D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = false;
        al = false;
        airQualityGases(2);
    };

    $scope.ammoniaData = function () {
        nh3D = true;
        setInterval(airQualityGases, 7000, 3);
        co2D = false;
        coD = false;
        c4h10D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = false;
        al = false;
        airQualityGases(3);
    };

    $scope.butanoData = function () {
        c4h10D = true;
        setInterval(airQualityGases, 7000, 4);
        co2D = false;
        coD = false;
        nh3D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = false;
        al = false;
        airQualityGases(4);
    };
    $scope.temperatureData = function () {
        c4h10D = false;
        setInterval(airQualityGases, 7000, 5);
        co2D = false;
        coD = false;
        nh3D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = true;
        al = false;
        airQualityGases(5);
    };
    $scope.humidityData = function () {
        c4h10D = false;
        setInterval(airQualityGases, 7000, 6);
        co2D = false;
        coD = false;
        nh3D = false;
        ppmD = false;
        hm = true;
        pr = false;
        te = false;
        al = false;
        airQualityGases(6);
    };
    $scope.pressureData = function () {
        c4h10D = false;
        setInterval(airQualityGases, 7000, 7);
        co2D = false;
        coD = false;
        nh3D = false;
        ppmD = false;
        hm = false;
        pr = true;
        te = false;
        al = false;
        airQualityGases(7);
    };
    $scope.altitudeData = function () {
        c4h10D = false;
        setInterval(airQualityGases, 7000, 8);
        co2D = false;
        coD = false;
        nh3D = false;
        ppmD = false;
        hm = false;
        pr = false;
        te = false;
        al = true;
        airQualityGases(8);
    };

    function graphairQuality() {
        new Chart(ctxQualityD, {
            type: "line",
            data: {
                labels: dataTimeD,
                datasets: [{
                        label: 'Internal air quality',
                        data: dataPPMID,
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
        new Chart(ctxDioxideD, {
            type: "line",
            data: {
                labels: dataTimeD,
                datasets: [{
                        label: 'Carbon Dioxide Concentration Internal',
                        data: dataPPMID,
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
        new Chart(ctxMonoxideD, {
            type: "line",
            data: {
                labels: dataTimeD,
                datasets: [{
                        label: 'Monoxide Concentration Internal',
                        data: dataPPMID,
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
        new Chart(ctxAmmoniaD, {
            type: "line",
            data: {
                labels: dataTimeD,
                datasets: [{
                        label: 'Amonia Concentration Internal',
                        data: dataPPMID,
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
        new Chart(ctxButanoD, {
            type: "line",
            data: {
                labels: dataTimeD,
                datasets: [{
                        label: 'Butano Concentration Internal',
                        data: dataPPMID,
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
                labels: dataTimeD,
                datasets: [{
                        label: 'Temperature',
                        data: dataPPMID,
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
                labels: dataTimeD,
                datasets: [{
                        label: 'Humidity',
                        data: dataPPMID,
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
                labels: dataTimeD,
                datasets: [{
                        label: 'Pressure',
                        data: dataPPMID,
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
                labels: dataTimeD,
                datasets: [{
                        label: 'Altitude',
                        data: dataPPMID,
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

    var ctxQualityD = document.getElementById("myChart0D").getContext("2d");//Air quality
    var ctxDioxideD = document.getElementById("myChartD").getContext("2d");//Dioxide
    var ctxMonoxideD = document.getElementById("myChart1D").getContext("2d");//Monoxide
    var ctxAmmoniaD = document.getElementById("myChart2D").getContext("2d");//Ammonia
    var ctxButanoD = document.getElementById("myChart3D").getContext("2d");//Nitrogen 
    var ctxTemperature = document.getElementById("myChart4D").getContext("2d");
    var ctxHumidity = document.getElementById("myChart5D").getContext("2d");
    var ctxPressure = document.getElementById("myChart6D").getContext("2d");
    var ctxAltitude = document.getElementById("myChart7D").getContext("2d");


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
    $("#staticBackdrop0").modal();
}
function abrirModalTemperatura() {
    $("#staticBackdropTemperature").modal();
}
function abrirModalHumidity() {
    $("#staticBackdropHumidity").modal();
}
function abrirModalPresion() {
    $("#staticBackdropPresion").modal();
}
function abrirModalAltitud() {
    $("#staticBackdropAltitud").modal();
}
