import { InputBox, SignButton, Button, Select, Title, KakaoIcon, 
  NaverIcon, MainLayout, PersonIcon, Tag, TagBtn, TagList } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback, useState } from 'react'
import { styled } from 'styled-components'


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
        { title: '# LG', bgColor: '#FBD14B', isRemove: true },
        { title: '# KT', bgColor: '#FBD14B',isRemove: true },
        { title: '# SSG', bgColor: '#FBD14B',isRemove: true },
        { title: '# NC', bgColor: '#FBD14B',isRemove: true },
        { title: '# 두산', bgColor: '#FBD14B',isRemove: true },
        { title: '# KIA', isRemove: true },
        { title: '# 롯데', isRemove: true },
        { title: '# 삼성', isRemove: true },
        { title: '# 한화', isRemove: true },
        { title: '# 키움' },
        { title: '# 팀무관' }
])


  return (
    <MainLayout>
      <TagList tags = { tags } />
      <TagList tags = { tagsRemove } />
      
      <Button type='cancel'>취소</Button>
      <Button type='parti'  onClick={ participate }>참가하기</Button>
      <Button type='save' onClick={ participate }>저장</Button>
      <Button type='reset' onClick={ participate }>초기화</Button>
      <SignButton>로그인</SignButton>

    </MainLayout>
  )
}

export default Dev
