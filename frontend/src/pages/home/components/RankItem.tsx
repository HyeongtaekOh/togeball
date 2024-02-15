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
  gap: 35%;
  padding: 10px 20px;
`

const RankItem = ( props : RankItemProps ) => {

  const { club } = props
  const { sponsorName, ranking, clubLogo } = club

  return(
    <RankItemWrapper>
      <div style={{ width : '10px' }}>
      <p style={{ fontWeight: 'bold'}}>{ ranking }</p>
      </div>
      <div style={{ display: 'flex', gap: '5px', marginRight: '40px',minWidth:'100px' }}>
        <p style={{ fontWeight: 'bold', marginTop: '3px' }}>{ sponsorName }</p>
        <img src = { clubLogo } style={{ width: '30px', height:'20px'}}/>
      </div>
    </RankItemWrapper>
  )
}

export default RankItem

type RankItemProps = {
  club : ClubType, 
}