import styled from "styled-components";

const TagWrapper = styled.div<{ bgColor : string }>`
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: ${ ( props ) => props.bgColor };
  height: 18px;
  max-width: 100px;
  min-width: 50px;
  border: none;
  color: black;
  padding: 5px 10px 5px 20px;
  line-height: 18px;
  text-align: center;
  text-decoration: none;
  font-size: 10px;
  font-weight: bolder;
  margin: 10px 5px 10px 5px;
  border-radius: 20px;
`;

const CloseButton = styled.button<{ bgColor : string }>`
  height: 16px;
  width: 16px;
  background-color: ${ (props) => props.bgColor };
  margin-left: 4px;
  margin-bottom: 1px;
  border-radius: 100%;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
`;

const Tag = (props: TagProps) => {
  const { children, isRemove = false, bgColor = "#DEDCEE", onDelete } = props;

  const handleDeleteClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    onDelete();
  };

  return (
    <TagWrapper bgColor={ bgColor }>
      <span>{ children }</span>
      { isRemove && <CloseButton bgColor={ bgColor }>X</CloseButton> }
    </TagWrapper>
  );
};

export default Tag;

type TagProps = {
  children?: string;
  bgColor?: string;
  isRemove?: boolean;
  onDelete?: () => void;
};
