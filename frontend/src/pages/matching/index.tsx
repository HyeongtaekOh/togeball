import React, { useEffect, useRef } from 'react';
import gsap from 'gsap';
import lufi from 'src/asset/images/lufi.jpg'

const MovingComponent: React.FC = () => {
  const myElementRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // 초기 위치 및 크기 설정
    gsap.set(myElementRef.current, { left: '200px', top: '100px', width: '150px', height: '150px' });

    // 초기 애니메이션 설정
    const initialAnimation = gsap.to(myElementRef.current, {
      duration: 2,
      x: 200,
      y: 100,
      width: '100px',
      height: '100px',
      ease: 'power2.inOut',
    });

    // 주기적으로 애니메이션 업데이트
    const interval = setInterval(() => {
      // 새로운 애니메이션 설정
      const newPositionX = Math.random() * 400;
      const newPositionY = Math.random() * 400;

      gsap.to(myElementRef.current, {
        duration: 2,
        x: newPositionX,
        y: newPositionY,
        width: '150px',
        height: '150px',
        ease: 'power2.inOut',
      });
    }, 900);

    // 컴포넌트가 언마운트되면 애니메이션 및 인터벌 정리
    return () => {
      initialAnimation.kill();
      clearInterval(interval);
    };
  }, []); // 빈 배열은 컴포넌트가 마운트될 때 한 번만 실행

  return (
    <div
      ref={myElementRef}
      style={{ width: '100px', height: '100px', position: 'relative', left: '0', top: '0', borderRadius: '100px'}}
    >
      <img src={lufi} alt="" />
    </div>
  );
};

export default MovingComponent;
