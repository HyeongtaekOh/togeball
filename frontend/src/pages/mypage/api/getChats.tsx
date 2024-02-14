import { getAxios } from "src/api/util"


export const getChats = async() => {
  try{
    return await getAxios( '/api/users/me/chatrooms/owned?page=0&size=1000' )
  } catch( err ){
    console.log(err)
  }
}