import { getAxios } from "src/api/util"

export const getClubs = async() => {
  try{
    return await getAxios('/api/league/clubs')
  } catch( err ){
    console.log(err)
  }
}