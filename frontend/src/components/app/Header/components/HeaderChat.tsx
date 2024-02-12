import styled from 'styled-components'
import { Pagination, Title } from 'src/components'
import { useQuery } from 'react-query'
import { getMyChats } from 'src/api'

const HeaderChatWrapper = styled.div`
  position: absolute;
  top: 110px;
  right: 5px;
  width: 250px;
  height: calc(100vh - 155px);
  border: 1px solid #DEDCEE;
  border-radius: 10px;
  background-color: white;
  z-index:999;
  padding: 10px;
`

const HeaderChat = ( props  ) => {  

  const { data: chats } = useQuery( 'chats', getMyChats )
  
  return(

   <HeaderChatWrapper>
      <Title color ='#6A60A9'>나의 채팅방</Title>
      <div style={{ width: '100%', marginTop: '20px' }}>
        <Pagination type= 'my' chats={ chats } />
      </div>
   </HeaderChatWrapper>

  )

}

export default HeaderChat

