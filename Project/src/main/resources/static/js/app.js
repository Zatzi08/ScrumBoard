$(document).foundation()

function emailPopup() {
    var emailInput = document.getElementById("emailInput");
    var lastAtPos = emailInput.value.lastIndexOf("@");
    var email = true;

    if (lastAtPos > 0 && emailInput.indexOf('.', lastAtPos) > lastAtPos) email = true;
    else email = false;

    if (email === true) {
        var popup = document.getElementById("emailGesendetPopup");
        popup.className = "show";
        setTimeout(function () {
            popup.className = popup.className.replace("show", "");
        }, 3000);
    }
}

// <button type="button" onclick="resetCSS()">Reset</button>
function resetCSS() {
    let links = document.getElementsByTagName('link');
    for (let i = 0; i < links.length; i++) {
        if (links[i].getAttribute('rel') === 'stylesheet') {
            let href = links[i].getAttribute('href').split('?')[0];
            let newHref = href + '?version='
                + new Date().getMilliseconds();
            console.log(newHref)
            links[i].setAttribute('href', newHref);
        }
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    const rmCheck = document.getElementById("rememberMe"),
        emailInput = document.querySelector(".Input[name='EMail']");

    if (localStorage.checkbox && localStorage.checkbox !== "") {
        rmCheck.setAttribute("checked", "checked");
        emailInput.value = localStorage.username;
    } else {
        rmCheck.removeAttribute("checked");
        emailInput.value = "";
    }

    rmCheck.addEventListener('change', function () {
        lsRememberMe();
    });

    emailInput.addEventListener('input', function () {
        lsRememberMe();
    });

    function lsRememberMe() {
        if (rmCheck.checked && emailInput.value !== "") {
            localStorage.username = emailInput.value;
            localStorage.checkbox = rmCheck.value;
        } else {
            localStorage.username = "";
            localStorage.checkbox = "";
        }
    }
});

function emailPopup() {
    var emailInput = document.getElementById("emailInput");
    var lastAtPos = emailInput.value.lastIndexOf("@");
    var email = true;

    if (lastAtPos > 0 && emailInput.value.indexOf('.', lastAtPos) > lastAtPos) email = true;
    else email = false;

    if (email === true) {
        var popup = document.getElementById("emailGesendetPopup");
        popup.className = "show";
        setTimeout(function () {
            popup.className = popup.className.replace("show", "");
        }, 3000);
    }
}