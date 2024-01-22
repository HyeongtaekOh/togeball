import { styled } from 'styled-components'

const HomeWrapper = styled.div`
    box-sizing: border-box;
	display: flex;
    width: 60%;
    height: 100%;
    min-width: 1200px;
    flex-direction: column;
    justify-content: center;
    align-center: center;
    gap: 15px;
`

const HomeLayout = ( props : HomeLayoutProps ) =>{

    const { children, style } = props

    return(
        <HomeWrapper style = { style }>
            { children }
        </HomeWrapper>
    )

}

export default  HomeLayout

type HomeLayoutProps = {
    children?: React.ReactNode
    style?: React.CSSProperties
}