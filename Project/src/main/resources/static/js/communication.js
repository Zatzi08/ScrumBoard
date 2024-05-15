// get Page
function SwitchToUserStory(sessionID) {
    let url = '/ProjectManager?sessionID=' + sessionID
    document.location.assign(url)
}

function SwitchToOwnProfile(sessionID) {
    let url = '/getProfilePage?sessionID=' + sessionID
    document.location.assign(url)
}

function SwitchToTasks(sessionID) {
    let url = '/getAllTask?sessionID=' + sessionID
    document.location.assign(url)
}

function SwitchToTasksbyUSID(sessionID, USID) {
    let url = '/getTaskByUSID?sessionID=' + sessionID + '&USID=' + USID
    document.location.assign(url)
}

// Post Requests
function saveTask($this, sessionID) {
    let id = document.getElementById('editId').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;
    let USID = document.getElementById('USDropdown').value;

    fetch('/saveTask?tID=' + id + '&description=' + description + '&priority=' + priority + "&USID=" + USID, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function saveUS($this, sessionID) {
    let id = document.getElementById('editId').value;
    let name = document.getElementById('inputName').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;

    fetch('/saveStory?ID=' + id + '&name=' + name + '&description=' + description + '&priority=' + priority, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
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
    let name = document.getElementById('inputName').value;
    let pdescription = document.getElementById('inputPDesc').value;
    let wdescription = document.getElementById('inputWDesc').value;
    // rolle = document.getElementById('inputRolle').value;  + "&rolle=" + rolle
    fetch('/saveUserData?SessionId=' + sessionID + '&uName=' + name + '&wDesc=' + wdescription + "&pDesc=" + pdescription ,{
        method: 'Post',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function deleteUS(usid, sessionID){
    fetch('/deleteUS?ID=' + usid.toString() + '&SessionId=' + sessionID, {
        method: 'POST',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
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
    fetch('/deleteTask?tID=' + Tid, {
        method: 'POST',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r => {
        if (r.ok) {
            location.reload()
        }
    });
}
