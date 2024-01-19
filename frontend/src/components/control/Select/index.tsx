import { DownIcon } from 'src/components/icon'
import styled from 'styled-components'

const SelectBoxWrapper = styled.div<{ width: string }>`
  width: 200px;
  height: 40px;
  border: 1px solid black;
  display: flex;
  background-color: #DEDCEE;
  align-items: center;
  border-radius: 20px;
  border: none;
  transition: background-color 0.3s, color 0.3s;
  &:hover {
    background-color: #7D74B4; /* 배경색 변경 */
    color: white; /* 텍스트 색상 변경 */
  }
`

const SelectWrapper = styled.select`
  width: 100%;
  height: 100%;
  padding: 0 28px 0px 20px;
  cursor: pointer;
  z-index: 999;
  background: transparent;
  border: none;
  font-weight: bold;
  &:hover {
    color: white; 
  }
`

const OptionWrapper = styled.option`
padding: 4px;
font-size: 14px;
color: #fff;
background: #272822;
`

const NameWrapper = styled.div`
   height: 20px;
`

const IconWrapper = styled.div`
  margin-left : -35px;
`

const SelectBox = ( props: SelectBoxProps ) => {

  const { width = '100px', dataSource, placeholder = '선택해주세요' } = props

  return (
    <SelectBoxWrapper width={ width }>
    <SelectWrapper defaultValue=''>
      <option style={{ display: 'none' }} value='' disabled>
        { placeholder }
      </option>
      { dataSource &&
        dataSource.map(({ value, name }, index) => {
          return (
            <OptionWrapper style={{ padding: '10px' }} key={ index } value={ value }>
              { name }
            </OptionWrapper>
          )
        })}
    </SelectWrapper>
    <IconWrapper>
      <DownIcon/>
    </IconWrapper>
    </SelectBoxWrapper>
  )

}

export default SelectBox

type SourceData = {
  value: string,
  name: any
}

type SelectBoxProps = {
  width?: string,
  dataSource: SourceData[],
  placeholder?: string
}
