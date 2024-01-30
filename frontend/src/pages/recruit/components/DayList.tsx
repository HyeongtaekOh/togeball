import { format } from 'date-fns';
import { useState } from 'react';
import { styled, css } from 'styled-components'

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width: 100%;
  gap: 5px;
`

const DayWrapper = styled.div<{ index: number }>`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 160px;
  border: 1px solid #6A60A9;
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

export const DayList = ( props ) => {
  
    const { list } = props
  
    const cal = [];
    for(let i = 0; i < list.length; i++) {
        if(i % 7 === 0) {
            cal.push([]);
        }
        cal[cal.length - 1].push(list[i]);
    }

    const [games, setGames] = useState([
      {
          games : [
            { 
              gameId : 33,
              chatroomId : 1234,
              datetime : '2024-03-24 18:00:00',
              homeClubName : '기아',
              awayClubName : '넥슨',
              stadiumName : '!!스타디움'
            },
            { 
              gameId : 34,
              chatroomId : 1236,
              datetime : '2024-03-24 18:00:00',
              homeClubName : '삼성',
              awayClubName : '롯데',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 35,
              chatroomId : 1237,
              datetime : '2024-03-24 18:00:00',
              homeClubName : '삼성2',
              awayClubName : '롯데2',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 36,
              chatroomId : 1238,
              datetime : '2024-03-24 18:00:00',
              homeClubName : '삼성3',
              awayClubName : '롯데3',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 37,
              chatroomId : 1235,
              datetime : '2024-03-24 18:00:00',
              homeClubName : '삼성4',
              awayClubName : '롯데4',
              stadiumName : '부산사직구장'
            }
          ]
      }
  ])
    
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
