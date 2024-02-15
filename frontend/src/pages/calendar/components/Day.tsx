import { Title } from 'src/components'
import { format } from 'date-fns'
import { GameType }  from 'src/types'
import styled from 'styled-components'

const DayWrapper = styled.div<{ index: number }>`
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  width: 100%;
  height: 200px;
  border: 1px solid #DEDCEE;
  padding: 10px 5px;
  border-radius:7px;
  margin: 2px;
  gap: 9px;
  color: 
  ${( props ) => props.index === 0 ? 'red' : ( props.index === 6 ? 'blue': 'black' )}
`
const ShowGamesWrapper = styled.div`
  display: flex;
  justify-content: center;
  color: black;
  align-items: center;
  gap: 8px;
  `
  const ImgWrapper = styled.img`
    display: flex;
    width: 23%;
  `

const Day = ( props: DayProps ) =>{

    const { index, day, gamelist } = props
    const games = gamelist?.filter(( game ) => game.datetime.substring(5, 10) === format( day, 'MM-dd' ))
    
    return(
        <DayWrapper index={ index }>
            { format( day, 'd' ) }
            { games?.map(( game ) => {
                return(
                    <ShowGamesWrapper>
                        <ImgWrapper src= { game.homeClubLogo }/>
                        <Title style={{fontSize:'9px'}}>VS</Title> 
                        <ImgWrapper src= { game.awayClubLogo }/>
                    </ShowGamesWrapper>
                )
            })}
        </DayWrapper>
    )
}

export default Day;


type DayProps = {
    children?: React.ReactNode
    index?: number,
    day?: string,
    gamelist?: GameType[]
}