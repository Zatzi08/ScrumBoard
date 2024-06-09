function toggleProfilMenü(uname, workDesc, priDesc){
    var profilBtn = document.getElementById("profilEditBtn");
    var profilMenü = document.getElementById("profilEditMenü");

    if(profilMenü.style.display == "none") {
        profilMenü.style.display = "block";
    } else {
        profilMenü.style.display = "none";
    }

    document.getElementById('inputName').value = uname
    document.getElementById('inputPDesc').value = priDesc
    document.getElementById('inputWDesc').value = workDesc

    // Hier habe ich's getestet und er gibt für priDesc und workDesc in den Placeholdern null aus, kann es aber im profil anzeigen
    // document.getElementById('inputName').placeholder = uname
    // document.getElementById('inputPDesc').placeholder = priDesc
    // document.getElementById('inputWDesc').placeholder = workDesc
}

window.onclick = function(event) {
    var profilPopup = document.getElementById("profilEditMenü");
    if (event.target == profilPopup) {
        profilPopup.style.display = "none";
    }
}

