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
  padding-bottom: 5px;
  justify-content: space-between;
  &:hover{
    background-color: #f2f2f2;
    cursor: pointer;
  }
`
const InfoWrapper = styled.div`
  display: flex;
  Background-color: white;
  gap: 20px;
  background-color: inherit;
  overflow: hidden;
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
  margin-top: 5px;
`
const ImgWrapper = styled.img`
  width: 8%;
  margin-bottom: 8px;
  min-width: 60px;
`
const ImgDivWrapper = styled.div`
  min-width : 80px;
  max-width : 80px;
  display: flex;
  align-items: center;
  justify-content: center;
`

const ChatItem = ( props: ChatListProps ) => {

  const { item, type, width } = props
  const { updateCount, count } = useHeaderStore()
  const { session } = useStore()

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
    <InfoWrapper>
      <ImgDivWrapper>
     <ImgWrapper src={ item?.cheeringClub?.logo || Logo } alt='로고'/> 
     </ImgDivWrapper>
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
            <p style={{ fontSize:'10PX' }}>#{ tag?.content }&nbsp;</p>
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
    </InfoWrapper>
    <div style={{ minWidth: '50px'}}>
    {
      type !== 'my' &&
      <p style={{ paddingTop: '40px'}}>{ item?.members?.length | 0 }/ { item?.capacity }명</p> 
    }
    </div>
   </ChatWrapper>
  )
}

export default ChatItem

type ChatListProps = {
  item?: any,
  type?: string,
  width?: string
}