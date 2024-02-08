import { getAxios } from 'src/api/util'

interface dataType {
  type: string;
}

export const getRecruits = async( data: dataType ) => {
    try{
        const response =  await getAxios(`/api/chatrooms?type=RECRUIT&page=0&size=100`)
        return response
    }catch( err ){
        console.log(err)
    }
}
