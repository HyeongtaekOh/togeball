import styled, { css } from 'styled-components'

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

const SubjectWrapper = styled.div`
    display: flex;
    justify-content: center;
    font-size: 24px;
    font-weight: bolder;
    margin-top: 20px;
`


const MyPage = ( () => {
 

    const board = { creatorName:'이운재', title: 'LG', createdTime: '2021-09-20', logo:'https://avatars.githubusercontent.com/u/10000000?v'}

    return (
        <MainLayout title='마이 페이지'>
            <HomeLayout>
            <Ticket/>
            <SubjectWrapper>
                내가 모집중인 채팅방
            </SubjectWrapper>
            <MainWrapper>
                {/* <ChatItem item={ chatItem }/> */}
            </MainWrapper>
                <SubjectWrapper>
                    내가 쓴 게시글
                </SubjectWrapper>
            <MainWrapper>
                <BoardItem board={ board }></BoardItem>
            </MainWrapper>
        </HomeLayout>
    </MainLayout>
    )
})

export default MyPage