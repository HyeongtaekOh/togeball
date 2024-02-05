import { GameType }  from 'src/types'
import useStore from '../store'
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

  ${( props ) => props.index === 0 &&
    css`
    color: red;
    `
  }

  ${( props ) => props.index === 6 &&
    css`
    color: blue;
    `
  }
`

const ShowGamesWrapper = styled.div`
  display: flex;
  justify-content: center;
  gap: 2px;
  color: black;
  cursor: pointer;
  `


export const Day = ( props: DayProps ) =>{

    const { index, day, gamelist } = props
    const { updateMatch, updateModal } = useStore()

    const games = gamelist?.filter(( game ) => game.datetime.substring( 0,10 ) === format( day, 'yyyy-MM-dd' ))
    
    function selectMatch(e, game): void {
        const match = game
        updateMatch( match )
        updateModal()
    }

    return(
        <DayWrapper index={ index }>
            { format( day, 'd' )}
            { games?.map(( game ) => {
                return(
                    <ShowGamesWrapper onClick={(e) => selectMatch( e, game )}>
                        { game.homeClubName } VS { game.awayClubName }
                    </ShowGamesWrapper>
                )
            })}
        </DayWrapper>
    )
}


type DayProps = {
    children?: React.ReactNode
    index?: number,
    day?: string,
    gamelist?: GameType[]
}