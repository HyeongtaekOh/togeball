import { format } from 'date-fns'
import styled from 'styled-components'


const DayListWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  width: 100%;
  justify-content: space-around;
  margin-top: 10px;
`

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: row;
`

const DayWrapper = styled.div<{ index: number }>`
  display: flex;
  box-sizing: border-box;
  width: 15%;
  height: 200px;
  text-align: right;
  border: 1px solid #DEDCEE;
  padding: 10px;
  border-radius:7px;
  margin: 2px;
  color: ${(props) => props.index === 0? 'red' : ( props.index === 6? 'blue': 'black')}
`

const DayList = ( props ) => {
  
  const { list } = props

  const cal = []
  for(let i = 0; i < list.length; i++) {
      i % 7 === 0  && cal.push([])
      cal[cal.length - 1].push( list[i] )
  }
  
  return(
    <DayListWrapper>
      { cal.map(( day, index ) => (
          <WeekWrapper key={ index }>
            { day.map( ( day, index ) => (
                  <DayWrapper key={ index } index= { index }>
                    { format( day, 'd' ) }
                  </DayWrapper>
            ))}
          </WeekWrapper>
      ))}
    </DayListWrapper>
  )
}

export default DayList