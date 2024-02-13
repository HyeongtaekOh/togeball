import styled from 'styled-components'
import { ChatItem, HomeLayout, MainLayout } from 'src/components'
import { Ticket } from './components'
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
    margin-top: 20px;
    gap: 10px;
`


const MyPage = (() => {

    const [ chats, setChats ] = useState([]);
    const [ chatPage, setChatPage ] = useState<number>(0);
    const [ chatLoading, setChatLoading ] = useState<boolean>( false );
    const chatObserver = useRef<IntersectionObserver>(); 

    const [ boards, setBoards ] = useState([]);
    const [ boardPage, setBoardPage ] = useState<number>(0);
    const [ boardLoading, setBoardLoading ] = useState<boolean>( false );
    const boardObserver = useRef<IntersectionObserver>(); 

    const [ newChatData, setNewChatData ] = useState([])
    const [ newBoardData, setNewBoardData ] = useState([])

    useEffect(()=>{
    },[])

    useEffect(() => {
        const fetchData = async () => {
            setChatLoading( true )
          try {
            const response = await getAxios( `/api/users/me/chatrooms?page=${chatPage}` )
            setNewChatData(response?.content)
            setChats(prevData => [ ...prevData, ...newChatData ])
            setChatLoading( false )
          } catch ( error ) {
            console.error( 'Error fetching data:', error )
            setChatLoading( false );
          }
        };
        fetchData();
      }, [ chatPage ]);

    useEffect(() => {
        const fetchData = async () => {
            setBoardLoading( true )
          try {
            const response = await getAxios( `/api/users/me/posts?page=${boardPage}` )
            setNewBoardData(response?.content)
            setBoards(prevData => [ ...prevData, ...newBoardData ])
            setBoardLoading( false )
          } catch ( error ) {
            console.error( 'Error fetching data:', error )
            setBoardLoading( false )
          }
        };
        fetchData()
    }, [ boardPage ])

    useEffect(() => {
        const options = {
          root: null,
          rootMargin: '0px',
          threshold: 1.0,
        }
    
        const handleObserver = ( entries ) => {
          const target = entries[0]
          target.isIntersecting && !chatLoading &&
            setChatPage( prevPage => prevPage + 1 )
        }
    
        chatObserver.current = new IntersectionObserver(handleObserver, options)
    
        chatObserver.current && chatLoading &&
            chatObserver.current.observe(document.getElementById('chatObserver'))
    
        return () => {
          chatObserver.current &&
            chatObserver.current.disconnect()
        }
    
    }, [ chatLoading ])

    useEffect(() => {
        const options = {
          root: null,
          rootMargin: '0px',
          threshold: 1.0,
        }
    
        const handleObserver = ( entries ) => {
          const target = entries[0];
          target.isIntersecting && !boardLoading &&
            setBoardPage( prevPage => prevPage + 1 )
        };
    
        boardObserver.current = new IntersectionObserver( handleObserver, options )
        boardObserver.current && boardLoading &&
            boardObserver.current.observe( document.getElementById( 'boardObserver' ))
        
        return () => {
          boardObserver.current &&
            boardObserver.current.disconnect()
        }
    
      }, [ boardLoading ])
    
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
                    <div style={{ display: 'flex', flexDirection: 'column'}}>
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