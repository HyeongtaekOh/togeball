import { InputBox, Title,Button, 
    MainLayout, SignLayout, HomeLayout } from '../../components'
import { useNavigate } from 'react-router-dom'
import { useCallback } from 'react'
import { styled } from 'styled-components'
  
const SearchWrapper = styled.div`
    display: flex;
    justify-content: space-around;
    align-items: center;
    
`
  
  
const Boards = () => {
  
    


    const onSearch = useCallback(() => {
      
    }, [])
  
    return (
      <MainLayout>
        <SearchWrapper>
       <InputBox width='700px' height='10px' placeholder='검색어를 입력하세요'></InputBox>
       <Button type='reset'>검색</Button>
        </SearchWrapper>
      </MainLayout>
    )

  }
  
  export default Boards