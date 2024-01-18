import { styled } from 'styled-components'

const MainWrapper = styled.div`     
    display: flex;
    flex-direction: column;
    padding: 10px;
`

const MainLayout = ( props : MainLayoutProps ) =>{

    const { children } = props

    return(
        <MainWrapper>
            { children }
        </MainWrapper>
    )

}
export default  MainLayout

type MainLayoutProps = {
    children?: React.ReactNode
}