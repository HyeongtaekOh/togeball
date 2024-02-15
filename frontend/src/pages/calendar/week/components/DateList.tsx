import styled from "styled-components"

const DateListWrapper = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  align-items: space-between;
  box-sizing: border-box;
  justify-content: space-around;
`

const DateList = () => {

  const date = [ 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'  ]

  return(
    <DateListWrapper>
      { date?.map(( day, index ) => {
        return(
          <div key={ index }>
            { day }
          </div>
      )})}
    </DateListWrapper>
  )
}

export default DateList