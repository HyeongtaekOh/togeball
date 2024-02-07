import { useState, useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import { ChatMessage, Participants } from './components'
import { HomeLayout, InputBox, LeftIcon, MainLayout } from 'src/components'
import styled from 'styled-components'
import { stompClient } from './util/chat'

const ChatPageWrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: row;
`
const ChatWrapper = styled.div`
  width: 100%;
  height: 100%;
  padding: 20px;
  display: flex; /* 추가 */
  flex-direction: column; /* 추가 */
`

const ScriptWrapper = styled.div`
  flex-grow: 1; 
  border: 1px solid #DEDCEE;
  border-radius: 10px;
  height: 70%;
  max-height:70%;
  overflow-y: scroll;
`

type PathParam = {
  chatroomId?: string
}

const Chat = () => {
  // const { chatroomId } = useParams< PathParam >()
  const chatroomId = 0
  const [ messages, setMessages ] = useState([])
  const  [input, setInput ] = useState('')
  const [messageTimes, setMessageTimes] = useState({})
  const scriptEndRef = useRef< HTMLDivElement >( null ) // 스크롤 이동을 위한 Ref 추가

  useEffect(() => {
    const onConnect = () => {
      stompClient.connected &&
        stompClient.subscribe(`/topic/room.${ chatroomId }`, ( message ) => {
          const newMessage = JSON.parse( message.body )
          console.log( newMessage.sender )
          console.log( chatroomId )
          setMessages(( prevMessages ) => [
            ...prevMessages,
            { content: newMessage.content, sender: newMessage.sender },
          ])
          setMessageTimes(( preMessageTimes) => ({
            ...preMessageTimes,
            [ newMessage.content ]: new Date().toLocaleTimeString(),
          }))
        })
    }

    const onError = (error) => {
      console.error('에러 발생:', error)
    }

    const connectToStomp = async () => {
      try {
        await stompClient.connect( {}, onConnect, onError )
      } catch (error) {
        console.error('Stomp 연결에 실패했습니다:', error)
      }
    };

    connectToStomp()

    return () => {
      stompClient.connected && stompClient.disconnect()
    }
  }, [ chatroomId ])

  useEffect(() => {
    if (scriptEndRef.current) {
      scriptEndRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' }) // 스크롤이 항상 하단으로 이동하도록 수정
    }
  }, [ messages ])

  const sendMessage = () => {
    if (input.trim() !== '') { 
      stompClient.connected &&
      stompClient.send(`/topic/room.${chatroomId}`, {}, JSON.stringify({ content: input, senderId:'hi', roomId: 123, type: 'TEXT' }))
      setInput('')
    }
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') {
      sendMessage()
    }
  }

  return (
    <MainLayout>
      <HomeLayout>
        <ChatPageWrapper>
          <Participants />
          <ChatWrapper>
            <ScriptWrapper>
              {messages.map((message, index) => (
                <ChatMessage key={ index } content={ message.content } sender={ message.sender } time={messageTimes[message.content]} />
              ))}
              <div ref={ scriptEndRef }></div> {/* 채팅창 맨 아래로 스크롤하기 위한 Ref 추가 */}
            </ScriptWrapper>
            
              <InputBox
                value={ input }
                icon={<LeftIcon/>}
                onChange={(e) => setInput(e.target.value)}
                onKeyDown={handleKeyDown}
                placeholder="메시지를 입력하세요"
                >
                <button onClick={sendMessage}>전송</button>
  
              </InputBox>
            
          </ChatWrapper>
        </ChatPageWrapper>
      </HomeLayout>
    </MainLayout>
  )
}

export default Chat
