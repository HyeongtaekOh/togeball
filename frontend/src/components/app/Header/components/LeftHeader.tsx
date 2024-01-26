import Logo from 'src/asset/images/Logo.jpg'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import styled from 'styled-components'

const ImgWrapper = styled.img`
  width: 100px;
  height: 80px;
  object-fit: cover;
`

const BtnWrapper = styled.button`
  border: none;
  background: none;
  outline: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 10px;
`

const TitleWrapper = styled.span`
  font-size: 24px;
  font-weight: bold;
  margin-left: 14px;
  margin-top: 50px;
`

const LeftHeader = (props: LeftHeaderProps) => {

  const { children } = props;

  const navigator = useNavigate()

  const onClickLogo = useCallback(() => {
    navigator('/home')
  },[])

  return(
    <>
    <div style={{ display: 'flex', flexDirection: 'row' }}>
      <BtnWrapper onClick={ onClickLogo }><ImgWrapper src= { Logo } alt='없다'/></BtnWrapper>
      <TitleWrapper>{ children }</TitleWrapper>
    </div>
    </>
  )
}

export default LeftHeader

type LeftHeaderProps = {
  children?: string,
}