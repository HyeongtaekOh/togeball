import { Day } from '../components';
import { useQuery } from 'react-query';
import { styled } from 'styled-components'
import { getGames } from '../../api';
import { GameType } from 'src/types';
import { format } from 'date-fns';

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width: 100%;
  gap: 5px;
`

export const DayList = ( props ) => {
  
    const { days } = props
  
    const cal = [];
    for(let i = 0; i < days.length; i++) {
        if(i % 7 === 0) {
            cal.push([]);
        }
        cal[ cal.length - 1 ].push( days[i] );
    }

    const param = {
      startDate: `${ format( days[0], 'yyyy-MM-dd' )}`,
      endDate: `${ format( days[ days.length-1 ], 'yyyy-MM-dd' )}`,
    }

    const { data: game } = useQuery<GameType[]>([ 'game', param ], () => getGames( param ))

    return(
      <>
        { cal?.map(( week, index ) => {
            return( 
              <WeekWrapper key={ index }>
                { week?.map(( day, index ) => {
                    return(
                      <Day day= { day } index={ index } key={ index } 
                      gamelist= { game }/>
                )})}
              </WeekWrapper>
        )})}
      </>
    )
  }
