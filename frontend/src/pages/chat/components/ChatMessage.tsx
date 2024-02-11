import { useEffect } from 'react'
import { Title } from 'src/components'
import useStore from 'src/store'
import styled, { css } from 'styled-components'

const ChatMessageWrapper = styled.div<{ type?: string}>` 
  display: flex;
  justify-content: ${ props => props.type !== 'you' ? 'flex-end' : 'flex-start' }; // 오른쪽 또는 왼쪽으로 정렬
`

const ChatWrapper = styled.div<{ type?: string}>`
  width: 100%;
  height: 10%;
  background-color:  #DEDCEE;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  margin-top : 1px;
  padding: 5px;

  ${( props ) =>
    props.type ==='you' &&
    css`
    background-color: #FBD14B;
  `}
`
const ChatTopWrapper = styled.div<{ type?: string}>`
  width: 30%;
  height: 10%;
  display: flex;
  flex-direction: column;
  padding: 5px;
  gap : 3px;
`

const ChatMessage = ( props: ChatMessageProps ) => {

  const { time, message } = props
  const { content, senderId, nickname } = message
  
  const userId = localStorage.getItem('userId')
  const type = userId == senderId ? 'me' : 'you';

  return (
    <ChatMessageWrapper type = { type }> 
    {
      senderId? (
        <ChatTopWrapper type = { type }>
        <p style={{ fontSize: '12px', marginLeft: '5px' }}>{ nickname } </p>
        <ChatWrapper type = { type }>  
          { content }
          { <p style={{ alignSelf: 'flex-end', marginRight: '5px', fontSize: '10px' }}>{ time.substring(0, 7) }</p>}
        </ChatWrapper>  
      </ChatTopWrapper>
      ):(
        <Title type='small'>{ content }</Title>
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