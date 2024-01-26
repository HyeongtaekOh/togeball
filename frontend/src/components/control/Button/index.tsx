import styled, { css } from 'styled-components'


const ButtonWrapper = styled.button<ButtonProps>`
  background-color: #A2A2A7;
  width: ${( props ) =>  props.width };
  display: flex;
  justify-content: center;
  align-items: center;
  height: 28px;
  border: none;
  border-radius: 10px;
  color:white;
  padding: 15px 25px;
  font-weight: bold;
  font-size: 11px;
  cursor: pointer;
  margin: 10px 0px 10px 0px;
  transition: background-color 0.3s, color 0.3s;
  &:hover {
    background-color: #EE0E2F; 
    color: white; 
  }

  ${(props) =>
  props.type ==='save' &&
  css`
    background-color: #FBD14B;  
    color: white;
    &hover{
      background-color: #FBD14B;
    }
  `}

  

  ${(props) =>
  props.type ==='parti' &&
  css`
    background-color: #7D74B4;  
    color: white;
    &:hover{
      background-color: #9008F2;
    }
  `}

  ${(props) =>
  props.type ==='reset' &&
  css`
    background-color: #DEDCEE;  
    color: #756868;
    &:hover{
      background-color: #EE0E2F;
    }
  `}
`

const Button = ( props: ButtonProps ) => {

  const { 
    children, type = 'parti', color = 'lightgrey', width = '100px',
    onClick 
  } = props

  

  return (
    <ButtonWrapper onClick={ onClick } type={ type } color={ color } width={ width }>
      { children }
    </ButtonWrapper>
  )

}

export default Button

type ButtonProps = {
  children?: string,
  color?: string,
  type?:'cancel' |'save' | 'reset' | 'parti'
  width?: string,
  onClick?: () => void
}
