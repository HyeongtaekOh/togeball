import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import   { MatchingQueue, Timer, MatchingModal }   from './components'
import { Title, MainLayout } from 'src/components'
import styled from 'styled-components'
import SockJS from 'sockjs-client'
import useStore from 'src/store'

const MatchingWrapper = styled.div`
  background-color: white;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
`
const TimerWrapper = styled.div`
display: flex;
justify-content: center;
align-items: center;
z-index: 777;
background-color: transparent;
`;

const Matching: React.FC = () => {
 
  const navigator = useNavigate()
 
  const [ matchingData, setMatchingData ] = useState({
    hashtags: [],
    counts: {},
  });

  const [ matchingInfo, setMatchingInfo ] = useState({
    participants: {},
    chatroomId: undefined,
    title : undefined,

  })

  const { setIsLogin } = useStore()

  useEffect(() => {
   const clientId = localStorage.getItem('accessToken')
   
   if ( !clientId ) {
    alert('로그인이 필요합니다!')
    setIsLogin(false)
    navigator('/home')
    return
   }
   
   const token = clientId.substring(7)
  
   const socket = new SockJS('https://i10a610.p.ssafy.io:8083/matching-server/matching?token=' + token)
   
   socket.onopen = function(event) {
     console.log("WebSocket 연결이 열렸습니다.")
  
     socket.send("Hello, Server!")
  }


  socket.onmessage = function(event) {
    const message = event.data
    if ( message[2] === 'h'){
        const newMessage = JSON.parse(message)
        setMatchingData({
          hashtags: newMessage.hashtags, 
          counts: newMessage.counts
        })
      }

      else {
        const newMessage = JSON.parse(message)
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

  const [isModalOpened, setIsModalOpened] = useState( false )

  const closeModal = () => {
    setIsModalOpened( false )
  }

  return (
    <MainLayout title='직관 메이트를 찾고 있어요'>
      <MatchingWrapper> 
        <div style={{ display: 'flex', justifyContent:'center', height:'100%'}}>
        <MatchingQueue data={ matchingData }/>
        { 
          isModalOpened && 
          <MatchingModal 
            isOpen={ isModalOpened } onClose={ closeModal } 
            participants = { matchingInfo.participants } 
            chatroomId = { matchingInfo.chatroomId } title = { matchingInfo.title } 
          />
        }
        </div>
        <TimerWrapper>
          <Timer duration={ 180 }/>
        </TimerWrapper>
      </MatchingWrapper>
    </MainLayout>
   
  )
}

export default Matching