$(document).foundation()

function emailPopup() {
    const emailInput = document.getElementById("emailInput");
    const lastAtPos = emailInput.value.lastIndexOf("@");

    let email = lastAtPos > 0 && emailInput.indexOf('.', lastAtPos) > lastAtPos;

    if (email === true) {
        const popup = document.getElementById("emailGesendetPopup");
        popup.className = "show";
        setTimeout(function () {
            popup.className = popup.className.replace("show", "");
        }, 3000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const rmCheck = document.getElementById("rememberMe"),
        emailInput = document.querySelector(".Input[name='EMail']");

    if (rmCheck && emailInput) {
        if (localStorage.checkbox && localStorage.checkbox !== "") {
            rmCheck.checked = true;
            emailInput.value = localStorage.username;
        } else {
            rmCheck.checked = false;
            emailInput.value = "";
        }

        rmCheck.addEventListener('change', function () {
            lsRememberMe();
        });

        emailInput.addEventListener('input', function () {
            lsRememberMe();
        });
    }

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