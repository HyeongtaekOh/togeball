import styled from 'styled-components'

const InputWrapper = styled.div<{ height: string; width: string }>`
  height: ${( props ) => props.height };
  width: ${( props ) => props.width };
  border: 1px solid #ccc;
  border-radius: 15px;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin: 3px 0px 3px 0px;
  padding : 2%;
  align-items: center;
`

const InputBox = ( props: InputProps ) => {

  const {
    title, height = '60px', width = '100%', 
    placeholder = '내용을 입력하세요', value, 
    onChange, onKeyDown, children, type,
    fontSize = '12px'
  } = props


  return (
    <InputWrapper height={ height } width={ width }>
      {
        title && 
        <div style={{ width: '30%', paddingLeft : '10px' }}> { title } </div>
      }
      <input 
        type = { type === 'password'? 'password': 'text' }
        value = { value } 
        onChange={ onChange } 
        onKeyDown={ onKeyDown }
        style={{ width: '70%', fontSize: `${ fontSize }` }} 
        placeholder={ placeholder }
      />  
      { children } 
    </InputWrapper>
  )

}

export default InputBox;

export type InputProps = {
  title?: string,
  height?: string,
  width?: string,
  icon?: React.ReactNode,
  placeholder?: string,
  value? : string,
  onChange? : ( value?: any ) => void
  onKeyDown?: (event: React.KeyboardEvent<HTMLInputElement>) => void
  children?: React.ReactNode,
  fontSize?: string | number,
  type?: string
}
