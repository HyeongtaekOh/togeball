import { useEffect, useRef, useState } from 'react'
import { PersonIcon, ChatIcon } from 'src/components'
import { MenuItem, HeaderChat, IconItem } from './index'
import type { MenuItemProps } from './MenuItem'
import useStore from 'src/store'
import styled from 'styled-components'
import { useNavigate } from 'react-router-dom'
import { EventSourcePolyfill } from 'event-source-polyfill'
import { getMyChats, getUserInfo } from 'src/api'
import { useQuery } from 'react-query'
import useHeaderStore from '../store'

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
const UnreadWrapper = styled.div`
  display: flex;
  position: absolute;
  width: 18px;
  height: 18px;
  font-size: 11px;
  background-color: #6A60A9;
  border-radius: 50%;
  justify-content: center;
  align-items: center;
  transform: translate(22px, -13px);
  color: WHITE;
`

const RightHeader = (  ) => {

  const { setAccessToken, setIsLogin, setSession  } = useStore()
  const { updateCount } =  useHeaderStore()
  const count = useRef(0)
  
  const { data: HeaderChats, isLoading } = useQuery( 'HeaderChats', getMyChats )
  
  useEffect(()=>{
    if( !HeaderChats ) return
    count.current = 0
    updateCount(0)
    HeaderChats?.content?.map((room)=>{
      count.current =  count.current + room?.status?.unreadCount
      updateCount( count.current )
    })

  },[ HeaderChats, updateCount ])
  
  useEffect(() => {
    let eventSource;

    const createSource = () => {
      const url = 'https://i10a610.p.ssafy.io:8080/sse/notification/subscribe'
      eventSource = new EventSourcePolyfill( url, {
        headers: {
          Authorization: localStorage.getItem('accessToken')
        }
      })

      eventSource.addEventListener('connected', data => {
        console.log(data)
      })

      eventSource.addEventListener('chat', data => {
        const match = window.location.href.match(/\/chat\/(\d+)/)
        if ( match ) {
          const chatId = match[1]
          if( data?.data?.roomId && chatId != data?.data?.roomId ){
            count.current = count.current + 1
            updateCount( count.current )
          }
        } else {
          count.current = count.current + 1
          updateCount(count.current)
        }
      })

      eventSource.onopen = () => {
        console.log("onopen....")
      }

      eventSource.onerror = err => {
        console.log("Error occurred:", err)
        eventSource?.close()
      }
    }
    
    if (!localStorage.getItem('accessToken')) return

    
    return () => {
      eventSource && eventSource?.close()
    }
    
  }, [])

  const navigator = useNavigate()
  const { isLogin } = useStore()

  const [ isChatOpen, setIsChatOpen ] = useState<boolean>(false)

  const openHandler =()=> {
    setIsChatOpen( !isChatOpen )
  }

  const stopClose = () => {
    setIsChatOpen(false)
  }

  const logout = () => {
    localStorage.removeItem( 'accessToken' )
    localStorage.removeItem( 'refreshToken' )
    localStorage.removeItem( 'userId' )
    navigator('/')
    window.location.reload()
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
          { name : '메이트 채팅방', path : '/recruit' },
          { name : '경기 일정', path : '/calendar' },
        ] 
      },
    ]

  return (

    <HeaderMenuWrapper>
      {
        menu?.map(( item : MenuItemProps ) => {
          return <MenuItem 
              key = { item?.title } title ={ item?.title } 
              path = { item?.path } menus ={ item?.menus }
            />
      })}
      {
        isLogin &&  (
        <HeaderIconWrapper>
          {
            count.current > 0 && 
            <UnreadWrapper><p>{ count.current }</p></UnreadWrapper>
          }
          <ChatIcon onClick = { openHandler }/>
          { isChatOpen
            && <HeaderChat chats = { HeaderChats } isLoading={ isLoading } onClick={ stopClose }/> }
          <IconItem menus = { personMenu }><PersonIcon /></IconItem>
        </HeaderIconWrapper> )
      }
    </HeaderMenuWrapper>
    
  )
}

export default RightHeader
