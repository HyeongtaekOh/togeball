import { Day } from '../components';
import { format } from 'date-fns';
import { useState } from 'react';
import { styled } from 'styled-components'

const WeekWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width: 100%;
  gap: 5px;
`

export const DayList = ( props ) => {
  
    const { list, isOpen } = props
  
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
              datetime : '2024-03-28 18:00:00',
              homeClubName : '기아',
              awayClubName : '넥슨',
              stadiumName : '!!스타디움'
            },
            { 
              gameId : 34,
              chatroomId : 1236,
              datetime : '2024-03-28 18:00:00',
              homeClubName : '삼성',
              awayClubName : '롯데',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 35,
              chatroomId : 1237,
              datetime : '2024-03-28 18:00:00',
              homeClubName : '삼성2',
              awayClubName : '롯데2',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 36,
              chatroomId : 1238,
              datetime : '2024-03-28 18:00:00',
              homeClubName : '삼성3',
              awayClubName : '롯데3',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 37,
              chatroomId : 1235,
              datetime : '2024-03-28 18:00:00',
              homeClubName : '삼성4',
              awayClubName : '롯데4',
              stadiumName : '부산사직구장'
            },
            { 
              gameId : 40,
              chatroomId : 1235,
              datetime : '2024-03-29 18:00:00',
              homeClubName : '삼성4',
              awayClubName : '롯데4',
              stadiumName : '부산사직구장'
            }
          ]
      }
  ])
  
  const gameItem = games[0].games
  
    return(
      <>
        {
          cal.map(( day, index ) => {
            return( 
              <WeekWrapper key={ index }>
                {
                  day.map(( day, index ) => {
                    return(
                      <Day day= { day } index={ index } key={ index } 
                      gamelist= { gameItem }/>
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
