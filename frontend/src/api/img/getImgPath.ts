import useAxios from "../util"


export const getImgPath = async( ) => {
    try {
        const response = await useAxios.get( 'https://i10a610.p.ssafy.io:8080/api/users/me/profile/presigned-url' )
        return response?.data
      } catch( error ){
        return Promise.reject(error)
    }
}
