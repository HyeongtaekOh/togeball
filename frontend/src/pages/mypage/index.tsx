import styled from 'styled-components'
import { ChatItem, HomeLayout, MainLayout } from 'src/components'
import { Ticket } from './components'
import { getChats, getBoards } from './api';
import { useQuery } from 'react-query';

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
    margin-top: 20px;
    gap: 10px;
`


const MyPage = (() => {

  const { data: mychats } = useQuery([ 'mychats' ], () => getChats())
  const { data: boards } = useQuery([ 'myboards' ], () => getBoards())


  const mychatlist = mychats?.content
  const boardlist = boards?.content

    
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
                        { mychatlist && mychatlist?.map( chatItem => (
                         <ChatItem key={ chatItem.id } item={ chatItem } width='300px ' ></ChatItem>
                            ))}
                        <div id="chatObserver" style={{ height: "10px" }}></div>
                    </ItemListWrapper>
                    </div>
                    <div style={{ display: 'flex', flexDirection: 'column' }}>
                    <SubjectWrapper>
                        내가 쓴 게시글
                    </SubjectWrapper>
                    <ItemListWrapper>
                    { boardlist && boardlist?.map( BoardItem => (
                         <BoardItem key={ BoardItem.id } item={ BoardItem }></BoardItem>
                            ))}
                        <div id="boardObserver" style={{ height: "10px" }}></div>
                    </ItemListWrapper>
                    </div>
                </SeparateWrapper>
            </HomeLayout>
        </MainLayout>
    )
})

export default MyPage