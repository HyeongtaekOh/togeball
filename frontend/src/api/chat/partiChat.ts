import { postAxios } from 'src/api/util'

interface dataType {
  chatRoomId: number,
  UserId: number
}

export const partiChat = async( data : dataType ) => {
  try{
    return await postAxios(`/api/chatrooms/${data?.chatRoomId}/participants/${data?.UserId}`)
  } catch( err ){
    console.log(err)
  }
}