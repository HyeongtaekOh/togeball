import useStore from 'src/store'
import styled, { css } from 'styled-components'



const ChatMessageWrapper = styled.div<{ type?: string}>` 
  display: flex;
  justify-content: ${ props => props.type !== 'you' ? 'flex-end' : 'flex-start' }; // 오른쪽 또는 왼쪽으로 정렬
  margin-bottom: 10px;
`

const ChatWrapper = styled.div<{ type?: string}>`
  width: 30%;
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

const ChatMessage = ( props: ChatMessageProps ) => {
  const { content, senderId, time } = props
  const { session } = useStore()
  const userId = session?.id
  const type = userId === senderId ? 'me' : 'you';

  console.log(senderId, userId )

  return (
    <ChatMessageWrapper type = { type }> 
      <ChatWrapper type = { type }>  
        <strong>{ session.nickname } :</strong>
        { content }
        { time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{ time.substring(0, 8) }</span>}
      </ChatWrapper>
    </ChatMessageWrapper>
  )
}

export default ChatMessage

type ChatMessageProps = {
  content?: string;
  senderId?: number;
  time?: string;
}