import { getAxios } from "src/api/util"

interface paramType {
  startDate : string,
  endDate: string
}

export const getGames = async( param : paramType ) => {
  try{
    return await getAxios('/api/league/games', param )
  } catch( err ){
    console.log(err)
  }
}