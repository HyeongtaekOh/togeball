import styled from "styled-components"

const DateListWrapper = styled.div`
  display: flex;
  width: 100%;
  align-items: space-between;
  justify-content: space-around;
  margin-top: 20px;
  margin-bottom: -20px
`

const DateBorder = styled.div`
  margin: 5px 15px 10px 5px;
  border-bottom: 2px solid #DEDCEE;
  padding: 0px 10px 10px 10px;
`

export const DateList = () => {

    const date = [ 'SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'  ]
  
    return(
      <DateListWrapper>
        { date?.map( ( day, index ) => {
          return(
            <DateBorder key={ index }>
              { day }
            </DateBorder>
        )})}
      </DateListWrapper>
    )
  }
