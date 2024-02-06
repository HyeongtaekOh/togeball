import React, { useEffect, useState } from 'react'
import Stomp from 'stompjs'
import   { MatchingQueue, Timer, MatchingModal }   from './components'
import { Title } from 'src/components'
import styled from 'styled-components'
import SockJS from 'sockjs-client'



const MatchingWrapper = styled.div`
  background-color: #7D74B4;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
`
const Matching: React.FC = () => {
  // 매칭에 올라온 큐를 다음과 같이 바꿔야함.
  const [ matchingData, setMatchingData ] = useState({
    hashtags: [ 'tag1', 'tag2', 'tag3', 'tag4', 'tag5', 'tag6' ],
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

     // WebSocket 연결 설정
    const clientId = 1
    const socket = new SockJS("https://i10a610.p.ssafy.io:8083/matching-server/matching?userId=" + clientId)
    
     // STOMP 클라이언트 생성
    // const stompClient = Stomp.over(socket)

    socket.onopen = function(event) {
      // WebSocket 연결이 열렸을 때 실행되는 코드
      console.log("WebSocket 연결이 열렸습니다.")
     
      // 연결이 열리면 서버에 메시지를 보낼 수 있음
      // 예: 서버에 "Hello, Server!" 메시지 보내기
      socket.send("Hello, Server!")
  };

  socket.onmessage = function(event) {
      // 서버에서 메시지를 받았을 때 실행되는 코드
      const message = event.data;
      console.log("서버로부터 메시지를 받았습니다: " + message);
      
  };

  return () => {
    socket.close()
  }
}, [])

  // 웹소켓서버 연결하면 false를 기본값으로 바꿀 예정
  const [isModalOpened, setIsModalOpened] = useState( true )

  const closeModal = () => {
    setIsModalOpened( false )
  }

  const openModal = () => {
    setIsModalOpened (true )
  }

  return (
    <MatchingWrapper>
      <div style={{ display:'flex', justifyContent:'center', height:'10%'}}>
        <Title type='large'>직관 메이트를 찾고 있어요</Title>
      </div>
      <div style={{ display: 'flex', justifyContent:'center', height:'80%'}}>
      <MatchingQueue data={ matchingData }/>
      { isModalOpened && <MatchingModal isOpen={ isModalOpened } onClose={ closeModal } />}
      </div>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '10%'}}>
        <Timer duration={ 180 }/>
      </div>
    </MatchingWrapper>
  )
}

export default Matching