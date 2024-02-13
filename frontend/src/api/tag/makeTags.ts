import { postAxios } from 'src/api/util'


interface dataType{
  tags : string[]
}

export const makeTags = async( data : dataType ) => {
  try{
    return await Promise.all( data?.tags.map(async( tag ) => {
      const response = await postAxios(`/api/hashtags`, { content: tag })
      return response?.data?.id
    }))
  } catch( err ){
    console.log(err)
  }
}

const getTagId  = async( data : string ) => {
  try{
    const response = await postAxios(`/api/hashtags/content/${data }`)
    console.log(response)
  } catch( err ){
    console.log(err)
  }
}