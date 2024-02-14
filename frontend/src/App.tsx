import { Suspense, useEffect, useState } from 'react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from 'react-query' // react-query 임포트
import { ReactQueryDevtools } from 'react-query/devtools' // DevTools를 사용하려면 임포트
import './asset/css/reset.css'
import './asset/css/common.css'
import routers from './pages/router'
import useStore from './store'
import { getMyChats, getUserInfo } from './api'

const queryClient = new QueryClient() // QueryClient 생성

function App() {

  const { accessToken, setAccessToken, setIsLogin, setSession } = useStore()

  useEffect( () => {
    if( localStorage.getItem('accessToken') ) {
      const setUser = async() => {
        const possible = await getMyChats()
        if( possible ){
          const user = await getUserInfo(localStorage.getItem('userId'))
          setIsLogin( true )
          setAccessToken( localStorage.getItem( 'accessToken' ) )
          setSession( user )
        }
        // else{
        //   localStorage.removeItem( 'accessToken' )
        //   localStorage.removeItem( 'refreshToken' )
        //   localStorage.removeItem( 'userId' )
        //   window.location.reload()         
        // }
      }

      setUser()
      
  }
  }, [ setAccessToken, setIsLogin, setSession, accessToken ] )


  const [ router ] = useState( createBrowserRouter( routers ))

  return (
    <QueryClientProvider client={ queryClient }> {/* QueryClientProvider 추가 */}
      <Suspense>
        <RouterProvider router={ router } />
      </Suspense>
      <ReactQueryDevtools /> {/* DevTools를 사용하려면 추가 */}
    </QueryClientProvider>
  )
}

export default App
