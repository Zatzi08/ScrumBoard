/*
* Author: Paula Krasnovska
* Revisited: /
* Funktion: Dropdown für Ansicht-Button
* Grund: /
* User-Story/Task-ID: /
*/
function ansichtDropdown() {
    document.getElementById("ansichtDropdownContent").classList.toggle("show");
}

/*
* Author: Paula Krasnovska
* Revisited: /
* Funktion: Dropdown für Filter-Button
* Grund: /
* User-Story/Task-ID: U1.F1
 */
function filterDropdown() {
    document.getElementById("filterDropdownContent").classList.toggle("show");
}

window.onclick = function(event) {
    if (!event.target.matches('.ansichtBtn')) {
        document.getElementById("ansichtDropdownContent").classList.remove("show");
    }
    if (!event.target.matches('.filterBtn')) {
        document.getElementById("filterDropdownContent").classList.remove("show");
    }
}

/*
* Author: Paula Krasnovska
* Revisited: /
* Funktion: farbliche Hervorhebung der Seitenauswahl im Reiter
* Grund: Benutzerfreundlichkeit
* User-Story/Task-ID: /
*/
function hover(){
    document.getElementById('pjBtn').style.borderColor = "#ba55d3";
}
function hoverLeave() {
    document.getElementById('pjBtn').style.borderColor = "#fff";
}

//Todo: Statt closest mit ID's umsetzen
/*
* Author: Paula Krasnovska
* Revisited: Zana Salih Hama
* Funktion: Ausklappen der Tasks
* Grund: Zueghörigkeit zu User-Stories
* User-Story/Task-ID: /
*/
function taskAusklappen(button) {
    const container = button.closest(".userStoryContainer");
    const taskContainer = container.querySelector("#taskContainer");
    const ausklappbarBtn = container.querySelector("#ausklappbarBtn");

    if (taskContainer.classList.contains("show")) {
        taskContainer.classList.remove("show");
        ausklappbarBtn.classList.remove("rotate");
    } else {
        taskContainer.classList.add("show");
        ausklappbarBtn.classList.add("rotate");
    }
}

/*
* Author: Zana Salih Hama
* Revisited: /
* Funktion: Toggeln des Bearbeitungsmenü'
* Grund: /
* User-Story/Task-ID: U3.F1, U4.F1
*/
function EditBox() {
    const toggleEditBox = document.querySelector('.editMenu');
    toggleEditBox.classList.toggle('visible');

    if (toggleEditBox.classList.contains('visible')) {
        toggleEditBox.style.right = '0'
    }
    else {
        toggleEditBox.style.right = '-350px';
    }
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

//Todo: ID-Erstellung hier einbetten?; Erstellung mit Thymleaf?
/*
* Author: Zana Salih Hama
* Revisited: Paula Krasnovska
* Funktion: Eine/mehrere User-Stories erstellen
* Grund: Auskommentiert, andere Funktion für hinzufügen von User Storys im Backend hinzugefügt
* User-ID/Task-ID: U3.F1
*/

/*
function replaceWithUserStoryContainer() {
    const createUserStory = document.querySelector('.createUserStory');
    const userStoryContainer = document.createElement('div');
    createUserStory.appendChild(userStoryContainer);
    userStoryContainer.outerHTML = `




                <div class="userStoryContainer">
                     <ul>
                        <li>
                            <div class="userStoryText">
                                <label id="userStoryTitel">Titel</label>
                                <label id="userStoryBeschreibung">Beschreibung</label>
                            </div>
                            <button class="editBtn" onclick="toggleEditBox()"><i class="fa fa-edit" style="margin-right: 8px;"></i></button>
                            <button class = "sizeBtn" id="zoomUserStoryBtn" onclick="zoomUserStoryContainer()"><i class="fa fa-search" ></i></button>
                            <button type="button" id="ausklappbarBtn" onClick="taskAusklappen(this)"> > </button>
                            <div id="taskContainer">
                                <div id="taskInhalt">
                                    <button id="neueTaskBtn">Tasks <i class="fa fa-plus"></i></button>
                                    <label id="taskBeschreibung">Noch keine Tasks..</label>
                                </div>
                            </div>
                        </li>
                     </ul>
                </div> -->
            `;
}
*/

/* Author: Zana Salih Hama
* Revisited: /
* Funktion: Zoom-Funktion für User-Stories
* Grund: Benutzerfreundlichkeit; angenehmer, um ein gesamtes User-Story-Konstrukt anzusehen
* User-ID/Task-ID: /
*/
function zoomUserStoryContainer(id) {
    const userStoryButton = document.querySelector('.createUserStory');
    const userStoryContainer = document.querySelector('.userStoryContainer');
    const zoomUserStoryBtn = document.getElementById('zoomUserStoryBtn'+id); // Korrekte Auswahl des Zoom-Buttons
    const overlay = document.createElement('div');
    overlay.classList.add('overlay');
    document.body.appendChild(overlay);

    const pivot1 = document.createElement('div');
    pivot1.classList.add('scroll-helper-pivot');
    const pivot2 = document.createElement('div');
    pivot2.classList.add('scroll-helper-pivot');

    overlay.appendChild(pivot1);
    overlay.appendChild(userStoryContainer);
    overlay.appendChild(pivot2);

    userStoryContainer.classList.add('zoomed');
    
    const onClose = function () {
        userStoryContainer.classList.remove('zoomed');
        userStoryButton.appendChild(userStoryContainer);
        overlay.remove();
        zoomUserStoryBtn.onclick = zoomUserStoryContainer; // Wieder das Zoomen aktivieren
    }

    zoomUserStoryBtn.onclick = onClose;

    overlay.addEventListener('click', function(event) {
        if (
            event.target === overlay ||
            event.target === pivot1 ||
            event.target === pivot2
        ) {
            onClose();
        }
    });
}

/*
* Author: Zana Salih Hama
* Revisited: /
* Funktion: Automatischer Seitenrefresh bei Submits ausschalten und Formular
*           nicht automatisch an Server gesendet; noch nicht genutzt
* Grund: Überwachung/Testzwecke
* User-ID/Task-ID: /
*/
function onFormSubmit(event) {
    event.preventDefault();
}
