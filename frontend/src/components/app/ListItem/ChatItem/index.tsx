import { Title } from "src/components";
import styled from "styled-components";

const ChatWrapper = styled.div<{ width?: string}>`
    display: flex;
    Background-color: white;
    border-radius: 10px;
    border: 2px solid #6A60A9;
    width: ${ (props) => props.width };
    height: 100px;
    justify-content: space-around;
    &:hover{
      background-color: #E4E2DD;
      cursor: pointer;
    }     
`
const TextWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    
    width: 70%
    
    `




const ChatList = (props: ChatListProps) => {

  const {
    children, chatList, onClick, content, width
  } = props


  console.log(chatList['chatrooms'][0].title)

  return(
   <ChatWrapper width='1000px'>
     <img src={ chatList['chatrooms'][0].cheeringTeamImageUrl } alt="로고"/> 
    <TextWrapper>
      <Title type='medium'>{ chatList['chatrooms'][0].title }</Title>
      <p style={{ marginBottom: "10px" }}>{ content }</p>
      <Title type='small'>{ chatList['chatrooms'][0].tags }</Title>
    </TextWrapper>
    <p style={{ paddingTop: '40px' }}>{chatList['chatrooms'][0].participants.length }/ {chatList['chatrooms'][0].capacity }명</p> 
   </ChatWrapper>
  )
}

export default ChatList

// type chatroomProps = {
//   participants: Array<{ userId: string, email: string, nickname: string }>,
//   title: string,
//   tags: Array<string>,
//   manager: Array<{userId: string, email: string, nickname: string}>,
//   gameId: number,
//   capacity: number,
//   cheeringTeamImageUrl: string
// }

// type chatItemProps = {
//   chatrooms: chatroomProps[],
//   totalCount: number,
//   pageSize: number,
//   pageNo: number
// }

type ChatListProps = {
  children?: React.ReactNode,
  chatList: object,
  onClick?: () => void,
  content?: string,
  width? : string,
}