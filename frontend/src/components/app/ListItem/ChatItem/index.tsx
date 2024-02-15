import { partiChat } from 'src/api/chat/partiChat'
import { useMutation } from 'react-query'
import { useNavigate } from 'react-router-dom'
import { Title } from 'src/components'
import styled from 'styled-components'
import useHeaderStore from '../../Header/store'
import useStore from 'src/store'
import Logo from 'src/asset/images/Logo.jpg'

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
  const { session } = useStore()
  console.log(item)

  const navigator = useNavigate()
  const partiMutation = useMutation( partiChat, {
    onSuccess: () => {
      ( item?.type === 'RECRUIT' ) ? 
      navigator(`/chat/${ item?.id }`):
      navigator(`/matchChat/${ item?.id }`)
    }
  } )

  const checkParti = () =>{
    item?.members?.map(( member )=>{
      if( member.id === session.id ){
        return 1
      }
    })
    return 2
  }

  const goChat = () => {
    
    if( !localStorage.getItem('userId') ){
      alert(' 로그인 하세요 ')
      navigator('/login')
    } 
    else {
      if( type!=='my' 
      && item?.capacity === item?.members?.length
      && item?.manager?.id !== session?.id
      )
      {
        const check = checkParti()
        if ( check === 2 ){
          alert('인원이 다 찼습니다')
          return
        }
      }
      else {
        updateCount( count - item?.status?.unreadCount )
        partiMutation.mutateAsync({ chatRoomId : item?.id })
      }
    }

  }

  return(
   <ChatWrapper onClick={()=> goChat()} width={ width }>
     <img src={ item?.cheeringClub?.logo || Logo } alt='로고' style={{ width: '8%', minWidth:'60px',marginBottom: '8px' }}/> 
    <TextWrapper>
      <div style={{ display: 'block', width:'100%' }}>
      <Title type='medium'>{ item?.title }</Title>
      {
        type !== 'my' &&
        <DescribeWrapper>{ item?.description }</DescribeWrapper>
      }
      </div>
        <TagWrapper>
          { item.tags?.map(( tag ) => (
            <Title type='small'>#{ tag?.content }&nbsp;</Title>
          ))}
        </TagWrapper>
        {
          type === 'my' && item?.status?.latestChatMessage?.type === 'TEXT' && 
          <p style={{ margin: '10px 0px 2px 0px', fontSize: '12px' }}>{( item?.status?.latestChatMessage?.content).substring(0, 13) }    
          { item?.status?.latestChatMessage?.content?.length > 13 &&  '..' }
          </p> 
        }
        {
          type === 'my' && item?.status?.latestChatMessage?.type === 'IMAGE' && 
          <p style={{ margin: '10px 0px 2px 0px', fontSize: '12px' }}>( 사진 )</p> 
        }
    </TextWrapper>
    {
      type !== 'my' &&
      <p style={{ paddingTop: '40px'}}>{ item?.members?.length | 0 }/ { item?.capacity }명</p> 
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