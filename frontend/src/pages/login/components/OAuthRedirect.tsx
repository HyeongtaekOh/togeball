import { postAxios } from "src/api/util"
import { useEffect, useState } from "react"

const OAuthRedirect = () => {


  const [ code, setCode ] = useState()

  useEffect(()=>{

    
    const urlParam = new URLSearchParams(window.location.search)
    const code = urlParam.get('code')

    const postH = async( ) => {


      const data = { code : code , provider : 'kakao'}
      console.log(data)
      return await postAxios("/api/auth/code", data )
      
    }

    postH()

  })

  return(
    <div><p>hi</p></div>
  )
}

export default OAuthRedirect