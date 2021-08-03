/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function obtenerFecha() {
    var date = new Date();
    var d = date.getDate();
    var m = date.getMonth() + 1;
    var y = date.getFullYear();
    var h = date.getHours();
    var min = date.getMinutes();
    var sg = date.getSeconds();
    var msg = date.getMilliseconds();

    return  y + '-' + m + '-' + d;
}
function reload() {
    location.reload();
}

window.onload = function () {

    var contenedor = document.getElementById('contenedor_carga');
    contenedor.style.visibility = 'hidden';
    contenedor.style.opacity = '0';

    $.getJSON('http://ip-api.com/json', function (data) {
        console.log(data);
    });
};
function cargando() {
    var contenedor = document.getElementById('contenedor_carga');
    contenedor.style.visibility = 'visible';
    contenedor.style.opacity = '100';
}
function cargado() {
    var contenedor = document.getElementById('contenedor_carga');
    contenedor.style.visibility = 'hidden';
    contenedor.style.opacity = '0';
}
function regresar() {
    window.history.back();
}
function validateEmail(valor) {
    var emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (emailRegex.test(valor)) {
        return false;
    } else {
        return true;
    }
}
var idUser = location.href.split('iduser=');
var idAdmin = location.href.split('idadmin=');
function dailyData() {
    location.href = "../pages/dailydata.html?iduser=" + idUser[1];
}
function initiation() {
    location.href = "../pages/initiation.html?iduser=" + idUser[1];
}
function monthData() {
    location.href = "../pages/monthdata.html?iduser=" + idUser[1];
}
function reportData() {
    location.href = "../pages/reportdata.html?iduser=" + idUser[1];
}
function profile() {
    location.href = "../pages/profile.html?iduser=" + idUser[1];
}
function setting() {
    location.href = "../pages/setting.html?iduser=" + idUser[1];
}
function profileadmin() {
    location.href = "../pages/profileadmin.html?idadmin=" + idAdmin[1];
}
function admin() {
    location.href = "../pages/admin.html?idadmin=" + idAdmin[1];
}
function logOff() {
    location.href = "../pages/login.html";
}

function validateFile(op) {
    var fileInput;
    if (op === 1) {
        fileInput = document.getElementById('selectdImg');
    } else if (op === 2) {
        fileInput = document.getElementById('');
    }
    var filePath = fileInput.value;
    console.log(filePath);
    var allowedExtensions = /(.jpg|.jpeg|.png|.gif)$/i;
    if (!allowedExtensions.exec(filePath)) {
        swal('Please select an image in format(.jpg|.jpeg|.png|.gif)');
        fileInput.value = '';
        if (op === 1) {
            document.getElementById('selectdImg').src = null;
        } else if (op === 2) {
            document.getElementById('').src = null;
        }
        return false;
    } else {
        //Image preview
        if (fileInput.files && fileInput.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                if (op === 1) {
                    document.getElementById('imgSelect').src = e.target.result;
                } else if (op === 2) {
                    document.getElementById('').src = e.target.result;
                }
            };
            reader.readAsDataURL(fileInput.files[0]);
        }
    }
}


