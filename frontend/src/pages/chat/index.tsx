import { useState, useEffect } from 'react'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { useSession } from 'src/hooks'
import { useParams } from 'react-router-dom'
import { ChatMessage } from './components'

type PathParam = {
  chatroomId?: string
}

const Chat = ( ) => {

  const { chatroomId } = useParams<PathParam>()
  const [ messages, setMessages ] = useState([])
  const [ input, setInput ] = useState('')

  const session = useSession()

  const [ userId ] = useState( session( 'id' ) )

  const socket = new SockJS(`/chat?userId=${ userId }`)
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
      })
    }

    const onError = (error) => {
      console.error('에러 발생:', error)
    }

    stompClient.connect({}, onConnect, onError )

    return () => {
      stompClient.disconnect()
    }
  }, []);

  const sendMessage = () => {
    if ( !input ) {
      alert( '메시지를 입력하세요.' );
      return;
     }
   
     const chatMessage = {
         roomId: chatroomId,
         senderId: userId,
         type: 'TALK',
         content: input
     }
   
     if ( stompClient && chatroomId ) {
         stompClient.send(`/pub/chat.${ chatroomId }`, {}, JSON.stringify( chatMessage ))
         setInput('')
     } else {
         alert( 'You are not connected to a chat room.' )
     }
  }

  return (
    <div>
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
      <button onClick={ sendMessage }>전송</button>
    </div>
  )

}

export default Chat;
