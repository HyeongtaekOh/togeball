import { getAxios } from 'src/api/util'

interface dataType {
  id: string
}

export const getParticipants = async( data : dataType ) => {
  try{
    return await getAxios(`/api/chatrooms/${ data?.id }/participants`)
  } catch( err ){
    console.log(err)
  }
}