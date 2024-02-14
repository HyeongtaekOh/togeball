import axios from 'axios'

const useAxios = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  // headers: {
  //   'Content-Type': 'application/json',
  // },
  // withCredentials: true,
  // responseType: 'json',
})

useAxios.interceptors.request.use( 
  async( config ) => {
      const accessToken  = localStorage.getItem('accessToken')
    
      if( accessToken ){
        config.headers['Authorization'] = `${ accessToken }`
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
    const { status } = error

    console.log(error)

    if( error?.response?.status === 409 ){
      return error.response
    }

    if( error?.response?.status === 401 || error === 401 ){

      if(localStorage.getItem('refreshToken')){
        const refreshToken = localStorage.getItem('refreshToken')
        console.log(refreshToken)
        const data = { "Authorization-refresh" : refreshToken }
        // const response = await axios.post<string>('https://i10a610.p.ssafy.io:8080/api/auth/reissue', { headers : data });
        // const response = await useAxios.post<string>('/api/auth/reissue', { headers : data });
       
        // localStorage.setItem("accessToken", response?.headers?.authorization )
        // localStorage.setItem("refreshToken", response?.headers[`refresh-token`] )

      } else{
        // if ( window.location.pathname !== "/" && window.location.pathname !== "/home") {
          // window.location.href = "/login"
        // }
      }
    }
  }
)


export const getAxios =  async ( url: string, params?: any )  => {
  try {
    const response = await useAxios.get( url, { params } )
    return response?.data
  } catch( error ){
    return Promise.reject(error)
  }
} 

export const postAxios =  async( url: string, data?: any, multi?:any )  =>{
  try{
    const response = await useAxios.post( url, data, multi )
    console.log(response)
    return response
  } catch( error ){
    return Promise.reject( error )
  }
}

export const patchAxios = async(url: string, data?: any ) =>{
  try{
    const response = await useAxios.patch( url, data )
    console.log(response)
    return response
  } catch( error ){
    return Promise.reject( error )
  }
  // fetch(process.env.REACT_APP_BASE_URL+url, {
	// headers: {
	// 'Accept': '*/*',
	// 'Content-Type': 'application/json',
  // 'charset': 'utf-8',
  // 'Authorization': `${ localStorage.getItem('accessToken')}`
	// },
	// method: "PATCH",	
	// body: JSON.stringify({ data })
  // }).then((response) => 
  //   response.ok ).then((data) =>
	//   console.log(data))
}

export default useAxios
