import { Title } from 'src/components'
import { TagList } from './index'
import styled from 'styled-components'


const RowTagListWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  gap: 10px;
  margin-top: 30px;
`

const TitleWrapper = styled.div<{ type? : string } >`
  display: flex;
  flex-direction: column;
  text-align: right;
  width: 100px;
  padding-top: ${(prop) => prop.type === 'input'? '13px': ( prop.type === 'value'? '0px': '2px' ) };
  font-weight: bold;
  margin-left:  ${(prop) => prop.type && '-10px' };
  margin-right: 12px;
`

const RowTagList = ( props : RowTagListProp ) => {

  const { children, list } = props

  return(
    <RowTagListWrapper>
      <TitleWrapper>
        <Title type='small'>{ children }</Title>
      </TitleWrapper>
      <TagList tags = { list }/>
    </RowTagListWrapper>
  )
}

export default RowTagList

interface dataSource  {
  name?: string,
  value?: number,
  isSelect?: boolean
}

type RowTagListProp = {
  children?: Array<string | any> | string,
  list?: dataSource[]
}