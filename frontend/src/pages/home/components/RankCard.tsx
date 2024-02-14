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

  const rankList = useMemo(() => { 
    return clubList.sort(( a, b )=> { return a.ranking - b.ranking }
  )}, [ clubList ] )

  console.log(clubList)

  return(
    <RankCardWrapper>
      {
        rankList.map(( club, index ) => {
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