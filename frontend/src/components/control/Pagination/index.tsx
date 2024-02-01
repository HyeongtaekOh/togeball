import { LeftIcon, RightIcon } from 'src/components/icon';
import { useState } from 'react';
import { ChatItem } from'src/components';
import styled from 'styled-components';

const PageWrapper = styled.div`
    display: flex;
    margin-top: 20px;
    justify-content: center;
`


const itemsPerPage = 5;

const Pagination = ( props ) => {

  const { chats } = props;

  const [ currentPage, setCurrentPage ] = useState( 1 )

  const totalPages = Math.ceil( chats.length / itemsPerPage )

  const handleClick = ( page ) => {
    setCurrentPage( page )
  };

  const handlePrevClick = () => {
    setCurrentPage(( prevPage ) => Math.max( prevPage - 1, 1 ))
  };

  const handleNextClick = () => {
    setCurrentPage(( prevPage ) => Math.min( prevPage + 1, totalPages ))
  };

  const renderPagination = () => {

    const pages = [];
    for ( let i = 1; i <= totalPages; i++ ) {
      pages.push(
        <button
          key={ i }
          onClick={ () => handleClick( i ) }
          style={{ color: currentPage === i ? '#6A60A9' : '#DEDCEE', backgroundColor: '#fff', border: 'none' }}
        >
          { i }
        </button>
      );
    }
    return pages;
  };

  const renderItems = () => {
    const startIndex = ( currentPage - 1 ) * itemsPerPage
    const endIndex = startIndex + itemsPerPage;
    return chats.slice( startIndex, endIndex ).map(( chat, index ) => (
        <div key={ index }>
          { chat.chatrooms.map(( chatroom, roomIndex ) => (
            <ChatItem key = { chatroom } item= { chat }/>
          ))}
        </div>
      ));
  };

  return (
    <>
      { renderItems() }
    <PageWrapper>
        <LeftIcon size= { 20 } onClick={ handlePrevClick } disabled={ currentPage === 1 }/>
        { renderPagination() }
        <RightIcon size= { 20 } onClick={ handleNextClick }/>
    </PageWrapper>
    </>
  );
};

export default Pagination;