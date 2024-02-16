import TagBtn from '../TagBtn'
import Tag from '../Tag'
import styled from 'styled-components'

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`

const TagList = ( props: TagListProps ) => {

  const { tags, myteam, bgColor, isTag, flag, mytags } = props

  const tagList = 
  isTag? (   
    tags?.map(( tag ) => (
      <Tag key={ tag?.id } bgColor={ bgColor }>
        { tag?.content }
      </Tag>
    ))
  ) : (
    tags?.map(( tag ) => (
      <TagBtn item = { tag } key={ tag?.id } mytags = { mytags }
      myteam = { myteam } stadiumFlag = { flag } >
        { tag?.content }
      </TagBtn>
    ))
  )

  return (
    <TagListWrapper>{ tagList }</TagListWrapper>
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
  tags: tagType[], 
  isTag?: boolean,
  flag?: boolean,
  myteam?: string,
  mytags?: tagType[],
  bgColor?: string,
}
