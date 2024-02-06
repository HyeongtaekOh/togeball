import { useState } from 'react';
import useModel from 'src/pages/profile/store';
import styled from 'styled-components';


const TagBtnWrapper = styled.button<{ $bgColor: string, color: string }>`
  background-color: ${( props ) => props.$bgColor };
  max-width: 100px;
  min-width: 100px;
  height: 33px;
  border: none;
  color: ${( props ) => props.color };
  font-size: 10px;
  cursor: pointer;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 11px;
  font-weight: bold;
`

const TagBtn = (props: TagBtnProps) => {

    const { children, isSelect, limitFlag } = props

    const [ isClick, setIsClick ] = useState<boolean>( isSelect )
    // const [ islimit, setIsLimit ] = useState<boolean>( limitFlag )
    const { limit, setLimit } = useModel()
    
    const backgroundColor = isClick? '#6A60A9' : '#DEDCEE';
    const letterColor = isClick? 'white' : 'black';

    const changeColor = () => {
      if (limitFlag) {
        if(limit===''){
          setIsClick(!isClick)
          setLimit(children)
        }else{
          if(limit===children){
            setIsClick(!isClick)
            setLimit('')
          }
        }
      }else{
        setIsClick(!isClick)
      }
    }

      return (
        <TagBtnWrapper onClick={ changeColor } $bgColor={ backgroundColor } color={ letterColor }>
          #{ children }
        </TagBtnWrapper>
      )
}


export default TagBtn

type TagBtnProps = {
    children?: string,
    isSelect?: boolean,
    limitFlag?: boolean,
}