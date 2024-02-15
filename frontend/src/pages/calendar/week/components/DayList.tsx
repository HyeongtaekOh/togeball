import { Day } from '../components';
import { styled, css } from 'styled-components'

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  width: 100%;
`

const DayList = ( props ) => {
  
  const { list, games } = props

  const cal = [];
  for(let i = 0; i < list.length; i++) {
      if(i % 7 === 0) {
          cal.push([]);
      }
      cal[cal.length - 1].push( list[i] );
  }
  
  return(
    <>
      {
        cal?.map(( day, index ) => {
          return(
            <WeekWrapper key={ index }>
              {
                day?.map( ( day, index ) => {
                  return(
                    <Day day= { day } index={ index } key={ index } 
                  gamelist= { games }/>
                  )
                })
              }
            </WeekWrapper>
          )
        })
      }
      </>
  )
}

export default DayList