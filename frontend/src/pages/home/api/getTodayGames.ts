import { getAxios } from "src/api/util"

export const getTodayGames = async() => {
  try{
    return await getAxios('/api/league/games', { startDate: '2024-03-24', endDate: '2024-03-24' })
  } catch( err ){
    console.log(err)
  }
}