// Get Page
function SwitchToUserStory(sessionID) {
    let url = '/ProjectManager?SessionId=' + sessionID
    document.location.assign(url)
}

function SwitchToOwnProfile(sessionID) {
    let url = '/GetProfilePage?SessionId=' + sessionID
    document.location.assign(url)
}

function SwitchToTasks(sessionID) {
    let url = '/GetAllTask?SessionId=' + sessionID
    document.location.assign(url)
}

function SwitchToTasksbyUSID(sessionID, USID) {
    let url = '/GetTaskByUSID?SessionId=' + sessionID + '&USID=' + USID
    document.location.assign(url)
}

// Post Requests
function saveTask($this, sessionID) {
    let id = document.getElementById('editId').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;

    fetch('/SaveTask?TID=' + id + '&SessionId=' + sessionID + '&description=' + description + '&priority=' + priority, {
        method: 'POST',
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function saveUS($this, sessionID) {
    id = document.getElementById('editId').value;
    name = document.getElementById('inputName').value;
    description = document.getElementById('inputDesc').value;
    priority = document.getElementById('editPrio').value;

    fetch('/saveStory?ID=' + id + '&SessionId=' + sessionID + '&name=' + name + '&description=' + description + '&priority=' + priority, {
        method: 'POST',
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
        if (r.status === 403){
            window.alert("Unzureichende Rechte! Bei Anfrage kontaktieren Sie bitte den Admin!");
        }
    });
}

function saveProfile(sessionID) {
    name = document.getElementById('inputName').value;
    pdescription = document.getElementById('inputPDesc').value;
    wdescription = document.getElementById('inputWDesc').value;
    rolle = document.getElementById('inputRolle').value;
    fetch('/SaveUserData?SessionId=' + sessionID + '&uName=' + name + '&wDesc=' + wdescription + "&pDesc=" + pdescription + "&rolle=" + rolle,{
        method: 'Post',
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function deleteUS(usid, sessionID){
    fetch('/deleteUS?ID=' + usid.toString() + '&SessionId=' + sessionID, {
        method: 'POST',
    }).then(r => {
        if (r.ok) {
            location.reload()
        }
        if (r.status === 403){
            window.alert("Unzureichende Rechte! Bei Anfrage kontaktieren Sie bitte den Admin!");
        }
    });
}

function deleteT(Tid, sessionID){
    fetch('/deleteTask?TID=' + Tid.toString() + '&SessionId=' + sessionID, {
        method: 'POST',
    }).then(r => {
        if (r.ok) {
            location.reload()
        }
    });
}
