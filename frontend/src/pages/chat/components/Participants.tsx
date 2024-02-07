import styled from "styled-components"

const PartiWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  width: 35%;
  border-radius: 20px 20px 10px 10px;
  box-shadow: 1px 1px 2px 2px lightGray;
  height: 83%;
  max-height: 83%;
`
const HeaderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  height: 50px;
  width: 100%;
  border-radius: 20px 20px 0px 0px;
  background-color: #6A60A9;
  box-shadow: 1px 1px 2px 2px lightGray;
  color : white;
  align-items: center;
  justify-content: center;
  font-size: 10px;
`
const Participants = () => {


  return(

    <PartiWrapper>
      <HeaderWrapper>
        2024 - 01 - 15 두산 vs LG 6: 00
      </HeaderWrapper>
    </PartiWrapper>
  )
}

export default Participants