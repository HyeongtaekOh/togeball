import styled, { css } from 'styled-components'
import { getUserInfo } from 'src/api'
import { TagBtn, Title, HomeLayout, MainLayout, BoardItem, ChatItem } from 'src/components'
import { useNavigate } from 'react-router-dom'
import { Ticket } from './components'
import { useQuery } from 'react-query'


const MainWrapper= styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    
`

const MyPage = ( () => {
    
    const { data: userInfo } = useQuery([ 'user' ], () => getUserInfo())

//     https://i10a610.p.ssafy.io:8080/api/users/me
// {
//     "id": 2,
//     "email": "ssafy@ssafy.com",
//     "nickname": "hi1",
//     "gender": null,
//     "birthdate": null,
//     "phone": null,
//     "profileImage": null,
//     "tags": [
//         {
//             "id": 1,
//             "content": "LG",
//             "type": "PREFERRED_TEAM"
//         },
//         {
//             "id": 29,
//             "content": "내야일반석",
//             "type": "PREFERRED_SEAT"
//         },
//         {
//             "id": 34,
//             "content": "감독형",
//             "type": "CHEERING_STYLE"
//         },
//         {
//             "id": 38,
//             "content": "ENFP",
//             "type": "MBTI"
//         },
//         {
//             "id": 60,
//             "content": "2030대",
//             "type": "UNLABELED"
//         }
//     ],
//     "clubSponsorName": null,
//     "clubName": null,
//     "clubLogo": null
// }
    // const user = { id: userInfo.id, myTeam : userInfo.clubSponsorName+userInfo.clubName,  nickName: userInfo.nickname, tag: userInfo.tags, logo: userInfo.clubLogo }
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
                {/* <ChatItem item={ chatItem }/> */}
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