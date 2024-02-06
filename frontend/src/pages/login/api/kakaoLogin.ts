import axios from 'axios'
import { getAxios, postAxios } from 'src/api/util'

export const kakaoLogin = async() => {
  try{
    const response 
      = await axios.get("https://i10a610.p.ssafy.io:8080/oauth2/authorization/kakao")
    console.log( response )
    // return response.headers.authorization
  } catch( err ){
    console.log( err )
  }
}