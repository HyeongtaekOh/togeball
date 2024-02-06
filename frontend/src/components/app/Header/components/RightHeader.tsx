import { useState } from 'react'
import { PersonIcon, ChatIcon } from 'src/components'
import { MenuItem, HeaderChat, IconItem } from './index'
import type { MenuItemProps } from './MenuItem'
import useStore from 'src/store'
import styled from 'styled-components'
import { useNavigate } from 'react-router-dom'

const HeaderMenuWrapper = styled.div`
  box-sizing: border-box;  
  display: flex;
  flex-direction: row;
  height: 60px;
`
const HeaderIconWrapper = styled( HeaderMenuWrapper )`
  gap: 20px;
  display: flex;
  align-items: center;
`

const RightHeader = () => {

  const navigator = useNavigate()

  const { isLogin } = useStore()
  const [ isChatOpen, setIsChatOpen ] = useState<boolean>(false)

  const logout = () => {
    localStorage.removeItem('accessToken')
    localStorage.removeItem('refreshToken')
    window.location.reload()
    navigator('/')
  }

  const personMenu = [
      { name : '마이페이지', path : '/mypage' },
      { name : '로그아웃', onClick : logout },
  ]
  
  const menu = 
    !isLogin ? [
      { title : '회원가입', path : '/signup' },
      { title : '로그인', path : '/login' },
    ]: [
      { title : 'Service', 
        menus : [ 
          { name : '매칭하기', path : '/matching' },
          { name : '메이트 모집하기', path : '/recruit/post' },
        ] 
      },
      { title : 'Content', 
        menus : [ 
          { name : '오픈 채팅방', path : '/todaygames' },
          { name : '메이트 채팅방', path : '/service' },
          { name : '경기 일정', path : '/calendar' },
        ] 
      },
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
        isLogin &&  (
        <HeaderIconWrapper>
          <ChatIcon onClick = {() => setIsChatOpen( !isChatOpen )}/>
          { isChatOpen && <HeaderChat /> }
          <IconItem menus = { personMenu }><PersonIcon /></IconItem>
        </HeaderIconWrapper> )
      }
    </HeaderMenuWrapper>
    
  )
}

export default RightHeader