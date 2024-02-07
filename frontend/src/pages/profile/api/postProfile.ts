import { TagType } from 'src/types'
import { postAxios } from 'src/api/util'

interface dataType {
        nickname?: string,
        team?: number,
        profileImage?: object,
        tags?: TagType[]
}

export const postProfile = async( data: dataType ) => {
    try{
        return await postAxios('/users', data)
    }catch( err ){
        console.log(err)
    }
}
