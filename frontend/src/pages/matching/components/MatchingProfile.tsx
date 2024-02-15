import styled from 'styled-components'
import React, { useState } from 'react'
import lufi from 'src/asset/images/lufi.jpg'
import { Title } from 'src/components'



const ProfileContainer = styled.div`
  position: relative;
  display: inline-block;
`

const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 50%;
  border : 1px solid lightgray;
  margin-left: 30px;
`

const Tooltip = styled.div<{ isVisible: boolean }>`
  position: absolute;
  width: 200px;
  height: 100px;
  top: 100%; /* 툴팁이 아이콘 아래에 위치하도록 조절 */
  left: 10%;
  transform: translateX(-50%);
  background: white;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  opacity: ${(props) => (props.isVisible ? 1 : 0)};
  pointer-events: ${(props) => (props.isVisible ? 'auto' : 'none')};
  transition: opacity 0.3s;
  z-index: 999;
  display: flex;
  flex-direction: column;    
`



const MatchingProfile = ( props ) => {
  const { name, profileImg, age, gender, myteam, tags  } = props

 
  const [ isTooltipVisible, setIsTooltipVisible ] = useState( false )
  console.log(name, profileImg, age, gender, myteam, tags)

  

  return (
    <ProfileContainer>
      <ProfileImage
      
        src={ profileImg ? profileImg : lufi }
        alt={`${ name }'s profile`}
        onMouseEnter={() => setIsTooltipVisible( true )}
        onMouseLeave={() => setIsTooltipVisible( false )}
      />
      <Tooltip as="div" isVisible={ isTooltipVisible }>
        <div style={{ display: 'flex'}}>
          <img src={ profileImg? profileImg : lufi } alt="" style={{ width: '40%', height: '80%', borderRadius: '50px', marginRight: '10px' }}/>
          <div style={{ width: '40%'}}>
            <Title type='medium'>{ name }</Title>
            <p>{ age }세 { gender }</p>            
          </div>
          <img src={ myteam } alt="" style={{ width: '20%', height: '50%', marginLeft: '5px'}} />
        </div>
        <div style={{ display: 'flex', flexWrap: 'wrap', paddingBottom: '20px'}}>  
        {tags?.map((tag, index) => (
    <p key={index} style={{ fontWeight: 'bold' }}>{tag}</p>
  ))}
        </div>
        {/* 다른 프로필 정보도 추가예정 */}
      </Tooltip>
    </ProfileContainer>
  );
};

export default MatchingProfile