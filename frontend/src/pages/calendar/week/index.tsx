import useDate from 'src/util/date'
import useStore from '../store'
import { Button, LeftIcon, RightIcon, Title } from 'src/components'
import { addDays, format, subDays } from 'date-fns'
import { DateList, DayList } from './components'
import { useEffect, useRef } from 'react'
import { useQuery } from 'react-query'
import { getGames } from '../api'
import { GameType } from 'src/types'
import styled from 'styled-components'

const CalendarWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  margin: auto;
  width: 100%;
  height: 100vh;
  align-items: center;
  padding: 10px;
  flex-direction: column;
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
  padding-bottom:7%;
`

const WeekCalendar = () => {

  const{ updateIsMonth } = useStore()
  const { currentMonth, setCurrentMonth, calculateDateRange } = useDate()
  const { weeksPassed, thisStartDate, thisEndDate } = calculateDateRange()

  const days = []
  
  let day = thisStartDate
  while( day < thisEndDate ){
    days.push( day )
    day = addDays( day, 1 )
  }

 


  const movePrevWeek = () => { setCurrentMonth( subDays( currentMonth, 7 )) }
  const moveNextWeek = () => { setCurrentMonth( addDays( currentMonth, 7 )) }
  const onMoveHandler = () => { 
    updateIsMonth() 
 }
 

  const param = {
    startDate: `${format(days[0], 'yyyy-MM-dd')}`,
    endDate: `${format(days[ days.length-1 ], 'yyyy-MM-dd')}`,
  }
  const { data: game, refetch } = useQuery<GameType[]>(['game', param ], () => getGames( param ))
  useEffect(() => { refetch() }, [ currentMonth, refetch ])
 
  
  return(
    <CalendarWrapper>
      <CalendarHeaderWrapper>
        <LeftIcon size= { 20 } onClick={ movePrevWeek }/>
        <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M') }월 { weeksPassed }주째</Title>
        <RightIcon size= { 20 } onClick={ moveNextWeek }/>
      </CalendarHeaderWrapper>
      <Button 
        type= 'parti' onClick={ onMoveHandler }
        style={{ marginTop : '-10px', alignSelf: 'flex-end' }}
      >
        월별 보기
      </Button>
      <CalendarBodyWrapper>
        <DateList />
        <DayList list= { days } games= { game }/>
      </CalendarBodyWrapper>
    </CalendarWrapper>
  )
}

export default WeekCalendar