import { useEffect, useState } from 'react'
import { GameType } from 'src/types'
import { LeftIcon, RightIcon, Title} from 'src/components'
import { GameItem } from './index'
import styled from 'styled-components'
import { getTodayGames } from '../api/getTodayGames'
import { useQuery } from 'react-query'

const GameCard = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  box-shadow: 1px 1px 1px lightGray;
  border-radius: 10px;
  width: 100%;
  height: 70px;
  padding: 0px 20px;
  border: 1px solid lightGray;
  align-items: center;
  justify-content: space-around;
  cursor: pointer;
  `
const TodayGameCard = () =>{

  const { data: todayGames, isLoading } = useQuery( 'todayGames', getTodayGames )
  
  const [ curIndex, setCurIndex ] = useState<number>(0)
  const [ curGame, setCurGame ] = useState<GameType>( todayGames && todayGames[0] )

  const onClickRight = () => {
    curIndex < todayGames.length - 1 && 
    setCurIndex(curIndex + 1)
  }

  const onClickLeft = () => {
    curIndex !==0 && setCurIndex(curIndex - 1)
  }

  useEffect(()=>{
    todayGames && setCurGame( todayGames[ curIndex ] )
  }, [ curIndex, todayGames ] )

  return(
    <GameCard>
      <LeftIcon onClick={ onClickLeft }/>
      { isLoading ? (
        <Title color="#746E6E" type="medium">
          로딩 중...
        </Title>
      ) : todayGames && todayGames.length > 0 ? (
        <GameItem
          game={curGame}
          // onClick={() => navigator(`/select/${curGame?.chatroomId}`)}
        />
      ) : (
        <Title color="#746E6E" type="medium">
          오늘의 경기가 없습니다.
        </Title>
      )}
      <RightIcon onClick={ onClickRight }/>
    </GameCard>
  )
}

export default TodayGameCard