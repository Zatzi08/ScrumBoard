stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/PM-T', (r) => {
        let message = JSON.parse(r.body);
        console.log(message.body);
        ForceSync(true)
    });
};