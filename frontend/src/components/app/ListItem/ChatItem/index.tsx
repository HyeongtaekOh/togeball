import { partiChat } from 'src/api/chat/partiChat'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { Title } from 'src/components'
import styled from 'styled-components'
import useStore from 'src/store'
import { TagType } from '@/types'

const ChatWrapper = styled.div`
    display: flex;
    Background-color: white;
    border-radius: 10px;
    border: 2px solid #6A60A9;
    height: 50px;
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


const ChatItem = ( props: ChatListProps ) => {

  const { item, type } = props

  const navigator = useNavigate()
  const partiMutation = useMutation( partiChat )

  const goChat = () => {
    partiMutation.mutateAsync({ chatRoomId : item?.id })
    navigator(`/chat/${ item?.id }`)
  }

  return(
   <ChatWrapper onClick={()=> goChat() }>
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
    {
      type !== 'my' &&
      <p style={{ paddingTop: '40px' }}>{ item?.members?.length | 0}/ { item?.capacity }명</p> 
    }
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  item?: any,
  type?: string
}