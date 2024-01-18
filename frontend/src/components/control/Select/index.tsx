import styled from 'styled-components'

const SelectWrapper = styled.select<{ width: string }>`
  width: ${( props ) => props.width };
`

const SelectBox = ( props: SelectBoxProps ) => {

  const { width = '100px', dataSource, placeholder = '선택해주세요' } = props

  return (
    <SelectWrapper width={ width } defaultValue=''>
      <option style={{ display: 'none' }} value='' disabled>
        { placeholder }
      </option>
      { dataSource &&
        dataSource.map(({ value, name }, index) => {
          return (
            <option key={ index } value={ value }>
              { name }
            </option>
          )
        })}
    </SelectWrapper>
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
