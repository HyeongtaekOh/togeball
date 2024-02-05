import { useState, useEffect } from 'react'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { useSession } from 'src/hooks'
import { useParams } from 'react-router-dom'
import { ChatMessage, Participants } from './components'
import { HomeLayout, InputBox, LeftIcon, MainLayout } from 'src/components'
import styled from 'styled-components'


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

`

type PathParam = {
  chatroomId?: string
}

const Chat = ( ) => {

  const { chatroomId } = useParams<PathParam>()
  const [ messages, setMessages ] = useState([])
  const [ input, setInput ] = useState('')

  const session = useSession()

  const [ userId ] = useState( session( 'id' ) )

  const socket = new SockJS(`https://i10a610.p.ssafy.io:8082/chat-server/chat?userId=${userId}`)

  socket.onerror = (error) => {
    // 여기서 에러를 캐치하고 처리하거나 로그할 수 있습니다.
    console.error('WebSocket Error:', error)
  
    // 예외를 던지거나 다른 에러 처리 로직을 추가할 수 있습니다.
  };

  const stompClient = Stomp.over( socket )

  useEffect(() => {

    const onConnect = () => {

      stompClient.subscribe(`/topic/room.${ chatroomId }`, ( message ) => {
        const body = JSON.parse( message.body )
        const newMessage = {
          sender: body.senderId,
          message: body.message,
          timestamp: new Date( body.timestamp )
        }
        setMessages(( prevMessages ) => [ ...prevMessages, newMessage ])
      }, {'auto-delete': true, 'durable': false, 'exclusive': false })
    }

    const onError = (error) => {
      console.error('에러 발생:', error)
    }

    stompClient.connect({}, onConnect, onError )

    return () => {
      stompClient.disconnect()
    }
  }, []);

  // const sendMessage = () => {
  //   if ( !input ) {
  //     alert( '메시지를 입력하세요.' );
  //     return;
  //    }
   
  //    const chatMessage = {
  //        roomId: chatroomId,
  //        senderId: userId,
  //        type: 'TALK',
  //        content: input
  //    }
   
  //    if ( stompClient && chatroomId ) {
  //        stompClient.send(`/pub/chat.${ chatroomId }`, {}, JSON.stringify( chatMessage ))
  //        setInput('')
  //    } else {
  //        alert( 'You are not connected to a chat room.' )
  //    }
  // }

  return (
    <MainLayout>
      <HomeLayout>
        <ChatPageWrapper>
          <Participants/>
          <ChatWrapper>
            <div>
              { messages.map(( message, index) => (
                <ChatMessage key={ index } content={ message.content } sender={ message.sender } />
              ))}
            </div>
            <input
              type="text"
              value={ input }
              onChange={( e ) => setInput( e.target.value )}
            />
            <button>전송</button>
            <InputBox
              value={ input }
              icon = { <LeftIcon/> }
              onChange={( e ) => setInput( e.target.value )}
            ></InputBox>
          </ChatWrapper>
        </ChatPageWrapper>
      </HomeLayout>
    </MainLayout>

  )

}

export default Chat;
