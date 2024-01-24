import { InputBox,SignButton, Button, Select, Title, KakaoIcon, 
  NaverIcon, MainLayout, OpenChatCard, BoardList, ChatList, Tag, TagBtn, TagList, ProfileCard, HomeLayout } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback, useState } from 'react'
import { styled } from 'styled-components'
import { ParticipantsType } from 'src/types'


const InputWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  margin-top: 20px;
`

const IconWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  gap: 10px;
`



const Dev = () => {
  const navigator = useNavigate()

  const participate = useCallback(() => {
    navigator('/participate')
  }, [])

  // const onLogin = useCallback(() => {
  //   navigator('/login')
  // }, [])

  const [tags, setTags] = useState([
        { title: '# LG' },
        { title: '# KT' },
        { title: '# SSG' },
        { title: '# NC' },
        { title: '# 두산' },
        { title: '# KIA' },
        { title: '# 롯데' },
        { title: '# 삼성' },
        { title: '# 한화' },
        { title: '# 키움' },
        { title: '# 팀무관' }
  ])
  const [tagsRemove, setTagsRemove] = useState([
        { title: '# LG' },
        { title: '# KT' },
        { title: '# SSG' },
        { title: '# NC' },
        { title: '# 두산'},
        { title: '# KIA' },
        { title: '# 롯데' },
        { title: '# 삼성' },
        { title: '# 한화' },
        { title: '# 키움' },
        { title: '# 팀무관' }
])
const [ participant, setParticipant ] = useState<ParticipantsType>(
  {
    userId: 1,
    nickname: '이운재',
    age: 32,
    gender: '남성',
    profileImg: 'https://i.namu.wiki/i/qA822LZ7zUrIq-AH8Q7IhXiCv_YyBYnZLwGYosmEpAz25gvsgBIUusUXsTJqxKhbSsBbYvSeqavdNmHc06s0FQ.webp',
    hashtags: ['#LG', '#KT', '#SSG', '#NC', '#LG', '#KT', '#SSG', '#NC','#LG', '#KT', '#SSG', '#NC' ]
  }
)


  return (
    <MainLayout>

      <ProfileCard participant = { participant }></ProfileCard>

      <TagList tags = { tags } />
      <TagList tags = { tagsRemove } isRemove= { true } />
      <TagList tags = { tagsRemove } bgColor= '#FBD14B' isRemove= { true } />

      
      <BoardList type='main' title='첫번째 제목' createdAt='10:10:10' user='이운재' logo='https://t1.daumcdn.net/cfile/tistory/2647254A5889D6BA03'></BoardList>
      <ChatList type='main' title='1/30 두산vs기아 18:00' mbti='#estp' numberofuser='3/4명' content='어디야?' ></ChatList>

      <Button type='cancel'>취소</Button>
      <Button type='parti'  onClick={ participate }>참가하기</Button>
      <Button type='save' onClick={ participate }>저장</Button>
      <Button type='reset' onClick={ participate }>초기화</Button>
      <SignButton>로그인</SignButton>

      <OpenChatCard children='하이하이하이' child='바이바이바이' />
    </MainLayout>
  )
}

export default Dev
