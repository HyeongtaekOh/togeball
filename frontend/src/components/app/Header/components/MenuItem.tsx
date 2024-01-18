import { Title } from 'src/components'
import { useState } from "react"
import styled from "styled-components"

const MenuWrapper = styled.div`
  box-sizing: border-box;  
  width: 100px;
  cursor: pointer;
`

const MenuItem = ( props : MenuItemProps ) => {

  const { title, menuDatas } = props
  const [ isOpen, setIsOpen ] = useState(false)

  return(

    <MenuWrapper 
      onMouseOver = {() => { setIsOpen( true )}}
      onMouseOut = {() => setIsOpen( false )}>
      <Title type='medium'>{ title }</Title>
      {
      isOpen && menuDatas &&
        (
          menuDatas.map((menu, index) => {
            console.log(index)
            return(
              <div>{ menu.name }</div>
            )
          })
        )
      }
    </MenuWrapper>

  )
}

export default MenuItem

type menuData = {
  name?: string,
  path?: string
}

type MenuItemProps = {
  title?: string,
  path?: string,
  menuDatas?: menuData[] 
}