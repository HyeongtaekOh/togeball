
import { getAxios } from 'src/api/util'

interface dataType {
  gameId: number
}

export const getGameChat = async( param : dataType ) => {
  try{
    const response = await getAxios(`//api/chatrooms/game`, param )
    return response?.id
  } catch( err ){
    console.log(err)
  }
}