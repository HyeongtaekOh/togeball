import { styled } from 'styled-components'

const HomeWrapper = styled.div`
	display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 15px;
`

const HomeLayout = ( props : HomeLayoutProps ) =>{

    const { children } = props

    return(
        <HomeWrapper>
            { children }
        </HomeWrapper>
    )

}

export default  HomeLayout

type HomeLayoutProps = {
    children?: React.ReactNode
}