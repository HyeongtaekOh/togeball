import TagBtn from '../TagBtn';
import styled from 'styled-components';
import Tag from '../Tag';

const TagListWrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  width: 100%;
  gap: 15px;
`

const TagList = ( props: TagListProps ) => {
  const { tags, bgColor, isTag, limit } = props;

  const tagList = 
  isTag? (   
    tags?.map(( tag ) => (
      <Tag key={ tag?.id } bgColor={ bgColor }>
        { tag?.content }
      </Tag>
    ))
  ) : (
    tags?.map(( tag ) => (
      <TagBtn key={ tag?.id } isSelect = { tag?.isSelect } limitFlag = { limit } >
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
  limit?: boolean,
  bgColor?: string,
}
