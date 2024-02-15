import { HomeLayout, MainLayout, Title } from 'src/components'
import { HomeCard, TodayGameCard, RankCard } from './components'
import { useEffect, useState } from 'react'
import { ClubType } from 'src/types'
import { getClubs } from './api'
import styled from 'styled-components'
import { useQuery } from 'react-query'

const MainComponentWrapper = styled.div`
  display : flex;
  width : 100%;
  height : 100%;
  flex-direction : column;
  box-sizing : border-box;
  align-items : center;
  justify-content : center;
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


  const [ clubList, setClubList ] = useState<ClubType[]>([])

  const { data: clubs } = useQuery([ 'clubs' ], () => getClubs())

  useEffect(()=>{
    setClubList([])
    clubs?.forEach(( club )=>{

      const clubFilter = {
        clubId: club?.id,
        sponsorName: club?.sponsorName,
        clubLogo: club?.logo,
        ranking: club?.ranking
      }
      
      setClubList((prevClubList) => [ ...prevClubList, clubFilter ])
    })
  },[ clubs ])

  return (
    <MainLayout>
      <HomeLayout>
        <MainComponentWrapper>
        <TableWrapper>
          <tbody>
            <tr>
              <td rowSpan={ 2 } colSpan={ 2 } style={{ width: '60%' }}>
                <GameWrapper>
                  <Title color= '#746E6E' type= 'medium'>오늘의 경기 채팅방</Title>
                    <TodayGameCard/>
                  <Title color='#746E6E' type= 'medium'>현재 순위</Title>
                    <RankCard clubList = { clubList }/>
                </GameWrapper>
              </td>
              <td colSpan={ 2 }>
                <HomeCard 
                  title= '매칭하러 가기' 
                  type= 'main'
                  color= '#6A60A9'
                  path='/matching'>
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
                  color= '#FBD14B'
                  path='/recruit/post'>
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
                  color= '#FBD14B'
                  path='/todaygames'>
                  경기별 오픈 채팅방을<br/> 
                  확인해보세요!
                </HomeCard>
              </td>   
              <td>
                <HomeCard 
                  title= '메이트 채팅방' 
                  type= 'sub'
                  color= '#6A60A9'
                  path = '/recruit'
                >
                  다른 직관러의 모집 채팅방에<br/> 
                  참여해보세요.
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
                  자유롭게 글을 쓸 수 있는<br/> 
                  공간 입니다.
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