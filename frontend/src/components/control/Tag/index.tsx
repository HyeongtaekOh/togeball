import styled from 'styled-components';

const TagWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center; 
  background-color: #FBD14B;
  height: 18px;
  max-width: 100px;
  border: none;
  color: black;
  padding: 5px 10px;
  line-height: 18px;
  text-align: center;
  text-decoration: none;
  font-size: 10px;
  font-weight: bolder;
  margin: 10px 5px 10px 5px;
  border-radius: 20px;
`
const CloseButton = styled.button`
  height: 16px;
  width: 16px;
  background-color: #E5B41A;
  margin-left: 4px;
  border-radius: 100%;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;  
  `;


const Tag = (props: TagProps) => {

    const handleDeleteClick = (e: React.MouseEvent) => {
        e.stopPropagation();
        onDelete?.();
      };

    const { 
        children,
        isRemove = false,
        onDelete
      } = props

    return (
        <TagWrapper>
          { children }
          { isRemove && <CloseButton onClick={ handleDeleteClick }>X</CloseButton> }
        </TagWrapper>
    )
}


export default Tag

type TagProps = {
    children?: string,
    isRemove?: boolean,
    onDelete?: () => void;
}



