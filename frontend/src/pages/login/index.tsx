import { InputBox, Title, KakaoIcon, 
  MainLayout, SignLayout, SignButton } from 'src/components'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { login } from './api'
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
    margin-top:-20px;
  `
  
const Login = () => {

  const { setIsLogin, setAccessToken } = useStore()

  const navigator = useNavigate()
  
  const [ email, setEmail ] = useState('')
  const [ password, setPassword ] = useState('')

  const loginMutation = useMutation( login, {
    onSuccess: (res) => {

      if(res?.status !== 200 ) {
        alert("이메일과 비밀번호를 입력하세요")
        return 
      }

      setAccessToken( res?.headers?.authorization )

      localStorage.setItem( 'accessToken', res?.headers?.authorization )
      localStorage.setItem( 'refreshToken', res?.headers["authorization-refresh"])
      localStorage.setItem( 'userId',  res?.data?.id )

      setIsLogin( true )

      navigator('/')
    }
  })
  
  const param = {
    email : email,
    password : password,
  }

  const onLogin = async() => {
    if( email === '' || password === '' ){
      alert("이메일과 비밀번호를 입력하세요")
      return
    }
    
    try {
      await loginMutation.mutateAsync( param )
    } catch ( error ) {
      if( error.response.status === 401 ){
        alert("비밀번호나 아이디를 확인하세요")
      }
    }
  }

  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_REST_API_KEY}&redirect_uri=${process.env.REACT_APP_REDIRECT_URI}&response_type=code`
  const handleKakao = ()=>{
      window.location.href = kakaoURL
  }

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    e.key === 'Enter' &&  onLogin()
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
            onKeyDown={ handleKeyDown }
          />
          <InputBox 
            title= '비밀번호' 
            placeholder= '비밀번호를 입력해주세요.'
            type = 'password'
            value={ password }
            onChange={(e) => { setPassword( e.target.value )}}
            onKeyDown={ handleKeyDown }
          />
      </InputWrapper>
      <SignButton onClick={ onLogin }>로그인</SignButton>
      <Title type='small'>SNS 로그인</Title>
      <IconWrapper>
      <KakaoIcon onClick={ handleKakao }/>
      </IconWrapper>
      <a href='./signup'>혹시 아직 회원이 아니신가요?</a>
     </SignLayout>
    </MainLayout>
  )

}
  
  export default Login