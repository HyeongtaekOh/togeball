import TagBtn from "../TagBtn";
import styled from "styled-components";
import Tag from "../Tag";

const TagListWrapper = styled.div`
  display: flex;
  align-items: space-between;
  justify-content: flex-start;
  flex-wrap: wrap;
  width: 100%;
`;

const TagList = (props: TagListProps) => {

  const { tags, isRemove, bgColor } = props;

  if (!isRemove && typeof tags!== 'string') {
    return (
      <TagListWrapper>
        { tags.map((tag, index) => (
          <TagBtn key={ index }>{ tag.title }</TagBtn>
        ))}
      </TagListWrapper>
    );
  } else if (typeof tags !== 'string'){
    return (
      <TagListWrapper>
        { tags.map((tag, index) => (
          <Tag key={ index } isRemove={ isRemove } bgColor={ bgColor }>
            { tag.title }
          </Tag>
        ))}
      </TagListWrapper>
    );
  }
  else{
    <Tag isRemove={ isRemove } bgColor={ bgColor }>
        tags
    </Tag>
  }
};

export default TagList;

type TagListProps = {
  tags: Array<{ title: string; }> | string; 
  isRemove?: boolean; 
  bgColor?: string;
};
