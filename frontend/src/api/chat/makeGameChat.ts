import { postAxios } from 'src/api/util'

// interface dataType {
//   title?: string,
//   description?: string,
//   capacity?: number
//   managerId?: number,
//   gameId?: number,
//   cheeringClubId?: number,
//   tagIds?: number[]
// }

interface dataType {
    title?: string,
    gameId?: number,
}



export const makeGameChat = async( data : dataType ) => {
  try{
    console.log(data)
    const response = await postAxios(`/api/chatrooms/game`, data )
    return response
  } catch( err ){
    console.log(err)
  }
}