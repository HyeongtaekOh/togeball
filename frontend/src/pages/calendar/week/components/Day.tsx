import { format } from 'date-fns'
import { GameType }  from 'src/types'
import { styled, css } from 'styled-components'

const DayWrapper = styled.div<{ index: number }>`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 30%;
  min-height: 80px;
  border: 1px solid #DEDCEE;
  padding: 10px 30px 10px 10px;
  border-radius:7px;
  margin: 2px;
  
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
  flex-direction: column;
  justify-content: center;
  color: black;
  margin: 5px 5px 0 5px;
`

const Day = ( props: DayProps ) =>{

    const { index, day, gamelist } = props
    const games= gamelist?.filter(( game ) => game.datetime.substring(8,10) === format( day, 'dd' ))
    
    return(
        <DayWrapper index={ index }>
            <div style={{ marginRight: '40px' }}>
            { format( day, 'd' ) }
            </div>
            { games?.map(( game ) => {
                return(
                    <ShowGamesWrapper>
                        <div>
                        { game.homeClubName } VS { game.awayClubName }
                        </div>
                        {/* 나중에 로고 넘어오면 로고 크기보고 조절해야함 */}
                        <div style={{ margin: 'auto', marginTop: '10px' }}>  
                            { game.stadiumName }
                        </div>
                        <div style={{  margin: 'auto', marginTop: '5px' }}>
                            { game.datetime.substring(11, 16) }
                        </div>
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
