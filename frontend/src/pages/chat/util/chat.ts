import SockJS from 'sockjs-client'
import StompJs from 'stompjs'

const socket = new SockJS(
  `https://i10a610.p.ssafy.io:8080/chat-server/chat?userId=${localStorage.getItem('userId')}`
)
const stompClient = StompJs.over( socket )

export { stompClient }