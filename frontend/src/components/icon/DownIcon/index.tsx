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
          <img width={ size } height={ size } src="https://img.icons8.com/external-others-inmotus-design/67/external-Down-basic-functions-others-inmotus-design.png" alt="external-Down-basic-functions-others-inmotus-design"/>
        </ButttonWrapper>
    )
}

export default DownIcon

type IconProps = {
    size?: number,
    onClick?: () => void,
}