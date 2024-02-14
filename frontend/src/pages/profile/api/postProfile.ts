import { patchAxios } from 'src/api/util'

interface dataType {
        nickname?: string,
        clubId?: number,
        role?: string,
        gender?: string,
        birthdate?: Date,
        profileImage?: string,
        tags?: number[]
}

export const patchProfile = async( data: dataType ) => {
    try{
        return await patchAxios('/api/users/me', data)
    }catch( err ){
        console.log(err)
    }
}
