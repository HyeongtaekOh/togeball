import { useState } from 'react'
import { HomeLayout, MainLayout, Title, RadioTagList, InputBox, Tag, Button } from 'src/components'
import styled from 'styled-components'
import { getTags } from './api'
import { RowTagList, ColTagList, TagList } from './components'
import useModel from './store'
import ImgUpload from './components/ImgUpload'
import { useQuery } from 'react-query'

const ProfileSettingWrapper = styled.div`
  box-sizing: border-box;
  flex-direction: column;
  width: 95%;
  height: 100%;
  margin: 50px;
`
const InputWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  gap: 10px;
  margin-top: 30px;
`
const TitleWrapper = styled.div<{ type? : string } >`
  display: flex;
  flex-direction: column;
  text-align: right;
  width: 100px;
  padding-top: ${(prop) => prop.type === 'input'? '13px': ( prop.type === 'value'? '0px': '2px' ) };
  font-weight: bold;
  margin-left:  ${(prop) => prop.type && '-10px' };
  margin-right: 12px;
`
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-bottom: 50px;
`

const Profile = () => {

  const param = {
    page: 0,
    size: 100
  }

  const { data: tags } = useQuery<TagApiType>([ 'tags', param ], () => getTags( param ))

  const preferredTeam = tags?.content.filter(item => item.type === "PREFERRED_TEAM");
  const preferredStadiums = tags?.content.filter(item => item.type === "PREFERRED_STADIUM");
  const preferredSeat = tags?.content.filter(item => item.type === "PREFERRED_SEAT");
  const cheeringStyle = tags?.content.filter(item => item.type === "CHEERING_STYLE");
  const mbti = tags?.content.filter(item => item.type === "MBTI");
  const seasonPass = tags?.content.filter(item => item.type === "SEASON_PASS");
  const unlabeled = tags?.content.filter(item => item.type === "UNLABELED");
  
  const [ id, setId ] = useState( '하이' )
  const [ nickName, setNickName ] = useState( '' )
  const { selectTags } = useModel()

  return(
    <MainLayout title='프로필 설정'>
      <HomeLayout style={{ paddingTop: '30px' }}>
        <ProfileSettingWrapper>
          <Title type = 'medium'>필수 정보</Title>
          <InputWrapper>
            <TitleWrapper type = 'value'>
              <Title type='small'>아이디</Title>
            </TitleWrapper>
            <Title type='small'>{ id }</Title>
          </InputWrapper>
          <ImgUpload/>
          <InputWrapper>
            <TitleWrapper type = 'input'>
              <Title type='small'>닉네임</Title>
            </TitleWrapper>
            <InputBox 
              value={ nickName } 
              placeholder = '닉네임을 입력하세요' 
              height = '40px' 
              width = '300px'
            />
          </InputWrapper>
          <RowTagList list = { preferredStadiums } >선호 구장</RowTagList>
          <RowTagList list = { preferredTeam }>팀선택{<br/>}(1개만 선택)</RowTagList>     
        </ProfileSettingWrapper>
        <ProfileSettingWrapper>
          <Title type = 'medium'>직관 스타일</Title>
          <Title type = 'small' bold><br/>나의 직관 스타일을 나타낼 수 있는 태그를 선택해주세요.(최소 5개, 최대 15개)</Title>
          <TagList tags = { selectTags } bgColor='#FBD14B' isTag/>
          <ColTagList list = { preferredTeam }>직관응원팀</ColTagList>
          <ColTagList list = { cheeringStyle }>응원 유형</ColTagList>
          <ColTagList list = { preferredSeat }>선호 좌석</ColTagList>
          <ColTagList list = { mbti }>MBTI</ColTagList>
          <ColTagList list = { seasonPass }>시즌권 보유</ColTagList>
          <ColTagList list = { unlabeled }>기타</ColTagList>
        </ProfileSettingWrapper>
        <ButtonWrapper>
          <Button type = 'save'>저장</Button>
          <Button type = 'cancel'>취소</Button>
        </ButtonWrapper>
      </HomeLayout>
    </MainLayout>
  )
}

export default Profile

interface contentItem {
  id: number,
  content: string,
  type: string
}

type TagApiType = {
  content?: contentItem[],
  totalElements?: number,
}