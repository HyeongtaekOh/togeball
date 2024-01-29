import { useNavigate } from 'react-router-dom'
import { addDays, addMonths, format, subMonths } from 'date-fns'
import { Button, HomeLayout, LeftIcon, MainLayout, RightIcon, Title } from 'src/components'
import { DateList, DayList }  from './components/index'
import useDate from 'src/util/date'
import styled from 'styled-components'

const CalenderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  flex-direction: column
`

const CalenderHeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 30px; 
  margin-top: 10px;
`

const Calender = () => {

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
 
  return(
    <MainLayout>
      <HomeLayout>
        <CalenderWrapper>
          <CalenderHeaderWrapper>
            <LeftIcon size= {20} onClick={ movePrevMonth }/>
                <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M' )}월</Title>
            <RightIcon size= {20} onClick={ moveNextMonth }/>
          </CalenderHeaderWrapper>
          <Button 
            type="parti" 
            style={{ marginTop : '-10px', alignSelf: 'flex-end' }}
            onClick={ onMoveHandler }
          >
            주별 보기
          </Button>
          <DateList/>
          <DayList list = { days }/>
        </CalenderWrapper>
      </HomeLayout>
    </MainLayout>
  )
}

export default Calender