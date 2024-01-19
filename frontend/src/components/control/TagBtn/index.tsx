import { useState } from 'react';
import styled from 'styled-components';


const TagBtnWrapper = styled.button<TagBtnProps>`
  background-color: ${( props ) => props.bgColor };
  width: 100px;
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

    const [isPurple, setIsPurple] = useState<boolean>(true);
    const [isWhite, setIsWhite] = useState<boolean>(true);

    const changeColor = () => {
        if(isChange){
            setIsPurple((prevIsPurple) => !prevIsPurple);
            setIsWhite((prevIsWhite) => !prevIsWhite);
        }
    }

    const backgroundColor = isPurple ? '#DEDCEE' : '#6A60A9';
    const letterColor = isWhite ? 'black' : 'white';

    const { 
        children,
        isChange = false,
        onClick 
      } = props

      return (
        <TagBtnWrapper onClick={ ()=> { changeColor(); onClick?.() } } bgColor={backgroundColor} color={ letterColor }>
          { children }
        </TagBtnWrapper>
      )
}


export default TagBtn

type TagBtnProps = {
    children?: string,
    bgColor?: string,
    color?: string,
    isChange?: boolean,
    onClick?: () => void
}