import styled from "styled-components"
import Logo from 'src/asset/images/Logo.jpg'

const ImgWrapper = styled.img`
  width: 120px;
  height: 100px;
  object-fit: cover;
`

const LeftHeader = () => {
  return(
    <ImgWrapper src= { Logo } alt="없다"/>
  )
}

export default LeftHeader