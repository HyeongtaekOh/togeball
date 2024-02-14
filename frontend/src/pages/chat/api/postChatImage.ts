import { postAxios } from 'src/api/util'

interface dataType {
    file: any,
    nickname: string,
    roomId: number,
    senderId: string,
    type: string
}

const postChatImage = async( data : dataType ) => {
  try{
    const response =  await postAxios(`/chat-server/chats/${data.roomId}/images`, data, {
      headers: {
          'Content-Type': 'multipart/form-data',
      },
  })
    return response
  }catch(error){
    throw error
  }

}

export default postChatImage
