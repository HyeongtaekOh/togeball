import styled from 'styled-components';

const TagWrapper = styled.div<{ $bgColor : string, width : string, height : string }>`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${ ( props ) => props.$bgColor };
  width: ${ ( props ) => props.width };
  height: ${ ( props ) => props.height };
  max-width: 100px;
  border: none;
  color: black;
  padding: 5px 10px 5px 10px;
  line-height: 18px;
  text-align: center;
  text-decoration: none;
  font-size: 10px;
  font-weight: bolder;
  margin: 1px 5px 1px 5px;
  border-radius: 20px;
`;

const Tag = ( props: TagProps ) => {
  const { children, isRemove = false, bgColor = '#DEDCEE', onDelete, width, height='18px' } = props

  const handleDeleteClick = ( e: React.MouseEvent ) => {
    e.stopPropagation()
    onDelete()
  };

  return (
    <TagWrapper $bgColor={ bgColor } width={ width } height={ height } >
      <span>{ children }</span>
      { isRemove && <CloseButton $bgColor={ bgColor }>X</CloseButton> }
    </TagWrapper>
  );
};

export default Tag

type TagProps = {
  children?: string,
  bgColor?: string,
  isRemove?: boolean,
  onDelete?: () => void
  width?: string,
  height?: string
};
