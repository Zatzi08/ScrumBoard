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
async function saveTask(sessionID, id) {
    let description = document.getElementById('inputDesc').value;
    let priority = document.getElementById('editPrio').value;
    let USID = document.getElementById('USDropdown').value;
    let dueDate = document.getElementById('inputDate').value;
    let TBID = document.getElementById("TBDropdown").value;
    let timeNeededG = document.getElementById("inputTimeNeeded").value;

    if (USID === "-1"){
        alert("Invalid UserStory-ID")
    } else {

        return fetch('/saveTask', {
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
                'timeNeededA': 0
            })
        }).then(r => {
            if (!r.ok) {
                location.reload();
            }
            return r
        });
    }
    return new Response("", {status: 400, statusText: "Bad Request"})
}

function setTaskTime(sessionID){
    let tID = document.getElementById('taskID').value.substring(1);
    let time = document.getElementById('taskFertigInput').value;

    let url = "/setRealTaskTime?TID="+ tID + "&time=" + time;
    fetch(url, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r =>{
        if (!r.ok){
            document.location.reload()
        } else {
            document.getElementById(document.getElementById('taskID').value).setAttribute('real', time);
            let ID = "tableContent-Visualisation:"+ tID
            document.getElementById(ID).setAttribute('real', time)
        }
    })
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

async function saveTaskBoard(sessionID, TBID, TBName) {
    return fetch('saveTaskBoard', {
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
        return r.status;
    })
}

async function deleteTB(sessionID, TBID){
    let url = 'deleteTaskBoard?TBID=' +TBID
    return fetch(url, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        }
    }).then(r  => {
            if (!r.ok){
                document.location.reload()
            } else return r.status
        })
}

async function changeAuth(sessionID, selectID, usID){
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
        if (!r.ok || parseInt(auth) <= 2){
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
        if (!r.ok && r.status === 403){
            alert("Invalid Request")
            document.location.reload()
        } else if (r.status === 409){

        }
    })
}

function setUsersOfTask(sessionID, tid, uIDs){
    let url = "setUserToTask?tID="+ tid

    return  fetch(url, {
        method: 'POST',
        cache: 'no-cache',
        headers: {
            'Content-Type': 'application/json',
            'sessionID': sessionID
        },
        body: JSON.stringify(uIDs)
    }).then(r =>{
        if (!r.ok){
            document.location.reload()
        }
        return r
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