import { getAxios } from "src/api/util"

export const getMyBoards = async() => {
  try{
    return await getAxios('/api/users/me/posts')
  } catch( err ){
    console.log(err)
  }
}

