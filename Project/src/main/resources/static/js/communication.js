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



// Post Requests
function saveTask($this, sessionID) {
    let id = document.getElementById('editId').value;
    name = document.getElementById('inputName').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;

    fetch('/saveTask?TID=' + id + '&SessionId=' + sessionID + '&name=' + name + '&description=' + description + '&priority=' + priority, {
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

    fetch('/SaveUserData?SessionId=' + sessionID + '&uName=' + name + '&uDesc=' + description + "&pDesc=" + wdescription + "&rolle=" + rolle, {
        method: 'POST',
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function saveProfile(sessionID) {
    name = document.getElementById('inputName').value;
    pdescription = document.getElementById('inputPDesc').value;
    wdescription = document.getElementById('inputWDesc').value;
    rolle = document.getElementById('inputRolle').value;

    fetch('/addStory?ID=' + id + '&SessionId=' + sessionID + '&name=' + name + '&description=' + description + '&priority=' + priority, {
        method: 'POST',
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
    });
}
