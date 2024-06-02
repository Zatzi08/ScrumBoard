/*
* Author: Zana Salih Hama
* Revisited: /
* Funktion: Toggeln des Bearbeitungsmenü'
* Grund: /
* User-Story/Task-ID: U3.F1, U4.F1
*/

function ansichtDropdown() {
    document.getElementById("ansichtDropdownContent").classList.toggle("show");
}

function toggleEditMenu(id) {
    var editMenu = document.querySelector('.editMenu');
    var overlay = document.querySelector('.overlay');

    if (editMenu) {
        if (editMenu.style.display === "block") {
            editMenu.style.display = "none";
            if (overlay) {
                overlay.remove();
                overlay.removeEventListener('click', toggleEditMenu);
            }
        } else {
            editMenu.style.display = "block";
            if (!overlay) {
                overlay = document.createElement('div');
                overlay.classList.add('overlay');
                document.body.appendChild(overlay);
                overlay.addEventListener('click', toggleEditMenu);
            }
        }
    } else {
        console.error("Das Element mit der Klasse 'editMenu' wurde nicht gefunden.");
    }
    console.log(id);
    if(id === 'addUserStoryBtn'){
        console.log('Userstory erstellen');
        document.querySelector('.editMenu label').textContent = "Erstellen";
    } else {
        document.querySelector('.editMenu label').textContent = "Bearbeiten";
    }
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
        case 'inputPrioLow' : document.getElementById('editPrio').value = 1; break;
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

function hover(buttonId){
    document.getElementById(buttonId).style.color = "#ba55d3";
}
function hoverLeave(buttonId) {
    document.getElementById(buttonId).style.color = "#fff";
}

document.addEventListener('DOMContentLoaded', function () {
    const rows = document.querySelectorAll('.userTaskTable tbody tr');

    rows.forEach(row => {
        row.draggable = true;
        row.addEventListener('dragstart', dragStart);
        row.addEventListener('dragover', dragOver);
        row.addEventListener('dragenter', dragEnter);
        row.addEventListener('dragleave', dragLeave);
        row.addEventListener('drop', drop);
        row.addEventListener('dragend', dragEnd);
    });

    let draggingRow;

    function dragStart(e) {
        draggingRow = this;
        this.classList.add('dragging');
        e.dataTransfer.effectAllowed = 'move';
        e.dataTransfer.setData('text/html', this.innerHTML);
    }

    function dragOver(e) {
        e.preventDefault();
        e.dataTransfer.dropEffect = 'move';
        const overRow = this;
        if (draggingRow !== overRow) {
            const isAfter = e.clientY > overRow.getBoundingClientRect().top + overRow.getBoundingClientRect().height / 2;
            const parent = overRow.parentNode;
            parent.insertBefore(draggingRow, isAfter ? overRow.nextElementSibling : overRow);
        }
    }

    function dragEnter(e) {
        e.preventDefault();
        if (this !== draggingRow) {
            this.classList.add('drag-over');
        }
    }

    function dragLeave(e) {
        if (this !== draggingRow) {
            this.classList.remove('drag-over');
        }
    }

    function drop(e) {
        e.preventDefault();
        if (this !== draggingRow) {
            this.classList.remove('drag-over');
        }
    }

    function dragEnd() {
        this.classList.remove('dragging');
    }
});

function deleteTableRow() {
    var row = document.querySelector('.userTaskTable tbody tr');
    if (row) {
        row.remove();
    }

    function taskToUS(){

    }
}

function toggleEditBoxT(id, TId, description, usID, dueDate, timeG, tbID, prio){
    toggleEditMenu(id);
    document.getElementById("inputDesc").textContent = description;
    if (TId !== undefined){
        document.getElementById("editId").value = TId;
    } else {
        document.getElementById("editId").value = -1;
    }
    let EID = ""
    if (usID !== undefined){
        EID = 'USDropdown:'+usID;
        document.getElementById('USDropdown').value = document.getElementById(EID).value;
    } else {
        document.getElementById('USDropdown').value = -1;
    }
    if (timeG !== undefined){
        document.getElementById('inputTimeNeeded').value = timeG;
    } else {
        document.getElementById('inputTimeNeeded').value = 0;
    }
    if (tbID !== undefined && tbID !== "-1"){
        EID = 'TBDropdown:'+tbID;
        document.getElementById('TBDropdown').value = document.getElementById(EID).value;
    } else {
        document.getElementById('TBDropdown').value = document.getElementById('TBDropDown:-1').value;
    }
    switch (prio){
        case "2":
            highlightPriorityButton('inputPrioNormal')
            break;
        case "3":
            highlightPriorityButton('inputPrioHigh')
            break;
        case "4":
            highlightPriorityButton('inputPrioUrgent')
            break;
        default:
            highlightPriorityButton('inputPrioLow')
            break;
    }
    if (dueDate !== undefined){
        document.getElementById('inputDate').value = dueDate;
    } else{
        document.getElementById('inputDate').value = "";
    }
}

function toggleEditBox(id,storyId, name, description, prio){
    toggleEditMenu(id);
    document.getElementById("inputName").textContent = name;
    document.getElementById("inputDesc").textContent = description;
    if (storyId !== undefined){
        document.getElementById("editId").value = storyId;
    } else {
        document.getElementById("editId").value = -1;
    }
    switch (prio){
        case "2":
            highlightPriorityButton('inputPrioNormal')
            break;
        case "3":
            highlightPriorityButton('inputPrioHigh')
            break;
        case "4":
            highlightPriorityButton('inputPrioUrgent')
            break;
        default:
            highlightPriorityButton('inputPrioLow')
            break;
    }
}


var checkList = document.getElementById('list1');
checkList.getElementsByClassName('anchor')[0].onclick = function(evt) {
    if (checkList.classList.contains('visible'))
        checkList.classList.remove('visible');
    else
        checkList.classList.add('visible');
}