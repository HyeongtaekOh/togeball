import { useState } from "react"
import { useSession } from "src/hooks"
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
  const { content, sender, time, type='me', right=true } = props;
  const session = useSession();
  const [ userId ] = useState(session('id'));

  return (
    <ChatMessageWrapper right={ userId === sender }> {/* right 프롭스 전달 */}
      { userId === sender ? (
        <MyWrapper>
          <strong>{ sender }나 :</strong>
          { content }
          { time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{ time.substring(0, 8) }</span>}
        </MyWrapper>
      ) : (
        <MyWrapper type='you' right={ false } >
          <strong>{ sender }상대 :</strong>
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
  sender?: string;
  time?: string;
  type?: 'me' | 'you'
  right?: boolean;
}