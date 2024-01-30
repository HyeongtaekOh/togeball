import { Button, InputBox, Select, MainLayout, HomeLayout, Title } from 'src/components';
import { TagsInput } from '../components';
import { DateList, DayList, WeekCalender } from '../components'
import { useState } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components'

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

const Buttons = styled.div`
    display: flex;
    justify-content: right;
    gap: 10px;
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
    width: 900px;
    height: 36 0px;
    background-color: #FFF;
    position: fixed;
    top: 25%;
    left: 20%;
    border: 1px solid lightgray;    
    border-radius: 20px;
`

const RecruitPost = () => {
    const [inputCount, setInputCount] = useState(0);
    const [isModalOpened, setIsModalOpened] = useState(false);

    const onInputHandler = (e) => {
        setInputCount(
            e.target.value.replace(/<br\s*V?>/gm, '\n').length
        );
    };

    const html = document.querySelector('html');

    const openModal = () => {
        setIsModalOpened(true);
        html?.classList.add('scroll-locked');
    };
        
    const closeModal = () => {
        setIsModalOpened(false);
        html?.classList.remove('scroll-locked');
    };

    const ModalPortal = ({ children, onClose  }) => {
      const el = document.body
      const handleBackgroundClick = (e) => {
        if (e.target === e.currentTarget) {
          onClose();
        }
      };
        return createPortal(<ModalBackground onClick={ handleBackgroundClick }>
            <Modal>{children}</Modal>
          </ModalBackground>,
          el);
      };


    const [teams, setTeams] = useState([
        { value: 'LG', name: 'LG' },
        { value: 'KT', name: 'KT' },
        { value: 'SSG', name: 'SSG' },
        { value: 'NC', name: 'NC' },
        { value: '두산', name: '두산' },
        { value: 'KIA', name: 'KIA' },
        { value: '롯데', name: '롯데' },
        { value: '삼성', name: '삼성' },
        { value: '한화', name: '한화' },
        { value: '키움', name: '키움' },
        { value: '팀무관', name: '팀무관' }
    ])

    const [seats, setSeats] = useState([
        { value: 'cheeringSeat', name: '응원석' },
        { value: 'tableSeat', name: '테이블석' },
        { value: 'vipSeat', name: 'VIP석' },
        { value: 'outfieldSeat', name: '외야석' }
    ])

    const [nums, setNums] = useState([
        { value: '1', name: '1' },
        { value: '2', name: '2' },
        { value: '3', name: '3' },
        { value: '4', name: '4' },
        { value: '5', name: '5' },
        { value: '6', name: '6' },
        { value: '7', name: '7' },
        { value: '8', name: '8' },
        { value: '9', name: '9' },
        { value: '10', name: '10' }
    ])

        return (
            <MainLayout title='직관 메이트 모집하기 '>  
                <HomeLayout>
                    <Title style={{ marginTop: '20px', marginLeft: '20px' }}>제목(최대 60자)</Title>
                    <InputBox height='20px' width='100%'/>
                    <MatchBtn onClick={ openModal }>경기를 선택하세요</MatchBtn>
                    { isModalOpened && createPortal(
                        <ModalPortal onClose={ closeModal }>
                            <Modal><WeekCalender/></Modal>
                        </ModalPortal>,
                        document.body,
                    )}
                    <Contents>
                        <Select dataSource={ teams } placeholder='응원하는 팀' height= '40px'></Select>
                        <Select dataSource={ seats } placeholder='선호하는 좌석' height= '40px'></Select>
                    </Contents>
                    <Contents>
                        <Title type='medium' style={{ marginTop: '6px' }}>인원</Title>
                        <Select dataSource={ nums } placeholder='인원' width='120px' height='36px'></Select>
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
                    <Buttons>
                        <Button type='parti' width='120px'>채팅방 만들기</Button>
                        <Button type='reset' width='90px'>초기화</Button>
                        <Button type='cancel' width='80px'>취소</Button>
                    </Buttons>
                </HomeLayout>
            </MainLayout>
        )
    }


    export default RecruitPost