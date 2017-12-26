function showToast(status,message) {

    var x = document.getElementById("snackbar");
    var span = document.getElementById("icon");
    var text = document.getElementById("text");
    if(status == "ok"){
//            x.className += " " + "ok";
        x.classList.add("ok");
        span.classList.add("glyphicon","glyphicon-fire");
        text.innerHTML = message;
    }else if(status === "err"){
        x.classList.add("err");
        span.classList.add("glyphicon","glyphicon-warning-sign");
        text.innerHTML = message;
    }

    // Add the "show" class to DIV
    x.className += " " + "show";

    // After 3 seconds, remove the show class from DIV
    setTimeout(function () {
        x.className = x.className.replace("show", "");
        x.classList.remove('ok','err');
    }, 3000)
};