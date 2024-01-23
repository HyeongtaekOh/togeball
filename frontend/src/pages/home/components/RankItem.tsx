import { ClubType } from "src/model/types"
import styled from "styled-components"

const RankItemWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width:50%;
  height: 40px;
  border-radius: 5px;
  background-color: #DEDCEE;
  gap: 40px;
  padding: 10px 20px;
`

const RankItem = ( props : RankItemProps ) => {

  const { club } = props
  const { clubName, ranking } = club

  return(
    <RankItemWrapper>
      <p style={{ fontWeight: 'bold' }}>{ ranking }</p>
      <img style={{ width: '20px' }} alt="이미지"/>
      <div style={{ width: '100%', textAlign: 'center' }}>
        <p style={{ fontWeight: 'bold' }}>{ clubName }</p>
      </div>
    </RankItemWrapper>
  )
}

export default RankItem

type RankItemProps = {
  club : ClubType, 
}