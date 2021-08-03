/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

app.controller('UserController', function ($scope, $http) {

    $(document).ready(function () {
        InformationAdmin();
    });
    $scope.userData = [];
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
                    console.log($scope.userData);
                });
            },
            error: function (objXMLHttpRequest) {
                console.log(objXMLHttpRequest);
            }
        });
    }

});