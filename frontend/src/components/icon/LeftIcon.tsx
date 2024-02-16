
import styled from 'styled-components'

const ButttonWrapper = styled.button<{ size : number }>`
    box-sizing: border-box;
    display: flex;
    width: ${( props ) => props.size }px;
    padding: 0px;
    border: none;
    outline: none;
    cursor: pointer;
    background-color: transparent;
`

const LeftIcon = ( props: IconProps ) => {

    const { size = 40, onClick } = props

    return(
        <ButttonWrapper size={ size } onClick={ onClick }>
           <img width={ size } height={ size } src="https://img.icons8.com/external-those-icons-lineal-those-icons/24/external-left-arrows-those-icons-lineal-those-icons.png" alt="external-left-arrows-those-icons-lineal-those-icons"/>
        </ButttonWrapper>
    )
}

export default LeftIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}