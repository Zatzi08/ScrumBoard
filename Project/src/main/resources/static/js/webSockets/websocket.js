
const stompClient = new StompJs.Client({
    brokerURL: 'ws://139.30.229.167:8080/gs-websocket'
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

let can_sync = true
let sync_tries = 0

function try_sync(){
    if (sync_tries > 0){
        ForceSync(can_sync)
    }
}
setInterval(try_sync, 1000);

async function ForceSync(sync) {
    if (sync) {
        disconnect();
        document.location.reload();
    }
}