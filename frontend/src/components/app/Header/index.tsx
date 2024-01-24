import { LeftHeader, RightHeader } from './components';
import styled from 'styled-components'


const HeaderWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  height: 60px;
  padding: 50px 70px;
  align-items: center;
`

const Header = (props: HeaderProps) => {

  const { title } = props;
  
  return(
    <HeaderWrapper>
      <LeftHeader children={ title }/>
      <RightHeader />
    </HeaderWrapper>
  )

}

export default Header

type HeaderProps = {
  title?: string,
}