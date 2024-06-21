
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

/*
  Mögliche Operationen : messageType
    - new Task : 0
    - deleted Task : 1
    - changed TL : 3 ---> changed TB?
    - changed Task-Name/-USID/-Prio/-Zeiten/-Frist : 2
    - deleted User : 1
    - changed UserParameter : 2
    - changed TBName : 2
    - deleted TB : 1
    - new TB : 0
 */