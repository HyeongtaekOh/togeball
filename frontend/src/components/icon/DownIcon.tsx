import styled from 'styled-components'

const ButttonWrapper = styled.button<{ size : number }>`
    box-sizing: border-box;
    display: flex;
    width: ${( props ) => props.size }px;
    padding: 0px;
    border: none;
    outline: none;
    cursor: pointer;
    background: none;
`

const DownIcon = ( props: IconProps ) => {

    const { size = 20, onClick } = props

    return(
        <ButttonWrapper size= { size } onClick= { onClick }>
          <img width={ size } height={ size } src="https://img.icons8.com/material-rounded/24/expand-arrow--v1.png" alt="expand-arrow--v1"/>
        </ButttonWrapper>
    )
}

export default DownIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}