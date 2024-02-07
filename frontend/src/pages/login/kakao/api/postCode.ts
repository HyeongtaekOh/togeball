import axios from 'axios'
import { getAxios, postAxios } from 'src/api/util'

export const postCode = async( data ) => {
  try{
    return await postAxios( '/api/auth/code', data )
  } catch( err ){
    console.log( err )
  }
}