import { getAxios } from 'src/api/util'

export const getMyInfo = async() => {

  try{
    const response =  await getAxios( `api/users/me` )
    return response
  } catch( err ){
    console.log(err)
  }
}