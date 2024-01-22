import styled, { css } from 'styled-components';
import { Title } from 'src/components'

const HomeCardWrapper = styled.div<{ type: string, color: string }>`
    padding: 20px;
    border-radius: 10px;
    background-color: ${( props ) => props.color};
    display: flex;
    flex-direction: column;
    cursor : pointer;
    box-shadow: 5px 5px 5px lightGray;
    width: 90%;
    height: 70%;
    margin:0 auto;

    &:hover{
      transform: translateY(-10px);
    }

    ${( props ) => 
      props.type === 'main' 
      && css`

      `
    }

    ${( props ) => 
      props.type === 'sub' 
      && css`
        width: 230px;
        height: 120px;
      `
    }

  `

const HomeCard = ( props : HomeCardProps ) => {

  const { title, type, color = "#6A60A9", children } = props

  const fontColor = color ==='#6A60A9' || type === 'main' ? 'white' : '#746E6E'

  return(
    <HomeCardWrapper type={ type } color={ color }>
      <Title 
        type= { type === 'main'? 'large': 'medium' }
        color= { fontColor } 
        style= {{ textShadow: '1px 1px 1px gray' }}
      > 
        { title } 
      </Title>
      <Title 
        type = 'small' 
        color= { fontColor }  
        style= {{ marginTop: type === 'main'? '90px': '50px' }}
      > 
        { children } 
      </Title>
      {
        type ==='main' 
        && (
          <Title 
            type = 'medium' 
            color= 'white' 
            style ={{ position: 'absolute', margin: '135px 0px 0px 350px' }} 
          >
            Go &gt;
          </Title>
        )
      }
    </HomeCardWrapper>
  )

}

export default HomeCard

type HomeCardProps = {
  type?: 'main' | 'sub',
  color?: string,
  title?: Array<string | any> | string,
  children?: Array<string | any> | string
}