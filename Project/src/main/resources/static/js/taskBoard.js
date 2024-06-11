function toggleZoomedTaskCard(id) {
    var editMenu = document.querySelector('#zoomedTaskCard');
    var overlay = document.querySelector('.overlay');

    if (editMenu) {
        if (editMenu.style.display === "block") {
            editMenu.style.display = "none";
            if (overlay) {
                overlay.remove();
                overlay.removeEventListener('click', closeOverlay);
            }
        } else {
            editMenu.style.display = "block";
            if (!overlay) {
                overlay = document.createElement('div');
                overlay.classList.add('overlay');
                document.body.appendChild(overlay);
                overlay.addEventListener('click', closeOverlay);
            }
        }
    } else {
        console.error("Das Element mit der Klasse 'editMenu' wurde nicht gefunden.");
    }
}

function closeOverlay() {
    var overlay = document.querySelector('.overlay');
    var zoomedTaskCard = document.querySelector('#zoomedTaskCard');

    if (zoomedTaskCard.style.display === "block") {
        zoomedTaskCard.style.display = "none";
        if (overlay) {
            overlay.remove();
            overlay.removeEventListener('click', closeOverlay);
        }
    }
}

function toggleVis(percentageDifference, absNum, numType) {
    var vis = document.querySelector('#visEstimateTracker');
    var overlay = document.querySelector('.overlay');

    if (vis) {
        if (vis.style.display === "block") {
            vis.style.display = "none";
            if (overlay) {
                overlay.remove();
                overlay.removeEventListener('click', toggleVis);
                destroyMyChart();
            }
        } else {
            vis.style.display = "block";
            if (!overlay) {
                overlay = document.createElement('div');
                overlay.classList.add('overlay');
                document.body.appendChild(overlay);
                switch (numType){
                    case 0:
                        document.getElementById('percentageDifferenceText').innerText = `Deine Bearbeitungszeit entspricht genau der Schätzung!`;
                        break;
                    case 1:
                        document.getElementById('percentageDifferenceText').innerText = `Der Mehraufwand gegenüber der geschätzten Bearbeitungszeit beträgt ${absNum}h!`;
                        break;
                    case 2:
                        document.getElementById('percentageDifferenceText').innerText = `Deine reale Bearbeitungszeit weicht zu ${percentageDifference}% von der geschätzten ab. Dies entspricht ${absNum}h!`;
                        break;
                }
                overlay.addEventListener('click', toggleVis);
            }
        }
    } else {
        console.error("Das Element mit der Klasse 'vis' wurde nicht gefunden.");
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

            const draggedElementId = e.dataTransfer.getData('text/plain');
            setTaskList(document.getElementById('sessionID').value, draggedElementId.substring(1), targetContainer.id);
            let id = targetContainer.id
            if (parseInt(id) % 5 === 0 ) {
                openPopup(draggedElementId)
                document.getElementById('taskID').value = draggedElementId
            } else {
                document.getElementById(draggedElementId).setAttribute('real', 0);
                let ID = "tableContent-Visualisation:"+ draggedElementId.substring(1)
                document.getElementById(ID).setAttribute('real', 0)
            }
        }
    }
});

function deleteButton(button) {
    button.parentNode.removeChild(button);
}

function changeTBNamePopUp(id){
    var TBNamePopup = document.getElementById(id);
    TBNamePopup.style.display = "block";
}

window.onclick = function(event) {
    var TBNamePopup = document.getElementById("popupChangeTBName");
    var TBCreatePopup = document.getElementById("popupCreateTB");
    var fertigPopup = document.getElementById("popupTaskFertig");
    if (event.target == TBNamePopup) {
        TBNamePopup.style.display = "none";
    }

    else if (event.target == fertigPopup) {
        fertigPopup.style.display = "none";
    }

    else if (event.target == TBCreatePopup) {
        TBCreatePopup.style.display = "none";
    }
}

