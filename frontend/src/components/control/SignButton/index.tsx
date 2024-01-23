import {styled} from 'styled-components'

const SignButtonWrapper = styled.button<SignButtonProps>`
  background-color: #7D74B4;
  width: 200px;
  height: 80px;
  color: white;
  border: none;
  border-radius: 10px;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
  &:hover {
    background-color: #9008F2;
  }
  `
const SignButton = (props: SignButtonProps) =>{

  const {
    children, color = 'lightgrey', 
    onClick
  } = props

  return (
    <SignButtonWrapper onClick={ onClick } color={ color } >
      { children }
    </SignButtonWrapper>
  )
}

export default SignButton

type SignButtonProps = {
  children?: string,
  color?: string,
  onClick?: () => void
}