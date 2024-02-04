import { TagBtn } from './index'
import { Tag } from 'src/components'
import styled from 'styled-components'

const TagListWrapper = styled.div<{ $isTag : boolean }>`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  height: auto;
  gap: 12px;
  min-height: ${( prop ) => prop.$isTag && '25px' }
`

const TagList = ( props: TagListProps ) => {
  const { tags, isTag, bgColor } = props;

  const tagList = 
  isTag? (   
    tags.map(( tag ) => (
      <Tag key={ tag?.value } bgColor={ bgColor }>
        { tag?.name }
      </Tag>
    ))
  ) : (
    tags.map(( tag ) => (
      <TagBtn item = { tag } key={ tag?.value } isSelect = { tag?.isSelect } >{ tag?.name }</TagBtn>
    ))
  )

  return (
    <TagListWrapper $isTag = { isTag }>
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
  isTag?: boolean, 
  bgColor?: string,
}
