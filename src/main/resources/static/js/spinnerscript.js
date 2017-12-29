function showCover() {
    var coverDiv = document.createElement('div');
    coverDiv.id = 'cover-div';
    document.body.appendChild(coverDiv);
}

function hideCover() {
    document.body.removeChild(document.getElementById('cover-div'));
}
function showSpinner() {
    showCover();
    var container = document.getElementById('spinner-container');
    container.style.display = 'block';

}
function removeSpinner() {
    hideCover();
    var container = document.getElementById('spinner-container');
    container.style.display ='none';
}
