import { getAxios } from 'src/api/util'

export const getCheckNickname = async( nickname) => {
    try{
        const res = await getAxios('/api/users/nickname', { nickname })
        if (res.code === "CONFLICT_NICKNAME") {
            return false;
        } else {
            return true;
        }
    } catch(err) {
        return false;
    }
}
