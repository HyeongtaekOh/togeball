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
  const { tags, bgColor, isTag } = props;

  const tagList = 
  isTag? (   
    tags.map(( tag ) => (
      <Tag key={ tag?.value } bgColor={ bgColor }>
        { tag?.name }
      </Tag>
    ))
  ) : (
    tags.map(( tag ) => (
      <TagBtn key={ tag?.value } isSelect = { tag?.isSelect } >
        { tag?.name }
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
  name?: string,
  value?: number
  isSelect?: boolean
}

type TagListProps = {
  tags: tagType[], 
  isTag?: boolean, 
  bgColor?: string,
}
