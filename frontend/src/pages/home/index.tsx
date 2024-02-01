import { HomeLayout, MainLayout, Button,Title } from 'src/components'
import { HomeCard, TodayGameCard, RankCard } from './components'
import { useEffect, useState } from 'react'
import { GameType, ClubType } from '@/types'
import styled from 'styled-components'
import { getAxios } from 'src/api/util'
import { getTodayGames } from './api/getTodayGames'

const MainComponentWrapper = styled.div`
  display : flex;
  width : 100%;
  height : 100%;
  flex-direction : column;
  box-sizing : border-box;
  align-items : center;
  `

const TableWrapper = styled.table`
  box-sizing: border-box;
  width: 100%;
  height: 50%;
  border-collapse: collapse;
  display: table;
  th, td {
    display:table-cell; 
    vertical-align:middle;
    padding: 6px 10px;
  }
  margin-bottom: 10px;
`

const GameWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
  width: 100%;
  height: 100%;
  padding: 5% 10%;
  padding-bottom: 20px;
  gap: 10px;
  justify-content: center;
  box-shadow: 0px 0px 5px lightGray;
`


const Home = () => {

  const [ gameList, setGameList ] = useState<GameType[]>( null )

  useEffect(()=>{

    const getGames = async() =>{
      const game : GameType[] = await getTodayGames()
      setGameList( game )
    }
    getGames()

  },[])

  const [ clubList, setClubList ] = useState<ClubType[]>([
    {
      clubId: 0,
      clubName: 'lg',
      sponsorName: 'lg',
      ranking: 1
    },
    {
      clubId: 1,
      clubName: 'kt',
      sponsorName: 'kt',
      ranking: 2
    },{
      clubId: 2,
      clubName: 'a',
      sponsorName: 'a',
      ranking: 3
    },{
      clubId: 3,
      clubName: 'b',
      sponsorName: 'b',
      ranking: 4
    },{
      clubId: 4,
      clubName: 'c',
      sponsorName: 'c',
      ranking: 6
    },{
      clubId: 5,
      clubName: 'c',
      sponsorName: 'c',
      ranking: 5
    },{
      clubId: 7,
      clubName: 'd',
      sponsorName: 'd',
      ranking: 7
    },{
      clubId: 8,
      clubName: 'e',
      sponsorName: 'e',
      ranking: 8
    },{
      clubId: 9,
      clubName: 'f',
      sponsorName: 'f',
      ranking: 9
    },{
      clubId: 10,
      clubName: 'g',
      sponsorName: 'g',
      ranking: 10
    }
  ])

  return (
    <MainLayout>
      <HomeLayout>
        <MainComponentWrapper>
        <TableWrapper>
          <tbody>
            <tr>
              <td rowSpan={ 2 } colSpan={ 2 } style={{ width: '60%' }}>
                <GameWrapper>
                  <Title color= '#746E6E' type= 'medium'>오늘의 경기</Title>
                    <TodayGameCard gameList = { gameList }/>
                  <Title color='#746E6E' type= 'medium'>현재 순위</Title>
                    <RankCard clubList = { clubList }/>
                </GameWrapper>
              </td>
              <td colSpan={ 2 }>
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
              <td colSpan={2}>
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
        </TableWrapper>
        <table style={{ width: '99%' }}>
          <tbody>
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
                  color= '#6A60A9'
                >
                  오늘 경기 외에 모든<br/> 
                  경기 일정을 확인해보세요.
                </HomeCard>
              </td>
              <td>
                <HomeCard 
                  title= '경기일정' 
                  type= 'sub'
                  color= '#DEDCEE'
                  path='/calendar'
                >
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
          </tbody>
        </table>
        </MainComponentWrapper>
      </HomeLayout>
    </MainLayout>
  )

}

export default Home