import { GameType } from 'src/types';
import { Title } from 'src/components'
import styled from 'styled-components';

const GameItemWrapper = styled.div`
  display: flex;
  flex-direction: row;
  border-radius: 10px;
  width: 80%;
  height: 80px;
  align-items: center;
  justify-content: space-around;
  gap: 5px;
  text-align: center;
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

  console.log(game)

  return(
    <GameItemWrapper onClick={ onClick }>
        {/* <img style={{ width : '30%' }} alt={ game?.homeClubName }></img> */}
        <Title style={{ width : '30%' }}>{ game?.homeClubName }</Title> 
        <InfomWrapper>
          <Title>VS</Title>
          <Title type='small'>{ game?.datetime }</Title>
        </InfomWrapper>
        {/* <img style={{ width : '30%' }} alt={ game?.awayClubName }></img> */}
        <Title style={{ width : '30%' }}>{ game?.awayClubName }</Title> 
    </GameItemWrapper>
  )
}

export default GameItem;

type GameItemProp  = {
  game?: GameType
  onClick? : () => void
}

 