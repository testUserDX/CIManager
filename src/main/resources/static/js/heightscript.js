var height = window.screen.height;

if(height >= 550 && height < 1000){
    var elem = document.getElementById('panelwrapper');
    elem.style.height = '80vh';
}
else if(height >= 1000){
    var elem = document.getElementById('panelwrapper');
    elem.style.height = '85vh';
}