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
  const { tags, isTag, bgColor, mytags } = props;

  const tagList = 
  isTag? (   
    tags?.map(( tag ) => (
      <Tag key={ tag?.id } bgColor={ bgColor }>
        { tag?.content }
      </Tag>
    ))
  ) : (
    tags?.map(( tag ) => (
      <TagBtn item = { tag } key={ tag?.id } mytags = { mytags }>{ tag?.content }</TagBtn>
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
  id?: number,
  content?: string,
  type?: string,
  isSelect?: boolean
}

type TagListProps = {
  tags: tagType[]
  mytags?: tagType[]
  isTag?: boolean 
  bgColor?: string
}
