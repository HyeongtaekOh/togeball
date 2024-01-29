import useDate from 'src/util/date'
import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button, HomeLayout, LeftIcon, MainLayout, RightIcon, Title } from 'src/components'
import styled from 'styled-components'
import { addDays, addMonths, format, subDays, subMonths } from 'date-fns'
import { DateList, DayList } from './components'

const CalenderWrapper = styled.div`
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

const CalenderHeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 30px; 
`

const CalenderBodyWrapper = styled.div`
  display: flex;
  width: 95%;
  height: 100%;
  alignItems: center;
  gap: 30px;
`

const WeekCalender = () => {

  const navigator = useNavigate()
 
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
    navigator( '/calender' )
  }
 
  return(
    <MainLayout>
      <HomeLayout>
        <CalenderWrapper>
          <CalenderHeaderWrapper>
            <LeftIcon size= {20} onClick={ movePrevWeek }/>
            <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M') }월 { weeksPassed }주째</Title>
            <RightIcon size= {20} onClick={ moveNextWeek }/>
          </CalenderHeaderWrapper>
          <Button 
            type="parti" 
            style={{ marginTop : '-10px', alignSelf: 'flex-end' }}
            onClick={ onMoveHandler }
          >
            월별 보기
          </Button>
          <CalenderBodyWrapper>
            <DateList/>
            <DayList list={ days }/>
          </CalenderBodyWrapper>
        </CalenderWrapper>
      </HomeLayout>
    </MainLayout>
  )
}

export default WeekCalender