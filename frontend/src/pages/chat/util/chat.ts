import SockJS from 'sockjs-client'
import StompJs from 'stompjs'

const createClient = () => {
  const socket = new SockJS(
    `https://i10a610.p.ssafy.io:8082/chat-server/chat?Authorization=${localStorage.getItem('accessToken')}`
  )
  const client = StompJs.over(socket)
  return client
}

export { createClient }