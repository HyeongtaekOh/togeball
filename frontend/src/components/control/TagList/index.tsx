import TagBtn from '../TagBtn';
import Tag from '../Tag';
import styled from 'styled-components';

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`

const TagList = ( props: TagListProps ) => {
  const { tags, bgColor, isTag, limit, flag } = props;

  const tagList = 
  isTag? (   
    tags?.map(( tag ) => (
      <Tag key={ tag?.id } bgColor={ bgColor }>
        { tag?.content }
      </Tag>
    ))
  ) : (
    tags?.map(( tag ) => (
      <TagBtn key={ tag?.id } isSelect = { tag?.isSelect } limitFlag = { limit } stadiumFlag = { flag } >
        { tag?.content }
      </TagBtn>
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
  id?: number,
  content?: string,
  type?: string,
  isSelect?: boolean
}

type TagListProps = {
  tags: tagType[], 
  isTag?: boolean,
  flag?: boolean,
  limit?: boolean,
  bgColor?: string,
}
