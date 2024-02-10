import styled from 'styled-components'
import { TagBtn, Title, HomeLayout, MainLayout, BoardItem, ChatItem } from 'src/components'
import { useNavigate } from 'react-router-dom'
import { Ticket } from './components'
import { useQuery } from 'react-query'
import { getMyBoards, getMyChatrooms } from './api'

const SeparateWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
`

const SubjectWrapper = styled.div`
    display: flex;
    justify-content: center;
    font-size: 24px;
    font-weight: bolder;
    margin-top: 20px;
`

const ItemListWrapper= styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
`


const MyPage = ( () => {

    const { data: myChatrooms } = useQuery([ 'chats' ], () => getMyChatrooms())
    const { data: myBoards } = useQuery([ 'boards' ], () => getMyBoards())
 
    const board = { creatorName:'이운재', title: 'LG', createdTime: '2021-09-20', logo:'https://avatars.githubusercontent.com/u/10000000?v'}

    return (
        <MainLayout title='마이 페이지'>
            <HomeLayout>
            <Ticket/>
            <SeparateWrapper>
            <div style={{ display: 'flex', flexDirection: 'column'}}>
            <SubjectWrapper>
                내가 모집중인 채팅방
            </SubjectWrapper>
            <ItemListWrapper>
                {/* <ChatItem item={ chatItem }/> */}
            </ItemListWrapper>
            </div>
            <div>
            <SubjectWrapper>
                내가 쓴 게시글
            </SubjectWrapper>
            <ItemListWrapper>
                {/* <BoardItem board={ board }></BoardItem> */}
            </ItemListWrapper>
            </div>
            </SeparateWrapper>
        </HomeLayout>
    </MainLayout>
    )
})

export default MyPage