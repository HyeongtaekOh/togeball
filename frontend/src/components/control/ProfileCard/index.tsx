import { ParticipantsType } from '@/model/types/Participands';
import styled from 'styled-components';


const ProfileCardWrapper = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    box-sizing: border-box;
    border-radius: 10px;
    border: 1px solid black;
    border-color: lightGray;
    display: flex;
    width: 200px;
    min-height: 80px;
`;

const ProfileImage = styled.img`
  border-radius: 50%;
  height: 42px;
  width: 42px;
  margin-left: 10px;
  margin-top: 10px;
`;

const TeamImage = styled.img`
  border-radius: 50%;
  height: 36px;
  width: 36px;
  margin-right: 4px;
  margin-top: 4px;
`;

const ProfileTagWrapper = styled.div`
  width : 100%;
  display: flex;
  justify-content: center;
`;


const ProfileTags = styled.div`
  display: flex;
  justify-content: center;
  font-size: 12px;
  margin-bottom: 4px;
  overflow: hidden;
  gap: 3px;
  padding: 0px 20px;
  width: 60%;
`;

  const ProfileCard = ( props : ProfileCardProps ) => {

    const { participant } = props
  
    return(
      <ProfileCardWrapper>
        <div style={{ display: 'flex'}}>
          <ProfileImage src = { participant.profileImg }/>
          <span style={{ margin: '10px 20px', fontSize: '12px' }}>
            <p style={{ fontWeight: 'bold', marginBottom: '3px' }}>{ participant.nickname }</p>
            { participant.age }세 { participant.gender }
          </span>
          <TeamImage src = { participant.profileImg }/>
      </div>
      <ProfileTagWrapper>
        <ProfileTags>
          { 
            participant.hashtags.map((tag, index) => (
             <div>{ tag }</div>
            ))
          }
        </ProfileTags>
      </ProfileTagWrapper>
      </ProfileCardWrapper>
    )
  
  }


  export default ProfileCard

  type ProfileCardProps = {
    participant?: ParticipantsType; 
  }