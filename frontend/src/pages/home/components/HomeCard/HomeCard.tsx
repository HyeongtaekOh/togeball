import styled from 'styled-components';


const HomeCardWrapper = styled.div`
    width: 100%;
    height: 100%;
    padding: 10px;
  `

const HomeCard = ( props : HomeCardProps ) => {

  const { title, description } = props

  return(
    <HomeCardWrapper>
      <h1>{ title }</h1>
      <p>{ description }</p>
    </HomeCardWrapper>
  )

}

export default HomeCard

type HomeCardProps = {
  type?: 'main' | 'sub',
  color?: string,
  title?: string,
  description?: string
}