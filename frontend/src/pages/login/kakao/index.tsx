import { postAxios } from "src/api/util"
import { useEffect, useRef, useState } from "react"
import { postCode } from './api/postCode'
import { useMutation } from "react-query"

const OAuthRedirect = () => {


  // const [ code, setCode ] = useState()
  const isPost = useRef<boolean>(false)
  const postMutations = useMutation( postCode )

    
    const urlParam = new URLSearchParams( window.location.search )
    const code = urlParam.get( 'code' )
    const data = { code : code, provider : 'kakao' }

    // try{
      if( isPost.current ) return
      
      isPost.current = true
      postMutations.mutateAsync(data)
         
    // } catch( err ){
    //   console.log( err )
    // }



  return(
    <div><p>hi</p></div>
  )
}

export default OAuthRedirect