import { Select, MainLayout, HomeLayout, Pagination, Button } from 'src/components'
import { styled } from 'styled-components'
import { useQuery } from 'react-query'
import { TagApiType } from 'src/types'
import { getTags } from 'src/api'
import { getRecruits } from './api'
import { useNavigate } from 'react-router-dom'
import { useState } from 'react'

const SettingWrapper = styled.div`
    display: flex;
    width: 100%;
    justify-content: left;
    gap: 10px;
    margin-top: 20px;
`
const MatchBtn = styled.button`
    width: 200px;
    height: 36px;
    background-color: #DEDCEE; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 16px;
`

const RecruitList = () => {

  const { data: tags } = useQuery<TagApiType>([ 'tags', { page: 0, size: 100 }], () => getTags({ page: 0, size: 100 }))
  const navigator = useNavigate()

  const [ team, setTeam ] = useState()
  const [ chatContent, setChatContent ] = useState()

  const teams = tags?.content.filter(item => item.type === 'PREFERRED_TEAM')
  const seats = tags?.content.filter(item => item.type === 'PREFERRED_SEAT')

  const { data : chats } = useQuery([ 'chats', { type: 'RECRUIT' }], () => getRecruits({ type: 'RECRUIT' }))

  const goWrite = () => {
    if( !localStorage.getItem('userId') ){
      alert(' 로그인 하세요 ')
      navigator('/login')
    } 
    else navigator('/recruit/post')
  }

  const FilterMine = () => {

    }
    
    return (
      <MainLayout title='직접 방 선택'>  
        <HomeLayout>
          <SettingWrapper>
            <MatchBtn >경기를 선택하세요</MatchBtn>
            <Select 
              dataSource={ teams } 
              placeholder='응원팀' 
              background='#DEDCEE' 
              width='100px' 
              height='36px' 
              setState={ setTeam }
            />
            {/* <Select dataSource={ seats } placeholder='선호 좌석' background='#DEDCEE' width='120px' height='36px' ></Select> */}
          </SettingWrapper>
          <Button 
            style={{ alignSelf: 'flex-end', padding: '10px', marginTop: '-15px', marginBottom: '-5px' }}
            onClick={ goWrite }>채팅방 생성</Button>
          <div style={{ paddingBottom : '50px' }}>
              <Pagination team = { team } chats={ chats } chatContent={ chatContent } setChatContent = { setChatContent }/>
          </div>
        </HomeLayout>
      </MainLayout>
    )
}


export default RecruitList