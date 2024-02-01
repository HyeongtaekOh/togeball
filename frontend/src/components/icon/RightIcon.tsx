
import styled from 'styled-components';

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

const RightIcon = ( props: IconProps ) => {

    const { size = 40, onClick, disabled= false } = props

    return(
        <ButttonWrapper size={ size } onClick={ onClick }>
          <img width={ size } height={ size } src="https://img.icons8.com/external-those-icons-lineal-those-icons/24/external-right-arrows-those-icons-lineal-those-icons-1.png" alt="external-right-arrows-those-icons-lineal-those-icons-1"/>
        </ButttonWrapper>
    )
}

export default RightIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
    disabled?: boolean
}