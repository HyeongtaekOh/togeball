import { postAxios } from 'src/api/util'

interface dataType {
  email?: string;
  password?: string;
  nickname?: string;
}

export const signup = async( data : dataType ) => {
  try{
    const response = await postAxios('/api/users', data )
    return response
  } catch( err ){
    console.log( err )
  }
}