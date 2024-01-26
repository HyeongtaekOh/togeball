import { HomeLayout, MainLayout, OpenChatCard } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import { styled } from 'styled-components'

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

  const games = [ { gameId: 1, datetime: '20240101', homeClubName: '두산', awayClubName: '롯데' },
  { gameId: 2, datetime: '20240101', homeClubName: '두산', awayClubName: '롯데' },
  { gameId: 3, datetime: '20240101', homeClubName: '두산', awayClubName: '롯데' },
  { gameId: 0, datetime: '20240101', homeClubName: '두산', awayClubName: '롯데' },
  { gameId: 4, datetime: '20240101', homeClubName: '두산', awayClubName: '롯데 '},
    
]


  return (
    <MainLayout>
      <HomeLayout>

      <WrapgameWrapper>
      { games.length > 0 ? (        
        games.map(( game, index ) => (
          <OpenChatCard games = { game } key={ index }></OpenChatCard>
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