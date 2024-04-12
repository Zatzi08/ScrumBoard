/*
* Author: Zana Salih Hama
* Revisited: /
* Funktion: Toggeln des Bearbeitungsmenü'
* Grund: /
* User-Story/Task-ID: U3.F1, U4.F1
*/

function EditBox() {
    /*Todo: onClickEvent() für PopUp der Edit/AddBox als Methode (PK)*/
}
function toggleEditBox(storyId, name, description){
    EditBox();
    document.getElementById("inputName").textContent = name;
    document.getElementById("inputDesc").textContent = description;
    document.getElementById("editId").value = storyId;
}
/*
* Author: Zana Salih Hama
* Revisited: /
* Funktion: Highlighten der Prioritätsbuttons im Bearbeitungsfenster bei Auswahl
* Grund: Benutzerfreundlichkeit
* User-ID/Task-ID: /
*/
function highlightPriorityButton(buttonId) {
    const priorityButtons = document.querySelectorAll('.inputEditPrio');
    switch (buttonId){
        case 'inputPrioUrgent' : document.getElementById('editPrio').value = 4; break;
        case 'inputPrioHigh' : document.getElementById('editPrio').value = 3; break;
        case 'inputPrioNormal' : document.getElementById('editPrio').value = 2; break;
        default : document.getElementById('editPrio').value = 1;
    }
    priorityButtons.forEach(button => {
        if (button.id === buttonId) {
            button.classList.add('selected');
        } else {
            button.classList.remove('selected');
        }
    });
}