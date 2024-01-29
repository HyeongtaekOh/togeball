import styled from 'styled-components'


const DayListWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  flex-direction: column;
  width: 100%;
  justify-content: space-around;
  margin-top: 20px;
`

const DayList = ( props ) => {
  
  const { list } = props

  const cal = list.reduce((acc, item, index) => {
    if( index % 7 === 0 ){
      acc.push( [] )
    }
    acc[acc.length - 1].push( item )
    return acc
  })
  
  return(
    <DayListWrapper></DayListWrapper>
  )
}

export default DayList