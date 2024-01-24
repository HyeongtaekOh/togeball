import axios from 'axios'
import useStore from 'src/store'

const useAxios = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
  responseType: "json",
  timeout: 10000,
})



useAxios.interceptors.request.use( 
  async( config ) => {
    const { accessToken, setAccessToken } = useStore()
    
      if( accessToken ){
        config.headers["Authorization"] = `Bearer ${accessToken}`
      }
      return config
  },
  ( error ) => {
    return Promise.reject( error )
  }
)

useAxios.interceptors.response.use(
  async( response ) => {
    return response
  },
  async( error ) => {
    const { status } = error.response;

    const { accessToken, setAccessToken } = useStore()

    if( status === 401 ){
      const reAccessToken = await useAxiosRefreshToken()
      setAccessToken( reAccessToken )
    }

  }

)

const useAxiosRefreshToken: () => Promise<string> = async() =>{
  const { data } = await useAxios.post<string>("/refresh");
  return data
}

export const getAxios =  async ( url: string, params?: any )  => {
    try {
      const response = await useAxios.get( url, { params } )
      return response.data;
    } catch( error ){
      return Promise.reject(error)
    }
} 

export const postAxios =  async( url: string, data?: any )  =>{
  try{
    const response = await useAxios.post( url, data )
    return response.data
  } catch( error ){
    return Promise.reject( error )
  }
}

export default useAxios