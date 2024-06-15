
stompClient.onConnect = (frame) => {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/PM-US', (r) => {
            let message = JSON.parse(r.body);
            console.log(message.body);
            ForceSync(true)
        });
};