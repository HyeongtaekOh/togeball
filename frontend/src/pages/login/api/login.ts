import { postAxios } from 'src/api/util'

interface dataType {
  email: string;
  password: string;
}

export const login = async( data : dataType ) => {

  try{
    const response =  await postAxios('/api/auth/login', data )
    return response
  }catch(error){
    throw error
  }
}