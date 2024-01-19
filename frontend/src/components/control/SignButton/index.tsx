import {styled} from 'styled-components'

const SignButtonWrapper = styled.button<SignButtonProps>`
  background-color: #7D74B4;
  width: 200px;
  height: 50px;
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 20px;
  font-weight: bold;
  font-family: 'BM HANNA_TTF';
  font-style: normal;
  line-height: normal;
  cursor: pointer;
  `
const SignButton = (props: SignButtonProps) =>{

  const {
    children, type = 'login', color = 'lightgrey', 
    onClick
  } = props

  return (
    <SignButtonWrapper onClick={ onClick } type={ type } color={ color } >
      { children }
    </SignButtonWrapper>
  )
}

export default SignButton

type SignButtonProps = {
  children?: string,
  color?: string,
  type?:'login' |'signup'
  onClick?: () => void
}