import { InputBox, Title, KakaoIcon, 
    NaverIcon, MainLayout, SignLayout, SignButton, HomeLayout } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback, useState } from 'react'
import { login } from './api'
import { styled } from 'styled-components'
import { useMutation } from 'react-query'
  
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
  
  const [ email, setEmail ] = useState()
  const [ password, setPassword ] = useState()

  const loginMutation = useMutation( login )
  
  const data = {
    email : email,
    password : password,
  }

  const onLogin = () => {
    loginMutation.mutateAsync( data )
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
      <IconWrapper><NaverIcon /><KakaoIcon /></IconWrapper>
      <a href='./signup'>혹시 아직 회원이 아니신가요?</a>
     </SignLayout>
    </MainLayout>
  )

}
  
  export default Login