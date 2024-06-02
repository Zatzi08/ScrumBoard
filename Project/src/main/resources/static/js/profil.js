
function toggleProfilMenü(uname, priDesc, workDesc){
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
}

window.onclick = function(event) {
    var profilPopup = document.getElementById("profilEditMenü");
    if (event.target == profilPopup) {
        profilPopup.style.display = "none";
    }
}

