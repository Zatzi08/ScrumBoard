
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    id = document.getElementById('data').getAttribute('uID')
    console.log("user: " + id)
    stompClient.subscribe('/topic/Pr/user'+id, (r) => {
        let message = JSON.parse(r.body);
        console.log(message.body);
        ForceSync(true)
    });
};