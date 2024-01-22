import { InputBox,SignButton, Button, Select, Title, KakaoIcon, 
  NaverIcon, MainLayout, OpenChatCard, BoardList, ChatList } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
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

  const onLogin = useCallback(() => {
    navigator('/login')
  }, [])

  const participate = useCallback(() => {
    navigator('/participate')
  }, [])

  return (
    <MainLayout>
    
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
