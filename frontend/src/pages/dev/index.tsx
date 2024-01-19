import { InputBox, Button, Select, Title, KakaoIcon, 
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

const dataSource = [
  { value: 'Kakao', name: 'Kakao' },
  { value: 'Naver', name: 'Naver' },
]

const Dev = () => {
  const navigator = useNavigate()

  const onLogin = useCallback(() => {
    navigator('/login')
  }, [])

  const [tags, setTags] = useState([
        {title: '# LG', isChange: true},
        {title: '# KT', isChange: true},
        {title: '# SSG', isChange: true},
        {title: '# NC', isChange: true},
        {title: '# 두산', isChange: true},
        {title: '# KIA', isChange: true},
        {title: '# 롯데', isChange: true},
        {title: '# 삼성', isChange: true},
        {title: '# 한화', isChange: true},
        {title: '# 키움', isChange: true},
        {title: '# 팀무관', isChange: false}
  ])
  const [tagsRemove, setTagsRemove] = useState([
        {title: '# LG', isRemove: true},
        {title: '# KT', isRemove: true},
        {title: '# SSG', isRemove: true},
        {title: '# NC', isRemove: true},
        {title: '# 두산', isRemove: true},
        {title: '# KIA', isRemove: true},
        {title: '# 롯데', isRemove: true},
        {title: '# 삼성', isRemove: true},
        {title: '# 한화', isRemove: true},
        {title: '# 키움', isRemove: false},
        {title: '# 팀무관', isRemove: false}
])


  return (
    <MainLayout>
      <Title type= 'large' color= 'gray'>
        이곳에 로그인을
        <br />
        독려하는 카피가 들어갑니다.
      </Title>
      <InputWrapper>
        <InputBox title= '이메일' placeholder= '이메일을 입력해주세요.' />
        <InputBox title= '비밀번호' placeholder= '비밀번호를 입력해주세요' />
      </InputWrapper>
      <Button type='medium' onClick={ onLogin }>
        로그인
      </Button>
      <p>SNS 로그인</p>

      <IconWrapper>
        <PersonIcon />
        <KakaoIcon />
      </IconWrapper>
      <TagBtn isChange={ true }>
        # INFJ 
      </TagBtn>
      <TagBtn>
        # 안변함
      </TagBtn>
      <Tag isRemove={ true }>
        # INFJ 
      </Tag>
      <Tag>
        # 안변함
      </Tag>
      <TagList tags = { tags } />
      <TagList tags = { tagsRemove } />
      <hr style= {{ width: '100%' }} />
      <Title type= 'small'>혹시</Title>
      <Title type= 'small'>비밀번호를 잊으셨나요?</Title>
      <Title type= 'small'>아직 회원이 아니신가요?</Title>
      <Select dataSource={ dataSource } placeholder= '선택해주세요' />
    </MainLayout>
  )
}

export default Dev
