// dateUtils.js

import { useState } from 'react';
import { startOfMonth, endOfMonth, startOfWeek, 
  endOfWeek, differenceInCalendarWeeks, format, addDays } from 'date-fns';

const useDate = () => {
  const [ currentMonth, setCurrentMonth ] = useState(new Date());

  const calculateDateRange = () => {
    const monthStart = startOfMonth( currentMonth )
    const monthEnd = endOfMonth( monthStart )
    const startDate = startOfWeek( monthStart )
    const endDate = endOfWeek( monthEnd )

    const startMonth = startOfWeek( monthStart, { weekStartsOn: 1 })

    const thisStartDate = startOfWeek( currentMonth )
    const thisEndDate = addDays( thisStartDate, 7 )

    const weeksPassed = differenceInCalendarWeeks( currentMonth, startMonth ) + 1


    return { 
      monthStart, monthEnd, startDate, 
      endDate, weeksPassed,  thisStartDate, thisEndDate
    }
  }

  return { currentMonth, setCurrentMonth, calculateDateRange }
}
  
export default useDate