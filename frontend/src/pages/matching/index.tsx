import React, { useEffect, useState } from 'react';
import Stomp from 'stompjs';
import   { MatchingQueue, Timer }   from './components';
import { Title } from 'src/components'

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



const Matching: React.FC = () => {
  // 매칭에 올라온 큐를 다음과 같이 바꿔야함.
  const [ matchingData, setMatchingData ] = useState({
    hashtags: ['tag1', 'tag2', 'tag3', 'tag4', 'tag5', 'tag6'],
    counts: {
      tag1: 10,
      tag2: 5,
      tag3: 8,
      tag4: 12,
      tag5: 15,
      tag6: 20,
    },
  });

  useEffect(() => {
    // 매 3초마다 임시 데이터를 업데이트하여 컴포넌트를 리렌더링
    const intervalId = setInterval(() => {
      setMatchingData(( prevData ) => ({
        hashtags: prevData.hashtags,
        counts: {
          tag1: Math.floor( Math.random() * 20 ), // 임시로 랜덤한 값 생성
          tag2: Math.floor( Math.random() * 20 ),
          tag3: Math.floor( Math.random() * 20 ),
          tag4: Math.floor( Math.random() * 20 ),
          tag5: Math.floor( Math.random() * 20 ),
          tag6: Math.floor( Math.random() * 20 ),
        },
      }));
    }, 5000); //5초마다 갱신

    // 컴포넌트가 언마운트되면 interval 해제
    return () => clearInterval(intervalId);
  }, []);

  return (
    <div>
      <div style={{ display:'flex', justifyContent:'space-between'}}>
        <Title type='large'>직관 메이트를 찾고 있어요</Title>
        <Timer duration={ 180 }/>
      </div>
      <MatchingQueue data={ matchingData }/>

    </div>
  );
};

export default Matching;