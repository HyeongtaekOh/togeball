import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import Stomp from 'stompjs'
import   { MatchingQueue, Timer, MatchingModal }   from './components'
import { Title } from 'src/components'
import styled from 'styled-components'
import SockJS from 'sockjs-client'
import { Participants } from '../chat/components'



const MatchingWrapper = styled.div`
  background-color: #7D74B4;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
`

const Matching: React.FC = () => {

 
  const navigator = useNavigate()
 

  // 매칭에 올라온 큐를 다음과 같이 바꿔야함.


  const [ matchingData, setMatchingData ] = useState({
    hashtags: [  ],
    counts: {
      
    },
  });

  const [ participants, setParticipants ] = useState({
    participants: []
  })

  useEffect(() => {

     // WebSocket 연결 설정
    const clientId = localStorage.getItem('accessToken')
    
    if (!clientId) return alert('로그인이 필요합니다!'), navigator('/home')
    
    
    const token = clientId.substring(7)
    
   
    const socket = new SockJS('https://i10a610.p.ssafy.io:8083/matching-server/matching?token=' + token)
    
    console.log(socket) 

    socket.onopen = function(event) {
      // WebSocket 연결이 열렸을 때 실행되는 코드
      console.log("WebSocket 연결이 열렸습니다.")

      // 예: 서버에 "Hello, Server!" 메시지 보내기
      socket.send("Hello, Server!")
  }

  socket.onmessage = function(event) {
      // 서버에서 메시지를 받았을 때 실행되는 코드
      const message = event.data
      //TODO : state를 업데이트하는 useState 추가.
      const newMessage = JSON.parse(message)
      
      setMatchingData({
        hashtags: newMessage.hashtags, 
        counts: newMessage.counts
      });
      
  }

  return () => {
    socket.close()
  }
}, [ matchingData, participants  ])

  // 웹소켓서버 연결하면 false를 기본값으로 바꿀 예정
  const [isModalOpened, setIsModalOpened] = useState( false )

  const closeModal = () => {
    setIsModalOpened( false )
  }

  

  return (
    <MatchingWrapper>
      <div style={{ display:'flex', justifyContent:'space-between', height:'10%'}}>
        <Title type='large'>직관 메이트를 찾고 있어요</Title>
        <img src="https://cdn.pixabay.com/animation/2023/10/22/03/31/03-31-43-608_512.gif" alt="" />
      </div>
      <div style={{ display: 'flex', justifyContent:'center', height:'80%'}}>
      <MatchingQueue data={ matchingData }/>
      { isModalOpened && <MatchingModal isOpen={ isModalOpened } onClose={ closeModal } participants = { participants }  />}
      </div>
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '10%'}}>
        <Timer duration={ 180 }/>
      </div>
    </MatchingWrapper>
  )
}

export default Matching