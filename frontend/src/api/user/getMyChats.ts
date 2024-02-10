import { getAxios } from 'src/api/util'

export const getMyChats = async() => {
  try{
    const response =  await getAxios( `api/users/me/chatrooms` )
    return response
  } catch( err ){
    console.log(err)
  }
}