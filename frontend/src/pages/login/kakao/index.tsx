import { postAxios } from "src/api/util"
import { useEffect, useRef, useState } from "react"
import { postCode } from './api/postCode'
import { useMutation } from "react-query"
import { useNavigate } from "react-router-dom"
import useStore from "src/store"

const KakaoPage = () => {

  const navigator = useNavigate()

  const { setAccessToken, setIsLogin } = useStore()


  const isPost = useRef<boolean>(false)
  const postMutations = useMutation( postCode, {
    onSuccess: (res) => {

      setAccessToken( res?.headers?.authorization )

      localStorage.setItem( 'accessToken', res?.headers?.authorization )
      localStorage.setItem( 'refreshToken', res?.headers["authorization-refresh"])
      localStorage.setItem( 'userId',  res?.data?.id )

      setIsLogin( true )

      navigator('/home')
    },
    onError: ( err ) => {
      console.log( err )
      alert( "로그인 실패했습니다" )
      navigator( '/login' )
    } 
  } )

    
  const urlParam = new URLSearchParams( window.location.search )
  const code = urlParam.get( 'code' )
  const data = { code : code, provider : 'kakao' }

  useEffect(()=>{
    if( isPost.current ) return
    isPost.current = true
    postMutations.mutateAsync( data )


  }, [data, postMutations])


  return(
    <div><p></p></div>
  )
}

export default KakaoPage

