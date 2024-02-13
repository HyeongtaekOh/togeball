import { postAxios } from 'src/api/util'

interface dataType {
  chatRoomId: number,
}

export const partiChat = async( data : dataType ) => {
  try{
    return await postAxios(`/api/users/me/chatrooms/${data?.chatRoomId}`)
  } catch( err ){
    console.log(err)
  }
}