import { useCallback, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'

const MenuWrapper = styled.div`
  box-sizing: border-box;  
  cursor: pointer;
  height: auto;
  display: flex;
  flex-direction: column;
`
const LiDivWrapper = styled.div`
  position: absolute;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 50px 0px;
  padding: 10px 10px;
  cursor: pointer;
  gap: 10px;
  z-index: 999;
  background-color: white;
  border-radius: 10px;
  box-shadow: 1px 1px 1px 1px lightgrey;
`

const IconItem = ( props : MenuItemProps ) => {

  const { menus, children } = props

  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const navigator = useNavigate()

  const navigateHandler = useCallback(( path : string ) => {
    navigator( path )
  }, [] )

  return (

    <MenuWrapper 
      onMouseOver = {() => setIsOpen( true )}
      onMouseLeave = {() => setIsOpen( false )}
    >
      { children }
      {
        isOpen && menus &&
        (
        <LiDivWrapper>
        {
          menus?.map(( menu : menuData ) => {
            return (
              <li key = { menu.name } onClick= { menu?.onClick? menu?.onClick: ()=>navigateHandler( menu?.path ) }>{ menu?.name }</li>
            )
          })
        }
        </LiDivWrapper>
        )
      }
    </MenuWrapper>

  )
}

export default IconItem

interface menuData {
  name?: string,
  path?: string,
  onClick?: () => void
}

export type MenuItemProps = {
  menus?: menuData[] 
  children?: React.ReactElement
}