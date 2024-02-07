import { InputBox, Title, KakaoIcon, 
    NaverIcon, MainLayout, SignLayout, SignButton } from 'src/components'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { kakaoLogin, login } from './api'
import { styled } from 'styled-components'
import { useMutation } from 'react-query'
import useStore from 'src/store'
  
const InputWrapper = styled.div`
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    margin-top: 20px;
    width : 100%;
    gap: 10px;
  `
  
const IconWrapper = styled.div`
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    gap: 10px;
  `
  
const Login = () => {

  const { setIsLogin, setAccessToken } = useStore()

  const navigator = useNavigate()
  
  const [ email, setEmail ] = useState('')
  const [ password, setPassword ] = useState('')

  const loginMutation = useMutation( login, {
    onSuccess: (res) => {
      setAccessToken( res?.headers?.authorization )
      localStorage.setItem( 'accessToken', res?.headers?.authorization )
      localStorage.setItem( 'refreshToken', res?.headers["authorization-refresh"])
      setIsLogin( true )
      navigator('/')
    }
  })

  const kakaoMutations = useMutation( kakaoLogin )
  
  const param = {
    email : email,
    password : password,
  }

  const onLogin = async() => {
    try {
      await loginMutation.mutateAsync( param )
    } catch ( error ) {
      if( error.response.status === 401 ){
        alert("비밀번호나 아이디를 확인하세요")
      }
    }
  }

  const Rest_api_key= 'cfc1e7455936fe459743b8dfe3dae5fe' //REST API KEY
  const redirect_uri = 'https://i10a610.p.ssafy.io/login/kakao' //Redirect URI
  
  // oauth 요청 URL
  const kakaoURL 
    = 
    `https://kauth.kakao.com/oauth/authorize?client_id=${Rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`
  
  const handleLogin = ()=>{
      window.location.href = kakaoURL
  }
   

  const onKaKao = () =>{
    kakaoMutations.mutateAsync()
  }

  return (
    <MainLayout>
      <SignLayout>
      <Title>로그인</Title>
      <InputWrapper>
          <InputBox 
            title= '이메일' 
            placeholder= 'ex) hongildong@gamli.com' 
            value={ email }
            onChange={(e) => { setEmail( e.target.value )}}
          />
          <InputBox 
            title= '비밀번호' 
            placeholder= '비밀번호를 입력해주세요.'
            value={ password }
            onChange={(e) => { setPassword( e.target.value )}}
          />
      </InputWrapper>
      <SignButton onClick={ onLogin }>
        로그인
      </SignButton>
      <Title type='small'>SNS 로그인</Title>
      <IconWrapper><NaverIcon />
      <KakaoIcon onClick={ handleLogin }/>
      <a href='https://i10a610.p.ssafy.io:8080/oauth2/authorization/kakao'>
        카카오
      </a>
      </IconWrapper>
      <a href='./signup'>혹시 아직 회원이 아니신가요?</a>
     </SignLayout>
    </MainLayout>
  )

}
  
  export default Login