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

function toggleZoomedTaskCard(id) {
    var editMenu = document.querySelector('#zoomedTaskCard');
    var overlay = document.querySelector('.overlay');

    if (editMenu) {
        if (editMenu.style.display === "block") {
            editMenu.style.display = "none";
            if (overlay) {
                overlay.remove();
                overlay.removeEventListener('click', toggleZoomedTaskCard);
            }
        } else {
            editMenu.style.display = "block";
            if (!overlay) {
                overlay = document.createElement('div');
                overlay.classList.add('overlay');
                document.body.appendChild(overlay);
                overlay.addEventListener('click', toggleZoomedTaskCard);
            }
        }
    } else {
        console.error("Das Element mit der Klasse 'editMenu' wurde nicht gefunden.");
    }
}

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
    placeholder.innerHTML = '<td colspan="4">&nbsp;</td>';

    function handleDragStart(e) {
        draggedElement = e.target;
        e.dataTransfer.effectAllowed = 'move';
        e.dataTransfer.setData('text/plain', e.target.id); // Store the ID of the dragged element
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
            const rows = Array.from(targetBody.querySelectorAll('tr'));
            const index = rows.indexOf(targetRow);

            if (index > -1) {
                const rect = targetRow.getBoundingClientRect();
                const mouseY = e.clientY - rect.top;

                if (mouseY > rect.height / 2) {
                    if (targetRow.nextElementSibling !== placeholder) {
                        targetBody.insertBefore(placeholder, targetRow.nextElementSibling);
                    }
                } else {
                    if (targetRow.previousElementSibling !== placeholder) {
                        targetBody.insertBefore(placeholder, targetRow);
                    }
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
            draggedElement.classList.remove('dragging');

            // Retrieve the ID of the dragged element
            const draggedElementId = e.dataTransfer.getData('text/plain');
            setTaskList(document.getElementById('sessionID').value, draggedElementId, targetContainer.id);
        }
    }
});

document.addEventListener('DOMContentLoaded', function() {
    function enableButtonEdit() {
        const headerButtons = document.querySelectorAll('.outerContainer-HeaderBtn');

        headerButtons.forEach(button => {
            button.addEventListener('dblclick', function(event) {
                const currentText = button.textContent.trim();
                const input = document.createElement('input');
                input.type = 'text';
                input.value = currentText;
                input.style.width = button.offsetWidth + 'px';
                input.style.height = button.offsetHeight + 'px';
                input.style.fontFamily = window.getComputedStyle(button).fontFamily;
                input.style.fontSize = window.getComputedStyle(button).fontSize;
                input.style.padding = '2px';
                input.style.margin = '0';
                input.style.border = '1px solid #ccc';
                input.style.borderRadius = '3px';
                input.style.boxSizing = 'border-box';
                input.style.display = 'inline';

                input.addEventListener('keyup', function(e) {
                    if (e.key === 'Enter') {
                        button.textContent = input.value.trim();
                        input.replaceWith(button);
                    }
                });

                input.addEventListener('blur', function() {
                    button.textContent = input.value.trim();
                    input.replaceWith(button);
                });

                button.replaceWith(input);
                input.focus();
            });
        });
    }

    enableButtonEdit();
});