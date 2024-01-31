import { format } from 'date-fns'
import { styled, css } from 'styled-components'

const DayWrapper = styled.div<{ index: number }>`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 160px;
  border: 1px solid #6A60A9;
  padding: 10px;
  border-radius:7px;
  margin: 2px;
  gap: 10px;

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

const ShowGames = styled.div`
  display: flex;
  justify-content: center;
  gap: 2px;
  color: black;
  cursor: pointer;
  `


export const Day = (props: DayProps) =>{

    const { index, day, gamelist } = props

    const games= gamelist.filter((game) => game.datetime.substring(8,10) === format(day, 'd'))
    
    function selectMatch(e): void {
        console.log("Select")
    }

    return(
        <DayWrapper index={ index }>
            { format(day, 'd')}
            { games.map((game, index) => {
                return(
                    <ShowGames onClick={ selectMatch }>
                        { game.homeClubName } VS { game.awayClubName }
                    </ShowGames>
                )
            })}
        </DayWrapper>
    )
}

type Game = {
        gameId : number,
        chatroomId : number,
        datetime : string,
        homeClubName : string,
        awayClubName : string,
        stadiumName : string
}

type DayProps = {
    children?: React.ReactNode
    index?: number,
    day?: string,
    gamelist?: Game[]
}