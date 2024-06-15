
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-websocket'
});

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