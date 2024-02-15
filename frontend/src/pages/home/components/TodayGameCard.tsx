import { useEffect, useRef, useState } from 'react'
import { GameType } from 'src/types'
import { LeftIcon, RightIcon, Title} from 'src/components'
import { GameItem } from './index'
import { getTodayGames } from '../api'
import { useMutation, useQuery } from 'react-query'
import styled from 'styled-components'
import { makeGameChat } from 'src/api'
import { useNavigate } from 'react-router-dom'

const GameCard = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  box-shadow: 1px 1px 1px lightGray;
  border-radius: 10px;
  width: 100%;
  height: 70px;
  padding: 0px 10px;
  border: 1px solid lightGray;
  align-items: center;
  justify-content: space-around;
  cursor: pointer;
  `
const TodayGameCard = () =>{

  const navigator = useNavigate()

  const { data: todayGames, isLoading } = useQuery( 'todayGames', getTodayGames )

  const indRef = useRef(0)
  const [ curGame, setCurGame ] = useState<GameType>( todayGames && todayGames[0] )

    
  const onClickRight = () => {
    indRef.current < todayGames.length - 1 && 
    ( indRef.current = indRef.current + 1 )
    setCurGame( todayGames[ indRef.current ] )
  }

  const onClickLeft = () => {
    indRef.current !==0 && 
    ( indRef.current = indRef.current - 1 )
    setCurGame( todayGames[ indRef.current ] )
  }
  
  useEffect(()=>{
    todayGames && setCurGame( todayGames[ indRef.current ] )
  }, [ todayGames ] )
  
  return(
    <GameCard>
      <LeftIcon onClick={ onClickLeft }/>
      { isLoading ? (
        <Title color='#746E6E' type='medium'>
          로딩 중...
        </Title>
      ) : curGame ? (
        <GameItem game={ curGame } />
      ) : (
        <Title color='#746E6E' type='medium'>
          오늘의 경기가 없습니다.
        </Title>
      )}
      <RightIcon onClick={ onClickRight }/>
    </GameCard>
  )
}

export default TodayGameCard