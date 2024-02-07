import { useState } from 'react'
import { Title } from 'src/components'
import styled from 'styled-components'

const MessageWrapper = styled.div`
  box-sizing: border-box;
`

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
  padding: 10px;
  padding-top: 20px;
  padding-bottom: 20px;
  align-items: center;
`

const InputBox = ( props: InputProps ) => {

  const {
    title, height = '60px', width = '100%', 
    placeholder = '내용을 입력하세요', value, 
    onChange, onKeyDown, children
  } = props


  return (
    <MessageWrapper>
    <InputWrapper height={ height } width={ width }>
      {
        title && 
        <div style={{ width: '30%', paddingLeft : '10px' }}> { title } </div>
      }
      <input 
        value = { value } 
        onChange={ onChange } 
        onKeyDown={ onKeyDown }
        style={{ width: '70%', fontSize: '12px' }} 
        placeholder={ placeholder }
      />  
      { children } 
    </InputWrapper>
    </MessageWrapper>
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
  children?: React.ReactNode
}
