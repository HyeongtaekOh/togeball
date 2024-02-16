import { TagType } from 'src/types'
import { useEffect, useState } from 'react'
import useModel from 'src/pages/profile/store'
import styled from 'styled-components'

const TagBtnWrapper = styled.button<{ $bgColor: string, color: string }>`
  background-color: ${( props ) => props.$bgColor };
  max-width: 100px;
  min-width: 100px;
  height: 33px;
  border: none;
  color: ${( props ) => props.color };
  font-size: 10px;
  cursor: pointer;
  border-radius: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 11px;
  font-weight: bold;
`

const TagBtn = (props: TagBtnProps) => {

  const { children, myteam, item, mytags, stadiumFlag = false } = props

  const { team, setTeam, addStadiums, deleteStadiums } = useModel()
  const isSelect = myteam===item.content
  
  const isMine = mytags?.find( tag => tag.id === item.id ) !== undefined || isSelect
  const [ isClick, setIsClick ] = useState<boolean>( isMine )

  useEffect(() => {
    if ( stadiumFlag && isMine ) {
        addStadiums(item)
    }
    if( isMine && team == 0 ){
      setTeam(item.id)
    }
  }, [])
  
  const backgroundColor = isClick? '#6A60A9' : '#DEDCEE'
  const letterColor = isClick? 'white' : 'black'

  const changeColor = () => {
    if( stadiumFlag ){ 
      ( !isClick ? addStadiums( item ) : deleteStadiums( item ))
      setIsClick( !isClick )
    } else {
      if( team===0 ){
        setIsClick( !isClick )
        setTeam( item.id )
      } else {
        if( team===item.id ){
          setIsClick( !isClick )
          setTeam(0)
        }
      }
    }
  }

  return (
    <TagBtnWrapper onClick={ changeColor } $bgColor={ backgroundColor } color={ letterColor }>
      #{ children }
    </TagBtnWrapper>
  )
}

export default TagBtn

type TagBtnProps = {
    children?: string,
    myteam?: string,
    stadiumFlag?: boolean,
    mytags?: TagType[],
    item?: TagType,
}