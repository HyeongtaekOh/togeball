import SockJS from 'sockjs-client'
import StompJs from 'stompjs'

const socket = new SockJS(`https://i10a610.p.ssafy.io:8082/chat-server/chat?userId='12'`)
const stompClient = StompJs.over( socket )

export { stompClient }