import React from 'react'
import styled from 'styled-components'
import  MatchingProfile  from './MatchingProfile'
import { Button, Title } from 'src/components'
import { useNavigate } from 'react-router-dom'



const ModalInfoWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  height: 80%;
`

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
`

const ModalContent = styled.div`
  background: white;
  width: 50%;
  height: 70%;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 10px 5px 5px ;
  
`

const CloseButton = styled.button`
  background: #ccc;
  padding: 8px 12px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  
`

const MatchingModal = ( props ) => {
  const { onClose } = props
  const participants = [
    {
      id: 102030,
      nickname: 'oonjae',
      birthdate: '2000-02-18',
      gender: '남성',
      profileImg: 'url',
    },
  ]

  const handleModalClose = () => {
       onClose()
  }

 
  const navigator = useNavigate()

  const onClickHandler = () => {
    navigator( '/chat' )
  }
  


  return (
    <ModalOverlay>
      <ModalContent onClick={(e) => e.stopPropagation()}>
        <CloseButton onClick={ handleModalClose }>닫기</CloseButton>
        <ModalInfoWrapper >

          <p style={{ fontSize: '30px', color: '#7D74B4' , fontWeight:'bolder' }}>#응원을 좋아하고 과몰입하는</p>
          <div style={{ textAlign: 'center'}}>
            { participants.map(( participant ) => (
              <MatchingProfile key={ participant?.id } { ...participant } />
              ))}
          </div>
          <Title>참가 인원 수: { participants.length }</Title>
          <Button type='parti' onClick={ onClickHandler } >참가하기</Button>
        </ModalInfoWrapper>
      </ModalContent>
    </ModalOverlay>
  );
};

export default MatchingModal