import { useEffect, useState } from 'react'
import { HomeLayout, MainLayout, Title,  InputBox, Button } from 'src/components'
import { patchProfile, getCheckNickname } from './api'
import { RowTagList, ColTagList, TagList, GenderTag, BirthdayPicker } from './components'
import useModel from './store'
import { getMyInfo, getTags } from 'src/api'
import { useNavigate } from 'react-router-dom';
import ImgUpload from './components/ImgUpload'
import { useQuery, useMutation } from 'react-query'
import styled from 'styled-components'
import { TagType } from 'src/types'


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
const TitleWrapper = styled.div<{ type? : string }>`
  display: flex;
  flex-direction: column;
  text-align: right;
  width: 100px;
  padding-top: ${(prop) => prop.type === 'input'? '13px': ( prop.type === 'value'? '0px': '2px' ) };
  font-weight: bold;
  margin-left:  ${(prop) => prop.type && '-10px' };
  margin-right: 12px;
`
const ErrorText = styled.span`
  color: red;
  font-size: 12px;
  margin: 28px 0px 0px 5px;
`
const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin: 30px 20px;
`

const Profile = () => {

  const navigator = useNavigate()

  const { data: userInfo, isLoading } = useQuery([ 'userInfo' ], () => getMyInfo())

  const id = userInfo?.email
  const [ nickName, setNickName ] = useState( userInfo?.nickname )
  const [ nicknameError, setNicknameError ] = useState('')
  const { selectTags, team, image, stadiums, updateTags, gender, selectedDate } = useModel()
  const [ isOk, setIsOk ] = useState<boolean>( true )

  useEffect(() => {
    userInfo && 
    updateTags( userInfo?.tags?.filter(( tag : TagType ) => {
      return tag.type !== 'PREFERRED_STADIUM'
    }))
  }, [ userInfo, updateTags ])

  const profileMutation = useMutation( patchProfile, {
    onSuccess: () => {
      navigator( '/mypage' )
    }
  })

  const param = {
    page: 0,
    size: 100
  }

  const { data: tags } = useQuery<TagApiType>([ 'tags', param ], () => getTags( param ))

  const preferredTeam = tags?.content.filter( item => item.type === "PREFERRED_TEAM" )
  const preferredStadiums = tags?.content.filter( item => item.type === "PREFERRED_STADIUM" )
  const preferredSeat = tags?.content.filter( item => item.type === "PREFERRED_SEAT" )
  const cheeringStyle = tags?.content.filter( item => item.type === "CHEERING_STYLE" )
  const mbti = tags?.content.filter( item => item.type === "MBTI" )
  const seasonPass = tags?.content.filter( item => item.type === "SEASON_PASS" )
  const unlabeled = tags?.content.filter( item => item.type === "UNLABELED" )
  
  const data = {
    nickname: nickName,
    clubId: team,
    profileImage: ( image==='' && userInfo?.profileImage!=='' ) ? userInfo?.profileImage : 
    image,
    role: 'BASIC',
    gender: gender,
    birthdate: selectedDate==='' ? null : new Date( selectedDate ),
    tagIds: [ ...selectTags, ...stadiums ].map( item => item.id )
  }

  useEffect(() => {
    const validateNickname = async () => {
      if ( !nickName ) {
        setNicknameError('')
        setIsOk( false )
        return
      }
      const isAvailable = await getCheckNickname( nickName );
      if( isAvailable ){
        setNicknameError('')
        setIsOk( true )
      }else{
        if( nickName === userInfo?.nickname ){
        }else{
          setNicknameError( '사용할 수 없는 닉네임입니다.' )
          setIsOk( false )
        }
      }
    }
    validateNickname();
  }, [ nickName ]);

  const handleNicknameChange = (e) => {
    setNickName( e.target.value )
  }

  const postProfileSetting = () => {
    if ( !isOk ){
      alert( '닉네임을 설정해주세요' )
    } else if( gender === '' ){
      alert( '성별을 설정해주세요' )
    } else if( selectedDate === '' ){
      alert( '생일을 설정해주세요' )
    } else if( team === 0 ){
      alert( '팀을 선택해주세요' )
    } else{
      profileMutation.mutateAsync( data )
    }
  }

  const goMyPage = () =>{
    navigator( '/mypage' )
  }
  
  
  return(
    <MainLayout title='프로필 설정'>
      <HomeLayout style={{ paddingTop: '30px' }}>
        {
          isLoading? <Title>로딩중..</Title> : (
            <>
            <ProfileSettingWrapper>
              <Title type='medium'>필수 정보</Title>
              <InputWrapper>
                <TitleWrapper type='value'>
                  <Title type='small'>이메일</Title>
                </TitleWrapper>
                <Title type='small'>{ id }</Title>
              </InputWrapper>
              <ImgUpload profileImage={ userInfo?.profileImage } />
              <InputWrapper>
                <TitleWrapper type='input'>
                  <Title type='small'>닉네임</Title>
                </TitleWrapper>
                <InputBox
                  value={ nickName === undefined ? userInfo?.nickname: nickName }
                  placeholder={ '닉네임을 입력하세요' }
                  height='40px' width='300px'
                  onChange={ handleNicknameChange } />
                { nicknameError && <ErrorText>{ nicknameError }</ErrorText>}
              </InputWrapper>
              <div style={{ display: 'flex', marginLeft: '45px' }}>
                <GenderTag userGen={ userInfo?.gender } />
                <BirthdayPicker mybirth={ userInfo?.birthdate } />
              </div>
              <RowTagList list={ preferredStadiums } flag={ true } mytags={ userInfo?.tags }>선호 구장</RowTagList>
              <RowTagList list={ preferredTeam } myteam={ userInfo?.clubSponsorName }>팀선택{<br />}(1개만 선택)</RowTagList>
            </ProfileSettingWrapper>
            <ProfileSettingWrapper>
              <Title type='medium'>직관 스타일</Title>
              <Title type='small' bold><br />
                나의 직관 스타일을 나타낼 수 있는 태그를 선택해주세요.(최소 5개, 최대 15개)
              </Title>
              <TagList tags={ selectTags } bgColor='#FBD14B' isTag />
              <ColTagList list={ preferredTeam } mytags={ userInfo?.tags }>직관응원팀</ColTagList>
              <ColTagList list={ cheeringStyle } mytags={ userInfo?.tags }>응원 유형</ColTagList>
              <ColTagList list={ preferredSeat } mytags={ userInfo?.tags }>선호 좌석</ColTagList>
              <ColTagList list={ mbti } mytags={ userInfo?.tags }>MBTI</ColTagList>
              <ColTagList list={ seasonPass } mytags={ userInfo?.tags }>시즌권 보유</ColTagList>
              <ColTagList list={ unlabeled } mytags={ userInfo?.tags }>기타</ColTagList>
              <ButtonWrapper>
                <Button type='save' onClick={ postProfileSetting }>저장</Button>
                <Button type='cancel' onClick={ goMyPage }>취소</Button>
              </ButtonWrapper>
            </ProfileSettingWrapper>
            </>
          )
        } 
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