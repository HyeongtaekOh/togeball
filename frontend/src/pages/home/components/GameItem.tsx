import { GameType } from "src/model/types/Game";
import { Title } from 'src/components'
import styled from "styled-components";

const GameItemWrapper = styled.div`
  display: flex;
  flex-direction: row;
  border-radius: 10px;
  width: 80%;
  height: 80px;
  align-items: center;
  justify-content: space-around;
`

const InfomWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 200px;
  height: 80%;
  align-items: center;
  gap: 10px;
`

const GameItem = ( props : GameItemProp ) => {

  const { game, onClick } = props;

  const { 
    gameId, chatroomId, datetime, 
    homeClubName, awayClubName, stadiumName } = game

  return(
    <GameItemWrapper onClick={ onClick }>
        <img alt={ homeClubName }></img>
        <InfomWrapper>
          <Title>VS</Title>
          <Title type="small">{ datetime }</Title>
        </InfomWrapper>
        <img alt={ awayClubName }></img>
    </GameItemWrapper>
  )
}

export default GameItem;

type GameItemProp  = {
  game?: GameType
  onClick? : () => void
}

 