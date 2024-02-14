import { getAxios } from "src/api/util"


export const getBoards = async() => {
  try{
    return await getAxios('/api/users/me/posts?&page=0&size=1000' )
  } catch( err ){
    console.log(err)
  }
}