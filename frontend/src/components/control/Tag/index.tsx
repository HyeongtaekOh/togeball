import styled from 'styled-components'

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
`

const Tag = ( props: TagProps ) => {
  
  const { children,  bgColor = '#DEDCEE', width, height='18px' } = props

  return (
    <TagWrapper $bgColor={ bgColor } width={ width } height={ height } >
     { children }
    </TagWrapper>
  )
}

export default Tag

type TagProps = {
  children?: string,
  bgColor?: string,
  width?: string,
  height?: string
}
