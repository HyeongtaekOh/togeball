import { ClubType } from 'src/types'
import { useMemo } from 'react'
import { RankItem } from './index'
import styled from 'styled-components'

const RankCardWrapper = styled.div`
  box-sizing: border-box;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  gap: 7px;
  height: 180px;
`

const RankCard = ( props : RankCardProps ) => {

  const { clubList } = props
  const rankList = [ ...clubList ].sort((a, b) => a.ranking - b.ranking )

  return(
    <RankCardWrapper>
      {
        rankList?.map(( club, index ) => {
          return(
            <RankItem key={ index } club={ club }/>            
          )
        })
      }
    </RankCardWrapper>
  )

}

export default RankCard

type RankCardProps = {
  clubList : ClubType[],
}