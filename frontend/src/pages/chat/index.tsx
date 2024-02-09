import { useState, useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import { ChatMessage, Participants } from './components'
import { InputBox, LeftIcon, MainLayout } from 'src/components'
import { stompClient } from './util/chat'
import useStore from 'src/store'
import styled from 'styled-components'

const ChatPageWrapper = styled.div`
  width: 80%;
  height: 95%;
  display: flex;
  flex-direction: row;
`
const ChatWrapper = styled.div`
  width: 100%;
  height: 100%;
  padding: 5px;
  display: flex; 
  flex-direction: column; 
  padding: 10px;
  padding-top: 0px;
`
const ScriptWrapper = styled.div`
  border: 1px solid #DEDCEE;
  border-radius: 10px;
  padding: 10px;
  height: 90%;
  overflow-y: scroll;
`

type PathParam = {
  chatroomId?: string
}

const Chat = () => {
  const { chatroomId } = useParams< PathParam >()

  const [ messages, setMessages ] = useState([])
  const  [ input, setInput ] = useState('')
  const [ messageTimes, setMessageTimes ] = useState({})
  const scriptEndRef = useRef< HTMLDivElement >( null ) 
  const { session } = useStore()

  useEffect(() => {
    const onConnect = () => {
      stompClient.connected &&
        stompClient.subscribe(`/topic/room.${ chatroomId }`, ( message ) => {
          const newMessage = JSON.parse( message.body )
          console.log( newMessage.senderId )
          console.log( chatroomId )
          setMessages(( prevMessages ) => [
            ...prevMessages,
            { content: newMessage.content, senderId: newMessage.senderId },
          ])
          setMessageTimes(( preMessageTimes) => ({
            ...preMessageTimes,
            [ newMessage.content ]: new Date().toLocaleTimeString(),
          }))
        })
    }

    const onError = ( error ) => {
      console.error('에러 발생:', error)
    }

    const connectToStomp = async () => {
      try {
        await stompClient.connect( {}, onConnect, onError )
      } catch ( error ) {
        console.error('Stomp 연결에 실패했습니다:', error)
      }
    };

    connectToStomp()

    return () => {
      stompClient.connected && stompClient.disconnect()
    }
  }, [ chatroomId ])

  useEffect(() => {
    if ( scriptEndRef.current ) {
      scriptEndRef.current.scrollIntoView({ behavior: 'smooth', block: 'end' }) 
    }
  }, [ messages ])

  const sendMessage = () => {
    if ( input.trim() !== '' ) { 
      stompClient.connected &&
      stompClient.send(
        `/topic/room.${ chatroomId }`, 
        {}, 
        JSON.stringify({ content: input, senderId: session?.id, roomId: chatroomId, type: 'TEXT' })
      )
      setInput('')
    }
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if ( e.key === 'Enter' ) {
      sendMessage()
    }
  }

  return (
    <MainLayout>
        <ChatPageWrapper>
          <Participants />
          <ChatWrapper>
            <ScriptWrapper>
              { messages.map(( message, index ) => (
                <ChatMessage key={ index } content={ message?.content } senderId={ message?.senderId } time={ messageTimes[ message.content ] } />
              ))}
              <div ref={ scriptEndRef }/>
            </ScriptWrapper>
            
              <InputBox
                value={ input }
                icon={ <LeftIcon/> }
                onChange={ (e) => setInput( e.target.value ) }
                onKeyDown={ handleKeyDown }
                placeholder="메시지를 입력하세요"
                >
                <button onClick={ sendMessage }>전송</button>
  
              </InputBox>
            
          </ChatWrapper>
        </ChatPageWrapper>
    </MainLayout>
  )
}

export default Chat
