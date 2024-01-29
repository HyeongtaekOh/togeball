import { format } from 'date-fns';
import { styled, css } from 'styled-components'

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  width: 100%;
`

const DayWrapper = styled.div<{ index: number }>`
  width: 100%;
  height: 80px;
  border: 1px solid #DEDCEE;
  padding: 10px;
  border-radius:7px;
  margin: 2px;
  
  ${(props) => props.index === 0 &&
    css`
    color: red;
    `
  }

  ${(props) => props.index === 6 &&
    css`
    color: blue;
    `
  }

`

const DayList = ( props ) => {
  
  const { list } = props

  const cal = [];
  for(let i = 0; i < list.length; i++) {
      if(i % 7 === 0) {
          cal.push([]);
      }
      cal[cal.length - 1].push(list[i]);
  }
  
  return(
    <>
      {
        cal.map( ( day, index ) => {
          return(
            <WeekWrapper key={ index }>
              {
                day.map( ( day, index ) => {
                  return(
                    <DayWrapper key={ index } index= { index }>
                      { format( day, 'd' ) }
                    </DayWrapper>
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