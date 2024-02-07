import { getAxios } from "src/api/util"

interface paramType {
  page: number,
  size: number
}

export const getTags = async( param : paramType ) => {
  try{
    return await getAxios('/api/hashtags', param )
  } catch( err ){
    console.log(err)
  }
}