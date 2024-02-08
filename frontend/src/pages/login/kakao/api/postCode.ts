import { postAxios } from 'src/api/util'

export const postCode = async( data ) => {
  try{
    const reponse =  await postAxios( '/api/auth/code', data )
    return reponse
  } catch( err ){
    console.log( err )
  }
}