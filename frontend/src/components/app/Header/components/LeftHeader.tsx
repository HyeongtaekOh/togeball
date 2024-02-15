import Logo from 'src/asset/images/Logo.jpg'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import styled from 'styled-components'

const ImgWrapper = styled.img`
  width: 110px;
  height: 70px;
  margin-top: 20px;
`
const BtnWrapper = styled.button`
  border: none;
  background: none;
  outline: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
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
    navigator('/')
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