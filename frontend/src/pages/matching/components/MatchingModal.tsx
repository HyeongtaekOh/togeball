import React from 'react'
import styled from 'styled-components'
import  MatchingProfile  from './MatchingProfile'
import { Button, Title } from 'src/components'


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
  width: 70%;
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
      userId: 102030,
      nickname: 'oonjae',
      age: 26,
      gender: 'male',
      profileImg: 'url',
      hashtags: ['#estp', '#응원형'],
    },
    {
      userId: 102031,
      nickname: 'ahjin',
      age: 25,
      gender: 'female',
      profileImg: 'url',
      hashtags: ['#estj', '#몰입형'],
    },
    
  ]

  const handleModalClose = () => {
       onClose()
  }

  return (
    <ModalOverlay>
      <ModalContent onClick={(e) => e.stopPropagation()}>
        <CloseButton onClick={ handleModalClose }>닫기</CloseButton>
        <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'space-evenly', alignItems: 'center', height: '80%'}}>

          <Title>#응원을 좋아하고 과몰입하는</Title>
          <div>
            { participants.map(( participant ) => (
              <MatchingProfile key={ participant.userId } { ...participant } />
              ))}
          </div>
          <Title>참가 인원 수: { participants.length }</Title>
          <Button type='parti'>참가하기</Button>
        </div>

      </ModalContent>
    </ModalOverlay>
  );
};

export default MatchingModal;