import { styled } from 'styled-components'
import Header from 'src/components/app/Header'

const MainWrapper = styled.div` 
    box-sizing: border-box;
    display: flex;
    width: 100%;
    height: 85%;
    flex-direction: column;
    align-items: center;
    margin-top: 100px;
`

const MainLayout = ( props : MainLayoutProps ) =>{

    const { children, title } = props

    return(
        <>
        <Header title={ title }/>
        <MainWrapper>
            { children }
        </MainWrapper>
        </>
    )

}
export default  MainLayout

type MainLayoutProps = {
    children?: React.ReactNode
    title?: string,
}