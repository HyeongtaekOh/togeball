import { useState } from 'react';
import styled from 'styled-components';


const TagBtnWrapper = styled.button<{ bgColor: string, color: string }>`
  background-color: ${( props ) => props.bgColor };
  max-width: 100px;
  min-width: 80px;
  height: 28px;
  border: none;
  color: ${( props ) => props.color };
  padding: 5px 10px;
  text-align: center;
  text-decoration: none;
  font-size: 10px;
  cursor: pointer;
  margin: 10px 10px 10px 10px;
  border-radius: 20px;
`


const TagBtn = (props: TagBtnProps) => {

    const { children } = props

    const [ isClick, setIsClick ] = useState<boolean>(false);
    
    const backgroundColor = !isClick ? '#DEDCEE' : '#6A60A9';
    const letterColor = !isClick ? 'black' : 'white';

    const changeColor = () => {
      setIsClick(!isClick);
    }

      return (
        <TagBtnWrapper onClick={ changeColor } bgColor={ backgroundColor } color={ letterColor }>
          { children }
        </TagBtnWrapper>
      )
}


export default TagBtn

type TagBtnProps = {
    children?: string,
}