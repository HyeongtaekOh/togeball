import styled from 'styled-components'
import React, { useState } from 'react'


const ProfileContainer = styled.div`
  position: relative;
  display: inline-block;
`

const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 50%;
`

const Tooltip = styled.div<{ isVisible: boolean }>`
  position: absolute;
  top: 120%; /* 툴팁이 아이콘 아래에 위치하도록 조절 */
  left: 50%;
  transform: translateX(-50%);
  background: white;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 8px;
  opacity: ${(props) => (props.isVisible ? 1 : 0)};
  pointer-events: ${(props) => (props.isVisible ? 'auto' : 'none')};
  transition: opacity 0.3s;
`

// const ProfileInfo = styled.div`
//   ${ ProfileContainer }:hover ${ Tooltip } {
//     opacity: 1;
//     pointer-events: auto;
//   }
// `

const MatchingProfile = ( props ) => {
  const { nickname, age, gender, profileImg, hashtags } = props
  const [isTooltipVisible, setIsTooltipVisible] = useState( false )

  return (
    <ProfileContainer>
      <ProfileImage
        src={profileImg}
        alt={`${nickname}'s profile`}
        onMouseEnter={() => setIsTooltipVisible(true)}
        onMouseLeave={() => setIsTooltipVisible(false)}
      />
      <Tooltip as="div" isVisible={ isTooltipVisible }>
        <p>{ nickname }</p>
        <p>{ age }세</p>
        <p>{ gender }</p>
        <p>{ hashtags }</p>
        {/* 다른 프로필 정보도 추가예정 */}
      </Tooltip>
    </ProfileContainer>
  );
};

export default MatchingProfile