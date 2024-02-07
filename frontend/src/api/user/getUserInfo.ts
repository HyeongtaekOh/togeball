
import useStore from 'src/store'
import { getAxios } from 'src/api/util'

export const getUserInfo = async( id : string ) => {

  try{
    const response =  await getAxios( `api/users/${id}` )
    return response
  } catch( err ){
    console.log(err)
  }
}