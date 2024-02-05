import React, { useEffect, useState } from 'react'
import Stomp from 'stompjs'
import   { MatchingQueue, Timer, MatchingModal }   from './components'
import { Title } from 'src/components'
import styled from 'styled-components'



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
     const socket = new WebSocket(`ws:${ process.env.REACT_APP_BASE_URL }/hashtags/matchings`)
    
     // STOMP 클라이언트 생성
     const stompClient = Stomp.over(socket)
 
     // 연결이 수립되었을 때의 콜백 함수
     const onConnect = () => {
       console.log('WebSocket 연결이 수립되었습니다.')
 
       // 특정 주제(subscribe)에 대한 메시지 수신
       stompClient.subscribe('/topic/matchingQueue', (message) => {
         const newmatchData = JSON.parse(message.body)
         console.log('새로운 매칭 데이터:', newmatchData)
         setMatchingData(newmatchData);
         // TODO: 매칭 데이터를 처리하는 로직 추가
       })
     }

     socket.addEventListener('open', onConnect);
   

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