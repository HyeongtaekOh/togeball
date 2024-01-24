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
  
const SignUp = () => {
  
    const onSignUp = useCallback(() => {
      
    }, [])
  
    return (
      <MainLayout>
        <SignLayout>
        <Title>회원가입</Title>
        <InputWrapper>
          <InputBox title= '이메일' placeholder= 'ex) hongildong@gamli.com' />
          <InputBox title= '비밀번호' placeholder= '비밀번호를 입력해주세요.' />
          <InputBox title= '비밀번호 확인' placeholder= '동일한 비밀번호를 입력해주세요.' />
        </InputWrapper>
        <SignButton onClick={ onSignUp }>
          회원가입
        </SignButton>
        <Title type='small'>SNS 회원가입</Title>
        <IconWrapper><NaverIcon /><KakaoIcon /></IconWrapper>
        
       </SignLayout>
      </MainLayout>
    )

  }
  
  export default SignUp