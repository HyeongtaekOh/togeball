import { useState } from "react";
import useStore from "../store";
import styled from "styled-components";

const TagsInputWrapper = styled.div`
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 5px;
`
const TagItem = styled.div`
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 26px;
  padding: 3px 13px;
  font-size: 13px;
  background-color: #dedcee;
  border: none;
  border-radius: 20px;
`
const Button = styled.button`
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 15px;
  margin-top: 3px;
  height: 15px;
  color: #9ba3a9;
  background-color: inherit;
  border: none;
`
const TagInput = styled.input`
  cursor: text;
  box-sizing: border-box;
  display: flex;
  width: 600px;
  font-size: 16px;
  background-color: inherit;
  border: none;
  outline: none;

  &::placeholder {
    color: #acb5bd;
  }
`

export const TagsInput = () => {
  const { tagItem, tagList, setTagItem, setTagList } = useStore()
  const [ placeholder, setPlaceholder ] = useState("태그를 설정하고 Enter키를 누르세요(최대 10개)")

  const duplicateCheck = ( text ) => {
    return text !== "" && tagList.indexOf( text ) !== -1 ?  false: true;
  }

  const onKeyDown = ( event ) => {
    if(event.target.value.length === 0 && event.key === 'Backspace' ){
      deleteTagItem( tagList[ tagList.length - 1 ] )
      setTagItem("")
    }
  }

  const onKeyUp = ( event ) => {
    if (event.target.value.length !== 0 && event.key === "Enter" ) {
      if( tagList.length >= 10 ) {
        alert( '태그는 최대 10개입니다.' )
        setPlaceholder( '태그는 최대 10개입니다.' )
      }
      else if( duplicateCheck( event.target.value )) setTagList([ ...tagList, tagItem ]);
      setTagItem('')
    }
  }

  const deleteTagItem = ( item ) => {
    const filteredTagList = tagList.filter(tagItem => tagItem !== item)
    setTagList(filteredTagList);
  }

  return (
    <TagsInputWrapper>
      { tagList?.map(( tagItem ) => {
        return (
          <TagItem key={ tagItem }>
            <span style={{ fontWeight: 'bold' }}>#{ tagItem }</span>
            <Button onClick={ () => deleteTagItem( tagItem ) }>X</Button>
          </TagItem>
      )
      })}
      <TagInput
        type="text"
        placeholder={ placeholder }
        onKeyUp={ onKeyUp }
        onKeyDown={ onKeyDown }
        onChange={( event ) => setTagItem( event.target.value )}
        value={ tagItem }
      />
    </TagsInputWrapper>
  );
}