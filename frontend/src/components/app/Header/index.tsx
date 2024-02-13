import { useEffect, useState } from 'react'
import { LeftHeader, RightHeader } from './components'
import { EventSourcePolyfill } from 'event-source-polyfill'
import styled from 'styled-components'


const HeaderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  height: 60px;
  padding: 50px 70px;
  align-items: center;
  position:fixed;
  z-index:999;
  background-color: white
`

const Header = ( props: HeaderProps ) => {

  const { title } = props
  const [ eventSource, setEventSource ] = useState( null )

  useEffect (()=>{

    const url = 'https://i10a610.p.ssafy.io:8080/sse/notification/subscribe'

    if( !localStorage.getItem('accessToken') || eventSource ) return

    const source = new EventSourcePolyfill(url, {
      headers:{
        Authorization: localStorage.getItem('accessToken')
      }
    })

    setEventSource( source )

  }, [])

  
  return(
    <HeaderWrapper>
      <LeftHeader children={ title }/>
      <RightHeader eventSource={ eventSource }/>
    </HeaderWrapper>
  )

}

export default Header

type HeaderProps = {
  title?: string,
}