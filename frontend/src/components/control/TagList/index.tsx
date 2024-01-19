import { TagBtn } from '../';
import styled from 'styled-components';

const TagListWrapper = styled.div`
  display: flex;
  align-items: space-between;
  justify-content: flex-start;
  flex-wrap: wrap;
  width: 100%;
`

const TagList = (props: TagListProps) => {

    const { tags } = props

    // const tagSize = tags.length

    return (
        <>
        <TagListWrapper>
        {tags[0]} {tags[1]} {tags[2]} {tags[3]} {tags[4]}
        </TagListWrapper>
        </>

    )

}

export default TagList

type TagListProps = {
    tags: {}
}
