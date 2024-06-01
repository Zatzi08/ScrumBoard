
function toggleProfilMenü(){
    var profilBtn = document.getElementById("profilEditBtn");
    var profilMenü = document.getElementById("profilEditMenü");

    if(profilMenü.style.display == "none") {
        profilMenü.style.display = "block";
    }
    else {
        profilMenü.style.display = "none";
    }

}

window.onclick = function(event) {
    var profilPopup = document.getElementById("profilEditMenü");
    if (event.target == profilPopup) {
        profilPopup.style.display = "none";
    }
}

