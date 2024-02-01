import React, { useEffect, useState } from 'react';
import Stomp from 'stompjs';

const WebSocketComponent = () => {
  const [matchingQueue, setMatchingQueue] = useState([]);

  useEffect(() => {
    const socket = new WebSocket('ws://localhost:8080');

    const stompClient = Stomp.over(socket);

    const onConnect = () => {
      console.log('WebSocket 연결이 수립되었습니다.');

      stompClient.subscribe('/topic/matchingQueue', (message) => {
        const matchData = JSON.parse(message.body);
        setMatchingQueue((prevQueue) => [...prevQueue, matchData]);
      });
    };

    const onDisconnect = () => {
      console.log('WebSocket 연결이 종료되었습니다.');
    };

    stompClient.connect({}, onConnect, onDisconnect);

    return () => {
      stompClient.disconnect();
    };
  }, []);

  return (
    <div>
      <h2>Matching Queue</h2>
      <ul>
        {matchingQueue.map((data) => (
          <li key={data.userId}>{data.message}</li>
        ))}
      </ul>
    </div>
  );
};

export default WebSocketComponent;