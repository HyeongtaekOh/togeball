import { getAxios } from 'src/api/util'

interface dataType {
  id: string
}

export const getChat = async( data : dataType ) => {
  try{
    return await getAxios(`/api/chatrooms/${ data?.id }`)
  } catch( err ){
    console.log(err)
  }
}