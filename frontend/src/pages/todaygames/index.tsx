import { HomeLayout, MainLayout, OpenChatCard } from '../../components'
import { useNavigate } from 'react-router-dom'
import { styled } from 'styled-components'
import { getGames } from 'src/pages/calendar/api'
import { useQuery } from 'react-query'
import { GameType } from 'src/types'


const WrapgameWrapper = styled.div`
    display: flex;
    justify-content: left;
    width: 100%;
    flex-wrap: wrap;
    margin-left : 40px;
   
`
const NogameWrapper = styled.div`
  font-size: 50px;
  text-align: center;
  width: 100%;
  
`

const Todaygames = () => {

  const today = new Date()
  const year = today.getFullYear()
  const month = (today.getMonth() + 1).toString().padStart(2, '0')
  const day = today.getDate().toString().padStart(2, '0')
  

  const param = {
    // startDate: `${year}-${month}-${day}`,
    // endDate: `${year}-${month}-${day}`,
    // 경기날짜가 있는걸로 임의 지정
    startDate: '2024-04-06',
    endDate: '2024-04-06',
  }
  
  const { data: games } = useQuery<GameType[]>(['games', param ], () => getGames( param ))
  console.log(games)



  return (
    <MainLayout>
      <HomeLayout>

      <WrapgameWrapper>
      { games?.length > 0 ? (        
        games.map(( game, index ) => (
          <OpenChatCard games = { game } key={ index }/>
          ))     
          
          ): (
            
            <NogameWrapper>오늘의 경기가 없습니다</NogameWrapper>
            
            )   
}
             </WrapgameWrapper>
     
      
            </HomeLayout>
    </MainLayout>
  )

}

export default Todaygames