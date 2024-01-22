import styled, {css} from "styled-components";

const ChatWrapper = styled.div<ChatListProps>`
    display: flex;
    Background-color: white;
    border: 1px solid black;
    border-radius: 10px;
    width = 100%;
    

    &:hover{
      background-color: #9008F2;
      cursor: pointer;
    } 

    ${props =>
    props.type === "sub" &&
    css`
        flex-direction: column;
        align-items: center;
        border: 1px solid white;
        justify-content: center;
        width: 80%
      `
    }
`

const LogoWrapper =styled.img`
  width: 10%;
`

const TitleWrapper = styled.p`
    color: black;
`

const MbtiWrapper = styled.p`
    color: lightgray;
`
const ContentWrapper = styled.p`
`

const NumboerOfUserWrapper = styled.p`
    color: black;
    padding-top: 11px;
`

const ChatList = (props: ChatListProps) => {

  const {
    children, title,logo, mbti,content, numberofuser, onClick, type = 'main'
  } = props

  return(
   <ChatWrapper>
    <LogoWrapper src={logo} alt='로고'/>
    <ChatWrapper type="sub">
        <TitleWrapper>{title}</TitleWrapper>
        <MbtiWrapper>{mbti}</MbtiWrapper>
        <ContentWrapper>{content}</ContentWrapper>

    </ChatWrapper>
    <NumboerOfUserWrapper>{numberofuser}</NumboerOfUserWrapper>


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
  type?: 'main'|'sub',
  logo?: string,
  content?: string

}