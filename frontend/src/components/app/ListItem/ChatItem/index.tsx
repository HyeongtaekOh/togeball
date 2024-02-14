import { partiChat } from 'src/api/chat/partiChat'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { Title } from 'src/components'
import styled from 'styled-components'
import useHeaderStore from '../../Header/store'


const ChatWrapper = styled.div<{ width?: string }>`
  display: flex;
  Background-color: white;
  width:  ${( props ) => props.width  };
  border-radius: 10px;
  border: 2px solid #6A60A9;
  padding: 10px;
  padding-bottom: 2px;
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
  width: 60%;
`
const DescribeWrapper = styled.p`
  display: flex;
  flex-wrap: nowrap;
  margin-top: 2px;
  margin-bottom: 8px;
`
const TagWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
`


const ChatItem = ( props: ChatListProps ) => {

  const { item, type, width } = props
  const { updateCount, count } = useHeaderStore()
  const unReadCount = count

  const navigator = useNavigate()
  const partiMutation = useMutation( partiChat, {
    onSuccess: () => {
      navigator(`/chat/${ item?.id }`)
    }
  } )

  const goChat = () => {
    if( !localStorage.getItem('userId') ){
      alert(' 로그인 하세요 ')
      navigator('/login')
    } 
    else {
      ( type === 'my' ) && 
        updateCount(count - item?.status?.unreadCount)  
      partiMutation.mutateAsync({ chatRoomId : item?.id })
    }
  }

  return(
   <ChatWrapper onClick={()=> goChat()} width={ width }>
     <img src={ item?.cheeringClub?.logo } alt='로고' style={{ width: '8%', minWidth:'60px',marginBottom: '8px' }}/> 
    <TextWrapper>
      <div style={{ display: 'block', width:'100%' }}>
      <Title type='medium'>{ item?.title }</Title>
      {
        type !== 'my' &&
        <DescribeWrapper>{ item?.description }</DescribeWrapper>
      }
      </div>
        <TagWrapper>
          { item.tags.map(( tag ) => (
            <Title type='small'>#{ tag?.content }&nbsp;</Title>
          ))}
        </TagWrapper>
        {
          type === 'my' &&
          <p style={{ margin: '10px 0px 2px 0px', fontSize: '12px' }}>{ item?.status?.latestChatMessage?.content }</p> 
        }
    </TextWrapper>
    {
      type !== 'my' &&
      <p style={{ paddingTop: '40px'}}>{ item?.members?.length | 0}/ { item?.capacity }명</p> 
    }
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  item?: any,
  type?: string,
  width?: string
}