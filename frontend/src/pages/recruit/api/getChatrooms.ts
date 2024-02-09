import { getAxios } from "src/api/util"


export const getChatrooms = async() => {
  try{
    return await getAxios('/api/chatrooms?type=Recruit&page=0&size=1000' )
  } catch( err ){
    console.log(err)
  }
}