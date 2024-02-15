import { Button, InputBox, Select, MainLayout, HomeLayout, Title } from 'src/components';
import { TagsInput, WeekCalendar } from './components'
import useStore from './store'
import { useEffect, useRef, useState } from 'react'
import { createPortal } from 'react-dom'
import { useMutation, useQuery } from 'react-query'
import { RecruitType, TagApiType } from 'src/types'
import { getTags, makeRecruitChat, makeTags, partiChat } from 'src/api'
import { styled } from 'styled-components'
import { useNavigate } from 'react-router-dom'

const MatchBtn = styled.button`
    width: 410px;
    height: 48px;
    background-color: #fff; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 18px;
    cursor: pointer;
    &:hover{
        background-color: lightgray;
    }
`
const Contents = styled.div`
    display: flex;
    gap: 20px;
    align-items: center;
    min-height: 50px;
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

  const navigator = useNavigate()
  
  const inputCount =  useRef(0)

  const [ title, setTitle ] = useState('')
  const [ team, setTeam ] = useState('')
  const [ seat, setSeat] = useState('')
  const [ capacity, setCapacity ] = useState(0)
  const [ textarea, setTextarea ] = useState('')
  
  const { data: tags } = useQuery<TagApiType>([ 'tags', { page: 0, size: 100 }], () => getTags({ page: 0, size: 100 }))

  const [ flag, setFlag ] = useState(false);
  const [ teams, setTeams ] = useState([ { id: 100, content: '경기를 선택하세요' }])
  const seats = tags?.content.filter(item => item.type === "PREFERRED_SEAT")
  let initialTeams = tags?.content.filter( item => item.type === "PREFERRED_TEAM" )

  const [ nums ] = useState([
    { id: 2, content: '2명' },
    { id: 3, content: '3명' },
    { id: 4, content: '4명' },
    { id: 5, content: '5명' },
    { id: 6, content: '6명' },
    { id: 7, content: '7명' },
    { id: 8, content: '8명' },
    { id: 9, content: '9명' },
    { id: 10, content: '10명' }
  ])

  const { match, isModalOpened, updateModal, tagList, updateMatch } = useStore()

  const partiMutation = useMutation( partiChat )
  const recruitMutation = useMutation( makeRecruitChat, {
    onSuccess: async( res ) => {
      if( res?.status === 201 ) {
        await partiMutation.mutateAsync({ chatRoomId : res?.data?.id })
        alert( '채팅방이 생성되었습니다. ')
        navigator('/recruit')
      }
    }
  } )
  
  const createTags = useMutation( makeTags )

  const onInputHandler = (e) => {
    const textareaValue = e.target.value
    inputCount.current = 
        textareaValue.replace(/<br\s*V?>/gm, '\n').length
    setTextarea( textareaValue )
  }
  const onReset = () => {
    setTitle('')
    setTeam('')
    setSeat('')
    setCapacity(0)
    setTextarea('')
    updateMatch({})
  }

  const html = document.querySelector('html')

  const openModal = () => {
      updateModal()
      html?.classList.add('scroll-locked')
  }
  const closeModal = () => {
      updateModal()
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

  
  const makeChatting = async () => {
    if( title === '' ) {
      alert('제목 입력하세요')
      return
    }
    if( capacity === 0 ) {
      alert('인원 선택하세요')
      return
    }
    const response =  await createTags.mutateAsync({ tags : tagList })
    if( response?.length >= 0 )  {
      const data : RecruitType = {
          title: title,
          description: textarea,
          capacity: capacity,
          managerId: Number(localStorage.getItem('userId')),
          gameId: match.id,
          cheeringClubId: Number( team ),
          tagIds: [ ...response, seat ]
        }
        console.log(data)
      await recruitMutation.mutateAsync( data )
    }
  }

  useEffect(() => {
    const newTeams = initialTeams?.filter((team)=>{
      return team.content === match?.homeClubName || team.content === match?.awayClubName
    })
    newTeams && setTeams([ ...newTeams, { id: 11, content: '응원팀무관' }])
  }, [ match ]); 

  useEffect(()=>{
    updateMatch({})
    setFlag( true )
  }, [])

  return (
    <MainLayout title='직관 메이트 모집하기'>  
      <HomeLayout>
        <Title style={{ marginTop: '20px', fontSize: '17px' }}>제목</Title>
        <InputBox 
          width='100%' 
          value= { title }
          onChange={(e) => { setTitle( e.target.value )}}
          placeholder='제목을 입력하세요(최대 60자)'
          fontSize= '17px'
        />
        <MatchBtn onClick={ openModal }>
        {
          match.homeClubName ? 
          `${ match.homeClubName } VS ${ match.awayClubName } ${ match.datetime.substring(0,10) + ' ' + match.datetime.substring(11,16)}`
          :'경기를 선택하세요' 
        }
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
          <Title style={{ fontSize : '16px'}}>응원팀</Title>
          <Select dataSource={ teams } placeholder='팀 선택' height='40px'
           setState={ setTeam }></Select>
           <Title style={{ fontSize : '16px'}}>선호 좌석</Title>
          <Select dataSource={ seats } placeholder='좌석 선택' height= '40px' 
           setState={ setSeat }></Select>
        </Contents>
        <Contents>
          <Title style={{ fontSize : '16px'}}>인원</Title>
            <Select 
              dataSource={ nums } placeholder='(2명 ~ 10명)' width='220px' height='40px' 
             setState={ setCapacity }/>
        </Contents>
        <Contents>
          <Title style={{ fontSize : '16px'}}>태그</Title><TagsInput />
        </Contents>
        <Title style={{ fontSize : '16px'}}>채팅방 소개</Title>
        <Input 
          value={ textarea } 
          onChange={(e) => onInputHandler(e) } 
          maxLength="300" 
        />
        <p style={{ textAlign: 'right' }}>
          <span>{ inputCount.current }</span>
          <span>/300 자</span>
        </p>
        <div style={{ display:'flex', justifyContent: 'right', gap: '10px' }}>
          <Button type='parti' width='120px' onClick= { makeChatting }>채팅방 만들기</Button>
          <Button type='cancel' width='80px' onClick= { () => navigator(-1) }>취소</Button>
        </div>
      </HomeLayout>
    </MainLayout>
  )
}


export default RecruitPost