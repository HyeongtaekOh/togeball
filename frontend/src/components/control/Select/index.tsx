import { TagType } from 'src/types'
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
  border-top: none;
  position: absolute;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 40px 0px;
  cursor: pointer;
  z-index: 333;
  background-color: white;
  border-radius: 10px;
`
const LiWrapper = styled.li`
  cursor: pointer;
  padding: 8px 15px;
  &:hover{
    background-color: #f2f2f2;
  }
`

const SelectBox = ( props: SelectBoxProps ) => {

  const { 
    width = '200px', dataSource,
    placeholder = '선택해주세요', 
    background = 'white', height = '60px' 
  } = props
  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const [ selectedValue, setSelectedValue ] = useState<string>( placeholder )

  const openHandler = () => {
    setIsOpen( !isOpen )
  }

  const changeHandler = ( data : SourceData | TagType ) => {
    setSelectedValue( data?.content )
    setIsOpen( false )
    props.setState( data?.id )
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
        dataSource?.map(( data : SourceData | TagType ) => {
          return <LiWrapper onClick={() => changeHandler( data )}>{ data?.content }</LiWrapper>
        })
      )}
      </LiDivWrapper>
    }
    {/* { 
      isOpen && tagSource &&
      <LiDivWrapper width = { width } background = { background }>
      {(
        tagSource?.map(( data : TagType ) => {
          return <LiWrapper onClick={() => changeHandler( data )}>{ data?.content }</LiWrapper>
        })
      )}
      </LiDivWrapper>
    } */}
    </div>
  )

}

export default SelectBox

type SourceData = {
  id?: number,
  content?: any
}

type SelectBoxProps = {
  setState?(name: any): unknown
  width?: string,
  height?: string,
  dataSource?: SourceData[] | TagType[],
  placeholder?: string,
  background?: string,
}
