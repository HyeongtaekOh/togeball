import styled from "styled-components"

const DateListWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: rows;
  width: 100%;
  justify-content: space-around;
  margin-top: 20px;
`

const DateList = () => {

  const date = [ 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'  ]

  return(
    <DateListWrapper>
      {
        date?.map( ( day, index ) => {
          return(
            <div key={ index }>
              { day }
            </div>
          )
        })
      }
    </DateListWrapper>
  )
}

export default DateList