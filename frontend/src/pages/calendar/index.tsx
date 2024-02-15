import { addDays, addMonths, format, subMonths } from 'date-fns'
import { Button, HomeLayout, LeftIcon, MainLayout, RightIcon, Title } from 'src/components'
import { DateList, DayList }  from './components/index'
import { useEffect } from 'react'
import { getGames } from './api'
import { useQuery } from 'react-query'
import useDate from 'src/util/date'
import useStore from './store'
import WeekCalendar from './week'
import styled from 'styled-components'

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

  const{ isMonth, updateIsMonth } = useStore()

  const { currentMonth, setCurrentMonth, calculateDateRange } = useDate()
  const { startDate, endDate } = calculateDateRange()

  const days = []
  let day = startDate

  while( day < endDate ){
    days.push( day )
    day = addDays( day, 1 )
  }

  const movePrevMonth = () =>{ setCurrentMonth( subMonths( currentMonth, 1) ) }
  const moveNextMonth = () => { setCurrentMonth( addMonths( currentMonth, 1) ) }
  const onMoveHandler = () => { 
    updateIsMonth() 
  }
  
  const param = {
    startDate: `${ format( days[0], 'yyyy-MM-dd' )}`,
    endDate: `${ format( days[ days.length-1 ], 'yyyy-MM-dd')}`,
  }
  const { data: game, refetch } = useQuery(['game', param ], () => getGames( param ))
  useEffect(() => { refetch() }, [ currentMonth, refetch ])
 
  return(
    <MainLayout title='경기 일정'>
      <HomeLayout>
        { isMonth ? 
        ( 
          <CalendarWrapper>
            <CalendarHeaderWrapper>
              <LeftIcon size= { 20 } onClick={ movePrevMonth }/>
              <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M' )}월</Title>
              <RightIcon size= { 20 } onClick={ moveNextMonth }/>
            </CalendarHeaderWrapper>
            <Button 
              type= 'parti' onClick={ onMoveHandler }
              style={{ marginTop : '-10px', alignSelf: 'flex-end' }}>
                주별 보기
            </Button>
            <DateList/>
            <DayList list = { days } games= { game }/>
          </CalendarWrapper>
        )
        : ( <WeekCalendar/> )
      }
      </HomeLayout>
    </MainLayout>
  )
}

export default Calendar