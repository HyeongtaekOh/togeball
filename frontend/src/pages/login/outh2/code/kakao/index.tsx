import { useEffect } from "react"
import { PathParam, useParams } from "react-router-dom"

const OAuthRedirect = ( param ) => {

  // const { chatroomId } = useParams<PathParam>()


  console.log("hi")


  useEffect(()=>{

    const code = new URL(window.location.href)
    console.log(code)
  })

  return(
    <div></div>
  )
}

export default OAuthRedirect