import { Title } from 'src/components'
import { useCallback, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'

const MenuWrapper = styled.div`
  box-sizing: border-box;  
  min-width: 90px;
  cursor: pointer;
  height: auto;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`
const LiDivWrapper = styled.div`
  position: absolute;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin: 20px 0px;
  padding: 20px 10px;
  cursor: pointer;
  gap: 15px;
  z-index: 999;
  background-color: white;
  border-radius: 10px;
`

const MenuItem = ( props : MenuItemProps ) => {

  const { title, menus, path } = props

  const [ isOpen, setIsOpen ] = useState<boolean>( false )
  const navigator = useNavigate()

  const onClick = useCallback(( path : string ) => {
    console.log(path)
    path && navigator( path )
  }, [] )

  return (

    <MenuWrapper 
      onMouseOver = {() => setIsOpen( true )}
      onMouseLeave = {() => setIsOpen( false )}
      onClick = {() => onClick( path )}>
      <Title type='medium'>{ title }</Title>
      {
        isOpen && menus &&
        (
        <LiDivWrapper>
        {
          menus?.map(( menu : menuData ) => {
            return (
              <li key = { menu.name } onClick= {() => onClick( menu?.path ) }>{ menu?.name }</li>
            )
          })
        }
        </LiDivWrapper>
        )
      }
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