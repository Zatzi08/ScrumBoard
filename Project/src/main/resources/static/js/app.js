$(document).foundation()

function emailPopup(){
    var emailInput = document.getElementById("emailInput");
    var lastAtPos = emailInput.value.lastIndexOf("@");
    var email = true;

    if (lastAtPos > 0 && emailInput.indexOf('.', lastAtPos) > lastAtPos) email = true;
    else email = false;

    if(email === true) {
        var popup = document.getElementById("emailGesendetPopup");
        popup.className = "show";
        setTimeout(function () {
            popup.className = popup.className.replace("show", "");
        }, 3000);
    }
}
