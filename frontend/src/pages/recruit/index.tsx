import { Select, MainLayout, HomeLayout, Pagination } from 'src/components'
import { useState } from 'react'
import { styled } from 'styled-components'

const SettingWrapper = styled.div`
    display: flex;
    justify-content: left;
    gap: 10px;
    margin-top: 50px;
`

const MatchBtn = styled.button`
    width: 200px;
    height: 36px;
    background-color: #DEDCEE; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 16px;
`

const FilterButton = styled.button`
    width: 140px;
    height: 36px;
    background-color: #DEDCEE; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 16px;
    margin-left: auto;
    margin-right: 10px;
`

const RecruitList = () => {

    const [teams, setTeams] = useState([
        { value: 'LG', name: 'LG' },
        { value: 'KT', name: 'KT' },
        { value: 'SSG', name: 'SSG' },
        { value: 'NC', name: 'NC' },
        { value: '두산', name: '두산' },
        { value: 'KIA', name: 'KIA' },
        { value: '롯데', name: '롯데' },
        { value: '삼성', name: '삼성' },
        { value: '한화', name: '한화' },
        { value: '키움', name: '키움' },
        { value: '팀무관', name: '팀무관' } 
    ])

    const [seats, setSeats] = useState([
        { value: 'cheeringSeat', name: '응원석' },
        { value: 'tableSeat', name: '테이블석' },
        { value: 'vipSeat', name: 'VIP석' },
        { value: 'outfieldSeat', name: '외야석' }
    ])

    const chats = [
        {
            "chatrooms" : [
              {
                 "participants": [
                   {
                      "userId": 'user1',
                      "email": 'user1@example.com',
                      "nickname": 'user1'
                   },
                 ],
                 "title": "타이틀!!",
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
          },
          {
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
          },
          {
            "chatrooms" : [
              {
                 "participants": [
                   {
                      "userId": 'user1',
                      "email": 'user1@example.com',
                      "nickname": 'user1'
                   },
                 ],
                 "title": "타이틀3!!",
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
          },
          {
            "chatrooms" : [
              {
                 "participants": [
                   {
                      "userId": 'user1',
                      "email": 'user1@example.com',
                      "nickname": 'user1'
                   },
                 ],
                 "title": "타이틀4!!",
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
          ,
        },
        {
          "chatrooms" : [
            {
               "participants": [
                 {
                    "userId": 'user1',
                    "email": 'user1@example.com',
                    "nickname": 'user1'
                 },
               ],
               "title": "타이틀5!!",
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
        },
        {
          "chatrooms" : [
            {
               "participants": [
                 {
                    "userId": 'user1',
                    "email": 'user1@example.com',
                    "nickname": 'user1'
                 },
               ],
               "title": "타이틀6!!",
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
        },
        {
          "chatrooms" : [
            {
               "participants": [
                 {
                    "userId": 'user1',
                    "email": 'user1@example.com',
                    "nickname": 'user1'
                 },
               ],
               "title": "타이틀7!!",
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
        ,
      }
    ]
    


    const FilterMine = () => {

      }

    
    
    return (
        <MainLayout title='직접 방 선택'>  
            <HomeLayout>
                <SettingWrapper>
                    <MatchBtn >경기를 선택하세요</MatchBtn>
                    <Select dataSource={ teams } placeholder='응원팀' background='#DEDCEE' width='100px' height='36px' ></Select>
                    <Select dataSource={ seats } placeholder='선호 좌석' background='#DEDCEE' width='120px' height='36px' ></Select>
                </SettingWrapper>
                <FilterButton>내 글만 보기</FilterButton>
                <div>
                    <Pagination chats={ chats } />
                </div>
            </HomeLayout>
        </MainLayout>
    )
}


export default RecruitList