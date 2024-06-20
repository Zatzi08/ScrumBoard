
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    id = document.getElementById('data').getAttribute('tbID')
    console.log("board: " + id)
    stompClient.subscribe('/topic/TB/board'+id, (r) => {
        let message = JSON.parse(r.body);
        console.log(message.body);
        ForceSync(true)
    });
};