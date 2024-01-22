import styled, {css} from "styled-components";

const BoardWrapper = styled.div<BoardListProps>`
    display: flex;
    Background-color: #7D74B4;
    border-radius: 10px;
    width: 100%;
    height: 60px;
    

    &:hover{
      background-color: #9008F2;
      cursor: pointer;
    } 

    ${props =>
    props.type === "sub" &&
    css`
        display: flex;
        flex-direction: column;
        margin-left: 10px;
        justify-content: space-around;
        width: 80%
        
      `
    }
`

const LogoWrapper =styled.img`
  width: 10%;
`

const TitleWrapper = styled.p`
    color: black;
`

const CreateTimeWrapper = styled.p`
    color: lightgray;
    font-size: 10px;
`
const UserWrapper = styled.div`
    color: white;
    padding-bottom: 10px;
    display: flex;
    align-items: flex-end;
    justify-content: center;

    
`

const BoardList = (props: BoardListProps) => {

  const {
    children, title,logo, createdAt, user, onClick, type = 'main'
  } = props

  return(
    <BoardWrapper>
      <LogoWrapper  src={logo} alt="이미지" />
      <BoardWrapper type="sub">
      <TitleWrapper>{title}</TitleWrapper>
      <CreateTimeWrapper>{createdAt}</CreateTimeWrapper>
      </BoardWrapper>
      <UserWrapper>{user}</UserWrapper>

    </BoardWrapper>
  )
}

export default BoardList

type BoardListProps = {
  children?: React.ReactNode,
  title?: string,
  createdAt?: string,
  user?: string,
  onClick?: () => void,
  type?: 'main'|'sub',
  logo?: string

}