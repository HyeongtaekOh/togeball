import { TagBtn } from './index'
import styled from 'styled-components'
import { Tag } from 'src/components'
import useModel from '../store'
import { useEffect } from 'react'

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`

const TagList = ( props: TagListProps ) => {
  const { tags, isRemove, bgColor } = props;


  const tagList = 
  !isRemove? (   
    tags.map(( tag ) => (
      <TagBtn item = { tag } key={ tag?.value } isSelect = { tag?.isSelect } >{ tag?.name }</TagBtn>
    ))
  ) : (
    tags.map(( tag ) => (
      <Tag key={ tag?.value } isRemove={ isRemove } bgColor={ bgColor }>
        { tag?.name }
      </Tag>
    ))
  )

  return (
    <TagListWrapper>
      { tagList }
    </TagListWrapper>
  )

}

export default TagList

interface tagType {
  name?: string,
  value?: number
  isSelect?: boolean
}

type TagListProps = {
  tags: tagType[], 
  isRemove?: boolean, 
  bgColor?: string,
  onlyOne?: boolean
}
