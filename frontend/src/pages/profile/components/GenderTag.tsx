import { Title } from 'src/components'
import { useEffect, useState } from 'react';
import { GenderItem } from './index'
import styled from 'styled-components';

const RowTagListWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  gap: 10px;
  margin-top: 30px;
  margin-left: 20px;
`

const TitleWrapper = styled.div`
  display: flex;
  text-align: right;
  width: 100px;
  font-weight: bold;
  margin-right: 12px;
`

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`

const GenderTag = (props) => {

    const gender = [ 'MALE', 'FEMALE' ]

    const genlist = 
    gender?.map(( gen ) => (
      <GenderItem userGen = { props.userGen }>
        { gen }
      </GenderItem>
    ))


      return (
        <RowTagListWrapper>
            <TitleWrapper>
                <Title type='small'>성별</Title>
            </TitleWrapper>
            { genlist }
            <TagListWrapper>
            </TagListWrapper> 
        </RowTagListWrapper>
      )
}


export default GenderTag
