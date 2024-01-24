import Logo from 'src/asset/images/Logo.jpg'
import { useNavigate } from "react-router-dom"
import { useCallback } from "react"
import styled from "styled-components"

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
`

const LeftHeader = () => {

  const navigator = useNavigate()

  const onClickLogo = useCallback(() => {
    navigator('/home')
  },[])

  return(
    <BtnWrapper onClick={ onClickLogo }><ImgWrapper src= { Logo } alt="없다"/></BtnWrapper>
  )
}

export default LeftHeader