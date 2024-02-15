import { getAxios } from 'src/api/util'

interface dataType {
  page: number;
  size: number;
}

export const getOpenChatMessages = async( id, data : dataType ) => {
  try{
    const response = await getAxios(`chat-server/chats/${id}/game`, data ) 
    return response
  } catch( err ){
    console.log(err)
  }
}