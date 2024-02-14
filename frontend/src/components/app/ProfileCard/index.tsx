import { Title } from 'src/components'
import { ParticipantsType } from 'src/types'
import lufi from 'src/asset/images/lufi.jpg'
import styled from 'styled-components'


const ProfileCardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-sizing: border-box;
  border-radius: 10px;
  border: 1px solid black;
  border-color: lightGray;
  width: 100%;
  min-height: 80px;
  padding: 7px;
`
const ProfileImage = styled.img`
  border-radius: 50%;
  height: 42px;
  min-width: 42px;
`
const TeamImage = styled.img`
  border-radius: 50%;
  height: 36px;
  width: 36px;
`
const ProfileTagWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`
const ProfileTags = styled.div`
  display: flex;
  justify-content: center;
  font-size: 12px;
  margin-bottom: 4px;
  overflow: hidden;
  gap: 3px;
  padding: 0px 20px;
  width: 60%;
`
const InfoWrapper = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
`

  const ProfileCard = ( props : ProfileCardProps ) => {

    const { participant } = props
    const age 
      = new Date().getFullYear() - Number(participant?.birthdate?.substring(0,4)) + 1
    const gender = participant?.gender === 'FEMALE' ? '여성' : '남성'

    return(
      <ProfileCardWrapper>
        <InfoWrapper>
          <ProfileImage src = {( participant?.profileImage===null || participant?.profileImage==='' ) ? lufi : participant?.profileImage } alt=''/>
          <div>
            <Title type='small' bold = { true }>{ participant?.nickname }</Title>
            <Title type='small' >{ age ? `${age}세`: '' } { gender }</Title>
          </div>
          <TeamImage src = { participant?.clubLogo }/>
        </InfoWrapper>
        <ProfileTagWrapper>
          <ProfileTags>
            {/* { 
              participant?.tags?.map((tag, index) => (
               <div>{ tag }</div>
              ))
            } */}
          </ProfileTags>
        </ProfileTagWrapper>
      </ProfileCardWrapper>
    )
  
  }


  export default ProfileCard

  type ProfileCardProps = {
    participant?: ParticipantsType; 
  }