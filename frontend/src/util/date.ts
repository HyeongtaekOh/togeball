// dateUtils.js

import { useState } from 'react';
import { startOfMonth, endOfMonth, startOfWeek, 
  endOfWeek, differenceInCalendarWeeks, format, addDays } from 'date-fns';

const useDate = () => {
  const [ currentMonth, setCurrentMonth ] = useState(new Date());

  const calculateDateRange = () => {
    const monthStart = startOfMonth( currentMonth ) // 이번달 시작 날짜 예) 1일
    const monthEnd = endOfMonth( monthStart ) //이번달 끝 날짜 예) 31일
    const startDate = startOfWeek( monthStart ) //이번달 시작 날짜 주의 시작 날짜 예) 전 달 28일
    const endDate = endOfWeek( monthEnd ) //이번달 끝 날짜 주의 끝 날짜 예) 다음달 3일

    const startMonth = startOfWeek( monthStart, { weekStartsOn: 1 }) 

    const thisStartDate = startOfWeek( currentMonth ) // 이번주 시작 날짜
    const thisEndDate = addDays( thisStartDate, 7 ) // 이번주 끝 날짜

    const weeksPassed = differenceInCalendarWeeks( currentMonth, startMonth ) + 1 // 몇주차인지


    return { 
      monthStart, monthEnd, startDate, 
      endDate, weeksPassed,  thisStartDate, thisEndDate
    }
  }

  return { currentMonth, setCurrentMonth, calculateDateRange }
}
  
export default useDate