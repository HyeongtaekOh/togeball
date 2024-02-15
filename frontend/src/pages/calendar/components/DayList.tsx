import { Day } from '../components';
import styled from 'styled-components'


const DayListWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  width: 100%;
  justify-content: space-around;
  margin-top: 10px;
  padding-bottom: 50px;
`

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: row;
`

const DayList = ( props ) => {
  
  const { list, games } = props

  const cal = []
  for(let i = 0; i < list.length; i++) {
      i % 7 === 0  && cal.push([])
      cal[ cal.length - 1 ].push( list[i] )
  }
  
  return(
    <DayListWrapper>
      { cal?.map(( day, index ) => (
          <WeekWrapper key={ index }>
            { day?.map(( day, index ) => (
                  <Day day= { day } index={ index } key={ index } 
                  gamelist= { games }/>
            ))}
          </WeekWrapper>
      ))}
    </DayListWrapper>
  )
}

export default DayList