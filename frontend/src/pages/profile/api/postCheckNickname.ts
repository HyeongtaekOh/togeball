import { getAxios } from 'src/api/util'

export const getCheckNickname = async( nickname) => {
    try{
        const res = await getAxios(`/api/users/nickname?nickname=${ nickname }`)
        console.log(res)
        return res
    }catch( err ){
        console.log(err)
        return false
    }
}
