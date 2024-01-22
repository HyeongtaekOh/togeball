import { Suspense, useState } from 'react';
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import './asset/css/reset.css';
import './asset/css/common.css';
import routers from './pages/router';

function App() {
  const [ router ] = useState( createBrowserRouter( routers ) )

  return (
    <>
    <Suspense>
      <RouterProvider router={ router }/>
    </Suspense>
</>
  );
}

export default App;
