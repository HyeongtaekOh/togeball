import React, { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import MatchingQueueBubbleChart from './components';

// const WebSocketComponent = () => {
//   const [matchingQueue, setMatchingQueue] = useState([]);

//   useEffect(() => {
//     const socket = new WebSocket('ws://localhost:8080');

//     const stompClient = Stomp.over(socket);

//     const onConnect = () => {
//       console.log('WebSocket 연결이 수립되었습니다.');

//       stompClient.subscribe('/hashtag/matchings', (message) => {
//         const matchData = JSON.parse(message.body);

//         setMatchingQueue((prevQueue) => [...prevQueue, matchData]);
//       });
//     };

//     const onDisconnect = () => {
//       console.log('WebSocket 연결이 종료되었습니다.');
//     };

//     stompClient.connect({}, onConnect, onDisconnect);

//     return () => {
//       stompClient.disconnect();
//     };
//   }, []);

//   return (
//     <div>
//       <h2>Matching Queue</h2>
//       <ul>
//         {matchingQueue.map((data) => (
//           <li key={data.userId}>{data.message}</li>
//         ))}
//       </ul>
//     </div>
//   );
// };

// export default WebSocketComponent;



const TemporaryParentComponent: React.FC = () => {
  const [matchingData, setMatchingData] = useState({
    hashtags: ['tag1', 'tag2', 'tag3'],
    counts: {
      tag1: 10,
      tag2: 5,
      tag3: 8,
    },
  });

  useEffect(() => {
    // 매 3초마다 임시 데이터를 업데이트하여 컴포넌트를 리렌더링
    const intervalId = setInterval(() => {
      setMatchingData((prevData) => ({
        hashtags: prevData.hashtags,
        counts: {
          tag1: Math.floor(Math.random() * 20), // 임시로 랜덤한 값 생성
          tag2: Math.floor(Math.random() * 20),
          tag3: Math.floor(Math.random() * 20),
        },
      }));
    }, 1000);

    // 컴포넌트가 언마운트되면 interval 해제
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div>
      <h1>Matching Queue Bubble Chart</h1>
      <MatchingQueueBubbleChart data={matchingData} />
    </div>
  );
};

export default TemporaryParentComponent;