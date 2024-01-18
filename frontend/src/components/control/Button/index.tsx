import { styled } from 'styled-components'

const size = {
  large: '100%',
  medium: '200px',
  small: '100px',
}

const ButtonWrapper = styled.button<ButtonProps>`
  background-color: ${( props ) => props.color };
  width: ${( props ) => size[ props.type ]};
  border: none;
  color: white;
  padding: 15px 32px;
  text-align: center;
  font-weight: bold;
  text-decoration: none;
  font-size: 16px;
  cursor: pointer;
  margin: 10px 0px 10px 0px;
`

const Button = ( props: ButtonProps ) => {

  const { 
    children, type = 'large', color = 'lightgrey', 
    onClick 
  } = props

  return (
    <ButtonWrapper onClick={ onClick } type={ type } color={ color }>
      { children }
    </ButtonWrapper>
  )

}

export default Button

type ButtonProps = {
  children?: string,
  color?: string,
  type?: 'medium' | 'large' | 'small',
  onClick?: () => void
}
