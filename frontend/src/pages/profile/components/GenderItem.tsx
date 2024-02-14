import { useEffect, useState } from 'react';
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

const GenderItem = (props: TagBtnProps) => {

    const { children, userGen } = props
    const { setGender, gender } = useModel()
    const isCorrect = children===userGen
    
    const [ isClick, setIsClick ] = useState<boolean>( isCorrect )

    useEffect(() =>{
        if( isCorrect && gender==='' ){
            setGender( children )
        }
    },[])

    const backgroundColor = isClick? '#6A60A9' : '#DEDCEE';
    const letterColor = isClick? 'white' : 'black';

    const changeColor = () => {
        if( gender==='' ){
            setIsClick( !isClick )
            setGender( children )
        }else{
            if( gender===children ){
                setIsClick( !isClick )
                setGender('')
            }
        }
        
    }

      return (
        <TagBtnWrapper onClick={ changeColor } $bgColor={ backgroundColor } color={ letterColor }>
          { children }
        </TagBtnWrapper>
      )
}


export default GenderItem

type TagBtnProps = {
    children?: string
    userGen?: string
}