$(document).foundation()

function emailPopup(){
    var popup = document.getElementById("emailGesendetPopup");
    popup.className = "show";
    setTimeout(function(){
        popup.className = popup.className.replace("show", ""); }, 3000);
}
