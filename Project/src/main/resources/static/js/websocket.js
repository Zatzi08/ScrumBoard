const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-websocket'
});

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/ProjectManagerStorys', (ProjectManagerStorys) =>{
        var message = JSON.parse(ProjectManagerStorys.body);
        console.log(message);
        ForceSync(message);
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
    console.log("Disconnected");
}

function sendStory(name, description, priority) {
    //var name = JSON.stringify({'name': $("name").val()});
    //var description = JSON.stringify({'description': $("discription").val()})
    //var priority = JSON.stringify({'priority': $("priority").val()})
    stompClient.publish({
        destination: "/websocket/UserStory",
        body: "name:" + name + ",discription:" + description + ",priority:" + priority
        });
}

function ForceSync(sync) {
    if (sync) {
        disconnect()
        window.location.href = "";
    }
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    //$( "#send" ).click(() => sendStory());
});