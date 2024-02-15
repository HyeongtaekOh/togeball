import { getAxios } from 'src/api/util'

export const getUserInfo = async( id : string ) => {
  try{
    return await getAxios( `api/users/${id}` )
  } catch( err ){
    console.log(err)
  }
}