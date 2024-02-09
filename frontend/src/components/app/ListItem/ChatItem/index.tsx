import { partiChat } from 'src/api/chat/partiChat'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { Title } from 'src/components'
import styled from 'styled-components'
import useStore from 'src/store'

const ChatWrapper = styled.div`
    display: flex;
    Background-color: white;
    border-radius: 10px;
    border: 2px solid #6A60A9;
    width:98%;
    height: 50px;
    padding: 10px;
    justify-content: space-around;
    &:hover{
      background-color: #E4E2DD;
      cursor: pointer;
    }
    margin-bottom: 10px;
`
const TextWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    width: 70%
    `
  const TagWrapper = styled.div`
    display: flex;
    flex-direction: row;
  `

const ChatItem = (props: ChatListProps) => {

  const { item } = props

  const navigator = useNavigate()
  const partiMutation = useMutation( partiChat )
  const { session } = useStore()

  const goChat = ( id : string ) => {
    partiMutation.mutateAsync({ chatRoomId : item?.id, UserId: session?.id })
    navigator(`/chat/${ id }`)
  }

  return(
   <ChatWrapper onClick={()=> goChat( item?.id) }>
     <img src={ item?.cheeringClub?.logo } alt='로고'/> 
    <TextWrapper>
      <Title type='medium'>{ item?.title }</Title>
      <p style={{ marginBottom: "10px" }}>{ item?.description }</p>
      <TagWrapper>
      {
        item.tags.map(( tag, index ) => (
          <Title type='small'>#{ tag?.content }</Title>
        ))
      }
      </TagWrapper>
    </TextWrapper>
    <p style={{ paddingTop: '40px' }}>{ item?.members?.length | 0}/ { item?.capacity }명</p> 
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  item: any,
}