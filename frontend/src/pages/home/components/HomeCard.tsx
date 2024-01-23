import styled, { css } from 'styled-components';
import { Title } from 'src/components'

const HomeCardWrapper = styled.div<{ type: string, color: string }>`
    padding: 20px;
    border-radius: 10px;
    background-color: ${( props ) => props.color };
    display: flex;
    flex-direction: column;
    cursor : pointer;
    width: 90%;
    height: 80%;
    margin:0 auto;

    ${( props ) => 
      props.type === 'main' 
      && css`
      box-shadow: 5px 5px 5px lightGray;

      &:hover{
        transform: translateX(20px);
      }  
      `
    }

    ${( props ) => 
      props.type === 'sub' 
      && css`
        width: 250px;
        height: 120px;
        box-shadow: 2px 2px 2px lightGray;

        &:hover{
          box-shadow: 5px 5px 5px lightGray;
        }
      `
    }

  `

const HomeCard = ( props : HomeCardProps ) => {

  const { title, type, color = "#6A60A9", children } = props

  const fontColor = color ==='#6A60A9' || type === 'main' ? '#FEFEFC' : '#746E6E'

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
        style={{ marginTop: type==='main'? '40px': '55px' }}
      > 
        { children } 
      </Title>
      {
        type ==='main' 
        && (
          <Title 
            type = 'medium' 
            color= 'white' 
            style ={{ position: 'absolute', margin: '140px 0px 0px 360px' }} 
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