import { InputBox, Title, KakaoIcon, 
    NaverIcon, MainLayout, SignLayout, SignButton, HomeLayout } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import { styled } from 'styled-components'
  
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
  
    const onLogin = useCallback(() => {
      
    }, [])
  
    return (
      <MainLayout>
        <SignLayout>
        <Title>투게볼</Title>
        <InputWrapper>
          <InputBox title= '이메일' placeholder= '이메일을 입력해주세요.' />
          <InputBox title= '비밀번호' placeholder= '비밀번호를 입력해주세요' />
        </InputWrapper>
        <SignButton onClick={ onLogin }>
          로그인
        </SignButton>
        <Title>SNS 로그인</Title>
        <IconWrapper><NaverIcon /><KakaoIcon /></IconWrapper>
        <a href='./sign'>혹시 아직 회원이 아니신가요?</a>
       </SignLayout>
      </MainLayout>
    )

  }
  
  export default Login