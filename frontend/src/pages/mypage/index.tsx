import styled, { css } from 'styled-components'
import { Tag, TagBtn, Title, HomeLayout, MainLayout, BoardItem, ChatItem } from 'src/components'
import { useNavigate } from 'react-router-dom'
import Ticket from './components'


const MainWrapper= styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    
`

const MyPage = ( () => {
    
    
    const user = { id: 'test01', myTeam : 'LG 트윈스', stadium : '고척 돔', nickName: '플레권', tag: ['#Estp', '#응원가형', '#시즌권보유', '#자차보유','#플레권'], logo: ''}
    const board = { creatorName:'이운재', title: 'LG', createdTime: '2021-09-20', logo:'https://avatars.githubusercontent.com/u/10000000?v'}
    const chatItem =    {
        "chatrooms" : [
          {
             "participants": [
               {
                  "userId": 'user1',
                  "email": 'user1@example.com',
                  "nickname": 'user1'
               },
             ],
             "title": "타이틀2!!",
             "tags": ["#LG", "#KT", "#SSG", "#NC", "#KIA",],
             "manager": {
                  "userId": 3,
                  "email": 'tmp@gmail.com',
                  "nickname": 'tmp'
              },
              "gameId": 23,
              "capacity": 4,
              "cheeringTeamImageUrl": "httpURL"
              }
          ],
          "totalCount": 20,
          "pageSize": 10,
          "pageNo": 1
      }
   
    return (
        <MainLayout title='마이 페이지'>
            <Ticket/>
                <Title type='large'>
                    내가 모집중인 채팅방
                    </Title>
            <MainWrapper>
                <ChatItem item={ chatItem } content='gigigigi'></ChatItem>
                </MainWrapper>
                <div style={{ margin: '50px' }}></div>
                <Title type='large'>
                    내가 쓴 게시글
                </Title>
            <MainWrapper>

                <BoardItem board={ board }></BoardItem>
            </MainWrapper>

    </MainLayout>
    )
})

export default MyPage