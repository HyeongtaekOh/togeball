import { useState } from 'react'
import { DownIcon } from 'src/components/icon'
import styled from 'styled-components'

const SelectWrapper = styled.div<{ width : string, background: string }>`
  display: flex;
  box-sizing: border-box;
  width: ${( props )=> props.width };
  height: 44px;
  background: ${( props )=> props.background };
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  font-weight: bold;
  border-radius: 20px;
  border: 1px solid lightgray;
  cursor: pointer;
`

const LiDivWrapper = styled.div<{ width : string, background: string }>`
  display: flex;
  flex-direction: column;
  width: calc(${( props )=> props.width } - 23px);
  background: ${( props )=> props.background };
  border: 1px solid lightgray;
  border-radius: 10px;
  padding: 10px;
  border-top: none;
  gap: 15px;
  position: absolute;
  z-index: 10;
`

const LiWrapper = styled.li`
  cursor: pointer;
`

const SelectBox = ( props: SelectBoxProps ) => {

  const { width = '200px', dataSource, placeholder = '선택해주세요', background = 'white' } = props
  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const [ selectedValue, setSelectedValue ] = useState<string>( placeholder )

  const openHandler = () => {
    setIsOpen( !isOpen )
  }

  const changeHandler = ( data ) => {
    setSelectedValue( data?.name )
    setIsOpen( false )
  }

  return (
    <div>
    <SelectWrapper onClick= { openHandler } width = { width } background = { background }>
      { selectedValue }
      <DownIcon/>
    </SelectWrapper>
      {
      isOpen && dataSource &&
      <LiDivWrapper width = { width } background = { background }>
        {
        (
          dataSource.map(( data : SourceData ) => {
            return (
              <LiWrapper onClick={() => changeHandler( data )}>{ data?.name }</LiWrapper>
            )
          })
        )
        }
    </LiDivWrapper>
    }
    </div>
   
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
  placeholder?: string,
  background?: string
}
