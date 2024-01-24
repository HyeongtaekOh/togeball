import { styled } from 'styled-components'

const SignWrapper = styled.div`
    box-sizing: border-box;
	display: flex;
    width: 500px;
    height: 100%;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    gap: 30px;
    margin: auto;
`

const SignLayout = ( props : SignLayoutProps ) =>{

    const { children, style } = props

    return(
        <SignWrapper style = { style }>
            { children }
        </SignWrapper>
    )

}

export default  SignLayout

type SignLayoutProps = {
    children?: React.ReactNode
    style?: React.CSSProperties
}