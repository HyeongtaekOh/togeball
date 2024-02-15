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
  max-height: 200px; /* 내부 컨텐츠의 최대 높이 설정 */
  overflow-y: auto; /* 내부 컨텐츠가 넘칠 때 스크롤 생성 */
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
  margin-top: 2px;    
`



const MatchingProfile = ( props ) => {
  const { name, profileImg, age, gender, myteam, tags  } = props

 
  const [ isTooltipVisible, setIsTooltipVisible ] = useState( false )
  console.log(name, profileImg, age, gender, myteam, tags)

  const newAge = new Date().getFullYear() - Number(age?.substring(0,4)) + 1
  const newGender = gender === 'Female' ? '여성' : '남성'

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
            <p style={{ fontWeight: 'bold', marginTop: '10px' }}> { newGender ? newGender : '성별 비공개' } { newAge ? '(' +newAge + ')' : '나이비공개' }</p>            
          </div>
          <img src={ myteam } alt="" style={{ width: '20%', height: '50%', marginLeft: '5px'}} />
        </div>
        <div style={{ display: 'flex', flexWrap: 'wrap', justifyContent: 'center', marginTop: '15px'}}>  
        {tags?.map((tag, index) => (
    <p key={ index } style={{ fontWeight: 'bold' }}>{'#'+ tag + ' '}</p>
  ))}
        </div>
        
      </Tooltip>
    </ProfileContainer>
  );
};

export default MatchingProfile