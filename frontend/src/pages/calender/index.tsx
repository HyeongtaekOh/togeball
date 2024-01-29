import { useState } from 'react'
import { addDays, addMonths, endOfMonth, endOfWeek, format, startOfMonth, startOfWeek, subMonths } from 'date-fns'
import { HomeLayout, LeftIcon, MainLayout, RightIcon } from 'src/components'
import styled from 'styled-components'
import { DateList, DayList }  from './components/index'

const CalenderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  margin: auto;
  width: 500px;
  height: 500px;
  border: 1px solid lightgrey;
  align-items: center;
  padding: 10px;
  flex-direction: column
`

const Calender = () => {

  const [ currentMonth, setCurrentMonth ] = useState( new Date() )
  const date = [ '일', '월', '화', '수', '목', '금', '토' ]

  const monthStart = startOfMonth( currentMonth ) //1월1일
  const monthEnd = endOfMonth( monthStart ) //1월31일
  const startDate = startOfWeek( monthStart ) // 12월31일
  const endDate = endOfWeek( monthEnd ) //12월 3일

  const days = []
  
  let day = startDate
  while( day < endDate ){
    days.push( day )
    day = addDays( day, 1 )
  }

  console.log(days)


  const movePrevMonth = () =>{
    setCurrentMonth( subMonths( currentMonth, 1) )
  }

  const moveNextMonth = () => {
    setCurrentMonth( addMonths( currentMonth, 1) )
  }
 
  return(
    <MainLayout>
      <HomeLayout>
        <CalenderWrapper>
          <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
            <LeftIcon onClick={ movePrevMonth }/>
                { format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M') }월
            <RightIcon onClick={ moveNextMonth }/>
          </div>
          <DateList/>
          <DayList list = { days }/>
        </CalenderWrapper>
        
      </HomeLayout>
    </MainLayout>
  )
}

export default Calender