import styled, {css} from "styled-components";
import {Title} from "../../font";

const ChatWrapper = styled.div<{ width?: string}>`
    display: flex;
    Background-color: white;
    
    border-radius: 10px;
    border: 3px solid #6A60A9;
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
    children, title,logo, mbti,content, numberofuser, onClick, width='1000px'
  } = props


  return(
   <ChatWrapper width='1000px'>
    <img src="" alt="로고" />
    <TextWrapper>
      
      <Title type="medium">gd</Title>
      <Title type="small" style={{marginBottom: '10px'}}>#entj</Title>
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
  content?: string,
  width? : string,

}