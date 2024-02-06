import { useState, useEffect } from 'react'
import { useSession } from 'src/hooks'
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

`

type PathParam = {
  chatroomId?: string
}

const Chat = ( ) => {

  const { chatroomId } = useParams<PathParam>()
  const [ messages, setMessages ] = useState([])
  const [ input, setInput ] = useState('')

  // const session = useSession()
  // const [ userId ] = useState("hi")

  useEffect(() => {

    const onConnect = () => {

      stompClient.connected && 
      stompClient.subscribe(`/topic/room.${ chatroomId }`, ( message ) => {
        const newMessage = JSON.parse( message.body )
        setMessages(( prevMessages ) => [ ...prevMessages, newMessage ])
      }, {'auto-delete': true, 'durable': false, 'exclusive': false })
    
    }

    const onError = (error) => {
      console.error('에러 발생:', error)
    }

    const connectToStomp = async () => {
      try {
        await stompClient.connect({}, onConnect, onError);
      } catch (error) {
        console.error('Stomp 연결에 실패했습니다:', error);
      }
    }

    connectToStomp()

    return () => {
      stompClient.conneted && stompClient.disconnect()
    }

  }, [ chatroomId ])

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
