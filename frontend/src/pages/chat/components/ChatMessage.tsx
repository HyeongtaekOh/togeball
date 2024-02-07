import { useState } from "react";
import { useSession } from "src/hooks";
import styled from "styled-components";

interface ChatMessageWrapperProps {
  right: boolean; 
}

const ChatMessageWrapper = styled.div<ChatMessageWrapperProps>` // ChatMessageWrapperProps를 제네릭으로 전달
  display: flex;
  justify-content: ${props => props.right ? 'flex-end' : 'flex-start'}; // 오른쪽 또는 왼쪽으로 정렬
  margin-bottom: 10px;
`

const MyWrapper = styled.div`
  width: 30%;
  height: 10%;
  background-color: #DEDCEE;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
`

const ChatMessage = (props: ChatMessageProps) => {
  const { content, sender, time } = props;
  const session = useSession();
  const [userId] = useState(session('id'));

  return (
    <ChatMessageWrapper right={userId === sender}> {/* right 프롭스 전달 */}
      {userId === sender ? (
        <MyWrapper>
          <strong>{ sender }나 :</strong>
          {content}
          {time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{time.substring(0, 8)}</span>}
        </MyWrapper>
      ) : (
        <div>
          <strong>{ sender }</strong>
          {content}
          {time && <span style={{ marginLeft: '10px', fontSize: '9px' }}>{time.substring(0, 8)}</span>}
        </div>
      )}
    </ChatMessageWrapper>
  )
}


export default ChatMessage;

type ChatMessageProps = {
  content?: string;
  sender?: string;
  time?: string;
}