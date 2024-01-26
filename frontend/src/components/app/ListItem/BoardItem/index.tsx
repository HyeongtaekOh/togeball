import styled, { css } from "styled-components";
import { BoardType } from "src/types/Board";

const BoardWrapper = styled.div<BoardListProps>`
    display: flex;
    Background-color: #DEDCEE;
    border-radius: 10px;
    width: 1000px;
    height: 60px;
    cursor: pointer;

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

const CreateTimeWrapper = styled.p`
    color: #A4A29F;
    font-size: 10px;
`
const UserWrapper = styled.div`
    color: black;
    padding-bottom: 10px;
    display: flex;
    align-items: flex-end;
    justify-content: center;

    
`

const BoardItem = ( props: BoardListProps ) => {

  const { board, onClick, type = 'main' } = props

  const { creatorName, title, createdTime, logo } = board

  return(
    <BoardWrapper >
      <img style={{ width: '10%', borderRadius:'10px' }} src={ logo} alt="" ></img>
      <BoardWrapper type="sub">
      <p style={{ color: 'black' }}>{ title }</p>
      <CreateTimeWrapper>{ createdTime }</CreateTimeWrapper>
      </BoardWrapper>
      <UserWrapper>{ creatorName }</UserWrapper>

    </BoardWrapper>
  )
}

export default BoardItem

type BoardListProps = {
  board?: BoardType,
  type?: 'main'|'sub',
  onClick? : () => void

}