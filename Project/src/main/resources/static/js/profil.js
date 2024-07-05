function toggleProfilMenu(uname, workDesc, priDesc){
    const profilMenu = document.getElementById("profilEditMenü");

    if(profilMenu.style.display === "none") {
        profilMenu.style.display = "block";
    } else {
        profilMenu.style.display = "none";
    }

    document.getElementById('inputName').value = uname
    document.getElementById('inputPDesc').value = priDesc
    document.getElementById('inputWDesc').value = workDesc
}

var rolesList = document.getElementById('roleDropdown');
rolesList.getElementsByClassName('setRolesBtn')[0].onclick = function(evt) {
    if (rolesList.classList.contains('visible')) {
        rolesList.classList.remove('visible');
    }
    else
        rolesList.classList.add('visible');
}

window.onclick = function(event) {
    const profilPopup = document.getElementById("profilEditMenü");
    if (event.target === profilPopup) {
        profilPopup.style.display = "none";
    }
}

