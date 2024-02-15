import { styled } from 'styled-components'

const HomeWrapper = styled.div`
    box-sizing: border-box;
	display: flex;
    height: 100%;
    width: 65%;
    min-width: 800px;
    flex-direction: column;
    gap: 13px;
    margin: auto;
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