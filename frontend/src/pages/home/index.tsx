import { HomeLayout, MainLayout, Button,Title } from 'src/components'
import { HomeCard, TodayGameCard } from './components'
import { useState } from 'react'
import { GameType } from 'src/model/types/Game'
import styled from 'styled-components'

const GameWrpper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  box-shadow: 5px 5px 5px lightGray;
  border-radius: 10px;
  width: 100%;
  height: 100%;
  padding: 20px 40px ;
  gap: 10px;
`

const TableWrpper = styled.table`
  width: 100%;
  height: 60%;
  border-collapse: collapse;
  display: table;
  margin-bottom: 10px;
  th, td {
    display:table-cell; 
    vertical-align:middle;
    padding: 6px 10px;
  }
`

const Home = () => {

  const [ gameList, setGameList ] = useState<GameType[]>([
    {
      gameId: 0,
      chatroomId: 0,
      datetime: '20240101',
      homeClubName: 'lg',
      awayClubName: 'kt',
      stadiumName: '잠실',
    },{
      gameId: 0,
      chatroomId: 0,
      datetime: '20240101',
      homeClubName: 'a',
      awayClubName: 'b',
      stadiumName: '잠실',
    }
  ])

  return (
    <MainLayout>
      <HomeLayout>
        <TableWrpper>
          <tbody>
            <tr>
              <td rowSpan={2} colSpan={2} style={{ width: '60%' }}>
                <GameWrpper>
                   <Title color='#746E6E'>오늘의 경기</Title>
                    <TodayGameCard gameList = { gameList }/>
                   <Title color='#746E6E'>현재 순위</Title>
                </GameWrpper>
              </td>
              <td colSpan={2}>
                <HomeCard 
                  title= '매칭하러 가기' 
                  type= 'main'
                  color= '#6A60A9'>
                  TOGEBALL의 맞춤 알고리즘으로<br/> 
                  메이트를 구해줍니다.
                </HomeCard>
              </td>
            </tr>
            <tr>
              <td  colSpan={2}>
                <HomeCard 
                  title= '직접 메이트 모집하기' 
                  type= 'main'
                  color= '#FBD14B'>
                  자세한 필터 설정으로<br/> 
                  직관 메이트를 직접 모집해보세요.
                </HomeCard>
              </td>
            </tr>
          </tbody>
        </TableWrpper>
        <table>
        <tr>
          <td>
            <HomeCard 
              title= '경기별 오픈 채팅방' 
              type= 'sub'
              color= '#FBD14B'>
              오늘 경기 외에 모든<br/> 
              경기 일정을 확인해보세요.
            </HomeCard>
          </td>   
          <td>
            <HomeCard 
              title= '메이트 채팅방' 
              type= 'sub'
              color= '#6A60A9'>
              오늘 경기 외에 모든<br/> 
              경기 일정을 확인해보세요.
            </HomeCard>
          </td>
          <td>
            <HomeCard 
              title= '경기일정' 
              type= 'sub'
              color= '#DEDCEE'>
              오늘 경기 외에 모든<br/> 
              경기 일정을 확인해보세요.
            </HomeCard>
          </td>
          <td>
            <HomeCard 
              title= '자유게시판' 
              type= 'sub'
              color= '#FFFFFF'>
              오늘 경기 외에 모든<br/> 
              경기 일정을 확인해보세요.
            </HomeCard>
          </td>
        </tr>
        </table>
      </HomeLayout>
    </MainLayout>
  )

}

export default Home