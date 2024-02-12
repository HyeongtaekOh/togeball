import { Title } from 'src/components'
import { TagList } from './index'
import styled from 'styled-components'
import { TagType } from '@/types'

const ColTagListWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 30px;
`

const ColTitleWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 100px;
  font-weight: bold;
  margin-bottom: 10px;
`

const ColTagList = ( props : RowTagListProp ) => {

  const { children, list, mytags } = props
  // console.log(list)

  return(
  <ColTagListWrapper>
    <ColTitleWrapper>
      <Title type='small'>{ children }</Title>
    </ColTitleWrapper>
    <TagList tags = { list } mytags = { mytags }/>
  </ColTagListWrapper>
  )
}

export default ColTagList

interface dataSource  {
  id?: number,
  content?: string,
  type?: string,
  isSelect?: boolean
}

type RowTagListProp = {
  children?: Array<string | any> | string,
  mytags?: dataSource[],
  list?: dataSource[]
}