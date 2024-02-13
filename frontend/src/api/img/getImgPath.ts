import useAxios from "../util"


export const getImgPath = async( path ) => {
    try {
        const response = await useAxios.get( `/api/users/me/profile/presigned-url/${path}` )
        console.log('img', response.data)
        return response?.data
      } catch( error ){
        return Promise.reject(error)
    }
}
