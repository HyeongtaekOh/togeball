import useAxios from "../util"


export const getImgPath = async() => {
    try {
        const response = await useAxios.get( `/api/users/me/profile/presigned-url` )
        return response?.data
      } catch( error ){
        return Promise.reject(error)
    }
}
