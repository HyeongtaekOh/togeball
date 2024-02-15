import { getUserInfo } from 'src/api'
import { useEffect, useState } from 'react'
import { Title } from 'src/components'
import styled, { css } from 'styled-components'
import Logo from 'src/asset/images/Logo.jpg'

const ChatMessageWrapper = styled.div<{ isMe?: string }>` 
  display: flex;
  min-width: 300px;
  justify-content: ${ props => props.isMe !== 'you' ? 'flex-end' : 'flex-start' }; 
`
const ChatWrapper = styled.div<{ isMe?: string}>`
  background-color:  #DEDCEE;
  min-height: 30px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  margin-top : 1px;
  padding: 7px;
  margin: 0px 10px;

  ${( props ) =>
    props.isMe ==='you' &&
    css`
    background-color: #FBD14B;
  `}
`
const ChatTopWrapper = styled.div<{ isMe?: string }>`
  width: 30%;
  display: flex;
  flex-direction: column;
  padding: 5px;
  gap : 3px;
`
const TimeWrapper = styled.p<{ isMe? : string }>`
  align-self: flex-end;
  font-size: 10px;
  min-width:50px;
  width:20%;
  text-align: ${(props)=>props.isMe === 'me'? 'right': 'left' }
`
const NickWrapper = styled.p<{ isMe? : string }>`
  display: flex;
  font-size: 11px;
  align-items: center;
  font-weight: bold;
`
const ImgWrapper = styled.img`
  width: 20px;
  height: 20px;
  border-radius: 50%;
`

const ChatMessage = ( props: ChatMessageProps ) => {

  const { time, message } = props
  const { content, senderId, nickname, type } = message
  
  const userId = localStorage.getItem( 'userId' )
  const isMe = userId == senderId ? 'me' : 'you';
  const [ userImage, setUserImage ] = useState()

  useEffect(()=>{
    const getUser = async() => {
      const user =  await getUserInfo( senderId )
      setUserImage( user?.profileImage )
      return user
    }
    getUser()
  },[])

  return (
    <ChatMessageWrapper isMe = { isMe }> 
    {
      senderId? (
          <ChatTopWrapper isMe = { isMe }>
          <div style={{ display:'flex', flexDirection: 'row', gap: '5px' }}>
            { isMe === 'me' &&  <TimeWrapper isMe={ isMe }>{ time.substring(0, 8) }</TimeWrapper>}
            <div style={{ display:'flex', flexDirection: 'column', gap : '5px', width:'100%' }}>
            {
              ( isMe==='me' )?(
               <div style={{ display:'flex', width: '100%', justifyContent: 'flex-end', gap: '5px'}}>
               <NickWrapper>{ nickname } </NickWrapper>
               <ImgWrapper src = { userImage || Logo } alt="hi"/>
               </div>
              ):(
               <div style={{ display:'flex', width: '100%', justifyContent: 'flex-start', gap: '5px' }}>
               <ImgWrapper src = { userImage || Logo } alt="hi"/>
               <NickWrapper>{ nickname } </NickWrapper>
               </div>
              )
            }
            {
              type === 'IMAGE'?
              ( <ChatWrapper isMe = { isMe }><img src={ content } style={{ width: '100%', height: '100%' }} alt=''/></ChatWrapper> ):
              ( <ChatWrapper isMe = { isMe }>{ content }</ChatWrapper> )
            }
            </div>  
            { isMe === 'you' && <TimeWrapper isMe={ isMe }>{ time.substring(0, 8) }</TimeWrapper>}
          </div>  
          </ChatTopWrapper>
      ):( 
        <div style={{ display: 'flex', width: '100%', justifyContent: 'center' }}>
          <Title type='small'>{ content }</Title> 
        </div>
      )
    }
    </ChatMessageWrapper>
  )
}

export default ChatMessage
 type ChatMessageProps = {
  content?: string;
  senderId?: number;
  time?: string;
  message? :any
}