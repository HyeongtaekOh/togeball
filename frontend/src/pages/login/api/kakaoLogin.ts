import { getAxios, postAxios } from 'src/api/util'

export const kakaoLogin = async() => {
  try{
    await getAxios('/social/oauth2/authorize/kakao' )
    // console.log( response )
    // return response.headers.authorization
  } catch( err ){
    console.log( err )
  }
}