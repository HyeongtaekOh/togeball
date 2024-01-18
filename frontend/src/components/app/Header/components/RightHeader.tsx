import { PersonIcon, ChatIcon } from 'src/components/icon';
import { useState } from 'react';
import useStore from 'src/store'
import MenuItem from './MenuItem'
import styled from 'styled-components'

const HeaderMenuWrapper = styled.div`
  box-sizing: border-box;  
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px;
`
const HeaderIconWrapper = styled(HeaderMenuWrapper)`
  gap: 30px;
`

const beforeLoginMenu = [
  { title : '회원가입', path : '/signup' },
  { title : '로그인', path : '/login' },
]

const loginMenu = [
  { 
    title : 'Service', 
    menus : [ 
      { name : '매칭하기', path : '/service' },
      { name : '매칭하기2', path : '/service' },] 
  },
  { 
    title : 'Content', 
    menus : [ { name : '참여하기', path : '/service' } ] 
  },
]


const RightHeader = () => {
  
  const { isLogin, setIsLogin, setSession } = useStore()
  const [ menu, setMenu ] = useState( isLogin? loginMenu : beforeLoginMenu )

  return (
    <HeaderMenuWrapper>
      {
          menu.map(( item, index ) => {
            return <MenuItem 
                title={ item.title } path={ item.path }
                menuDatas={ item.menus }
              />
        })
      }
      {
        !isLogin &&  (
          <HeaderIconWrapper>
            <ChatIcon />
            <PersonIcon />
          </HeaderIconWrapper>
        )
      }
    </HeaderMenuWrapper>

  )

}

export default RightHeader