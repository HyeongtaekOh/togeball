import { useState } from 'react'
import { PersonIcon, ChatIcon } from 'src/components'
import { MenuItem, HeaderChat } from './index'
import type { MenuItemProps } from './MenuItem'
import useStore from 'src/store'
import styled from 'styled-components'

const HeaderMenuWrapper = styled.div`
  box-sizing: border-box;  
  display: flex;
  flex-direction: row;
  height: 60px;
`
const HeaderIconWrapper = styled( HeaderMenuWrapper )`
  gap: 40px;
  display: flex;
  align-items: center;
`

const RightHeader = () => {

  const { isLogin, setIsLogin, setSession } = useStore();
  const [ isChatOpen, setIsChatOpen ] = useState<boolean>(false);
  
  const menu = 
    !isLogin ? [
      { title : '회원가입', path : '/signup' },
      { title : '로그인', path : '/login' },
    ]: [
      { title : 'Service', 
        menus : [ 
          { name : '매칭하기', path : '/service' },
          { name : '메이트 모집하기', path : '/service' },
        ] 
      },
      { title : 'Content', 
        menus : [ 
          { name : '오픈 채팅방', path : '/service' },
          { name : '메이트 채팅방', path : '/service' },
          { name : '경기 일정', path : '/calender' },
        ] 
      }
    ]

  return (

    <HeaderMenuWrapper>
      {
        menu.map(( item : MenuItemProps ) => {
          return <MenuItem 
              key = { item?.title } title ={ item?.title } 
              path = { item?.path } menus ={ item?.menus }
            />
      })}
      {
        !isLogin &&  (
        <HeaderIconWrapper>
          <ChatIcon onClick = {() => setIsChatOpen( !isChatOpen )}/>
          { isChatOpen && <HeaderChat /> }
          <PersonIcon />
        </HeaderIconWrapper>
      )}
    </HeaderMenuWrapper>
    
  )
}

export default RightHeader