import { RecruitType } from '@/types'
import { postAxios } from 'src/api/util'

export const makeRecruitChat = async( data : RecruitType ) => {
  try{
    console.log("hi", data)
    const response = await postAxios(`/api/chatrooms`, data )
    console.log(response)
    return response
  } catch( err ){
    console.log(err)
  }
}