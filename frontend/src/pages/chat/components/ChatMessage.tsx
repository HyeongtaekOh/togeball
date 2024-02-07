import { useState } from "react"
import styled, { css } from "styled-components"



const ChatMessageWrapper = styled.div< ChatMessageProps >` 
  display: flex;
  justify-content: ${ props => props.right ? 'flex-end' : 'flex-start' }; // 오른쪽 또는 왼쪽으로 정렬
  margin-bottom: 10px;
`

const MyWrapper = styled.div< ChatMessageProps >`
  width: 30%;
  height: 10%;
  background-color:  #DEDCEE;
  border-radius: 10px;
  display: flex;
  flex-direction: column;

  ${( props ) =>
    props.type ==='you' &&
    css`
    background-color: #FBD14B;
  `}
`

const ChatMessage = ( props: ChatMessageProps ) => {
  const { content, senderId, time, type='me', right=true } = props
  const [ userId ] = useState('hi')

  return (
    <ChatMessageWrapper right={ userId === senderId }> {/* right 프롭스 전달 */}
      { userId === senderId ? (
        <MyWrapper>
          <strong>{ senderId }나 :</strong>
          { content }
          { time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{ time.substring(0, 8) }</span>}
        </MyWrapper>
      ) : (
        <MyWrapper type='you' right={ false } >
          <strong>{ senderId }</strong>
          { content }
          {time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{ time.substring(0, 8) }</span>}
        </MyWrapper>
      )}
    </ChatMessageWrapper>
  )
}


export default ChatMessage;

type ChatMessageProps = {
  content?: string;
  senderId?: string;
  time?: string;
  type?: 'me' | 'you'
  right?: boolean;
}