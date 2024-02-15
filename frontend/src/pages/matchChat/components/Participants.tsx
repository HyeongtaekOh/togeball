import { ProfileCard, Title } from 'src/components'
import styled from 'styled-components'

const PartiWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  width: 25%;
  min-width: 200px;
  border-radius: 20px 20px 10px 10px;
  box-shadow: 1px 1px 2px 2px lightGray;
`
const HeaderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  gap: 5px;
  height: 50px;
  width: 100%;
  border-radius: 20px 20px 0px 0px;
  background-color: #6A60A9;
  box-shadow: 1px 1px 2px 2px lightGray;
  color : white;
  font-size: 10px;
`
const MemberWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-left: 10px;
  margin-top: 10px;
  gap: 5px;
  height: 100%;
  padding-top: 10px;
  padding: 5px;
  overflow-y: scroll;
  overflow-x: hidden;
`
const Participants = ( props ) => {

  const { list } = props

  return(
    <PartiWrapper>
      <MemberWrapper>
      {
        list?.map(( participant, index ) => (
          <ProfileCard key={ participant?.id } participant = { participant }/>
        ))
      }
      </MemberWrapper>
    </PartiWrapper>
  )
}

export default Participants