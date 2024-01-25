import { ClubType } from 'src/types'
import styled from 'styled-components'

const RankItemWrapper = styled.div`
  display: flex;
  box-sizing: border-box;
  width:50%;
  height: 30px;
  align-items: center;
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
      <img style={{ width: '40px' }}/>
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