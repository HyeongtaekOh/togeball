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

const TitleWrapper = styled.div<{ type? : string } >`
  display: flex;
  flex-direction: column;
  text-align: right;
  width: 100px;
  padding-top: ${( prop ) => prop.type === 'input'? '13px': ( prop.type === 'value' ? '0px': '2px' ) };
  font-weight: bold;
  margin-left:  ${( prop ) => prop.type && '-10px' };
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
