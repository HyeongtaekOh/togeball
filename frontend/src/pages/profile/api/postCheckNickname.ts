import { postAxios } from 'src/api/util'

export const postCheckNickname = async( nickname: string) => {
    try{
        const res = await postAxios('/api/users/', nickname)
        return res.data.available
    }catch( err ){
        console.log(err)
        return false
    }
}
