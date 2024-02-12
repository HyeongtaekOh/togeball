import styled from 'styled-components'
import { ChatItem, HomeLayout, MainLayout } from 'src/components'
import { useNavigate } from 'react-router-dom'
import { Ticket } from './components'
import { useQuery } from 'react-query'
import { useState, useEffect, useRef } from 'react';
import { getAxios } from 'src/api/util'

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


const MyPage = (() => {
    const [ chats, setChats ] = useState([]);
    const [ chatPage, setChatPage ] = useState<number>(0);
    const [ chatLoading, setChatLoading ] = useState<boolean>(false);
    const chatObserver = useRef<IntersectionObserver>(); 

    const [ boards, setBoards ] = useState([]);
    const [ boardPage, setBoardPage ] = useState<number>(0);
    const [ boardLoading, setBoardLoading ] = useState<boolean>(false);
    const boardObserver = useRef<IntersectionObserver>(); 

    useEffect(() => {
        const fetchData = async () => {
            setChatLoading(true);
          try {
            const response = await getAxios(`/api/users/me/chatrooms?page=${chatPage}`)
            const newData = response?.content
            setChats(prevData => [ ...prevData, ...newData ])
            setChatLoading(false);
          } catch (error) {
            console.error('Error fetching data:', error);
            setChatLoading(false);
          }
        };
        fetchData();
      }, [chatPage]);

    useEffect(() => {
        const fetchData = async () => {
            setBoardLoading(true);
          try {
            const response = await getAxios(`/api/users/me/posts?page=${boardPage}`)
            const newData = response?.content
            setBoards(prevData => [...prevData, ...newData])
            setBoardLoading(false);
          } catch (error) {
            console.error('Error fetching data:', error);
            setBoardLoading(false);
          }
        };
        fetchData();
    }, [boardPage]);

    useEffect(() => {
        const options = {
          root: null,
          rootMargin: '0px',
          threshold: 1.0,
        };
    
        const handleObserver = (entries) => {
          const target = entries[0];
          if (target.isIntersecting && !chatLoading) {
            setChatPage(prevPage => prevPage + 1);
          }
        };
    
        chatObserver.current = new IntersectionObserver(handleObserver, options);
    
        if (chatObserver.current && chatLoading) {
            chatObserver.current.observe(document.getElementById('chatObserver')); 
        }
    
        return () => {
          if (chatObserver.current) {
            chatObserver.current.disconnect(); 
          }
        };
    
    }, [chatLoading]);

    useEffect(() => {
        const options = {
          root: null,
          rootMargin: '0px',
          threshold: 1.0,
        };
    
        const handleObserver = (entries) => {
          const target = entries[0];
          if (target.isIntersecting && !boardLoading) {
            setBoardPage(prevPage => prevPage + 1);
          }
        };
    
        boardObserver.current = new IntersectionObserver(handleObserver, options);
    
        if (boardObserver.current && boardLoading) {
            boardObserver.current.observe(document.getElementById('boardObserver')); 
        }
    
        return () => {
          if (boardObserver.current) {
            boardObserver.current.disconnect(); 
          }
        };
    
      }, [boardLoading]);
    
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
                        { chats && chats.map(chatItem => (
                         <ChatItem key={chatItem.id} item={ chatItem } width='130% ' ></ChatItem>
                            ))}
                        <div id="chatObserver" style={{ height: "10px" }}></div>
                    </ItemListWrapper>
                    </div>
                    <div>
                    <SubjectWrapper>
                        내가 쓴 게시글
                    </SubjectWrapper>
                    <ItemListWrapper>
                    { boards && boards.map(BoardItem => (
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