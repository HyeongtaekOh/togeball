import { Button, InputBox, Select, MainLayout, HomeLayout, Title } from 'src/components';
import { TagsInput, WeekCalendar } from '../components';
import { postRecruit } from './api';
import useStore from '../store'
import { useState } from 'react';
import { createPortal } from 'react-dom';
import { useMutation, useQuery } from 'react-query'
import { styled } from 'styled-components'
import { TagApiType } from 'src/types';
import { getTags } from 'src/api';

const MatchBtn = styled.button`
    width: 430px;
    height: 48px;
    background-color: #fff; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 18px;
    cursor: pointer;
`
const Contents = styled.div`
    display: flex;
    gap: 30px;
`
const Input = styled.textarea<{ maxLength: string }>`
    height: 60px;
    width: 96%;
    border: 1px solid lightgray;
    border-radius: 20px;
    padding: 20px;
`
const ModalBackground = styled.div`
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    z-index: 400;
`
const Modal = styled.div`
    width: 1100px;
    height: 36 0px;
    background-color: #FFF;
    position: fixed;
    top: 25%;
    left: 15%;
    border: 1px solid lightgray;    
    border-radius: 20px;
`

const RecruitPost = () => {
    const [ inputCount, setInputCount ] = useState(0)
    const [ title, setTitle ] = useState('')
    const [ team, setTeam ] = useState('')
    const [ seat, setSeat] = useState('')
    const [ capacity, setCapacity ] = useState()
    const { tagList } = useStore()
    const [ textarea, setTextarea ] = useState('')

    const { match, isModalOpened, updateModal } = useStore()

    const recruitMutation = useMutation( postRecruit );

    const onInputHandler = (e) => {
        const textareaValue = e.target.id;
        setInputCount(
            textareaValue.replace(/<br\s*V?>/gm, '\n').length
        )
        setTextarea( textareaValue )
    }

    const html = document.querySelector('html');

    const openModal = () => {
        updateModal()
        html?.classList.add('scroll-locked')
    }
        
    const closeModal = () => {
        updateModal();
        html?.classList.remove('scroll-locked')
    }

    const ModalPortal = ({ children, onClose  }) => {
      const handleBackgroundClick = (e) => {
        (e.target === e.currentTarget) && onClose()
      }

      return createPortal(
        <ModalBackground onClick={ handleBackgroundClick }>
          <Modal>{ children }</Modal>
        </ModalBackground>,
        document.body
      )
    }

    const { data: tags } = useQuery<TagApiType>([ 'tags', { page: 0, size: 100 }], () => getTags({ page: 0, size: 100 }))

    const teams = tags?.content.filter(item => item.type === "PREFERRED_TEAM")
    const seats = tags?.content.filter(item => item.type === "PREFERRED_SEAT")

    const [nums, setNums] = useState([
        { id: 1, content: '1' },
        { id: 2, content: '2' },
        { id: 3, content: '3' },
        { id: 4, content: '4' },
        { id: 5, content: '5' },
        { id: 6, content: '6' },
        { id: 7, content: '7' },
        { id: 8, content: '8' },
        { id: 9, content: '9' },
        { id: 10, content: '10' }
    ])
    

    const data = {
        gameId: match.id,
        userId: 123,
        title: title,
        description: textarea,
        capacity: capacity,
        cheeringTeam: team,
        tags: tagList,
        preferSeats: seat,
    }

    const makeChatting = () => {
        recruitMutation.mutateAsync( data )
    }

    return (
      <MainLayout title='직관 메이트 모집하기 '>  
          <HomeLayout>
            <Title style={{ marginTop: '20px', marginLeft: '20px' }}>제목(최대 60자)</Title>
            <InputBox height='20px' width='100%' onChange={(e) => { setTitle( e.target.id )}}/>
            <MatchBtn onClick={ openModal }>
                { match.homeClubName ? match.homeClubName +' VS '+ match.awayClubName : '경기를 선택하세요' }
            </MatchBtn>
            { 
                isModalOpened 
                && createPortal(
                  <ModalPortal onClose={ closeModal }>
                    <Modal><WeekCalendar/></Modal>
                  </ModalPortal>,
                  document.body )
            }
            <Contents>
                <Select dataSource={ teams } placeholder='응원하는 팀' height='40px'
                 setState={ setTeam }></Select>
                <Select dataSource={ seats } placeholder='선호하는 좌석' height= '40px' 
                 setState={ setSeat }></Select>
            </Contents>
            <Contents>
                <Title type='medium' style={{ marginTop: '6px' }}>인원</Title>
                <Select dataSource={ nums } placeholder='인원' width='120px' height='36px' 
                   setState={ setCapacity }></Select>
            </Contents>
            <Contents>
                <Title type='medium'>태그</Title><TagsInput />
            </Contents>
                <Title type='medium'>채팅방 소개</Title>
            <Input onChange={ onInputHandler } maxLength="300" />
            <p style={{ textAlign: 'right' }}>
                <span>{ inputCount }</span>
                <span>/300 자</span>
            </p>
            <div style={{ display:'flex', justifyContent: 'right', gap: '10px' }}>
                <Button type='parti' width='120px' onClick= { makeChatting }>채팅방 만들기</Button>
                <Button type='reset' width='90px'>초기화</Button>
                <Button type='cancel' width='80px'>취소</Button>
            </div>
          </HomeLayout>
        </MainLayout>
    )
}


export default RecruitPost