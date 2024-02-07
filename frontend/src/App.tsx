import { Suspense, useEffect, useState } from 'react'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import { QueryClient, QueryClientProvider } from 'react-query' // react-query 임포트
import { ReactQueryDevtools } from 'react-query/devtools' // DevTools를 사용하려면 임포트
import './asset/css/reset.css'
import './asset/css/common.css'
import routers from './pages/router'
import useStore from './store'
import { RouterInfo } from './util/router'

const queryClient = new QueryClient() // QueryClient 생성

function App() {

  const { setAccessToken, setIsLogin } = useStore()

  useEffect( () => {
    if( localStorage.getItem('accessToken') ) {
        setIsLogin( true )
        setAccessToken( localStorage.getItem('accessToken') )
    }
  }, [] )


  const [ router ] = useState( createBrowserRouter( routers ))
  const routerObject = createBrowserRouter( RouterInfo )

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
