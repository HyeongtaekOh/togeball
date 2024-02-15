import { Title } from 'src/components'
import { useNavigate } from 'react-router-dom'
import styled, { css } from 'styled-components'

const HomeCardWrapper = styled.div<{ type: string, color: string }>`
    box-sizing: border-box;
    padding: 7%;
    padding-right: 0%;
    border-radius: 10px;
    background-color: ${( props ) => props.color };
    display: flex;
    flex-direction: column;
    cursor : pointer;
    width: 100%;
    height: 100%;
    margin:0 auto;

    ${(props) => 
      props.color === '#FFFFFF' 
      && css`
      width: 97%;
      box-shadow: 0px 0px 5px lightGray;

      &:hover{
        box-shadow: 5px 5px 5px lightGray;
      }
      `
    }

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
      && props.color !== '#FFFFFF' && css`
        width: 97%;
        box-shadow: 2px 2px 2px lightGray;

        &:hover{
          box-shadow: 5px 5px 5px lightGray;
        }
      `
    }

  `

const HomeCard = ( props : HomeCardProps ) => {

  const navigator = useNavigate()

  const onClickHandler = () => {
    if( path === '/recruit/post' && !localStorage.getItem('accessToken')){
      alert( '로그인 하세요' )
      navigator('/login')
    }
    else navigator( path )
  }

  const { title, type, color = '#6A60A9', children, path } = props

  const fontColor = color ==='#6A60A9' || type === 'main' ? '#FEFEFC' : '#746E6E'

  return(
    <HomeCardWrapper type={ type } color={ color } onClick={ onClickHandler }>
      <Title 
        type= { type === 'main'? 'large': 'medium' }
        color= { fontColor } 
        style= {{ textShadow: type === 'main' && '1px 1px 1px gray' }}
      > 
        { title } 
      </Title>
      <Title 
        type = 'small' 
        color= { fontColor }
        style= {{ marginTop: type === 'main' ? '20%': '15%' }}
      > 
        { children } 
      </Title>
        { type ==='main' 
          && (
          <Title 
            type = 'medium'  
            color= 'white' 
            style ={{ margin: '0% 0% 0% 80%'}} 
          >
            Go &gt;
          </Title>
        )}
    </HomeCardWrapper>
  )

}

export default HomeCard

type HomeCardProps = {
  type?: 'main' | 'sub',
  color?: string,
  title?: Array<string | any> | string,
  children?: Array<string | any> | string,
  path?: string
}