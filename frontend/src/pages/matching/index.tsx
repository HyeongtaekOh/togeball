import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import   { MatchingQueue, Timer, MatchingModal }   from './components'
import { Title, MainLayout } from 'src/components'
import styled from 'styled-components'
import SockJS from 'sockjs-client'

const MatchingWrapper = styled.div`
  background-color: white;
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

  const [ matchingInfo, setMatchingInfo ] = useState({
    participants: {},
    chatroomId: undefined,
    title : undefined,

  })

  useEffect(() => {
    // WebSocket 연결 설정
   const clientId = localStorage.getItem('accessToken')
   
   if (!clientId) {
    alert('로그인이 필요합니다!')
    navigator('/home')
    return
   }
   
   
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
      if ( message[2] === 'h'){
        const newMessage = JSON.parse(message)
        console.log(message)
        console.log( newMessage)
        setMatchingData({
          hashtags: newMessage.hashtags, 
          counts: newMessage.counts
        })

      }
      else {
        const newMessage = JSON.parse(message)
        console.log(message)
        console.log( newMessage )
        setMatchingInfo({
          participants: newMessage.matching.users,
          title: newMessage.matching.title,
          chatroomId: newMessage.matching.matchingChatroomId
        })
        setIsModalOpened(true)
      }
      
  }

  return () => {
    socket.close()
  }
}, [])

  // 웹소켓서버 연결하면 false를 기본값으로 바꿀 예정
  const [isModalOpened, setIsModalOpened] = useState( false )

  const closeModal = () => {
    setIsModalOpened( false )
  }

  

  return (
    <MainLayout>
      <MatchingWrapper> 
        <div style={{ display:'flex', justifyContent:'center', zIndex: '777'}}>
          <Title type='large'>직관 메이트를 찾고 있어요</Title>
          
        </div>
        <div style={{ display: 'flex', justifyContent:'center', height:'100%'}}>
        <MatchingQueue data={ matchingData }/>
        { isModalOpened && 
          <MatchingModal 
            isOpen={ isModalOpened } onClose={ closeModal } 
            participants = { matchingInfo.participants } 
            chatroomId = { matchingInfo.chatroomId } title = { matchingInfo.title } 
          />}
        </div>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', zIndex: '777'}}>
          <Timer duration={ 180 }/>
        </div>
      </MatchingWrapper>
    </MainLayout>
   
  )
}

export default Matching