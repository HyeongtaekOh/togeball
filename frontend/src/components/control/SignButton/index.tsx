import styled from 'styled-components'

const SignButtonWrapper = styled.button<{ width : string }>`
  background-color: #7D74B4;
  width: ${( props ) => props.width };
  height: 60px;
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
const SignButton = ( props: SignButtonProps ) =>{

  const {
    children, width = '100%',
    onClick
  } = props

  return (
    <SignButtonWrapper onClick={ onClick } width={ width } >
      { children }
    </SignButtonWrapper>
  )
}

export default SignButton

type SignButtonProps = {
  children?: string,
  onClick?: () => void
  width?: string,
}