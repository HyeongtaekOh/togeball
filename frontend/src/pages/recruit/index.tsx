import { Select, MainLayout, HomeLayout, Pagination } from 'src/components'
import { styled } from 'styled-components'
import { useQuery } from 'react-query'
import { TagApiType } from 'src/types'
import { getTags } from 'src/api'
import { getRecruits } from './api'

const SettingWrapper = styled.div`
    display: flex;
    justify-content: left;
    gap: 10px;
    margin-top: 50px;
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
const FilterButton = styled.button`
    width: 140px;
    height: 36px;
    background-color: #DEDCEE; 
    border-radius: 20px;
    border: 1px lightgray solid;
    font-weight: bold;
    font-size: 16px;
    margin-left: auto;
    margin-right: 10px;
`

const RecruitList = () => {

  const { data: tags } = useQuery<TagApiType>([ 'tags', { page: 0, size: 100 }], () => getTags({ page: 0, size: 100 }))

  const teams = tags?.content.filter(item => item.type === 'PREFERRED_TEAM')
  const seats = tags?.content.filter(item => item.type === 'PREFERRED_SEAT')

  const { data : chats } = useQuery([ 'chats', { type: 'RECRUIT' }], () => getRecruits({ type: 'RECRUIT' }))

  const FilterMine = () => {

    }
    
    return (
        <MainLayout title='직접 방 선택'>  
            <HomeLayout>
                <SettingWrapper>
                    <MatchBtn >경기를 선택하세요</MatchBtn>
                    <Select dataSource={ teams } placeholder='응원팀' background='#DEDCEE' width='100px' height='36px' ></Select>
                    <Select dataSource={ seats } placeholder='선호 좌석' background='#DEDCEE' width='120px' height='36px' ></Select>
                </SettingWrapper>
                <FilterButton>내 글만 보기</FilterButton>
                <div>
                    <Pagination chats={ chats } />
                </div>
            </HomeLayout>
        </MainLayout>
    )
}


export default RecruitList