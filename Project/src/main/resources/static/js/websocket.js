
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-websocket'
});

stompClient.onConnect = (frame) => {
    if (document.title === 'Projektmanager') {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/projectManager', (r) => {
            var message = JSON.parse(r.body);
            console.log(JSON.parse(message.body).object);
            //ForceSync(message);
        });
    }
};

stompClient.onWebsocketError = (error) => {
    console.error('Error with Websocket', error);
}

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.header['message']);
    console.error('Additional details: ' + frame.body);
};

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    console.log("Disconnected");
}

function ping(message) {
    stompClient.publish({
        destination: "/websocket/ping",
        body: message
        });
}

function ForceSync(sync) {
    if (sync) {
        disconnect();
        document.location.reload();
    }
}