function openPopup(){
    var fertigPopup = document.getElementById("popupTaskFertig");
    fertigPopup.style.display = "block";
}

async function deleteTaskBoard(button, sessionID, TBID) {
    await deleteTB(sessionID, TBID)
    deleteButton(button.parentElement)
    let aktuellTB = document.getElementById('outerContainer').getAttribute('tbID');
    if (TBID === aktuellTB) {
        SwitchToTaskBoard(sessionID);
    }
}

function changeTBName(sessionID, tbID) {
    let name = document.getElementById('changeTBNameInput').value;
    saveTaskBoard(sessionID, tbID, name);
    let id = 'Name' + tbID;
    document.getElementById(id).innerText = name;
}

async function addTaskBoard(sessionID) {
    let name = document.getElementById('createTBNameInput').value;
    let num = await saveTaskBoard(sessionID, -1, name);
    console.log('Hi')
    document.location.reload()
}

function toggleZoomedTaskCardforTask(USName, TBName, TaskDesc, estTime, realTime,prio ,DueDate, NutzerList){
    toggleZoomedTaskCard()
    document.getElementById('zoomedTaskCard-US').innerText = USName;
    document.getElementById('zoomedTaskCard-taskBoard').innerText = TBName;
    document.getElementById('zoomedTaskCard-description').innerText = TaskDesc;
    document.getElementById('zoomedTaskCard-estimatedTime').innerText = estTime;
    document.getElementById('zoomedTaskCard-priority').innerText = prio;
    document.getElementById('zoomedTaskCard-workedTime').innerText = realTime;
    document.getElementById('zoomedTaskCard-deadline').innerText = DueDate
    document.getElementById('zoomedTaskCard-role').innerText;
    NutzerList = parseUser(NutzerList)
    var ul = document.getElementById("zoomedTaskCard-nutzerDropdown-content");
    // ul.innerHTML = ""
    // NutzerList.forEach((nutzer)=>{
    //     var li = document.createElement("li")
    //     li.id = 'N' + nutzer.id;
    //     li.appendChild(document.createTextNode(nutzer.name));
    //     ul.appendChild(li);
    // })
}

function parseUser(a){
    let list = []
    while (a !== "]" && a !== "[]"){
        let user = a.substring(a.indexOf('{'), end = (a.indexOf('}') + 1)).replaceAll("'", '"')
        a = a.substring(a.indexOf('}') + 1)
        let ju = JSON.parse(user);
        list.push(ju)
    }
    return list
}

function closePopup(){
    let popupChange = document.getElementById("popupChangeTBName");
    let popupFertig = document.getElementById("popupTaskFertig");

    popupChange.style.display = "none";
    popupFertig.style.display = "none";
}

function openAssignedUsers(){
    var assignedUsers = document.querySelector('#assignedUsers');
    var overlay2 = document.querySelector('.overlay2');

    if (assignedUsers) {
        if (assignedUsers.style.display === "block") {
            if (overlay2) {
                overlay2.remove();
                overlay2.removeEventListener('click', closeOverlay2);
            }
            assignedUsers.style.display = "none";
        } else {
            assignedUsers.style.display = "block";
            if (!overlay2) {
                overlay2 = document.createElement('div');
                overlay2.classList.add('overlay2');
                document.body.appendChild(overlay2);
                overlay2.addEventListener('click', closeOverlay2);
            }
        }
    }
}

function closeOverlay2() {
    var overlay2 = document.querySelector('.overlay2');
    var assignedUsers = document.querySelector('#assignedUsers');

    if (assignedUsers.style.display === "block") {
        assignedUsers.style.display = "none";
        if (overlay2) {
            overlay2.remove();
            overlay2.removeEventListener('click', closeOverlay2);
        }
    } else {
        toggleZoomedTaskCard();
    }
}

function randomizeAssignedUsersColour(id){
    var selected = document.getElementById(id)
    selected.style.backgroundColor = generateColors(1)[0]
}