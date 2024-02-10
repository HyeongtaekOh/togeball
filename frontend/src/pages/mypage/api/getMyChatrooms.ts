import { getAxios } from "src/api/util"

export const getMyChatrooms = async() => {
  try{
    return await getAxios('/api/users/me/chatrooms')
  } catch( err ){
    console.log(err)
  }
}