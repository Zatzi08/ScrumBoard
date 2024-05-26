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
/*
* Author: Paula Krasnovska
* Revisited: /
* Funktion: Toggeln des Bearbeitungsmenü, Zuweisung von Attributen für Thymeleaf
* Grund: /
* User-Story/Task-ID: U3.F1, U4.F1
*/
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
document.addEventListener('DOMContentLoaded', function() {
    const draggableRows = document.querySelectorAll('tbody tr[draggable="true"]');
    const innerContainers = document.querySelectorAll('.innerContainer');

    draggableRows.forEach(row => {
        row.addEventListener('dragstart', handleDragStart);
        row.addEventListener('dragend', handleDragEnd);
    });

    innerContainers.forEach(container => {
        container.addEventListener('dragover', handleDragOver);
        container.addEventListener('drop', handleDrop);
    });

    let draggedElement = null;
    let placeholder = document.createElement('tr');
    placeholder.className = 'placeholder';

    function handleDragStart(e) {
        draggedElement = e.target;
        e.dataTransfer.effectAllowed = 'move';
        e.dataTransfer.setData('text/plain', e.target.id);
        e.target.classList.add('dragging');
    }

    function handleDragEnd(e) {
        e.target.classList.remove('dragging');
        placeholder.remove();
        draggedElement = null;
    }

    function handleDragOver(e) {
        e.preventDefault();
        e.dataTransfer.dropEffect = 'move';

        const targetContainer = e.currentTarget;
        const targetBody = targetContainer.querySelector('tbody');
        const targetRow = e.target.closest('tr');

        if (targetBody && draggedElement && e.target !== draggedElement) {
            if (targetRow) {
                const rect = targetRow.getBoundingClientRect();
                const mouseY = e.clientY - rect.top;
                if (mouseY > rect.height / 2) {
                    if (!targetRow.nextElementSibling || targetRow.nextElementSibling === placeholder) {
                        targetBody.appendChild(placeholder);
                    } else {
                        targetBody.insertBefore(placeholder, targetRow.nextElementSibling);
                    }
                } else {
                    targetBody.insertBefore(placeholder, targetRow);
                }
            } else {
                targetBody.appendChild(placeholder);
            }
        }
    }

    function handleDrop(e) {
        e.preventDefault();
        const targetContainer = e.currentTarget;
        const targetBody = targetContainer.querySelector('tbody');

        if (targetBody && draggedElement) {
            targetBody.insertBefore(draggedElement, placeholder);
            placeholder.remove();

            // Gibt dir die ID raus
            const draggedElementId = e.dataTransfer.getData('text/plain');
            console.log('TID:', draggedElementId);
            console.log('TLID:', targetContainer.id);
            setTaskList(document.getElementById("sessionID").sessionID,draggedElementId, targetContainer.id)
        }
    }

    document.addEventListener('click', function(e) {
        if (!e.target.closest('.innerContainer')) {
            innerContainers.forEach(container => {
                container.classList.remove('zoomed');
            });
        }
    });
});