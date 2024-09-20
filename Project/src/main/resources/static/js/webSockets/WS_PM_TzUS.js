stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    let usID = document.getElementById('A').getAttribute('USID');
    console.log('USID= ' + usID)
    stompClient.subscribe('/topic/PM-TzUS/US'+usID, (r) => {
        let message = JSON.parse(JSON.parse(r.body).body);
        console.log(message);
        if(message.ResponceTyp !== '4' ) {
            if (message.objectTyp === 'class com.team3.project.Classes.UserStory' && message.object.id === usID) {
                if (message.ResponceTyp === '1') {
                    SwitchToTasks(document.getElementById('selectUserstory').getAttribute('Sid'));
                } else {
                    sync_tries++;
                    ForceSync(can_sync)
                }
            } else if (message.objectTyp === 'class com.team3.project.Classes.Task'){
                id = 'T' + message.object.id
                if (document.getElementById(id) !== null || message.ResponceTyp === '0'){
                    sync_tries++;
                    ForceSync(can_sync)
                }
            }
        }
    });
};