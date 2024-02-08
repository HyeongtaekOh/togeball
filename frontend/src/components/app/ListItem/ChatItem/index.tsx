import { Title } from "src/components"
import styled from "styled-components"

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




const ChatItem = (props: ChatListProps) => {

  const {
    children, item, onClick, width
  } = props

  const { title } = item

  console.log(item)

  return(
   <ChatWrapper width='1000px'>
     <img src={ item?.cheeringClub?.logo } alt='로고'/> 
    <TextWrapper>
      <Title type='medium'>{ title }</Title>
      <p style={{ marginBottom: "10px" }}>{ item?.description }</p>
      {
        item.tags.map(( tag, index ) => (
          <Title type='small'>#{ tag?.content }</Title>
        ))
      }
    </TextWrapper>
    {/* <p style={{ paddingTop: '40px' }}>{ item['chatrooms'][0].participants.length }/ { item['chatrooms'][0].capacity }명</p>  */}
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  children?: React.ReactNode,
  item: any,
  onClick?: () => void,
  width? : string,
}