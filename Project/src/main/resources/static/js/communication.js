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

function SwitchToUsers(sessionID){
    let url = "/getUserList?sessionID=" + sessionID
    document.location.assign(url)
}

function SwitchToOtherProfile(sessionID, email){
    let url = '/getProfilePageByEmail?sessionID=' + sessionID + '&email=' + email
    document.location.assign(url)
}

function SwitchToTaskBoardByID(sessionID, TBID){
    let url = "/getTaskBoardByID?sessionID=" + sessionID + "&TBID=" + TBID;
    document.location.assign(url)
}

function SwitchToTaskBoard(sessionID){
    let url = "/getTaskBoard?sessionID=" + sessionID;
    document.location.assign(url)
}

// Post Requests
function saveTask($this, sessionID) {
    let id = document.getElementById('editId').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;
    let USID = document.getElementById('USDropdown').value;
    let dueDate = document.getElementById('inputDate').value;
    let TBID = document.getElementById("TBDropdown").value;
    let timeNeededG = document.getElementById("inputTimeNeeded").value;
    let timeNeededA = -1;//TODO document.getElementById().value

    if (USID === "-1"){
        alert("Invalid UserStory-ID")
    } else {

        fetch('/saveTask', {
            method: 'POST',
            cache: 'no-cache',
            headers: {
                'Content-Type': 'application/json',
                'sessionID': sessionID
            },
            body: JSON.stringify({
                'tID': id,
                'description': description,
                'priority': priority,
                'userStoryID': USID,
                'dueDate': dueDate,
                'tbID': TBID,
                'timeNeededG': timeNeededG,
                'timeNeededA': timeNeededA
            })
        }).then(r => {
            if (r.ok) {
                location.reload();
            }
        });
    }
}

function saveUS($this, sessionID) {
    let id = document.getElementById('editId').value;
    let name = document.getElementById('inputName').value;
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;

    fetch('/saveStory', {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        },
        body: JSON.stringify({
            'ID': id,
            'name': name,
            'description': description,
            'priority': priority
        })
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
    let email = "";
    // roles = getRoles();

    fetch('/saveUserData' ,{
        method: 'Post',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        },
        body: JSON.stringify({
            'uname': name,
            'email': email,
            'privatDesc': pdescription,
            'workDesc': wdescription,
            'roles': null
        })
    }).then(r => {
        if (r.ok) {
            location.reload();
        }
    });
}

function deleteUS(usid, sessionID){
    fetch('/deleteUS?ID=' + usid.toString() + '&sessionID=' + sessionID, {
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
        document.location.reload()
    });
}

function saveTaskBoard(sessionID, TBID, TBName) {

    fetch('saveTaskBoard', {
        method: 'POST',
        cache: 'no-cache',
        redirect: 'manual',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        },
        body: JSON.stringify({
            'tbID': TBID,
            'name': TBName,
            'taskListList': null
        })
    }).then(r=> {
        if(!r.ok){
            document.location.reload()
        }
    })
}

function deleteTB(sessionID, TBID){
    let url = 'deleteTaskBoard?TBID=' +TBID
        fetch(url, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r  => {
            if (!r.ok){
                document.location.reload()
            }
        })
}

function changeAuth(sessionID, selectID, usID){
    let selected = document.getElementById(selectID);
    let auth = selected.value;
    let url = 'setAuthority?USID=' + usID + '&Auth=' + auth;

    fetch(url,{
        method: 'Get',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r => {
        if (!r.ok){
            document.location.reload()
        }
    })
}

function setTaskList(sessionID, tID, tlID){
    let url = 'setTaskListToTask?TID=' + tID + '&TLID=' + tlID;

    fetch(url,{
        method: 'Get',
        cache: 'no-cache',
        headers:{
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r => {
        if (!r.ok){
            alert("Invalid Request")
            document.location.reload()
        }
    })
}

// Zusätzliche Funktionen
function getRollen(){
    var list = document
        .getElementById('roleList')
        .getElementsByClassName("li");
    let a = new NodeList();

    for (let l in list){
        let toAdd = l.getAttribute("rID")
        a.add(toAdd)
    }
    // TODO: integrate
    return null
}