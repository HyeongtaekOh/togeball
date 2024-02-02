import { postAxios } from 'src/api/util'

interface dataType {
  email: string;
  password: string;
  nickname: string;
}

export const signup = async( data : dataType ) => {
  try{
    return await postAxios('/api/users', data )
  } catch( err ){
    console.log( err )
  }
}