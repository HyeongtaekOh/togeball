
import { getAxios } from 'src/api/util'

interface dataType {
  gameId: number
}

export const getGameChat = async( param : dataType ) => {
  try{
    return await getAxios(`//api/chatrooms/game`, param )
  } catch( err ){
    console.log(err)
  }
}