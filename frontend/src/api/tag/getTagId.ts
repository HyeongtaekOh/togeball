import { postAxios } from '../util'

export const getTagId  = async( data : string ) => {
  try{
    console.log(data)
    const response = await postAxios(`/api/hashtags/content/${data }`)
    console.log(response)
  } catch( err ){
    console.log(err)
  }
}