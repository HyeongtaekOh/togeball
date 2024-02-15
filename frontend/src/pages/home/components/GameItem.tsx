import { GameType } from 'src/types';
import { Title } from 'src/components'
import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { useMutation, useQuery } from 'react-query';
import { partiChat, getGameChat } from 'src/api';

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
  margin-left: 30px;
  margin-right: 30px;
`

const InfomWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 200px;
  height: 80%;
  align-items: center;
  justify-content: center;
  `
  
const GameItem = ( props : GameItemProp ) => {
    
  const { game } = props
  const navigator = useNavigate()
  
  const param  = {
    gameId: game?.id
  }
  const { data: gameChat } = useQuery(['gameChat', param ], () => getGameChat( param ))
  
  const partiMutation = useMutation( partiChat, {
    onSuccess: () => {
      console.log(gameChat)
      navigator(`/openChat/${gameChat?.id}`)
    }
  })
  
  const goChat =  (  ) => {
    if( !localStorage.getItem('userId') ){
      alert(' 로그인 하세요 ')
      navigator('/login')
    } 
    else partiMutation.mutateAsync({ chatRoomId : game?.id })
  }
  

  return(
    <GameItemWrapper onClick={() => goChat() }>
        <img style={{ width : '25%' }} alt={ game?.homeClubName } src={ game?.homeClubLogo }/>
        <InfomWrapper>
          <Title>VS</Title>
          <Title type='small'>{ game?.datetime.substring(0,10) + ' ' + game?.datetime.substring(11,16) }</Title>
        </InfomWrapper>
        <img style={{ width : '25%' }} alt={ game?.awayClubName } src={ game?.awayClubLogo }/>
    </GameItemWrapper>
  )
}

export default GameItem;

type GameItemProp  = {
  game?: GameType
}

 