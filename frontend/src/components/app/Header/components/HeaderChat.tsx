import styled from 'styled-components'
import { Button, Pagination, Title } from 'src/components'

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

const CloseButton = styled.button`
  background: #ccc;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  
`

const HeaderChat = ( props ) => {  

  const { chats, isLoading, onClick } = props
  
  return(

   <HeaderChatWrapper>
    <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '-15px'}}>
      <Title color ='#6A60A9'>나의 채팅방</Title>
      <Button style={{ width: '10px', height: '10px', marginTop: '-1px'}}
      onClick={ onClick }>close</Button>
    </div>
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

