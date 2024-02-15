import { Select, MainLayout, HomeLayout, Pagination, Button } from 'src/components'
import { styled } from 'styled-components'
import { WeekCalendar } from './components'
import { useQuery } from 'react-query'
import { TagApiType } from 'src/types'
import { getTags } from 'src/api'
import { createPortal } from 'react-dom'
import { getRecruits } from './api'
import useStore from './store'
import { useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'

const SettingWrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: left;
    gap: 10px;
    margin-top: 20px;
`

const MatchBtn = styled.button`
    width: 300px;
    height: 36px;
    background-color: #DEDCEE; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 16px;
    cursor: pointer;
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

const RecruitList = () => {

  const { data: tags } = useQuery<TagApiType>([ 'tags', { page: 0, size: 100 }], () => getTags({ page: 0, size: 100 }))
  const navigator = useNavigate()

  const [ team, setTeam ] = useState()
  const [ chatContent, setChatContent ] = useState()
  const { match, isModalOpened, updateModal, updateMatch } = useStore()

  const teams = tags?.content.filter( item => item.type === 'PREFERRED_TEAM' )
  
  useEffect(()=>{
    updateMatch({})
  }, [])
  
  const { data : chats } = useQuery([ 'chats', { type: 'RECRUIT' }], () => getRecruits({ type: 'RECRUIT' }))

  const goWrite = () => {
    if( !localStorage.getItem( 'userId' ) ){
      alert(' 로그인 하세요 ')
      navigator( '/login' )
    } 
    else navigator( '/recruit/post' )
  }

  const html = document.querySelector( 'html' )

  const openModal = () => {
      updateModal()
      html?.classList.add( 'scroll-locked' )
  }
  const closeModal = () => {
      updateModal()
      html?.classList.remove( 'scroll-locked' )
  }

  const ModalPortal = ({ children, onClose  }) => {
    const handleBackgroundClick = (e) => {
      ( e.target === e.currentTarget ) && onClose()
    }
    return createPortal(
      <ModalBackground onClick={ handleBackgroundClick }>
        <Modal>{ children }</Modal>
      </ModalBackground>,
      document.body
    )
  }
    
    return (
      <MainLayout title='직접 방 선택'>  
        <HomeLayout>
          <SettingWrapper>
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
            <Select 
              dataSource={ teams } 
              placeholder='응원팀' 
              background='#DEDCEE' 
              width='100px' 
              height='36px' 
              setState={ setTeam }
            />
          </SettingWrapper>
          <Button 
            style={{ alignSelf: 'flex-end', padding: '10px', marginTop: '-15px', marginBottom: '-5px' }}
            onClick={ goWrite }>채팅방 생성</Button>
          <div style={{ paddingBottom : '50px' }}>
              <Pagination match = { match } team = { team } chats={ chats } chatContent={ chatContent } setChatContent = { setChatContent }/>
          </div>
        </HomeLayout>
      </MainLayout>
    )
}


export default RecruitList