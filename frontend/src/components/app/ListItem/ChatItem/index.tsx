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
  width: 120%;
  height: 60px;
  padding: 10px;
  justify-content: space-around;
  &:hover{
    background-color: #E4E2DD;
      cursor: pointer;
  }
  margin-top: 20px;
`
const TextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 70%
`
const DescribeWrapper = styled.p`
display: flex;
flex-wrap: wrap;
margin-top: 2px;
margin-bottom: 8px;
`

const TagWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
`

const CapacityWrapper = styled.p`
  padding-top: 50px;
  font-size: 10px;
  color: #6A60A9;
  opacity: 0.6;
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
     <img src={ item?.cheeringClub?.logo } alt='로고' style={{ width: '20%' }}/> 
    <TextWrapper>
      <Title type='medium' style={{ display: 'flex', flexWrap: 'wrap'}}>{ item?.title }</Title>
        <DescribeWrapper>{ item?.description }</DescribeWrapper>
        <TagWrapper>
          { item.tags.map(( tag, index ) => (
            <Title type='small'>#{ tag?.content }&nbsp;</Title>
          ))}
        </TagWrapper>
    </TextWrapper>
    <CapacityWrapper>
      { item?.members?.length | 0}/ { item?.capacity }명
    </CapacityWrapper> 
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  item: any,
}