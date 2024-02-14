import { RecruitType } from 'src/types'
import { postAxios } from 'src/api/util'

export const makeRecruitChat = async( data : RecruitType ) => {
  try{
    const response = await postAxios(`/api/chatrooms`, data )
    return response
  } catch( err ){
    console.log(err)
  }
}