import { useEffect, useState } from 'react'
import { GameType } from 'src/model/types/Game'
import { LeftIcon, RightIcon} from 'src/components'
import { GameItem } from './index'
import styled from 'styled-components'
import { useNavigate } from 'react-router-dom'

const GameCard = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  box-shadow: 1px 1px 1px lightGray;
  border-radius: 10px;
  width: 100%;
  height: 90px;
  padding: 0px 20px;
  border: 1px solid lightGray;
  align-items: center;
  justify-content: space-around;
  cursor: pointer;
  `
const TodayGameCard = ( props : GameCardProps ) =>{

  const { gameList } =  props

  const navigator = useNavigate();

  const [ curIndex, setCurIndex ] = useState<number>(0)
  const [ curGame, setCurGame ] = useState<GameType>( gameList[ curIndex ] )

  const onClickRight = () => {
    curIndex < gameList.length - 1 && setCurIndex(curIndex + 1)
  }

  const onClickLeft = () => {
    curIndex !==0 && setCurIndex(curIndex - 1)
  }

  useEffect(()=>{
    setCurGame( gameList[ curIndex ] )
  }, [ curIndex, gameList ] )

  return(

    <GameCard>
      <LeftIcon onClick={ onClickLeft }/>
          <GameItem game = { curGame } onClick= {() => navigator(`/select/${ curGame?.chatroomId }`)}/>
      <RightIcon onClick={ onClickRight }/>
    </GameCard>
    
  )
}

export default TodayGameCard;

type GameCardProps = {
  gameList? : GameType[]
}