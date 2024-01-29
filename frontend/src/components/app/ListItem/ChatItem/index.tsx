import styled, { css } from "styled-components";
import { Title } from "../../../font";

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
    children, title, logo, tags, capacity, content, numberofuser, onClick, width='1000px'
  } = props


  return(
   <ChatWrapper width='1000px'>
    <img src={ logo } alt="로고"/>
    <TextWrapper>
      
      <Title type='medium'>{ title }</Title>
      <p style={{ marginBottom: "10px" }}>{ content }</p>
      <Title type='small'>{ tags }</Title>
    </TextWrapper>
    <p style={{ paddingTop: '40px' }}>{ numberofuser }/ { capacity }명</p>



   </ChatWrapper>
  )
}

export default ChatList

type ChatListProps = {
  children?: React.ReactNode,
  title?: string,
  tags?: string[],
  numberofuser?: number,
  capacity?: number,
  onClick?: () => void,
  logo?: string,
  content?: string,
  width? : string,
}