import { InputBox, Button, Select, Title, KakaoIcon, 
    NaverIcon, MainLayout } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import { styled } from 'styled-components'



  
const InputWrapper = styled.div`
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    margin-top: 20px;
  `
  
const IconWrapper = styled.div`
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    gap: 10px;
  `
  
const dataSource = [
    { value: 'Kakao', name: 'Kakao' },
    { value: 'Naver', name: 'Naver' },
  ]
  
const Login = () => {
    const navigator = useNavigate()
  
    const onLogin = useCallback(() => {
      navigator('/login')
    }, [])
  
    return (
      <MainLayout>
        <Title type= 'large' color= 'gray'>
          이곳에 로그인을
          <br />
          독려하는 카피가 들어갑니다.
        </Title>
        <InputWrapper>
          <InputBox title= '이메일' placeholder= '이메일을 입력해주세요.' />
          <InputBox title= '비밀번호' placeholder= '비밀번호를 입력해주세요' />
        </InputWrapper>
        <Button type='cancel' onClick={ onLogin }>
          로그인
        </Button>
        <p>SNS 로그인</p>
        <IconWrapper>
          <NaverIcon />
          <KakaoIcon />
        </IconWrapper>
        <hr style= {{ width: '100%' }} />
        <Title type= 'small'>혹시</Title>
        <Title type= 'small'>비밀번호를 잊으셨나요?</Title>
        <Title type= 'small'>아직 회원이 아니신가요?</Title>
        <Select dataSource={ dataSource } placeholder= '선택해주세요' />
      </MainLayout>
    )

  }
  
  export default Login
// import {styled} from 'styled-components'

// const SignButtonWrapper = styled.button<SignButtonProps>`
//   background-color: #7D74B4;
//   width: 200px;
//   height: 50px;
//   color: white;
//   border: none;
//   border-radius: 10px;
//   font-size: 20px;
//   font-weight: bold;
//   font-family: 'BM HANNA_TTF';
//   font-style: normal;
//   line-height: normal;
//   `
// const SignButton = (props: SignButtonProps) =>{

//   const {
//     children, type = 'login', color = 'lightgrey', 
//     onClick
//   } = props

//   return (
//     <SignButtonWrapper onClick={ onClick } type={ type } color={ color } >
//       { children }
//     </SignButtonWrapper>
//   )
// }

// export default SignButton

// type SignButtonProps = {
//   children?: string,
//   color?: string,
//   type?:'login' |'signup'
//   onClick?: () => void
