import styled from 'styled-components';

const TagWrapper = styled.div<{ $bgColor : string }>`
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${ ( props ) => props.$bgColor };
  height: 30px;
  max-width: 100px;
  min-width: 70px;
  border: none;
  font-size: 10px;
  font-weight: bolder;
  margin: 10px -10px -15px 5px;
  border-radius: 20px;
`;

const Tag = ( props: TagProps ) => {
  const { children, bgColor = '#DEDCEE' } = props

  return (
    <TagWrapper $bgColor={ bgColor }>
      { children }
    </TagWrapper>
  );
};

export default Tag

type TagProps = {
  children?: string,
  bgColor?: string,
};
