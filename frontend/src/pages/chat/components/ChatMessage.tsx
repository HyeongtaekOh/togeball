import { Title } from 'src/components'
import styled, { css } from 'styled-components'

const ChatMessageWrapper = styled.div<{ type?: string }>` 
  display: flex;
  justify-content: ${ props => props.type !== 'you' ? 'flex-end' : 'flex-start' }; 
`
const ChatWrapper = styled.div<{ type?: string}>`
  background-color:  #DEDCEE;
  min-height: 30px;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  margin-top : 1px;
  padding: 7px;

  ${( props ) =>
    props.type ==='you' &&
    css`
    background-color: #FBD14B;
  `}
`
const ChatTopWrapper = styled.div<{ type?: string }>`
  width: 30%;
  display: flex;
  flex-direction: column;
  padding: 5px;
  gap : 3px;
`
const TimeWrapper = styled.p<{ type? : string }>`
  align-self: flex-end;
  font-size: 10px;
  width:20%;
  text-align: ${(props)=>props.type === 'me'? 'right': 'left' }
`
const NickWrapper = styled.p<{ type? : string }>`
  align-self: ${(props)=>props.type === 'me'? 'flex-end': 'flex-start' };
  font-size: 10px;
`

const ChatMessage = ( props: ChatMessageProps ) => {

  const { time, message } = props
  const { content, senderId, nickname } = message
  
  const userId = localStorage.getItem( 'userId' )
  const type = userId == senderId ? 'me' : 'you';

  return (
    <ChatMessageWrapper type = { type }> 
    {
      senderId? (
      <ChatTopWrapper type = { type }>
        <div style={{ display:'flex', flexDirection: 'row', gap: '5px' }}>
          { type === 'me' &&  <TimeWrapper type={ type }>{ time.substring(0, 7) }</TimeWrapper>}
          <div style={{ display:'flex', flexDirection: 'column', gap : '5px', width:'100%' }}>
            <NickWrapper type = { type }>{ nickname } </NickWrapper>
            <ChatWrapper type = { type }>{ content }</ChatWrapper>
          </div>  
          { type === 'you' && <TimeWrapper type={ type }>{ time.substring(0, 7) }</TimeWrapper>}
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