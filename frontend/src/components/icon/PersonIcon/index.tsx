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

const PersonIcon = ( props: IconProps ) => {

    const { size = 50, onClick } = props

    return(
        <ButttonWrapper size={ size } onClick={ onClick }>
            <img width={ size } height={ size} src="https://img.icons8.com/pastel-glyph/64/person-male--v2.png" alt="person-male--v2"/>
        </ButttonWrapper>
    )
}

export default PersonIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}