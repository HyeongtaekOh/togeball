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

  const date = [ '일', '월', '화', '수', '목', '금', '토' ]

  return(
    <DateListWrapper>
      {
        date.map( ( day, index ) => {
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