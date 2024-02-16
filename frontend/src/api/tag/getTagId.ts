import { postAxios } from '../util'

export const getTagId  = async( data : string ) => {
  try{
    const response = await postAxios(`/api/hashtags/content/${data }`)
  } catch( err ){
    console.log(err)
  }
}