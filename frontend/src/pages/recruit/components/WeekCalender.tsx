import useDate from 'src/util/date'
import { LeftIcon, RightIcon, Title } from "src/components";
import { DateList, DayList } from '../components'
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import { addDays, addMonths, format, subDays, subMonths } from "date-fns";

const CalenderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  margin-top: 20px  ;
  width: 100%;
  height: 100%;
  align-items: center;
  padding: 10px;
  flex-direction: column;
`

const CalenderHeaderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`

const CalenderBodyWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 95%;
  alignItems: center;
  gap: 30px;
`

export const WeekCalender = () => {

    const { currentMonth, setCurrentMonth, calculateDateRange } = useDate();
    const { weeksPassed, thisStartDate, thisEndDate } = calculateDateRange();
  
    const days = []
    
    let day = thisStartDate
    while( day < thisEndDate ){
      days.push( day )
      day = addDays( day, 1 )
    }
    const movePrevWeek = () =>{
        setCurrentMonth( subDays( currentMonth, 7 ))
      }
    
      const moveNextWeek = () => {
        setCurrentMonth( addDays( currentMonth, 7 ))
      }


    return (
      <>
        <CalenderWrapper>
            <CalenderHeaderWrapper>
                <LeftIcon size= { 20 } onClick={ movePrevWeek }/>
                <Title>{ format( currentMonth, 'yyyy' )}년 { format( currentMonth, 'M' ) }월 { weeksPassed }주</Title>
                <RightIcon size= { 20 } onClick={ moveNextWeek }/>
            </CalenderHeaderWrapper>
            <CalenderBodyWrapper>
            <DateList/>
            <DayList list={ days }/>
            </CalenderBodyWrapper>
        </CalenderWrapper>
      </>
    )
}
