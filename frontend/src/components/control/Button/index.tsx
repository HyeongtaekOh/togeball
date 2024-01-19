import { styled } from 'styled-components'



const backgroundColor = {
  cancel : '#A2A2A7',
  parti : '#7D74B4',
  save : '#FBD14B',
  reset : '#756868'

}

const color = {
  cancel : 'white',
  parti : 'white',
  save : 'black', 
  reset : 'white'
}

const onCursorColor = {
  cancel : '#F21408',
  parti : '#9008F2',
  save : ' #F9B303',
  reset : '#EE0E2F'
}
const ButtonWrapper = styled.button<ButtonProps>`
  background-color: ${( props ) => backgroundColor[props.type] };
  width: ${( props ) =>  props.width };
  display: flex;
  justify-content: center;
  align-items: center;
  height: 20.659px;
  border: none;
  border-radius: 100px;
  color: ${( props) => color[props.type] };
  padding: 15px 25px;
  font-weight: bold;
  font-size: 11px;
  cursor: pointer;
  margin: 10px 0px 10px 0px;
  transition: background-color 0.3s, color 0.3s;
  &:hover {
    background-color: ${(props) => onCursorColor[props.type]}; /* 배경색 변경 */
    color: white; /* 텍스트 색상 변경 */
  }
`

const Button = ( props: ButtonProps ) => {

  const { 
    children, type = 'parti', color = 'lightgrey', width = '100.981px',
    onClick 
  } = props

  

  return (
    <ButtonWrapper onClick={ onClick } type={ type } color={ color } width={width}>
      { children }
    </ButtonWrapper>
  )

}

export default Button

type ButtonProps = {
  children?: string,
  color?: string,
  type?:'cancel' |'save' |'reset'|'parti'
  width?: string,
  onClick?: () => void
}
