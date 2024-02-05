const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-websocket'
});

stompClient.onConnect = (frame) => {
    setConnection(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/ProjectManagerStorys', (Sync) =>{
        ForceSync(JSON.parse(Sync.body).sync);
    });
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
    setConnection(false);
    console.log("Disconnected");
}

function sendStory() {
    var name = JSON.stringify({'name': $("name").val()});
    var description = JSON.stringify({'description': $("discription").val()})
    var priority = JSON.stringify({'priority': $("priority").val()})
    stompClient.publish({
        destination: "/websocket/Story",
        body: name + description + priority
        });
}

function ForceSync(sync) {
    if(sync == "true") {
        href = ""
    }
}