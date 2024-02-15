import { postAxios } from 'src/api/util'

interface dataType {
  roomId: number,
  data : {
    lastReadMessageId : string
  }
}

const postLastChat = async( data: dataType ) => {
  try{
    const response =  await postAxios(`/chat-server/me/chats/${ data.roomId }/read`, data.data )
    return response
  }
  catch(error){
    throw error
  }

}

export default postLastChat
