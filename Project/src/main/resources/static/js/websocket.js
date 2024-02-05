const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-websocket'
});

stompClient.onConnect = (frame) => {
    setConnection(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/ProjectManagerStorys', (Storys) =>{
        showStory(JSON.parse(Storys.body).userID, JSON.parse((Storys.body).description, JSON.parse(Storys.body).priority));
    });
};

stompClient.onWebsocketError = (error) => {
    console.error('Error with Websocket', error);
}

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.header['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {

}