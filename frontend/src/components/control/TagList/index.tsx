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
  const { tags } = props;

  if (!tags[0].isRemove) {
    return (
      <TagListWrapper>
        { tags.map((tag, index) => (
          <TagBtn key={ index }>{ tag.title }</TagBtn>
        ))}
      </TagListWrapper>
    );
  } else {
    return (
      <TagListWrapper>
        { tags.map((tag, index) => (
          <Tag key={ index } isRemove={ tag.isRemove } bgColor={ tag.bgColor }>
            { tag.title }
          </Tag>
        ))}
      </TagListWrapper>
    );
  }

  // return (
  //     <TagListWrapper>
  //         {tags.map((tag, index) => (
  //           <TagBtn key={index} isChange={ tag.isChange }>
  //             { tag.title }
  //           </TagBtn>
  //         ))}
  //       </TagListWrapper>
  //   );
};

export default TagList;

type TagListProps = {
  tags: Array<{ title: string; isRemove?: boolean; bgColor?: string }>;
};
