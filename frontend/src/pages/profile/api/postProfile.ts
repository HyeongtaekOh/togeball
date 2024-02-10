import { TagType } from 'src/types'
import { postAxios } from 'src/api/util'

interface dataType {
        nickname?: string,
        clubId?: number,
        role?: string,
        profileImage?: object,
        tags?: TagType[]
}

export const postProfile = async( data: dataType ) => {
    try{
        return await postAxios('/api/users/tmp/', data)
    }catch( err ){
        console.log(err)
    }
}
