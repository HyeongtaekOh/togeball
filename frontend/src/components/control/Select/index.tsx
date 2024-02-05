import { useState } from 'react'
import { DownIcon } from 'src/components/icon'
import styled from 'styled-components'

const SelectWrapper = styled.div<{ width : string, height : string, background: string }>`
  display: flex;
  box-sizing: border-box;
  width: ${( props )=> props.width };
  height: ${( props )=> props.height };
  max-height: 40px;
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
  width: ${( props )=> props.width };
  background: ${( props )=> props.background };
  border: 1px solid #DEDCEE;
  padding: 10px;
  border-top: none;
  position: absolute;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 40px 0px;
  padding: 20px 10px;
  cursor: pointer;
  gap: 15px;
  z-index: 333;
  background-color: white;
  border-radius: 10px;
`

const LiWrapper = styled.li`
  cursor: pointer;
`

const SelectBox = ( props: SelectBoxProps ) => {

  const { width = '200px', dataSource, placeholder = '선택해주세요', background = 'white', height = '60px' } = props
  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const [ selectedValue, setSelectedValue ] = useState<string>( placeholder )

  const openHandler = () => {
    setIsOpen( !isOpen )
  }

  const changeHandler = ( data : SourceData ) => {
    setSelectedValue( data?.name )
    setIsOpen( false )
    props.setState(data?.name);
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column'}}>
    <SelectWrapper onClick= { openHandler } width = { width } height = { height } background = { background }>
      { selectedValue }
      <DownIcon/>
    </SelectWrapper>
    { 
      isOpen && dataSource &&
      <LiDivWrapper width = { width } background = { background }>
      {(
        dataSource.map(( data : SourceData ) => {
          return <LiWrapper onClick={() => changeHandler( data )}>{ data?.name }</LiWrapper>
        })
      )}
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
  setState?(name: any): unknown
  width?: string,
  height?: string,
  dataSource: SourceData[],
  placeholder?: string,
  background?: string
}
