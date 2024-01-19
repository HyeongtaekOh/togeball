import { Title } from 'src/components'
import { useCallback, useState } from "react"
import { useNavigate } from 'react-router-dom'
import styled from "styled-components"

const MenuWrapper = styled.div`
  box-sizing: border-box;  
  min-width: 130px;
  cursor: pointer;
  height: auto;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`
const LiDiv = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 20px 0px;
  padding-bottom: 20px;
  cursor: pointer;
  gap: 10px;
  z-index: 999;
`

const MenuItem = ( props : MenuItemProps ) => {

  const { title, menus } = props

  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const navigator = useNavigate()

  const onClick = useCallback(( path : string ) => {
    navigator( path )
  }, [] )

  return (

    <MenuWrapper 
      onMouseOver = {() => setIsOpen( true )}
      onMouseLeave = {() => setIsOpen( false )}>
      <Title type='medium'>{ title }</Title>
      <LiDiv>
      {
      isOpen && menus &&
        (
          menus.map(( menu : menuData ) => {
            return (
              <li onClick= {() => onClick( menu?.path ) }>{ menu?.name }</li>
            )
          })
        )
      }
      </LiDiv>
    </MenuWrapper>

  )
}

export default MenuItem

interface menuData {
  name?: string,
  path?: string
}

export type MenuItemProps = {
  title?: string,
  path?: string,
  menus?: menuData[] 
}