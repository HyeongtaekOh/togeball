import { Select, MainLayout, BoardList, HomeLayout } from 'src/components';
import { useState } from 'react';
import { styled } from 'styled-components'

const SettingWrapper = styled.div`
    display: flex;
    justify-content: left;
    gap: 10px;
    margin-top: 50px;
    margin-left: -80px;
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

const RecruitPost = () => {

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

    const board = { creatorName:'이운재', title: 'LG', createdTime: '2021-09-20', logo:'https://avatars.githubusercontent.com/u/10000000?v'}


    const FilterMine = () => {

      }

    return (
        <MainLayout title='직접 방 선택'>  
            <HomeLayout>
                <SettingWrapper>
                    <MatchBtn >경기를 선택하세요</MatchBtn>
                    <Select dataSource={ teams } placeholder='응원팀' background='#DEDCEE' width='100px'></Select>
                    <Select dataSource={ seats } placeholder='선호 좌석' background='#DEDCEE' width='120px'></Select>
                </SettingWrapper>
                <FilterButton>내 글만 보기</FilterButton>
                <BoardList board={ board } type='sub'></BoardList> 
            </HomeLayout>
        </MainLayout>
    )
}


export default RecruitPost