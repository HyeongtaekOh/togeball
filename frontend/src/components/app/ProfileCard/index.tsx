import { Title } from 'src/components'
import { ParticipantsType } from 'src/types'
import Logo from 'src/asset/images/Logo.jpg'
import styled from 'styled-components'

const ProfileCardWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  box-sizing: border-box;
  border-radius: 10px;
  border: 1px solid black;
  border-color: lightGray;
  width: 100%;
  max-height: 100px;
  padding: 7px;
`
const ProfileImage = styled.img`
  height: 42px;
  width: 42px;
  object-fit: cover;
  border-radius: 10%;
`
const TeamImage = styled.img`
  border-radius: 50%;
  height: 23px;
  width: 23px;
  object-fit: cover;
  object-position: center; 
`
const ProfileTagWrapper = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  font-size: 9px;
  overflow: hidden;
  gap: 3px;
  width: 100%;
  margin-top: 2px;
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
        <div style={{ display: 'flex', gap: '7px' }}>
          <ProfileImage src = { participant?.profileImage || Logo } alt=''/>
          <div>
            <Title type='small' bold = { true }>
            {( participant?.nickname ).replace('Guest','').substring(0, 7) }    
            { participant?.nickname?.length > 13 &&  '..' }
            </Title>
            <Title type='small' >{ age ? `${age}세`: '' } { gender || '' }</Title>
          </div>
          </div>
          <TeamImage src = { participant?.clubLogo }/>
        </InfoWrapper>
        <ProfileTagWrapper>
            { 
              participant?.tags?.slice(0, 10).map((tag, index) => (
               <div>#{ tag?.content }</div>
              ))
            }
        </ProfileTagWrapper>
      </ProfileCardWrapper>
    )
  
  }

  export default ProfileCard

  type ProfileCardProps = {
    participant?: ParticipantsType; 
  }