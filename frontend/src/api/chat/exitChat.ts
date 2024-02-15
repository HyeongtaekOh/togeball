import { deleteAxios } from "../util"

interface dataType {
  id: string
}

export const exitChat = async( data : dataType ) => {
  try{
    return await deleteAxios(`/api/chatrooms/{chatroomId}/participants`)
  } catch( err ){
    console.log(err)
  }
}