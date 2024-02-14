import { postAxios } from 'src/api/util'

interface dataType {
    title?: string,
    gameId?: number,
}


export const makeGameChat = async( data : dataType ) => {
  try{
    const response = await postAxios(`/api/chatrooms/game`, data )
    return response
  } catch( err ){
    console.log(err)
  }
}