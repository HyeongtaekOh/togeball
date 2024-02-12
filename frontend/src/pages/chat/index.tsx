import { useState, useEffect, useRef } from 'react'
import { useParams } from 'react-router-dom'
import { ChatMessage, Participants } from './components'
import { Button, InputBox, LeftIcon, MainLayout } from 'src/components'
import { stompClient } from './util/chat'
import useStore from 'src/store'
import styled from 'styled-components'
import { getChat, getChatMessages, getParticipants } from 'src/api'
import { useQuery } from 'react-query'
import { formatDate } from './util'

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
const InputBoxWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: row;
  gap: 10px;
  width: 100%;
  align-items: center;
`
const LabelWrapper =styled.label`
  background-color: gray;
  display: flex;
  width: 15px;
  height: 13px;
  color: white;
  padding: 8px;
  font-size: 20px;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
  &:hover{
    background-color: #E4E2DD;
  }
`

type PathParam = {
  chatroomId?: string
}
const Chat = () => {

  const { chatroomId } = useParams< PathParam >()

  const userId = localStorage.getItem('userId')
  const [ messages, setMessages ] = useState([])
  const  [ input, setInput ] = useState('')
  const scriptEndRef = useRef< HTMLDivElement >( null ) 
  const { session } = useStore()
  const { data: participants } = useQuery([ 'participants', { id : chatroomId }], () => getParticipants( { id : chatroomId }))
  const { data : chatInfo } = useQuery([ 'chatInfo', { id : chatroomId }], () => getChat( { id : chatroomId }))
  
  useEffect(() => {
    const onConnect = () => {
      stompClient.connected &&
      stompClient.subscribe(`/topic/room.${ chatroomId }`, ( message ) => {
        const newMessage = JSON.parse( message.body )
        setMessages(( prevMessages ) => [
          ...prevMessages,
          { 
            content: newMessage?.content, 
            senderId: newMessage?.senderId,  
            type : newMessage?.type,
            nickname: newMessage?.nickname,
            time: formatDate(new Date())
          },
      ])},
      { 'auto-delete': true, durable: false, exclusive: false })       
    }

    const connectToStomp = async () => {
      try {
        await stompClient.connect({ Authorization :  userId }, onConnect )
      } catch ( error ) {
        console.error('Stomp 연결에 실패했습니다:', error)
      }
    }

    const param = {
      userId: userId,
      page: 0,
      size: 100
    }

    const getMessages= async () => {
      try{
        setMessages([])
        const response= await getChatMessages( chatroomId, param )
        response?.content.map(( chat )=>{
          setMessages(( prevMessages ) => [
            ...prevMessages,
            { 
              content: chat?.content, 
              senderId: chat?.senderId,  
              type : chat?.type,
              nickname: chat?.nickname,
              time: formatDate(new Date( chat?.timestamp ))
            },
        ])
      })
        return response
      }catch( error ){
        console.error('데이터를 가져오지 못했습니다.', error)
      }
    }

    getMessages()
    connectToStomp()

    return () => {
      stompClient.connected && stompClient.disconnect()
    }
  }, [])

  useEffect(() => {
    scriptEndRef.current && 
    scriptEndRef.current.scrollIntoView({ block: 'end' }) 
  }, [ messages ])

  const sendMessage = () => {
    if( input.trim()==='') return;
    stompClient.connected && stompClient.send(
      `/pub/chat.${ chatroomId }`, 
      { Authorization : userId }, 
        JSON.stringify(
          {
            roomId: chatroomId,
            senderId: userId,
            type: 'TEXT',
            nickname: session?.nickname,
            content: input,
          }
        )
    )
    setInput('')
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    e.key === 'Enter' &&  sendMessage()
  }

  return (
    <MainLayout>
        <ChatPageWrapper>
          <Participants list = { participants } game = { chatInfo?.game }/>
          <ChatWrapper>
            <ScriptWrapper>
              { 
                messages.map(( message, index ) => (
                <ChatMessage 
                  key={ index } 
                  message = { message } 
                  time={ message?.time }  
                />
              ))}
              <div ref={ scriptEndRef }/>
            </ScriptWrapper>
            
            <InputBoxWrapper>
              <input 
                style= {{ display: 'none' }} type= 'file' accept= 'image/*' id= 'files' 
                // ref={ inputRef } onChange={ onUploadImage }
                />
              <LabelWrapper htmlFor= 'files'>+</LabelWrapper>
                <InputBox
                  value={ input }
                  icon={ <LeftIcon/> }
                  onChange={ (e) => setInput( e.target.value ) }
                  onKeyDown={ handleKeyDown }
                  placeholder='메시지를 입력하세요'
                >
                  <Button style={{ padding : '0px' }} width='40px'onClick={ sendMessage }>
                    전송
                  </Button>
                </InputBox>
            </InputBoxWrapper>
            
          </ChatWrapper>
        </ChatPageWrapper>
    </MainLayout>
  )
}

export default Chat
