import { useEffect } from "react"

const OAuthRedirect = () => {


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