import styled from 'styled-components'

const ButttonWrapper = styled.button<{ size : number }>`
    box-sizing: border-box;
    display: flex;
    width: ${( props ) => props.size }px;
    padding: 0px;
    border: none;
    outline: none;
    cursor: pointer;
`

const NaverIcon = ( props: IconProps ) => {

    const { size = 30, onClick } = props

    return(
        <ButttonWrapper size={ size } onClick = { onClick }>
          <svg width={size} height={size} viewBox='0 0 20 20' fill='none' xmlns='http://www.w3.org/2000/svg'>
            <g clipPath='url(#clip0_403_243)'>
            <path d='M18 20H2C0.9 20 0 19.1 0 18V2C0 0.9 0.9 0 2 0H18C19.1 0 20 0.9 20 2V18C20 19.1 19.1 20 18 20Z' fill='#03C75A'/>
            <path d='M11.35 10.25L8.50002 6.19995H6.15002V13.8H8.65002V9.74995L11.5 13.8H13.85V6.19995H11.35V10.25Z' fill='white'/>
            </g>
            <defs>
            <clipPath id='clip0_403_243'>
            <rect width='20' height='20' fill='white'/>
            </clipPath>
            </defs>
         </svg>
        </ButttonWrapper>
    )
}

export default NaverIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}