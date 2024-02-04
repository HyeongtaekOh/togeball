import { useEffect, useRef, useState } from 'react'
import styled from 'styled-components'
import useModel from '../store'
import { TagType } from 'src/types'

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

const TagBtn = ( props: TagBtnProps ) => {

    const { children, isSelect, item } = props
    const { addSelectTags, deleteTags } = useModel()

    const isClick = useRef( isSelect )

    const backgroundColor = isClick.current ? '#6A60A9' : '#DEDCEE';
    const letterColor = isClick.current ? 'white' : 'black';

    const changeColor = () => {
      isClick.current =!isClick.current;      
      isClick.current ? addSelectTags( item ) : deleteTags( item ) ;
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
    item?: TagType,
}