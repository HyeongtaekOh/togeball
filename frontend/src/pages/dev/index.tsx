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
      
      <Button type='cancel'>취소</Button>
      <Button type='parti'  onClick={ participate }>참가하기</Button>
      <Button type='save' onClick={ participate }>저장</Button>
      <Button type='reset' onClick={ participate }>초기화</Button>
      <SignButton>로그인</SignButton>

    </MainLayout>
  )
}

export default Dev
