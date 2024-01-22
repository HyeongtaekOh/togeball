import { styled } from 'styled-components'
import Header from '../../app/Header'

const MainWrapper = styled.div` 
    display: flex;
    height: calc(100vh - 60px);
    flex-direction: column;
    padding: 10px;
    align-items: center;
`

const MainLayout = ( props : MainLayoutProps ) =>{

    const { children } = props

    return(
        <MainWrapper>
            <Header/>
            { children }
        </MainWrapper>
    )

}
export default  MainLayout

type MainLayoutProps = {
    children?: React.ReactNode
}