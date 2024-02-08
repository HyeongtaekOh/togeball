import axios from 'axios'
import useStore from 'src/store'

const useAxios = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  // headers: {
  //   'Content-Type': 'application/json',
  // },
  // withCredentials: true,
  // responseType: 'json',
  timeout: 10000,
})

useAxios.interceptors.request.use( 
  async( config ) => {
    const accessToken  = localStorage.getItem('accessToken')
    
      if( accessToken ){
        config.headers['Authorization'] = `Bearer ${ accessToken }`
      }
      return config
  },
  ( error ) => {
    return Promise.reject( error )
  }
)

// useAxios.interceptors.response.use(
//   async( response ) => {
//     return response
//   },
//   async( error ) => {
//     const { status } = error.response;

//     const { setAccessToken } = useStore()

//     if( status === 401 ){
//       const refreshToken = localStorage.getItem('refreshToken')
//       const data = { "Authorization-refresh" : refreshToken }
//       const response = await useAxios.post<string>('/api/auth/reissue', { headers : data });
//       setAccessToken( response?.headers?.authorization )
//       localStorage.setItem("accessToken", response?.headers?.authorization )
//       // localStorage.setItem("refreshToken", response?.headers[`refresh-token`] )
//     }
//   }
// )


export const getAxios =  async ( url: string, params?: any )  => {
  try {
    const response = await useAxios.get( url, { params } )
    console.log(process.env.REACT_APP_BASE_URL)
    return response.data
  } catch( error ){console.log(process.env.REACT_APP_BASE_URL)
    return Promise.reject(error)
  }
} 

export const postAxios =  async( url: string, data?: any )  =>{
  try{
    const response = await useAxios.post( url, data )
    console.log(response)
    return response
  } catch( error ){
    return Promise.reject( error )
  }
}

export default useAxios
