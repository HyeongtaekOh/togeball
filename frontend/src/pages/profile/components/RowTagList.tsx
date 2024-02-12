import { Title, TagList } from 'src/components'
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

  const { children, list, flag, myteam, mytags } = props

  return(
    <RowTagListWrapper>
      <TitleWrapper>
        <Title type='small'>{ children }</Title>
      </TitleWrapper>
      <TagList tags = { list } myteam= { myteam } flag = { flag } mytags = { mytags }/>
    </RowTagListWrapper>
  )
}

export default RowTagList

interface dataSource  {
  id?: number,
  content?: string,
  type?: string
}

type RowTagListProp = {
  children?: Array<string | any> | string,
  flag?: boolean,
  mytags?: dataSource[],
  myteam?: string,
  list?: dataSource[]
}