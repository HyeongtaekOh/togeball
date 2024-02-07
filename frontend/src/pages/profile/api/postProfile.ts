import { TagType } from 'src/types'
import { postAxios } from 'src/api/util'

interface dataType {
        id?: number,
        email?: string,
        nickname?: string,
        stadium?: string[],
        team?: string,
        gender?: string,
        birthdate?: string,
        phone?: string,
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
