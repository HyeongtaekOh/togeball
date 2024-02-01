import React, { useState, useEffect } from 'react'

// interface TimerProps {
//   duration: number; // 타이머 기간 (초)
// }

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
    <div>
      
      <p>경과 시간: { formatTime( elapsedTime ) }</p>
      
    </div>
  )
}

export default Timer
