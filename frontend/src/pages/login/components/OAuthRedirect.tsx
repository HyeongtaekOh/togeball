import { postAxios } from "src/api/util"
import { useEffect, useState } from "react"
import { postCode } from "./api/postCode"
import { useMutation } from "react-query"

const OAuthRedirect = () => {


  // const [ code, setCode ] = useState()

  // const postMutations = useMutation( postCode )
  useEffect(()=>{

    
    const urlParam = new URLSearchParams(window.location.search)
    const code = urlParam.get('code')
    console.log(code)

    const data = { code : code, provider : 'kakao'}

    // try{

    //   postMutations.mutateAsync(data)
    // }catch(err){
    //   console.log("에러다")
    //   console.log(err)
    // }


  },[])

  return(
    <div><p>hihi</p></div>
  )
}

export default OAuthRedirect