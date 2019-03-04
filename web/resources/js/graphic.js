var xVal = sessionStorage.getItem("xPizda");
var yVal = sessionStorage.getItem("yPizda");
var rVal = sessionStorage.getItem("rPizda");

var r = 1;
var bt = document.getElementById("newXYR:but1");

function radiusclick(val) {
    /*if(bt!=undefined){
    bt.style.background=val.style.background;
    }
    val.style.backgroundColor='green';
    bt=val;*/
    sessionStorage.setItem("rPizda", val.value);
    try {
        drawCanvas('canvas', val.value);
    } catch (e) {
    }
    xVal = sessionStorage.getItem("xPizda");
    yVal = sessionStorage.getItem("yPizda");
    drawPoint("1.5", "1.5", val.value);

    document.getElementById('newXYR:r').value = val.value;
}
function init(){
    drawCanvas('canvas',document.getElementById('newXYR:r').value);
    xVal = sessionStorage.getItem("xPizda");
    yVal = sessionStorage.getItem("yPizda");
    rVal = sessionStorage.getItem("rPizda");
    if (xVal != undefined) {
        drawPoint(xVal, yVal, rVal);
    }
}

function drawCanvas(id, r){
    this.r = r;
    var canvas = document.getElementById(id),
        context = canvas.getContext("2d");
//очистка
    context.clearRect(0, 0, canvas.width, canvas.height);
//треугольник
    context.beginPath();
    // context.moveTo(150, 150);
    // context.lineTo(150, 85);
    // context.lineTo(280,150);
    // context.lineTo(150, 150);
    context.moveTo(150, 150);
    context.lineTo(150, 85);
    context.lineTo(85,150);
    context.lineTo(150, 150);
    context.closePath();
    context.strokeStyle = "#3BA4C7";
    context.fillStyle = "#3BA4C7";
    context.fill();
    context.stroke();

//прямоугольник
    context.beginPath();
    // context.rect(20, 150, 130, 130);
    context.rect(20, 150, 130, 65);
    context.closePath();
    context.strokeStyle = "#3BA4C7";
    context.fillStyle = "#3BA4C7";
    context.fill();
    context.stroke();

//сектор
    context.beginPath();
    context.moveTo(150, 150);
    context.arc(150, 150, 65, 0, Math.PI/2, false);
    context.closePath();
    context.strokeStyle = "#3BA4C7";
    context.fillStyle = "#3BA4C7";
    context.fill();
    context.stroke();

//отрисовка осей
    context.beginPath();
    context.font = "12px Verdana";
    context.moveTo(150, 0); context.lineTo(150, 300);
    context.moveTo(150, 0); context.lineTo(145, 12);
    context.moveTo(150, 0); context.lineTo(155, 12);
    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();

    context.beginPath();
    context.fillText("Y", 160, 10);
    context.moveTo(0, 150);
    context.lineTo(300, 150);
    context.moveTo(300, 150);
    context.lineTo(288, 145);
    context.moveTo(300, 150);
    context.lineTo(288, 155);
    context.fillText("X", 290, 135);
    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();

//деления X
    context.beginPath();
    context.moveTo(145, 20);
    context.lineTo(155, 20);
    context.moveTo(145, 85);
    context.lineTo(155, 85);
    context.moveTo(145, 215);
    context.lineTo(155, 215);
    context.moveTo(145, 280);
    context.lineTo(155, 280);
    if (r === 0){
        context.fillText("R", 160, 25);
        context.fillText("R/2", 160, 90);
        context.fillText("-R/2", 160, 220);
        context.fillText("-R", 160, 285);
    } else {
        context.fillText(r, 160, 25);
        context.fillText((r / 2), 160, 90);
        context.fillText(-(r / 2), 160, 220);
        context.fillText(-r, 160, 285);
    }

//деления Y
    context.moveTo(20, 145);
    context.lineTo(20, 155);
    context.moveTo(85, 145);
    context.lineTo(85, 155);
    context.moveTo(215, 145);
    context.lineTo(215, 155);
    context.moveTo(280, 145);
    context.lineTo(280, 155);
    if (r===0){
        context.fillText("-R", 12, 140);
        context.fillText("-R/2", 70, 140);
        context.fillText("R/2", 205, 140);
        context.fillText("R", 275, 140);
    } else {
        context.fillText(-r, 12, 140);
        context.fillText(-(r / 2), 70, 140);
        context.fillText((r / 2), 205, 140);
        context.fillText(r, 275, 140);
    }

    context.closePath();
    context.strokeStyle = "black";
    context.fillStyle = "black";
    context.stroke();
}

function clickCanvas(){
    var canvas = document.getElementById('canvas');
    var br = canvas.getBoundingClientRect();
    var left = br.left;
    var top = br.top;
    var event = window.event;
    var x = event.clientX-left;
    var y = event.clientY-top;
    var size = canvas.height;
    if (r>0) {
        x = Math.round((x - size / 2) * r * 10 / 2 / 65) / 10;
        y = Math.round((-y + size / 2) * r * 10 / 2 / 65) / 10;
        drawCanvas('canvas', r);
    //     document.getElementById("form:x_hidden").value = x;
    //     document.getElementById("form:Y").value = y;
    //     yVal = y;
    //     alert(x);
        drawPoint(x, y, r);
    //     document.getElementById('form:validationButton').click();
    //     drawAllPoints();

        document.getElementById('newXYR:y').value=y;
        document.getElementById('newXYR:x').value=x;

        sessionStorage.setItem("xPizda", x);
        sessionStorage.setItem("yPizda", y);
        sessionStorage.setItem("rPizda", r);

        document.getElementById("newXYR:check").click();

        // var request = new XMLHttpRequest();
        // request.open("POST", "http://localhost:8080/Drop_war_exploded/main.xhtml");
        // request.onreadystatechange = function (){
        //     alert("lol");
        // }
        // request.send("newXYR: newXYR&newXYR:x:=1&newXYR:y:=1&newXYR:r:=2&newXYR:j_idt24:=Check&javax.faces.ViewState:=6185241858636643211:-2645044552540146115");
    }
}

function drawPoint(x,y,r){
    var color;
    var canvas = document.getElementById('canvas'),
        ctx = canvas.getContext("2d");
    if (isArea(x,y,r)) {
        color = 'green';
    } else {
        color = 'red';
    }
    ctx.beginPath();
    ctx.arc(150+x*130/r,150-y*130/r,2,0,2*Math.PI);
    ctx.fillStyle = color;
    ctx.fill();
    ctx.closePath();
}

function isArea(x, y, r) {

    if((x <= 0 && y <= 0) && (x >= -r) && (y >= (-r/2))){
        return true;
    }
    else if(x <= 0 && y >= 0 && y <= (x+(r/2)) && x >= (-r/2) && y <= (r/2)){
        return true;
    }
    else if (x >= 0 && y <= 0 && (r/2) >= Math.sqrt(y*y+x*x)){
        return true;
    }
    else {
        return false;
    }
}
