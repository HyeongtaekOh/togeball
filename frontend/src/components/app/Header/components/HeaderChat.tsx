import styled from 'styled-components'
import { Title } from 'src/components'

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
  padding: 20px;
`

const HeaderChat = ( props  ) => {  
  
  return(

   <HeaderChatWrapper>
      <Title color ='#6A60A9'>나의 채팅방</Title>
   </HeaderChatWrapper>

  )

}

export default HeaderChat

