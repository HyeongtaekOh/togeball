import styled from 'styled-components'
import { Pagination, Title } from 'src/components'
import { useQuery } from 'react-query'
import { getMyChats } from 'src/api'

const HeaderChatWrapper = styled.div`
  position: absolute;
  top: 90px;
  right: 5px;
  width: 280px;
  border: 1px solid #DEDCEE;
  border-radius: 10px;
  background-color: white;
  z-index: 999;
  padding: 10px;
`

const HeaderChat = ( props ) => {  

  const { chats, isLoading } = props
  
  return(

   <HeaderChatWrapper>
      <Title color ='#6A60A9'>나의 채팅방</Title>
      <div style={{ width: '100%', marginTop: '20px' }}>
        {
          isLoading ? 
          <Title>로딩중</Title>:
          <Pagination type= 'my' chats={ chats } />
        }
      </div>
   </HeaderChatWrapper>

  )

}

export default HeaderChat

