import { postAxios } from 'src/api/util'

interface dataType {
  email: string;
  password: string;
}

export const login = async( data : dataType ) => {
  try{
    console.log(data)
    const response =  await postAxios('/api/auth/login', data )
    console.log(response.headers)
  } catch( err ){
    console.log( err )
  }
}