import { MainLayout, Select } from 'src/components'
import { useState } from 'react'

const Home = () => {

  const [ dataSource, setDataSource ] = useState([
    { name : "lg", value: "1" }
  ])

  return (
    <MainLayout>
      <Select dataSource = { dataSource } placeholder='응원팀'/>
    </MainLayout>
  )

}

export default Home
