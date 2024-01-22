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

const ChatIcon = ( props: IconProps ) => {

    const { size = 30, onClick } = props

    return(
        <ButttonWrapper size={ size } onClick={ onClick }>
        <img width={ size } height={ size } 
         src="https://img.icons8.com/material-outlined/24/chat.png" alt="chat"/>
        </ButttonWrapper>
    )
}

export default ChatIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}