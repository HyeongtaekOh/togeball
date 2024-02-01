import useDate from 'src/util/date'
import useStore from '../store'
import { Button, LeftIcon, RightIcon, Title } from 'src/components'
import styled from 'styled-components'
import { addDays, format, subDays } from 'date-fns'
import { DateList, DayList } from './components'
import { useState } from 'react'

const CalendarWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  margin: auto;
  width: 100%;
  height: 100vh;
  align-items: center;
  padding: 10px;
  flex-direction: column;
  margin-bottom : 100px;
`

const CalendarHeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 30px; 
`

const CalendarBodyWrapper = styled.div`
  display: flex;
  width: 95%;
  height: 100%;
  alignItems: center;
  gap: 30px;
`

const WeekCalendar = () => {

  const{ setIsMonth } = useStore();

 
  const { currentMonth, setCurrentMonth, calculateDateRange } = useDate();
  const { weeksPassed, thisStartDate, thisEndDate } = calculateDateRange();

  const days = []
  
  let day = thisStartDate
  while( day < thisEndDate ){
    days.push( day )
    day = addDays( day, 1 )
  }

  const movePrevWeek = () =>{
    setCurrentMonth( subDays( currentMonth, 7) )
  }

  const moveNextWeek = () => {
    setCurrentMonth( addDays( currentMonth, 7) )
  }

  const onMoveHandler = () => {
    setIsMonth()
  }

  const [games, setGames ] = useState([
    {
        games : [
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
    <CalendarWrapper>
      <CalendarHeaderWrapper>
        <LeftIcon size= { 20 } onClick={ movePrevWeek }/>
        <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M') }월 { weeksPassed }주째</Title>
        <RightIcon size= { 20 } onClick={ moveNextWeek }/>
      </CalendarHeaderWrapper>
      <Button type="parti" onClick={ onMoveHandler }
        style={{ marginTop : '-10px', alignSelf: 'flex-end' }}>
      월별 보기
      </Button>
      <CalendarBodyWrapper>
        <DateList/>
        <DayList list= { days } games= { gameItem }/>
      </CalendarBodyWrapper>
    </CalendarWrapper>
  )
}

export default WeekCalendar