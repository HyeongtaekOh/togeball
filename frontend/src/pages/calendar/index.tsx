import { useNavigate } from 'react-router-dom'
import { addDays, addMonths, format, subMonths } from 'date-fns'
import { Button, HomeLayout, LeftIcon, MainLayout, RightIcon, Title } from 'src/components'
import { DateList, DayList }  from './components/index'
import useDate from 'src/util/date'
import styled from 'styled-components'
import { useState } from 'react'

const CalendarWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  flex-direction: column
`

const CalendarHeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 30px; 
  margin-top: 10px;
`

const Calendar = () => {

  const navigator = useNavigate()

  const { currentMonth, setCurrentMonth, calculateDateRange } = useDate()
  const { startDate, endDate } = calculateDateRange()

  const days = []
  
  let day = startDate
  while( day < endDate ){
    days.push( day )
    day = addDays( day, 1 )
  }

  const movePrevMonth = () =>{
    setCurrentMonth( subMonths( currentMonth, 1) )
  }

  const moveNextMonth = () => {
    setCurrentMonth( addMonths( currentMonth, 1) )
  }

  const onMoveHandler = () => {
    navigator( 'week' )
  }

  const [games, setGames] = useState([
    {
        games : [
          { 
            gameId : 30,
            chatroomId : 12,
            datetime : '2024-02-01 18:00:00',
            homeClubName : '기아',
            awayClubName : '넥슨',
            stadiumName : '!!스타디움'
          },
          { 
            gameId : 31,
            chatroomId : 1111,
            datetime : '2024-02-01 18:00:00',
            homeClubName : '기아',
            awayClubName : '넥슨',
            stadiumName : '!!스타디움'
          },
          { 
            gameId : 32,
            chatroomId : 1222,
            datetime : '2024-02-01 18:00:00',
            homeClubName : '기아',
            awayClubName : '넥슨',
            stadiumName : '!!스타디움'
          },
          { 
            gameId : 33,
            chatroomId : 1234,
            datetime : '2024-03-28 18:00:00',
            homeClubName : '기아',
            awayClubName : '넥슨',
            stadiumName : '!!스타디움'
          },
          { 
            gameId : 34,
            chatroomId : 1236,
            datetime : '2024-03-28 18:00:00',
            homeClubName : '삼성',
            awayClubName : '롯데',
            stadiumName : '부산사직구장'
          },
          { 
            gameId : 35,
            chatroomId : 1237,
            datetime : '2024-03-28 18:00:00',
            homeClubName : '삼성2',
            awayClubName : '롯데2',
            stadiumName : '부산사직구장'
          },
          { 
            gameId : 36,
            chatroomId : 1238,
            datetime : '2024-03-28 18:00:00',
            homeClubName : '삼성3',
            awayClubName : '롯데3',
            stadiumName : '부산사직구장'
          },
          { 
            gameId : 37,
            chatroomId : 1235,
            datetime : '2024-03-28 18:00:00',
            homeClubName : '삼성4',
            awayClubName : '롯데4',
            stadiumName : '부산사직구장'
          },
          { 
            gameId : 40,
            chatroomId : 1235,
            datetime : '2024-03-29 18:00:00',
            homeClubName : '삼성4',
            awayClubName : '롯데4',
            stadiumName : '부산사직구장'
          }
        ]
    }
])

const gameItem = games[0].games
 
  return(
    <MainLayout>
      <HomeLayout>
        <CalendarWrapper>
          <CalendarHeaderWrapper>
            <LeftIcon size= {20} onClick={ movePrevMonth }/>
                <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M' )}월</Title>
            <RightIcon size= {20} onClick={ moveNextMonth }/>
          </CalendarHeaderWrapper>
          <Button 
            type="parti" 
            style={{ marginTop : '-10px', alignSelf: 'flex-end' }}
            onClick={ onMoveHandler }
          >
            주별 보기
          </Button>
          <DateList/>
          <DayList list = { days } games= { gameItem }/>
        </CalendarWrapper>
      </HomeLayout>
    </MainLayout>
  )
}

export default Calendar