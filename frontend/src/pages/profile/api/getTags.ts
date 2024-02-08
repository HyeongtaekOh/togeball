import { getAxios } from "src/api/util"

interface paramType {
  page: number,
  size: number
}

export const getTags = async( param : paramType ) => {
  try{
    return await getAxios('https://i10a610.p.ssafy.io:8080/api/hashtags', param )
  } catch( err ){
    console.log(err)
  }
}