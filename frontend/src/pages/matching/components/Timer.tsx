import React, { useState, useEffect } from 'react'
import styled from 'styled-components'

// interface TimerProps {
//   duration: number; // 타이머 기간 (초)
// }

const TimerWrapper = styled.p`
  font-size: 30px;
  font-weight: bold;
  color: lightgrey; 
`

const Timer = ( props ) => {
  const { duration } = props
  
  const [ elapsedTime, setElapsedTime ] = useState(0)

  useEffect(() => {
    const intervalId = setInterval(() => {
      setElapsedTime(( prevElapsedTime ) => prevElapsedTime + 1)
    }, 1000)

    return () => clearInterval( intervalId ); // 컴포넌트가 언마운트되면 인터벌 해제
  }, [])

  const formatTime = ( seconds: number ) => {
    const minutes = Math.floor( seconds / 60 )
    const remainingSeconds = seconds % 60
    return `${ minutes }:${ remainingSeconds < 10 ? '0' : '' }${ remainingSeconds }`
  }

  return (
    <div style={{ backgroundColor : 'inherit', marginBottom: '10%', position: 'absolute' }}>     
      <TimerWrapper>경과 시간: { formatTime( elapsedTime ) }</TimerWrapper>     
    </div>
  )
}

export default Timer
