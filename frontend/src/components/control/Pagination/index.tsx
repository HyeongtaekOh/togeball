import { LeftIcon, RightIcon } from 'src/components/icon'
import { useEffect, useState } from 'react'
import { ChatItem } from'src/components'
import styled from 'styled-components'

const PageWrapper = styled.div`
    display: flex;
    margin-top: 20px;
    justify-content: center;
    align-items: center;
`
const UnreadWrapper = styled.div`
  display: flex;
  position: absolute;
  width: 25px;
  height: 25px;
  background-color: #6A60A9;
  border-radius: 50%;
  justify-content: center;
  align-items: center;
  transform: translate(-5px, -5px);
  color: WHITE;
`

const Pagination = ( props ) => {
  
  const { match, chats, type = 'all', team, chatContent, setChatContent } = props

  const itemsPerPage = type === 'my'? 4 : 5
  const [ currentPage, setCurrentPage ] = useState( 1 )
  const [ totalPages, setTotalPages ] = useState( Math.ceil( chats?.totalElements / itemsPerPage ))

  useEffect(()=>{
    if( type === 'my' ) return

    setChatContent( chats?.content )
    if( chatContent &&  team !== 11 ){
      setChatContent( chats?.content )
      setChatContent( chats?.content?.filter(( chat )=> chat?.cheeringClub?.id === team ))
      setCurrentPage( 1 )
    }
  }, [ team, chats])

  useEffect(()=>{
    if( type === 'my' ) return

    setChatContent( chats?.content )
    if( chatContent && chats?.content ){
      setChatContent( chats?.content?.filter(( chat )=> 
      ( chat?.game?.homeClubName === match?.homeClubName ) &&
      ( chat?.game?.awayClubName === match?.awayClubName ) &&
      ( chat?.game?.datetime === match?.datetime )
      ))
      setCurrentPage( 1 )
    }
  }, [ chats, match ])

  useEffect(() => {
    chatContent && 
    setTotalPages( Math.ceil(chatContent?.length / itemsPerPage ))
  }, [ chatContent ])


  const handleClick = ( page ) => {
    setCurrentPage( page )
  }
  const handlePrevClick = () => {
    setCurrentPage(( prevPage ) => Math.max( prevPage - 1, 1 ))
  }
  const handleNextClick = () => {
    setCurrentPage(( prevPage ) => Math.min( prevPage + 1, totalPages ))
  }

  const renderPagination = () => {
    const pages = []
    for ( let i = 1; i <= totalPages; i++ ) {
      pages.push(
        <button
          key={ i }
          onClick={() => handleClick( i )}
          style={{ color: currentPage === i ? '#6A60A9' : '#DEDCEE', backgroundColor: '#fff', border: 'none', cursor: 'pointer' }}
        >
          { i }
        </button>
      )
    }
    return pages
  }

  const renderItems = () => {
    const startIndex = ( currentPage - 1 ) * itemsPerPage
    const endIndex = startIndex + itemsPerPage

    if( type === 'my' ){
      return chats?.content.slice( startIndex, endIndex )?.map(( chat ) => (
        <div>
          {
            chat?.status?.unreadCount > 0 && 
            <UnreadWrapper><p>{ chat?.status?.unreadCount }</p></UnreadWrapper>
          }
          <ChatItem type = { type }  item= { chat }/>
        </div>
      ))
    }
    else {
      return chatContent?.slice( startIndex, endIndex )?.map(( chat ) => (
        <ChatItem type = { type } key = { chat?.id } item= { chat }/>
      ))
    }
  }

  return (
    <>
      <div style={{ display: 'flex', gap: '10px', flexDirection: 'column', width: '100%' }}>
        { renderItems() }
      </div>
      {
        ( chatContent?.length > 0 || chats?.content?.length > 0 ) ?
        (
          <PageWrapper>
            <LeftIcon size= { 20 } onClick={ handlePrevClick }/>
            { renderPagination() }
            <RightIcon size= { 20 } onClick={ handleNextClick }/>
          </PageWrapper>
        ):
        ( <p>채팅방이 없습니다</p> )
      }
    </>
  )
}

export default Pagination