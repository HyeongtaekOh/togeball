import TagBtn from "../TagBtn";
import styled from "styled-components";
import Tag from "../Tag";
import { useEffect, useState } from "react";

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`;

const TagBtnWrapper = styled.button<{ $isSelect: boolean }>`
  background-color: ${( props ) => props.$isSelect? '#6A60A9' : '#DEDCEE' };
  max-width: 100px;
  min-width: 100px;
  height: 33px;
  border: none;
  color: ${( props ) => props.$isSelect? 'white' : 'black' };
  font-size: 10px;
  cursor: pointer;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 11px;
  font-weight: bold;
`

const RadioTagList = ( props: TagListProps ) => {
  const { tags } = props;
  
    const changeColor = (e) => {
      console.log(e.currentTarget)
    }

    return (
      <TagListWrapper>
      {
        tags?.map(( tag ) => (
          <TagBtnWrapper 
            key = { tag?.value } onClick={(e) => changeColor(e) } $isSelect = { tag?.isSelect }>
            #{ tag?.name }
          </TagBtnWrapper>
        ))
      }
      </TagListWrapper>
    )

};

export default RadioTagList;

interface tagType {
  name : string,
  value: number
  isSelect?: boolean
}

type TagListProps = {
  tags: tagType[] 
};
