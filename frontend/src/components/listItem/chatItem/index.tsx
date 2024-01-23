import styled, {css} from "styled-components";

const ChatWrapper = styled.div`
    display: flex;
    Background-color: white;
    box-shadow: 5px 5px 5px gray;
    border-radius: 10px;
    width: 100%;
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
    children, title,logo, mbti,content, numberofuser, onClick, 
  } = props


  return(
   <ChatWrapper>
    <img src="" alt="로고" />
    <TextWrapper>
      <p>토요일 같이 경기 가실분 구해요</p>
      <p style={{marginTop: "-25px"}}>#ENFJ #ESTP</p>
      <p style={{marginBottom: "10px"}}>어디야?</p>
    </TextWrapper>
    <p style={  { paddingTop: "40px" } }>4/10명</p>



   </ChatWrapper>
  )
}

export default ChatList

type ChatListProps = {
  children?: React.ReactNode,
  title?: string,
  mbti?: string,
  numberofuser?: string,
  onClick?: () => void,
  
  logo?: string,
  content?: string

}