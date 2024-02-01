import { Day } from '../components';
import { useState } from 'react';
import { useQuery } from 'react-query';
import { styled } from 'styled-components'
import { getGames } from '../api';
import { GameType } from 'src/types';

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width: 100%;
  gap: 5px;
`

export const DayList = ( props ) => {
  
    const { list } = props
  
    const cal = [];
    for(let i = 0; i < list.length; i++) {
        if(i % 7 === 0) {
            cal.push([]);
        }
        cal[cal.length - 1].push(list[i]);
    }

    // const param = {
    //   startDate: `${format(days[0'yyyy-MM-dd')}`,
    //   endDate: `${format(days[days.length-1], 'yyyy-MM-dd')}`,
    // }

    const param = {
      startDate: '2024-03-01',
      endDate: '2024-04-30'
    }
    const { data: game } = useQuery<GameType[]>([ 'game', param ], () => getGames( param ))

    return(
      <>
        { cal.map(( week, index ) => {
            return( 
              <WeekWrapper key={ index }>
                { week.map(( day, index ) => {
                    return(
                      <Day day= { day } index={ index } key={ index } 
                      gamelist= { game }/>
                )})}
              </WeekWrapper>
        )})}
      </>
    )
  }
